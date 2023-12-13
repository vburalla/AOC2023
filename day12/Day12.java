package day12;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;

public class Day12 {

    public static void main(String... args) {

        var lines = ReadFiles.getInputData("day12/input.txt");
        List<Arrangement> arrangements = new ArrayList<>();
        Long solutions = 0L;
        Long solutions2 = 0L;

        for (String line : lines) {
            Arrangement arrangement = new Arrangement(line);
            solutions += getPossibleSolutions(arrangement.getArrange()+".", arrangement.getSizes(), 0);
            solutions2 += getPossibleSolutions(arrangement.getLongArrange()+".", arrangement.getLongSizes(), 0);
        }
        System.out.println(solutions);
        System.out.println(solutions2);
    }

    private static Long getPossibleSolutions(String remainingArrange, List<Integer> remainingSizes, Integer builtGroups) {

        if (remainingArrange == null || remainingArrange.equals(""))
            return remainingSizes.isEmpty() && remainingArrange.equals("") ? 1L : 0L;

        Long solutions = 0L;

        var options = (remainingArrange.charAt(0) == '?') ? List.of('.', '#') : List.of(remainingArrange.charAt(0));
        for (char c : options) {
            if (c == '#')
                solutions += getPossibleSolutions(remainingArrange.substring(1), remainingSizes, builtGroups + 1);
            else {
                if (builtGroups != null && builtGroups > 0) {
                    if (!remainingSizes.isEmpty() && remainingSizes.get(0).equals(builtGroups)) {
                        List<Integer> sublist = remainingSizes.subList(1, remainingSizes.size());
                        solutions += getPossibleSolutions(remainingArrange.substring(1), sublist, 0);
                    }
                } else
                    solutions += getPossibleSolutions(remainingArrange.substring(1), remainingSizes, 0);
            }
        }
        return solutions;
    }

}
