package day5;

public class Range {

    Long start;
    Long end;

    Range(Long start, Long end) {

        this.start = start;
        this.end = end;
    }

    public boolean contains(Long value) {
        return value >= start && value < end;
    }

    public Long getValueDelta(Long value) {

        return value - start;
    }

    public Long getValueAtDelta(Long delta) {
        return start + delta;
    }
}
