package day5;

import utils.ReadFiles;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day5 {

    private static List<Seed> seeds;
    private static List<Seed> seeds2;
    private static List<Range> sourceList = new ArrayList<>();
    private static List<Range> destinationList = new ArrayList<>();
    public static void main(String ... args) {

        var lines = ReadFiles.getInputData("day5/input.txt");
        seeds = Arrays.stream(lines.remove(0).split(": ")[1].split(" ")).map(n -> new Seed(Long.valueOf(n))).collect(Collectors.toList());
        lines.remove(0);
        while(!lines.isEmpty()) {
            List<String> instructions = new ArrayList<>();
            String instruction;
            while(!lines.isEmpty() && !(instruction = lines.remove(0)).equals(""))
                instructions.add(instruction.replace(" map:",""));
            var mapType = almanacInstructionsToRangeList(instructions);
            for(Seed seed : seeds) {
                updateSeedValues(seed, mapType);
            }
        }

        System.out.println(String.format("Part 1: %d", seeds.stream().min(Comparator.comparing(Seed::getLocation)).get().getLocation()));
    }

    private static String[] almanacInstructionsToRangeList(List<String> instructions) {

        String[] type = instructions.remove(0).split("-to-");
        sourceList = new ArrayList<>();
        destinationList = new ArrayList<>();

        while(!instructions.isEmpty()) {
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
        int i=0;
        boolean found = false;
        while(i < sourceList.size() && !found) {
            if(sourceList.get(i).contains(k)) {
                found = true;
                Long delta = sourceList.get(i).getValueDelta(k);
                value = destinationList.get(i).getValueAtDelta(delta);
            } else {
                i++;
            }
        }
        return found? value : k;
    }

    private static List<Seed> getSeeds2ndPart() {

        List<Seed> newSeeds = new ArrayList<>();
        int i=0;
        while(i < seeds.size()) {
            Long elements = seeds.get(i+1).getId();
            for(long l=seeds.get(i).getId(); l<elements;l++) {
                newSeeds.add(new Seed(l));
            }
            i+=2;
        }
        return newSeeds;
    }

}
