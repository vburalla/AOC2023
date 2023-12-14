package day13;

import utils.Point;
import utils.ReadFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(String... args) {
        Integer sum = 0;
        Integer sum2 = 0;
        var lines = ReadFiles.getInputData("day13/input.txt");
        List<String> patternLines = new ArrayList<>();
        int i = 0;
        for (String line : lines) {
            if (line.equals("")) {
                i++;
                System.out.println("Pattern " + i);
                sum += getResult(patternLines);
                sum2 += getResultWithSmudge(patternLines);
                patternLines.clear();
            } else {
                patternLines.add(line);
            }
        }
        sum += getResult(patternLines);
        sum2 += getResultWithSmudge(patternLines);
        System.out.println(sum);
        System.out.println(sum2);
    }

    public static Point findSmudge2(List<String> patterns) {

        int i = 0;
        int j = 0;
        int k = 0;
        int row = 0;
        boolean charSwapped = false;
        boolean equalFound = true;
        int charDifferencePosition = -1;

        while (i < patterns.size() - 1) {
            charSwapped = false;
            equalFound = false;
            charDifferencePosition = -1;
            if (patterns.get(i).equals(patterns.get(i + 1)) || (charDifferencePosition = charDifference(patterns.get(i), patterns.get(i + 1))) >= 0) {
                if ((charSwapped = charDifferencePosition >= 0)) {
                    row = i;
                    equalFound = true;
                }
                j = i - 1;
                k = i + 2;
                while (j >= 0 && k < patterns.size() && ((equalFound = patterns.get(j).equals(patterns.get(k))) ||
                        ((charDifferencePosition = charDifference(patterns.get(j), patterns.get(k))) >= 0 && !charSwapped))) {
                    if (!equalFound && charDifferencePosition >= 0) {
                        equalFound = true;
                        charSwapped = true;
                        row = j;
                    }
                    j--;
                    k++;
                }
                if (equalFound && (j < 0 || k >= patterns.size()) && charSwapped) {
                    return new Point(row, charDifferencePosition);
                }
                charSwapped = false;
            }
            i++;
        }
        return null;
    }

    public static Point findSmudge(List<String> patterns) {

        int i = 0;
        int j = 0;
        int k = 0;
        int charPosition = 0;
        boolean found = false;
        boolean foundSmudge = false;
        Point point = null;

        while (i < patterns.size() - 1 && !foundSmudge) {
            if (patterns.get(i).equals(patterns.get(i + 1))) {
                found = true;
                j = i - 1;
                k = i + 2;
                boolean swapped = false;
                while (found && j >= 0 && k < patterns.size()) {
                    found = patterns.get(j).equals(patterns.get(k));
                    if (!found && (charPosition = charDifference(patterns.get(j), patterns.get(k))) >= 0 && !swapped) {
                        swapped = true;
                        point = new Point(j, charPosition);
                    }
                    j--;
                    k++;
                }
                if (swapped && found)
                    return point;
            } else {
                if ((charPosition = charDifference(patterns.get(i), patterns.get(i + 1))) >= 0) {
                    j = i - 1;
                    k = i + 2;
                    found = true;
                    while (j >= 0 && k < patterns.size() && found) {
                        found = patterns.get(j).equals(patterns.get(k));
                        j--;
                        k++;
                    }
                    if (found)
                        return new Point(i + 1, charPosition);
                }
            }
            i++;
        }
        return null;
    }

    private static List<String> updateLinesWithSmudge(List<String> lines, Point point) {
        List<String> lines2 = lines.stream().collect(Collectors.toList());
        var line = lines2.get(point.getRow()).toCharArray();
        line[point.getColumn()] = line[point.getColumn()] == '#' ? '.' : '#';
        lines2.set(point.getRow(), new String(line));
        return lines2;
    }

    private static Integer getResultWithSmudge(List<String> lines) {

        Integer total = 0;
        Point smudge = findSmudge2(lines);
        if (smudge != null) {
            var lines2 = updateLinesWithSmudge(lines, smudge);
            var result = analyzeMirror(lines2);
            //total = result != null? 100 * analyzeMirror(lines2) : 0;
        }

        lines = transpose(lines);
        smudge = findSmudge2(lines);
        if (smudge != null) {
            lines = updateLinesWithSmudge(lines, smudge);
            var result = analyzeMirror(lines);
            total = result != null? total + result : total;
        }
        return total;
    }


    private static Integer getResult(List<String> lines) {

        Integer result = analyzeMirror(lines);
        Integer total = result != null ? 100 * result : 0;
        result = analyzeMirror(transpose(lines));
        return result != null ? total + result : total;

    }

    private static Integer analyzeMirror(List<String> patterns) {

        int i = 0;
        int j = 0;
        int k = 0;
        boolean found = false;
        while (i < (patterns.size() - 1) && !found) {
            if (patterns.get(i).equals(patterns.get(i + 1))) {
                found = true;
                j = i - 1;
                k = i + 2;
                while (found && j >= 0 && k < patterns.size()) {
                    found = patterns.get(j).equals(patterns.get(k));
                    j--;
                    k++;
                }
            }
            i++;
        }
        return found ? i : null;
    }

    private static List<String> transpose(List<String> lines) {

        List<String> transposedLines = new ArrayList<>();
        int limit = lines.get(0).length();
        for (int n = 0; n < limit; n++) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line.charAt(n));
            }
            transposedLines.add(sb.toString());
        }
        return transposedLines;
    }

    private static int charDifference(String s1, String s2) {

        int pos = -1;
        int distinct = 0;
        int i = 0;
        while (i < s1.length() && distinct < 2) {
            if (s1.charAt(i) != s2.charAt(i)) {
                distinct++;
                pos = i;
            }
            i++;
        }
        return distinct < 2 ? pos : -1;
    }
}
