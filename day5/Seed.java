package day5;

public class Seed {
    private Long id;
    private Long soil;
    private Long fertilizer;
    private Long water;
    private Long light;
    private Long temperature;
    private Long humidity;


    public Long getLocation() {
        return location;
    }

    private Long location;

    public Seed(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setValue(Long v, String type) {

        switch (type) {
            case "soil": this.soil = v;
            break;
            case "fertilizer": this.fertilizer = v;
            break;
            case "water": this.water = v;
            break;
            case "light": this.light = v;
            break;
            case "temperature": this.temperature = v;
            break;
            case "humidity": this.humidity = v;
            break;
            case "location": this.location = v;
            break;
        }
    }

    public Long getValue(String type) {

        return
        switch (type) {
            case "soil": yield  this.soil;
            case "fertilizer": yield this.fertilizer;
            case "water": yield this.water;
            case "light": yield this.light;
            case "temperature": yield this.temperature;
            case "humidity": yield this.humidity;
            case "location": yield this.location;
            default: yield this.id;
        };
    }
}
