package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDayLong;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

public class AOCDay20 implements AOCDayLong {
    private enum PULSE {
        HIGH,
        LOW;
    }

    private abstract class Module {
        protected final String name;

        public Module(final String name) {
            this.name = name;
        }

        public abstract Optional<PULSE> recieve(Module sender, PULSE pulse);

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "%s [name=%s]".formatted(getClass().getSimpleName(), name);
        }
    }

    private class FlipFlopModule extends Module {

        private boolean state = false;

        public FlipFlopModule(final String name) {
            super(name);
        }

        @Override
        public Optional<PULSE> recieve(Module _sender, PULSE pulse) {
            if (PULSE.LOW.equals(pulse)) {
                final boolean currentState = state;
                state = !state;
                return currentState ? Optional.of(PULSE.LOW) : Optional.of(PULSE.HIGH);
            }
            return Optional.empty();
        }

        @Override
        public String toString() {
            return "FlipFlopModule [name=%s, state=%b]".formatted(name, state);
        }
    }

    private class ConjunctionModule extends Module {

        private final Map<String, PULSE> inputState = new HashMap<>();

        public ConjunctionModule(final String name) {
            super(name);
        }

        public void addInputModule(final String input) {
            inputState.put(input, PULSE.LOW);
        }

        @Override
        public Optional<PULSE> recieve(Module sender, PULSE pulse) {
            if (!inputState.containsKey(sender.getName())) {
                throw new RuntimeException("recieved pulse from unregistered input");
            }
            inputState.put(sender.getName(), pulse);

            final boolean isAllHigh =
                    inputState.values().stream().allMatch(p -> p.equals(PULSE.HIGH));
            return isAllHigh ? Optional.of(PULSE.LOW) : Optional.of(PULSE.HIGH);
        }

        @Override
        public String toString() {
            return "ConjunctionModule [name=%s, inputState=%s]"
                    .formatted(name, inputState.toString());
        }
    }

    private class BroadcastModule extends Module {

        public BroadcastModule(final String name) {
            super(name);
        }

        @Override
        public Optional<PULSE> recieve(Module _sender, PULSE pulse) {
            return Optional.of(pulse);
        }
    }

    private class EmptyModule extends Module {

        public EmptyModule(final String name) {
            super(name);
        }

        @Override
        public Optional<PULSE> recieve(Module _sender, PULSE _pulse) {
            return Optional.empty();
        }
    }

    private record State(Module sender, Module reciever, PULSE pulse) {}
    ;

    @Override
    public long part1(final List<String> input) {
        final int BUTTON_PUSHES = 1000;
        final Map<String, List<String>> config = new HashMap<>();
        final Map<String, Module> lookup = new HashMap<>();

        for (final String line : input) {
            final String[] data = line.split(" -> ");
            final String name = "broadcaster".equals(data[0]) ? data[0] : data[0].substring(1);
            final List<String> dest = Arrays.stream(data[1].split(", ")).toList();
            config.put(name, dest);

            if (data[0].charAt(0) == '%') {
                lookup.put(name, new FlipFlopModule(name));
            } else if (data[0].charAt(0) == '&') {
                lookup.put(name, new ConjunctionModule(name));
            } else if ("broadcaster".equals(name)) {
                lookup.put(name, new BroadcastModule(name));
            } else {
                lookup.put(name, new EmptyModule(name));
            }
        }

        for (final Entry<String, List<String>> c : config.entrySet()) {
            for (final String name : c.getValue()) {
                if (lookup.containsKey(name)) {
                    if (lookup.get(name) instanceof ConjunctionModule conjunction) {
                        conjunction.addInputModule(c.getKey());
                    }
                } else {
                    lookup.put(name, new EmptyModule(name));
                }
            }
        }

        int numHighPules = 0;
        int numLowPules = 0;

        for (int i = 0; i < BUTTON_PUSHES; i++) {
            final Queue<State> queue = new LinkedList<>();
            queue.add(new State(null, lookup.get("broadcaster"), PULSE.LOW));
            while (!queue.isEmpty()) {
                final State current = queue.remove();

                if (PULSE.LOW.equals(current.pulse())) {
                    numLowPules++;
                } else {
                    numHighPules++;
                }

                final Module reciever = current.reciever();
                final Optional<PULSE> nextPules =
                        reciever.recieve(current.sender(), current.pulse());

                if (nextPules.isPresent()) {
                    for (final String name : config.get(reciever.getName())) {
                        queue.add(new State(reciever, lookup.get(name), nextPules.get()));
                    }
                }
            }
        }
        return numHighPules * numLowPules;
    }

    @Override
    public long part2(final List<String> input) {
        final Map<String, List<String>> config = new HashMap<>();
        final Map<String, Module> lookup = new HashMap<>();

        for (final String line : input) {
            final String[] data = line.split(" -> ");
            final String name = "broadcaster".equals(data[0]) ? data[0] : data[0].substring(1);
            final List<String> dest = Arrays.stream(data[1].split(", ")).toList();
            config.put(name, dest);

            if (data[0].charAt(0) == '%') {
                lookup.put(name, new FlipFlopModule(name));
            } else if (data[0].charAt(0) == '&') {
                lookup.put(name, new ConjunctionModule(name));
            } else if ("broadcaster".equals(name)) {
                lookup.put(name, new BroadcastModule(name));
            } else {
                lookup.put(name, new EmptyModule(name));
            }
        }

        for (final Entry<String, List<String>> c : config.entrySet()) {
            for (final String name : c.getValue()) {
                if (lookup.containsKey(name)) {
                    if (lookup.get(name) instanceof ConjunctionModule conjunction) {
                        conjunction.addInputModule(c.getKey());
                    }
                } else {
                    lookup.put(name, new EmptyModule(name));
                }
            }
        }

        final List<String> feedList =
                config.entrySet().stream()
                        .filter(e -> e.getValue().contains("rx"))
                        .map(Entry::getKey)
                        .toList();
        assert feedList.size() == 1 : "(assumption) expect only one value to feed into module rx";

        final String feed = feedList.getFirst();
        assert lookup.get(feed) instanceof ConjunctionModule
                : "(assumption) expect module feeding rx to be a conjunction module";

        final Map<String, Integer> cycleLength = new HashMap<>();
        final Map<String, Integer> seen =
                config.entrySet().stream()
                        .filter(e -> e.getValue().contains(feed))
                        .collect(Collectors.toMap(Entry::getKey, _e -> 0));

        int buttonPresses = 0;
        while (true) {
            buttonPresses++;

            final Queue<State> queue = new LinkedList<>();
            queue.add(new State(null, lookup.get("broadcaster"), PULSE.LOW));
            while (!queue.isEmpty()) {
                final State current = queue.remove();
                final Module reciever = current.reciever();

                if (feed.equals(reciever.getName()) && PULSE.HIGH.equals(current.pulse())) {
                    final String senderName = current.sender().getName();
                    seen.compute(senderName, (_k, v) -> v + 1);

                    if (!cycleLength.containsKey(senderName)) {
                        cycleLength.put(senderName, buttonPresses);
                    } else {
                        assert buttonPresses == cycleLength.get(senderName) * seen.get(senderName)
                                : "encountered %s outside of expected cycle".formatted(senderName);
                    }

                    if (seen.values().stream().allMatch(n -> n > 0)) {
                        return lcm(cycleLength.values().stream().map(Long::valueOf).toList());
                    }
                }

                final Optional<PULSE> nextPules =
                        reciever.recieve(current.sender(), current.pulse());

                if (nextPules.isPresent()) {
                    for (final String name : config.get(reciever.getName())) {
                        queue.add(new State(reciever, lookup.get(name), nextPules.get()));
                    }
                }
            }
        }
    }

    private long gcd(long a, long b) {
        while (b > 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    private long lcm(List<Long> nums) {
        return nums.stream().skip(1).reduce(nums.getFirst(), (acc, n) -> lcm(acc, n));
    }
}
