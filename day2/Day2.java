package day2;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;

public class Day2 {
    public static void main(String[] args) {

        List<Integer> possibleGames = new ArrayList<>();
        Colour totalCubes = new Colour(12, 13, 14);

        var lines = ReadFiles.getInputData("day2/input1.txt");
        var totalPower = 0;
        int i=1;
        for(String line : lines) {

            Game game = new Game(line.split(": ")[1]);
            totalPower+=game.getPower();
            if(game.compare1(totalCubes)) possibleGames.add(i);
            i++;
        }
        System.out.println(String.format("Part 1: %d",possibleGames.stream().reduce(0, Integer::sum)));
        System.out.println(String.format("Part 2: %d", totalPower));
    }

}
