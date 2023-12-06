package day6;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 {

    private static final String NUMERIC_PATTERN = "(\\d+)";

    public static void main(String ... args) {

        var lines = ReadFiles.getInputData("day6/input.txt");
        List<Race> races = new ArrayList<>();
        Race finalRace = null;
        int i = 0;
        while(i < lines.size()) {
            Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
            Matcher m = pattern.matcher(lines.get(i));
            int j=0;
            while (m.find()) {
                if(i == 0) races.add(new Race(Long.valueOf(m.group(0))));
                else races.get(j).setDistance(Long.valueOf(m.group(0)));
                j++;
            }
            i++;
        }
        finalRace = new Race(Long.valueOf(lines.get(0).split(":")[1].replace(" ","")));
        finalRace.setDistance(Long.valueOf(lines.get(1).split(":")[1].replace(" ","")));

        System.out.println(String.format("Part 1: %d", races.stream().map(Race::getWinningWays).reduce(1L, (a, b) -> a * b)));
        System.out.println(String.format("Part 2: %d", finalRace.getWinningWays()));
    }
}
