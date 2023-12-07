package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDay;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AOCDay07 implements AOCDay {

    @Override
    public int part1(final List<String> input) {
        enum TYPE {
            HIGH_CARD,
            ONE_PAIR,
            TWO_PAIR,
            THREE_OF_A_KIND,
            FULL_HOUSE,
            FOUR_OF_A_KIND,
            FIVE_OF_A_KIND,
        }

        record Hand(String cards, int bid) implements Comparable<Hand> {
            private TYPE getType() {
                final Map<Character, Integer> count = new HashMap<>();
                for (int i = 0; i < cards.length(); i++) {
                    char c = cards.charAt(i);
                    count.put(c, 1 + count.getOrDefault(c, 0));
                }

                final List<Integer> values = count.values().stream().sorted().toList();
                if (values.equals(List.of(5))) {
                    return TYPE.FIVE_OF_A_KIND;
                } else if (values.equals(List.of(1, 4))) {
                    return TYPE.FOUR_OF_A_KIND;
                } else if (values.equals(List.of(2, 3))) {
                    return TYPE.FULL_HOUSE;
                } else if (values.equals(List.of(1, 1, 3))) {
                    return TYPE.THREE_OF_A_KIND;
                } else if (values.equals(List.of(1, 2, 2))) {
                    return TYPE.TWO_PAIR;
                } else if (values.equals(List.of(1, 1, 1, 2))) {
                    return TYPE.ONE_PAIR;
                }
                return TYPE.HIGH_CARD;
            }

            private int getLabelStrength(char c) {
                return switch (c) {
                    case 'A' -> 13;
                    case 'K' -> 12;
                    case 'Q' -> 11;
                    case 'J' -> 10;
                    case 'T' -> 9;
                    case '9' -> 8;
                    case '8' -> 7;
                    case '7' -> 6;
                    case '6' -> 5;
                    case '5' -> 4;
                    case '4' -> 3;
                    case '3' -> 2;
                    case '2' -> 1;
                    default -> throw new RuntimeException("unknown label");
                };
            }

            public int compareTo(Hand other) {
                final int typeCompare = getType().compareTo(other.getType());
                if (typeCompare != 0) {
                    return typeCompare;
                }

                for (int i = 0; i < cards.length(); i++) {
                    int c1 = getLabelStrength(cards.charAt(i));
                    int c2 = getLabelStrength(other.cards().charAt(i));
                    if (c1 != c2) {
                        return c1 < c2 ? -1 : 1;
                    }
                }
                return 0;
            }
        }

        final List<Hand> hands =
                input.stream()
                        .map(l -> l.split(" "))
                        .map(h -> new Hand(h[0], Integer.valueOf(h[1])))
                        .sorted()
                        .toList();
        return IntStream.rangeClosed(1, hands.size()).map(i -> i * hands.get(i - 1).bid()).sum();
    }

    @Override
    public int part2(final List<String> input) {
        enum TYPE {
            HIGH_CARD,
            ONE_PAIR,
            TWO_PAIR,
            THREE_OF_A_KIND,
            FULL_HOUSE,
            FOUR_OF_A_KIND,
            FIVE_OF_A_KIND,
        }

        record Hand(String cards, int bid) implements Comparable<Hand> {
            private TYPE getType() {
                final Map<Character, Integer> count = new HashMap<>();
                int j = 0;
                for (int i = 0; i < cards.length(); i++) {
                    char c = cards.charAt(i);
                    if (c != 'J') {
                        count.put(c, 1 + count.getOrDefault(c, 0));
                    } else {
                        j++;
                    }
                }

                final List<Integer> values =
                        count.values().stream().sorted().collect(Collectors.toList());
                if (values.size() > 0) {
                    values.set(values.size() - 1, values.get(values.size() - 1) + j);
                } else {
                    values.add(j);
                }

                if (values.equals(List.of(5))) {
                    return TYPE.FIVE_OF_A_KIND;
                } else if (values.equals(List.of(1, 4))) {
                    return TYPE.FOUR_OF_A_KIND;
                } else if (values.equals(List.of(2, 3))) {
                    return TYPE.FULL_HOUSE;
                } else if (values.equals(List.of(1, 1, 3))) {
                    return TYPE.THREE_OF_A_KIND;
                } else if (values.equals(List.of(1, 2, 2))) {
                    return TYPE.TWO_PAIR;
                } else if (values.equals(List.of(1, 1, 1, 2))) {
                    return TYPE.ONE_PAIR;
                }
                return TYPE.HIGH_CARD;
            }

            private int getLabelStrength(char c) {
                return switch (c) {
                    case 'A' -> 12;
                    case 'K' -> 11;
                    case 'Q' -> 10;
                    case 'T' -> 9;
                    case '9' -> 8;
                    case '8' -> 7;
                    case '7' -> 6;
                    case '6' -> 5;
                    case '5' -> 4;
                    case '4' -> 3;
                    case '3' -> 2;
                    case '2' -> 1;
                    case 'J' -> 0;
                    default -> throw new RuntimeException("unknown label");
                };
            }

            public int compareTo(Hand other) {
                final int typeCompare = getType().compareTo(other.getType());
                if (typeCompare != 0) {
                    return typeCompare;
                }

                for (int i = 0; i < cards.length(); i++) {
                    int c1 = getLabelStrength(cards.charAt(i));
                    int c2 = getLabelStrength(other.cards().charAt(i));
                    if (c1 != c2) {
                        return c1 < c2 ? -1 : 1;
                    }
                }
                return 0;
            }
        }

        final List<Hand> hands =
                input.stream()
                        .map(l -> l.split(" "))
                        .map(h -> new Hand(h[0], Integer.valueOf(h[1])))
                        .sorted()
                        .toList();
        return IntStream.rangeClosed(1, hands.size()).map(i -> i * hands.get(i - 1).bid()).sum();
    }
}
