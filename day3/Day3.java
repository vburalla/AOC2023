package day3;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    private static final String NUMERIC_PATTERN = "(\\d+)";
    private static final String SYMBOL_PATTERN = "[\\d\\.]";
    private static List<String> lines;

    public static void main(String[] args) {

        lines = ReadFiles.getInputData("day3/input.txt");
        Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
        Pattern gearPattern = Pattern.compile("\\*");
        List<Integer> validNumbers = new ArrayList<>();
        List<Integer> gearRatios = new ArrayList<>();
        Integer gearRatio = 0;

        for (int i = 0; i < lines.size(); i++) {
            Matcher matcher = pattern.matcher(lines.get(i));
            Matcher gearMatcher = gearPattern.matcher(lines.get(i));
            while (matcher.find()) {
                var number = matcher.group(0);
                if (findNeighbourSymbol(number, matcher.start(), matcher.end(), i))
                    validNumbers.add(Integer.valueOf(number));
            }
            while(gearMatcher.find()) {
                if((gearRatio = findGearNeighbourNumbers(gearMatcher.start(), i)) != 0)
                    gearRatios.add(gearRatio);
            }
        }
        System.out.println(String.format("Part 1: %d", validNumbers.stream().reduce(0, Integer::sum)));
        System.out.println(String.format("Part 2: %d", gearRatios.stream().reduce(0, Integer::sum)));
    }

    private static boolean findNeighbourSymbol(String number, int start, int end, int lineNumber) {

        int startRow = lineNumber == 0 ? lineNumber : lineNumber - 1;
        int endRow = lineNumber == lines.size() - 1 ? lineNumber : lineNumber + 1;
        int startPosition = start == 0 ? start : start - 1;
        int endPosition = end >= lines.get(0).length() - 1 ? end : end + 1;

        boolean symbolFound = false;
        int i = startRow;
        while (!symbolFound && i <= endRow) {
            symbolFound = lines.get(i).substring(startPosition, endPosition).replaceAll(SYMBOL_PATTERN, "").isEmpty();
            i++;
        }
        return symbolFound;
    }

    private static Integer findGearNeighbourNumbers(int start, int lineNumber) {

        int startRow = lineNumber == 0 ? lineNumber : lineNumber - 1;
        int endRow = lineNumber == lines.size() - 1 ? lineNumber : lineNumber + 1;
        int startPosition = start == 0 ? start : start - 1;
        int endPosition = start == lines.get(0).length() - 1 ? start : start + 2;

        int i = startRow;
        int neighbours = 0;
        List<Integer> adjacentNumbers = new ArrayList<>();

        while (neighbours < 2 && i <= endRow) {
            String l = lines.get(i).substring(startPosition, endPosition);
            if(!l.replaceAll("\\D","").isEmpty()) {
                neighbours++;
                adjacentNumbers.addAll(findNumberInLine(i,start));
            }
            i++;
        }
        return adjacentNumbers.size() >= 2? adjacentNumbers.stream().reduce(1, (a, b) -> a * b) : 0;

    }

    private static List<Integer> findNumberInLine(int lNum, int innerPosition) {

        Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
        Matcher m = pattern.matcher(lines.get(lNum));
        String number = "";
        List<Integer> numbers = new ArrayList<>();
        boolean found = false;
        while(m.find()) {
            if(m.start() <= innerPosition+1 && m.end() >= innerPosition) {
                number = m.group(0);
                numbers.add(Integer.valueOf(number));
            }
        }
        return numbers;
    }
}
