package day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Arrangement {

    private String arrange;

    public String getArrange() {
        return arrange;
    }

    public String getLongArrange() {
        StringBuilder sb = new StringBuilder(this.arrange);
        for(int i=0; i<4; i++) {
            sb.append("?").append(this.arrange);
        }
        return sb.toString();
    }

    public List<Integer> getLongSizes() {
        List<Integer> longSizes = new ArrayList<>();
        for(int i=0; i<5; i++) {
            longSizes.addAll(this.sizes);
        }
        return longSizes;
    }

    public List<Integer> getSizes() {
        return sizes;
    }

    private List<Integer> sizes;

    public Arrangement(String text) {
        var parts = text.split(" ");
        arrange  = parts[0];
        sizes = Arrays.stream(parts[1].split(",")).map(c -> Integer.valueOf(c)).collect(Collectors.toList());
    }
}
