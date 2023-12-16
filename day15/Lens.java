package day15;

public class Lens {
    String label;
    int focalLength;

    public Lens(String label, int focalLength) {
        this.label = label;
        this.focalLength = focalLength;
    }

    public Lens(String[] lensInfo) {
        this.label = lensInfo[0];
        this.focalLength = Integer.parseInt(lensInfo[1]);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        Lens l = (Lens) o;
        return this.label.equals(l.label);
    }
}
