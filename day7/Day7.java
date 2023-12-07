package day7;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day7 {

    public static void main(String... args) {

        var lines = ReadFiles.getInputData("day7/input.txt");
        List<Hand> hands = new ArrayList<>();

        for(String line : lines) {
            String[] info = line.split(" ");
            hands.add(new Hand(info[0],Integer.valueOf(info[1])));
        }
        Collections.sort(hands);
        System.out.println(String.format("Part 1: %d",getTotalPoints(hands)));
        Hand.part = 2;
        Collections.sort(hands);
        System.out.println(String.format("Part 2: %d",getTotalPoints(hands)));
    }

    private static Integer getTotalPoints(List<Hand> handsList) {

        Integer total = 0;

        for(int i=0; i< handsList.size(); i++) {
            total += (i+1) * handsList.get(i).getBid();
        }
        return total;
    }
}
