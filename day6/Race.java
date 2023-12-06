package day6;

public class Race {

    long time;
    long distance;

    public Race(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getWinningWays() {

        long max = (long) Math.ceil((time + Math.sqrt(Math.pow(time, 2) - 4 * (distance))) / 2);
        long min = (long) Math.ceil((time - Math.sqrt(Math.pow(time, 2) - 4 * (distance + 0.01))) / 2);
        return max - min;
    }
}
