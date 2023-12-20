package day18;

import utils.Point;

public class DiggedCube {

    private Point point;

    public Point getPoint() {
        return point;
    }

    public Integer getColour() {
        return colour;
    }

    private Integer colour;

    public DiggedCube(Point point, Integer colour) {
        this.colour = colour;
        this.point = point;
    }
}
