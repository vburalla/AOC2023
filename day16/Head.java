package day16;

import utils.Point;

import java.util.Objects;

public class Head {
    Point point;
    Point direction;

    public Head(Point point, Point direction) {
        this.point = point;
        this.direction = direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, direction);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        Head head = (Head) obj;
        return this.direction.equals(head.direction) && this.point.equals(head.point);
    }
}
