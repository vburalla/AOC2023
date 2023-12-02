package day2;

public class Colour {

    private int r;
    private int g;
    private int b;

    public Colour(int r,int g,int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Colour(String text) {

        var cubes = text.split(",");
        for(String cube : cubes) {
            if(cube.contains("red")) {
                this.r = Integer.valueOf(cube.replaceAll("\\D",""));
            } else if(cube.contains("green")) {
                this.g = Integer.valueOf(cube.replaceAll("\\D",""));
            } else {
                this.b = Integer.valueOf(cube.replaceAll("\\D",""));
            }
        }
    }

    public Colour() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public int power() {
        return this.r * this.g * this.b;
    }

    public int getRed() {
        return this.r;
    }

    public int getBlue() {
        return this.b;
    }

    public int getGreen() {
        return this.g;
    }

    public void setRed(int r) {
        this.r = r;
    }

    public void setGreen(int g) {
        this.g = g;
    }

    public void setBlue(int b) {
        this.b = b;
    }

}
