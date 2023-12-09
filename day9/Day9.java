package day9;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 {

    private static List<ChainedNumbers> chainedNumbers = new ArrayList<>();

    public static void main(String ... args) {

        var lines = ReadFiles.getInputData("day9/input.txt");
        for(String line : lines) {
            chainedNumbers.add(new ChainedNumbers(line));
        }
        System.out.println(String.format("Part 1: %d",chainedNumbers.stream().map(ChainedNumbers::getFinalNumber).reduce(0,(a,b) -> a + b)));
        System.out.println(String.format("Part 2: %d",chainedNumbers.stream().map(ChainedNumbers::getFirstNumber).reduce(0,(a,b) -> a + b)));
    }
}
