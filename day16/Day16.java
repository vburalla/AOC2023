package day16;

import utils.Point;
import utils.ReadFiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day16 {

    static char[][] matrix;
    static List<Head> currentHeads = new ArrayList<>();
    static List<Head> newHeads = new ArrayList<>();
    static Set<Head> visitedPoints = new HashSet<>();
    static Set<Point> uniquePoints = new HashSet<>();

    public static void main(String... args) {

        createMatrix(ReadFiles.getInputData("day16/input.txt"));
        System.out.println(String.format("Part 1: %d", energize(new Head(new Point(0,0), new Point(0, 1)))));
        Integer max = 0;
        for(Head head : createPossiblePoints()) {
            Integer value = energize(head);
            if(value > max)
                max = value;
        }
        System.out.println(String.format("Part 2: %d", max));
    }

    private static List<Head> getNewHeads(Point currentDirection, Point currentPoint) {
        char c = matrix[currentPoint.getRow()][currentPoint.getColumn()];
        List<Head> newHeads = new ArrayList<>();
        switch (c) {
            case '\\' -> {
                Point direction = new Point(currentDirection.getColumn(), currentDirection.getRow());
                addNewHead(newHeads, currentPoint, direction);
            }
            case '/' -> {
                Point direction = new Point(-1 * currentDirection.getColumn(), -1 * currentDirection.getRow());
                addNewHead(newHeads, currentPoint, direction);
            }
            case '-' -> {
                if (currentDirection.getRow() == 0)
                    addNewHead(newHeads, currentPoint, currentDirection);
                else {
                    Point direction1 = new Point(currentDirection.getColumn(), currentDirection.getRow());
                    addNewHead(newHeads, currentPoint, direction1);
                    Point direction2 = new Point(currentDirection.getColumn(), -1 * currentDirection.getRow());
                    addNewHead(newHeads, currentPoint, direction2);
                }
            }
            case '|' -> {
                if (currentDirection.getColumn() == 0)
                    addNewHead(newHeads, currentPoint, currentDirection);
                else {
                    Point direction1 = new Point(currentDirection.getColumn(), currentDirection.getRow());
                    addNewHead(newHeads, currentPoint, direction1);
                    Point direction2 = new Point(-1 * currentDirection.getColumn(), currentDirection.getRow());
                    addNewHead(newHeads, currentPoint, direction2);
                }
            }
            default -> addNewHead(newHeads, currentPoint, currentDirection);
        }
        return newHeads;
    }

    private static void addNewHead(List<Head> heads, Point point, Point direction) {
        Point newPoint = movePoint(point, direction);
        if (isInMatrix(newPoint))
            heads.add(new Head(newPoint, direction));
    }

    private static void createMatrix(List<String> lines) {

        matrix = new char[lines.size()][];
        int i = 0;
        for (String line : lines) {
            matrix[i] = line.toCharArray();
            i++;
        }
    }

    private static Point movePoint(Point point1, Point direction) {

        return new Point(point1.getRow() + direction.getRow(), point1.getColumn() + direction.getColumn());
    }

    private static boolean isInMatrix(Point point) {

        return point.getRow() >= 0 && point.getColumn() >= 0 && point.getRow() < matrix.length && point.getColumn() < matrix[0].length;
    }

    private static Integer energize(Head startHead) {
        currentHeads.clear();
        newHeads.clear();
        visitedPoints.clear();
        uniquePoints.clear();

        currentHeads.add(startHead);
        do {
            for (Head head : currentHeads) {
                visitedPoints.add(head);
                uniquePoints.add(head.point);
                newHeads.addAll(getNewHeads(head.direction, head.point));
            }
            currentHeads = newHeads.stream().collect(Collectors.toList());
            newHeads.clear();
        } while (!currentHeads.stream().allMatch(h -> visitedPoints.contains(h)));
        return uniquePoints.size();
    }

    private static List<Head> createPossiblePoints() {
        List<Head> points = new ArrayList<>();
        for(int i = 0; i < matrix[0].length; i++) {
            points.add(new Head(new Point(0, i), new Point(1,0)));
            points.add(new Head(new Point(matrix.length - 1, i), new Point(-1, 0)));
        }
        for(int i = 0; i < matrix.length; i++) {
            points.add(new Head(new Point(i, 0),new Point(0, 1)));
            points.add(new Head(new Point(i,matrix[0].length - 1), new Point(0, -1)));
        }
        return points;
    }

}
