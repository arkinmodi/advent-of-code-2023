package com.arkinmodi.adventofcode.days;

import com.arkinmodi.adventofcode.AOCDayLong;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AOCDay24 implements AOCDayLong {

    @Override
    public long part1(final List<String> input) {
        return part1(input, 200_000_000_000_000L, 400_000_000_000_000L);
    }

    public int part1(final List<String> input, final long lowerBound, final long upperBound) {
        record Hailstone(
                long px, long py, long pz, long vx, long vy, long vz, long a, long b, long c) {}
        ;

        final List<Hailstone> hailstones = new ArrayList<>();
        for (final String line : input) {
            final List<Long> data =
                    Arrays.stream(line.replace('@', ',').split(","))
                            .map(String::strip)
                            .map(Long::valueOf)
                            .toList();

            final long px = data.get(0);
            final long py = data.get(1);
            final long pz = data.get(2);
            final long vx = data.get(3);
            final long vy = data.get(4);
            final long vz = data.get(5);

            final long a = vy;
            final long b = -vx;
            final long c = vy * px - vx * py;

            hailstones.add(new Hailstone(px, py, pz, vx, vy, vz, a, b, c));
        }

        int total = 0;
        for (int i = 0; i < hailstones.size(); i++) {
            final double a1 = hailstones.get(i).a();
            final double b1 = hailstones.get(i).b();
            final double c1 = hailstones.get(i).c();

            for (int j = 0; j < i; j++) {
                final double a2 = hailstones.get(j).a();
                final double b2 = hailstones.get(j).b();
                final double c2 = hailstones.get(j).c();

                if (a1 * b2 == b1 * a2) {
                    continue;
                }

                final double x = (c1 * b2 - c2 * b1) / (a1 * b2 - a2 * b1);
                final double y = (c2 * a1 - c1 * a2) / (a1 * b2 - a2 * b1);

                if (lowerBound <= x && x <= upperBound && lowerBound <= y && y <= upperBound) {
                    final boolean allMatch =
                            Stream.of(hailstones.get(i), hailstones.get(j))
                                    .allMatch(
                                            hs ->
                                                    (x - hs.px()) * hs.vx() >= 0
                                                            && (y - hs.py()) * hs.vy() >= 0);
                    if (allMatch) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

    @Override
    @SuppressWarnings("unchecked")
    public long part2(final List<String> input) {
        record Hailstone(long px, long py, long pz, long vx, long vy, long vz) {}
        ;

        final List<Hailstone> hailstones = new ArrayList<>();
        for (final String line : input) {
            final List<Long> data =
                    Arrays.stream(line.replace('@', ',').split(","))
                            .map(String::strip)
                            .map(Long::valueOf)
                            .toList();

            final long px = data.get(0);
            final long py = data.get(1);
            final long pz = data.get(2);
            final long vx = data.get(3);
            final long vy = data.get(4);
            final long vz = data.get(5);

            hailstones.add(new Hailstone(px, py, pz, vx, vy, vz));
        }

        try (final Context ctx = new Context()) {
            final Solver solver = ctx.mkSolver();

            final IntExpr rockPX = ctx.mkIntConst("rockPX");
            final IntExpr rockPY = ctx.mkIntConst("rockPY");
            final IntExpr rockPZ = ctx.mkIntConst("rockPZ");
            final IntExpr rockVX = ctx.mkIntConst("rockVX");
            final IntExpr rockVY = ctx.mkIntConst("rockVY");
            final IntExpr rockVZ = ctx.mkIntConst("rockVZ");

            for (int i = 0; i < hailstones.size(); i++) {
                final Hailstone hs = hailstones.get(i);
                solver.add(
                        ctx.mkEq(
                                ctx.mkMul(
                                        ctx.mkSub(rockPX, ctx.mkInt(hs.px())),
                                        ctx.mkSub(ctx.mkInt(hs.vy()), rockVY)),
                                ctx.mkMul(
                                        ctx.mkSub(rockPY, ctx.mkInt(hs.py())),
                                        ctx.mkSub(ctx.mkInt(hs.vx()), rockVX))));

                solver.add(
                        ctx.mkEq(
                                ctx.mkMul(
                                        ctx.mkSub(rockPY, ctx.mkInt(hs.py())),
                                        ctx.mkSub(ctx.mkInt(hs.vz()), rockVZ)),
                                ctx.mkMul(
                                        ctx.mkSub(rockPZ, ctx.mkInt(hs.pz())),
                                        ctx.mkSub(ctx.mkInt(hs.vy()), rockVY))));
            }

            final Status status = solver.check();
            assert status.equals(Status.SATISFIABLE) : "z3 failed to find a solution";

            final Model model = solver.getModel();
            final long x = Long.valueOf(model.eval(rockPX, false).toString());
            final long y = Long.valueOf(model.eval(rockPY, false).toString());
            final long z = Long.valueOf(model.eval(rockPZ, false).toString());

            return x + y + z;
        }
    }
}
