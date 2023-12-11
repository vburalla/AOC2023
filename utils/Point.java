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
}
