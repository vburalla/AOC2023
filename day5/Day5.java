package day5;

import utils.ReadFiles;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    private static List<Seed> seeds;
    private static List<Range> seeds2;
    private static List<Range> sourceList = new ArrayList<>();
    private static List<Range> destinationList = new ArrayList<>();

    public static void main(String... args) {

        var lines = ReadFiles.getInputData("day5/input.txt");
        var seedLine = lines.remove(0).split(": ")[1].split(" ");
        seeds = Arrays.stream(seedLine).map(n -> new Seed(Long.valueOf(n))).collect(Collectors.toList());
        seeds2 = getSeeds2(Arrays.stream(seedLine).map(Long::valueOf).collect(Collectors.toList()));
        lines.remove(0);
        while (!lines.isEmpty()) {
            List<String> instructions = new ArrayList<>();
            String instruction;
            while (!lines.isEmpty() && !(instruction = lines.remove(0)).equals(""))
                instructions.add(instruction.replace(" map:", ""));
            var mapType = almanacInstructionsToRangeList(instructions);
            for (Seed seed : seeds) {
                updateSeedValues(seed, mapType);
            }
            seeds2 = reArrangeConversions(seeds2);
        }

        System.out.println(String.format("Part 1: %d", seeds.stream().min(Comparator.comparing(Seed::getLocation)).get().getLocation()));
        System.out.println(String.format("Part 2: %d", seeds2.stream().min(Comparator.comparing(Range::getStart)).get().getStart()));
    }

    private static String[] almanacInstructionsToRangeList(List<String> instructions) {

        String[] type = instructions.remove(0).split("-to-");
        sourceList = new ArrayList<>();
        destinationList = new ArrayList<>();

        while (!instructions.isEmpty()) {
            String[] ins = instructions.remove(0).split(" ");
            sourceList.add(new Range(Long.valueOf(ins[1]), Long.valueOf(ins[1]) + Long.valueOf(ins[2])));
            destinationList.add(new Range(Long.valueOf(ins[0]), Long.valueOf(ins[0]) + Long.valueOf(ins[2])));
        }
        return type;
    }

    private static void updateSeedValues(Seed s, String[] type) {

        String source = type[0];
        String destination = type[1];

        Long value = getValueInRanges(s.getValue(source));
        s.setValue(value, destination);
    }

    private static Long getValueInRanges(Long k) {
        Long value = null;
        int i = 0;
        boolean found = false;
        while (i < sourceList.size() && !found) {
            if (sourceList.get(i).contains(k)) {
                found = true;
                Long delta = sourceList.get(i).getValueDelta(k);
                value = destinationList.get(i).getValueAtDelta(delta);
            } else {
                i++;
            }
        }
        return found ? value : k;
    }

    private static List<Range> getSeeds2(List<Long> seedsDefinition) {

        List<Range> newSeeds = new ArrayList<>();
        int i = 0;
        while (i < seedsDefinition.size()) {
            Long elements = seedsDefinition.get(i + 1);
            newSeeds.add(new Range(seedsDefinition.get(i), seedsDefinition.get(i) + seedsDefinition.get(i + 1)));
            i += 2;
        }
        return newSeeds;
    }

    private static List<Range> reArrangeConversions(List<Range> currentRanges) {

        List<Range> newSeeds = new ArrayList<>();
        Long delta = 0L;
        for (Range r : currentRanges) {
            int i = 0;
            boolean found = false;
            while (!found && i < sourceList.size()) {
                Range sourceRange = sourceList.get(i);
                if (sourceRange.containsRange(r)) {
                    Range destinationRange = destinationList.get(i);
                    found = true;
                    delta = r.start - sourceRange.start;
                    newSeeds.add(new Range(destinationRange.start + delta, destinationRange.start + delta + r.length()));
                } else if (r.containsRange(sourceRange)) {
                    Range destinationRange = destinationList.get(i);
                    newSeeds.add(destinationRange);
                    List<Range> outBounds = new ArrayList<>();
                    if (r.start < sourceRange.start)
                        outBounds.add(new Range(r.start, sourceRange.start));
                    if (r.end > sourceRange.end)
                        outBounds.add(new Range(sourceRange.end, r.end));
                    newSeeds.addAll(reArrangeConversions(outBounds));
                    found = true;
                } else {
                    if ((sourceRange.intersects(r))) {
                        Range destinationRange = destinationList.get(i);
                        if ((delta = r.start - sourceRange.start) < 0) {
                            newSeeds.add(new Range(destinationRange.start, destinationRange.start + r.length() + delta));
                            r.end = sourceRange.start;
                        } else {
                            newSeeds.add(new Range(destinationRange.start + delta, destinationRange.end));
                            r.start = sourceRange.end;
                        }
                    }
                    i++;
                }
            }
            if (!found) {
                newSeeds.add(r);
            }
        }
        return newSeeds;
    }

}
