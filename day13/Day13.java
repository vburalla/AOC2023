package day13;

import utils.ReadFiles;
import java.util.ArrayList;
import java.util.List;

public class Day13 {

    public static void main(String... args) {
        Integer sum = 0;
        Integer sum2 = 0;
        var lines = ReadFiles.getInputData("day13/input.txt");
        List<String> patternLines = new ArrayList<>();
        for (String line : lines) {
            if (line.equals("")) {
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

    public static Integer findSmudge2(List<String> patterns) {

        int i = 0;
        int j = 0;
        int k = 0;
        int row;
        boolean charSwapped = false;
        boolean equalFound = true;
        int charDifferencePosition = -1;
        int startRow = 0;

        while (i < patterns.size() - 1) {
            charSwapped = false;
            equalFound = false;
            charDifferencePosition = -1;
            if (patterns.get(i).equals(patterns.get(i + 1)) || (charDifferencePosition = charDifference(patterns.get(i), patterns.get(i + 1))) >= 0) {
                if ((charSwapped = charDifferencePosition >= 0)) {
                    row = i;
                    equalFound = true;
                }
                startRow = i;
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
                    return startRow + 1;
                }
                charSwapped = false;
            }
            i++;
        }
        return -1;
    }

    private static Integer getResultWithSmudge(List<String> lines) {
        Integer result = findSmudge2(lines);
        if(result >= 0) {
            return 100 * result;
        } else {
            lines = transpose(lines);
            return findSmudge2(lines);
        }
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
