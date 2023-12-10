package day10;

import utils.Point;
import utils.ReadFiles;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Day10 {

    private static char[][] matrix;

    private static Point lastVisitedPoint;
    private static Point currentPoint;
    private static LinkedList<Point> visitedPoints = new LinkedList<>();

    private static Set<Point> insidePoints = new HashSet<>();

    public static void main(String ... args) throws IOException {

        var lines = ReadFiles.getInputData("day10/input.txt");
        matrix = new char[lines.size()][];
        int i = 0;
        int col = 0;
        Point startPosition = null;
        lastVisitedPoint = startPosition;

        for (String line : lines) {
            if((col = line.indexOf('S')) >= 0)
                startPosition = new Point(i,col );
            matrix[i] = line.toCharArray();
            i++;
        }
        currentPoint = findFirstValidPath(startPosition);
        while(matrix[currentPoint.getRow()][currentPoint.getColumn()] != 'S') {
            visitedPoints.add(currentPoint);
            currentPoint = findFirstValidPath(currentPoint);
        }
        visitedPoints.add(currentPoint);
        System.out.println(String.format("Part 1: %d", (visitedPoints.size() / 2)));
        cleanMatrix();
        scanPoints();
        System.out.println(insidePoints.size());
    }

    private static Point findFirstValidPath(Point point) {

        char c = matrix[point.getRow()][point.getColumn()];
        Point neighbour = null;
        if((c=='S' || c=='F' || c=='7' || c == '|') && point.getRow() < matrix.length-1) {
            char c2 = matrix[point.getRow() + 1][point.getColumn()];
            if(c2 == '|' || c2 == 'J' || c2 == 'L' || c2 == 'S') {
                Point nextPoint = new Point(point.getRow() + 1, point.getColumn());
                if(!nextPoint.equals(lastVisitedPoint)) {
                    neighbour = nextPoint;
                    lastVisitedPoint = point;
                }
            }
        }
        if(neighbour == null && (c == 'S' || c == '|' || c == 'J' || c == 'L') && point.getRow() > 0) {
            char c2 = matrix[point.getRow() - 1][point.getColumn()];
            if(c2 == '|' || c2 == '7' || c2 == 'F' || c2 == 'S') {
                Point nextPoint = new Point(point.getRow() - 1, point.getColumn());
                if(!nextPoint.equals(lastVisitedPoint)) {
                    neighbour = nextPoint;
                    lastVisitedPoint = point;
                }
            }
        }
        if(neighbour == null && (c == '-' || c == 'S' || c == 'J' || c == '7') && point.getColumn() > 0) {
            char c2 = matrix[point.getRow()][point.getColumn() - 1];
            if(c2 == '-' || c2 == 'S' || c2 == 'F' || c2 == 'L') {
                Point nextPoint = new Point(point.getRow(), point.getColumn() - 1);
                if(!nextPoint.equals(lastVisitedPoint)) {
                    neighbour = nextPoint;
                    lastVisitedPoint = point;
                }
            }
        }
        if(neighbour == null && (c == '-' || c == 'S' || c == 'L' || c == 'F') && point.getColumn() < matrix[0].length-1) {
            char c2 = matrix[point.getRow()][point.getColumn() + 1];
            if(c2 == '-' || c2 == 'S' || c2 == 'J' || c2 == '7') {
                Point nextPoint = new Point(point.getRow(), point.getColumn() + 1);
                if(!nextPoint.equals(lastVisitedPoint)) {
                    neighbour = nextPoint;
                    lastVisitedPoint = point;
                }
            }
        }
        if(neighbour == null) {
            System.out.println();
        }
        return neighbour;
    }

    private static void cleanMatrix() {

        for(int i=0; i<matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                Point point = new Point(i, j);
                if(!visitedPoints.contains(point)) {
                    matrix[i][j] = '.';
                }

            }
        }
    }

    private static void scanPoints() {

        for(int i=0; i<matrix.length; i++) {
            int intersections = 0;
            char c1 = '.';
            for(int j=0; j<matrix[i].length;j++) {
                char c = matrix[i][j];
                intersections = switch (c) {
                    case '7'-> c1 == 'L'? intersections : intersections + 1;
                    case 'J'-> c1 == 'F'? intersections : intersections + 1;
                    case 'L','|','F','S'-> intersections + 1;
                    case '.'->  {
                        if(!isOdd(intersections))
                            insidePoints.add(new Point(i,j));
                        yield intersections;
                    }
                    default->  intersections;
                };
                c1 = c != '-'? c : c1;
            }
        }
    }

    private static boolean isOdd(int value) {

        return value % 2 == 0;
    }

}
