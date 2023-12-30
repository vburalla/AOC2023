package utils;

import java.util.Objects;

public class Point {

    int row;

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    int column;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void add(Point point) {

        this.row += point.getRow();
        this.column += point.getColumn();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return row == point.row && column == point.column;
    }

    @Override
    public String toString() {
        return "Point{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public Long getManhattan(Point point) {
        return Math.abs((long) this.getColumn() - (long) point.getColumn()) + Math.abs((long) this.getRow() - (long) point.getRow());
    }

    public static Point movePoint(Point point1, Point direction) {

        return new Point(point1.getRow() + direction.getRow(), point1.getColumn() + direction.getColumn());
    }

    public Point move(Point direction) {

        this.row = this.row + direction.row;
        this.column = this.column + direction.column;
        return this;
    }

    public static Point moveSteps(Point point, Integer steps) {
        return new Point(point.row * steps, point.column * steps);
    }

    public static Point rotateLeft(Point point) {
        Point newPoint = new Point(0,0);
        if(point.row == 0)
            newPoint.row = -1 * point.column;
        else if(point.column == 0)
            newPoint.column = point.row;
        return newPoint;
    }

    public static Point rotateRight(Point point) {
        Point newPoint = new Point(0, 0);
        if(point.row == 0)
            newPoint.row = point.column;
        else if(point.column == 0)
            newPoint.column = -1 * point.row;
        return newPoint;
    }

    public Point copy() {
        return new Point(this.row, this.column);
    }
}
