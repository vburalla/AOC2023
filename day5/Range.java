package day5;

public class Range {

    Long start;
    Long end;

    public Long getStart() {
        return start;
    }

    Range(Long start, Long end) {

        this.start = start;
        this.end = end;
    }

    public boolean contains(Long value) {
        return value >= start && value < end;
    }

    public boolean containsRange(Range range) {
        return this.start <= range.start && this.end >= range.end;
    }

    public boolean intersects(Range range) {
        return ((range.start >= this.start && range.start < this.end) || (range.end <= this.end && range.end > this.start));
    }

    public Long getValueDelta(Long value) {

        return value - start;
    }


    public Long length() {
        return end - start;
    }

    public Long getValueAtDelta(Long delta) {
        return start + delta;
    }
}
