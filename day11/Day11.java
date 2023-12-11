package day11;

import utils.Point;
import utils.ReadFiles;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Day11 {

    private static List<Point> points = new ArrayList<>();
    private static Integer expanse;
    public static void main (String ... args) {

        part1();
        part2();
    }

    private static void part1() {
        expanse = 2;
        expandColumns(getEmptyColumnsAndPoints(ReadFiles.getInputData("day11/input.txt")));
        System.out.println(String.format("Part 1: %d", calculateDistance()));
    }

    private static void part2() {
        expanse = 1000000;
        expandColumns(getEmptyColumnsAndPoints(ReadFiles.getInputData("day11/input.txt")));
        System.out.println(String.format("Part 2: %d", calculateDistance()));
    }

    private static BitSet getLineAsBitSet(String line) {
        BitSet bitSet = new BitSet(line.length());
        int i = 0;
        for(char c : line.toCharArray()) {
            if(c == '#') bitSet.set(i);
            i++;
        }
        return bitSet;
    }

    private static Long calculateDistance() {
        Long totalDistance = 0L;
        while(points.size() > 1) {
            Point point = points.remove(0);
            for(Point p : points) 
                totalDistance += point.getManhattan(p);
        }
        return totalDistance;
    }

    private static List<Integer> getEmptyColumnsAndPoints(List<String> lines) {

        int i = 0;
        BitSet emptyColumns = new BitSet(lines.get(0).length());
        for(String line : lines) {
            BitSet bLine = getLineAsBitSet(line);
            if(bLine.cardinality() == 0)
                i+=expanse - 1;
            else {
                for(Integer p : bLine.stream().mapToObj(Integer::valueOf).collect(Collectors.toList()))
                    points.add(new Point(i, p));
            }
            i++;
            emptyColumns.or(bLine);
        }
        emptyColumns.flip(0, emptyColumns.length());
        return emptyColumns.stream().mapToObj(Integer::valueOf).collect(Collectors.toList());
    }

    private static void expandColumns(List<Integer> emptyCols) {

        int leftExpandedCols = 0;
        for(Point point : points) {
            int col = point.getColumn();

            if((leftExpandedCols = emptyCols.stream().filter(v -> v <= col).collect(Collectors.toList()).size()) > 0) {
                point.setColumn(col + (leftExpandedCols * (expanse - 1)));
            }
        }
    }

}
