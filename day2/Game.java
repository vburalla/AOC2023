package day2;

import java.util.ArrayList;
import java.util.List;

public class Game {

    List<Colour> sets = new ArrayList<>();

    public Game(String text) {
        var textSets = text.split(";");
        for(String set : textSets) {
            this.sets.add(new Colour(set));
        }
    }

    public boolean compare1(Colour colour) {

        return sets.stream().allMatch(c -> {
            return c.getRed() <= colour.getRed() &&
                    c.getGreen() <= colour.getGreen() &&
                    c.getBlue() <= colour.getBlue();
        });
    }

    public int getPower() {

        Colour max = new Colour();
        for(Colour set : sets) {

            if(max.getRed() < set.getRed()) max.setRed(set.getRed());
            if(max.getGreen() < set.getGreen()) max.setGreen(set.getGreen());
            if(max.getBlue() < set.getBlue()) max.setBlue(set.getBlue());
        }

        return max.power();
    }
}
