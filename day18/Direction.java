package day18;

import utils.Point;

public enum Direction {

    R(new Point(0, 1)), L(new Point(0, -1)), U(new Point(-1, 0)), D(new Point(1, 0));

    private Point direction;
    private Direction(Point direction) {
        this.direction = direction;
    }

    public Point getDirection() {
        return this.direction;
    }

    public Point getEncodedDirection(String code) {
        return switch (code) {
            case "0" -> R.getDirection();
            case "1" -> D.getDirection();
            case "2" -> L.getDirection();
            case "3" -> U.getDirection();
            default -> throw new IllegalStateException("Unexpected value: " + code);
        };
    }
}
