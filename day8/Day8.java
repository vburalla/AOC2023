package day8;

import utils.ReadFiles;
import utils.CarouselList;
import utils.NumberUtils;

import java.util.*;
import java.util.stream.Collectors;


public class Day8 {

    private static Map<String, List<String>> paths = new HashMap<>();
    private static List<String> sortedPaths = new ArrayList<>();
    private static final String DESTINATION_PATH = "ZZZ";
    private static CarouselList<Integer> instructions;

    public static void main(String ... args) {

        var lines = ReadFiles.getInputData("day8/input.txt");
        var z = lines.remove(0).chars().mapToObj(c -> c == 'R'? 1 : 0).collect(Collectors.toList());
        instructions = new CarouselList(z);
        lines.remove(0);
        while(!lines.isEmpty()) {
            addPath(lines.remove(0));
        }
        instructions.reset();
        part1();
        part2();
    }

    private static void part1() {
        String currentPath = "AAA";
        int i =0;
        while(!currentPath.equals(DESTINATION_PATH)) {
            var p = paths.get(currentPath);
            currentPath = p.get(instructions.getNext());
            i++;
        }
        System.out.println(String.format("Part 1: %d", i));
    }

    private static void part2() {
        List<String> originNodes = sortedPaths.stream().filter(s -> s.endsWith("A")).collect(Collectors.toList());
        List<Integer> cycles = new ArrayList<>();

        for(String originNode : originNodes) {
            instructions.reset();
            cycles.add(findPathToDestination(originNode));
        }
        Integer[] a = cycles.toArray(Integer[] ::new);
        System.out.println(String.format("Part 2: %d", NumberUtils.lcm(a)));
    }

    private static Integer findPathToDestination(String originNode) {

        String destinationNode = "AAA";
        int i=0;
        while(!destinationNode.endsWith("Z")) {
            var c = instructions.getNext();
            var p = paths.get(originNode);
            destinationNode = p.get(c);
            originNode = destinationNode;
            i++;
        }
        return i;
    }

    private static void addPath(String line) {

        var data = line.split(" = ");
        String key = data[0];
        List<String> values = Arrays.asList(data[1].replace("(","").replace(")","").split(", "));
        paths.put(key, values);
        sortedPaths.add(key);
    }
}
