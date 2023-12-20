package day18;

import utils.Point;
import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;

public class Day18 {

    private static List<Point> boundaries = new ArrayList<>();
    private static Point point = new Point(0,0);
    private static Long boundaryPoints = 0L;

    public static void main(String... args) {

        var lines = ReadFiles.getInputData("day18/input.txt");
        lines.stream().forEach(t -> dig(t, 1));
        System.out.println(getArea());
        resetValues();
        lines.stream().forEach(t -> dig(t, 2));
        System.out.println(getArea());
    }

    private static void dig(String text, int part) {

        var instruction = text.split(" ");
        String colour = instruction[2].substring(2,instruction[2].length()-1);

        Point direction = (part ==1)? Direction.valueOf(instruction[0]).getDirection() : Direction.D.getEncodedDirection(colour.substring(colour.length()-1));
        Integer meters = (part == 1)? Integer.parseInt(instruction[1]) : Integer.parseInt(colour.substring(0,colour.length()-1), 16);

        boundaries.add(point.move(Point.moveSteps(direction, meters)).copy());
        boundaryPoints+=meters;
    }

    private static Long getArea() {

        Long result1 = 0L;
        Long result2 = 0L;
        Point p1 = boundaries.remove(boundaries.size()-1);
        while(!boundaries.isEmpty()) {
            Point p2 = boundaries.remove(0);

            result1+=  (long) p2.getRow() * (long) p1.getColumn();
            result2+= (long) p1.getRow() * (long) p2.getColumn();
            p1 = p2;
        }
        return Math.abs(result1 - result2)/2 + (boundaryPoints / 2) + 1;
    }

    private static void resetValues() {
        boundaryPoints = 0L;
        boundaries.clear();
        point = new Point(0,0);
    }
}
