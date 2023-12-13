package day13;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;

public class Day13 {

    public static void main(String...args) {
        Integer sum = 0;
        var lines = ReadFiles.getInputData("day13/input.txt");
        List<String> patternLines = new ArrayList<>();
        for(String line : lines) {
            if(line.equals("")) {
                sum+=getResult(patternLines);
                patternLines.clear();
            } else {
                patternLines.add(line);
            }
        }
        sum+=getResult(patternLines);
        System.out.println(sum);
    }

    private static Integer getResult(List<String> lines ) {
        List<String> newLines = transpose(lines);
        Integer result = 0;
        Integer total = 0;
        total =((result = analizeMirror(newLines)) != null)? result : 0;
        return ((result = analizeMirror(lines)) != null)? total + 100 * result : total;
    }

    private static Integer analizeMirror(List<String> patterns) {

        int i = 0;
        int j = 0;
        int k = 0;
        boolean found = false;
        while(i < (patterns.size() - 1) && !found) {
            if(patterns.get(i).equals(patterns.get(i+1))) {
                found = true;
                j= i - 1;
                k = i + 2;
                while (found && j >= 0 && k < patterns.size()) {
                    found = patterns.get(j).equals(patterns.get(k));
                    j--;
                    k++;
                }
            }
            i++;
        }
        return found? i : null;
    }

    private static List<String> transpose(List<String> lines) {

        List<String> transposedLines = new ArrayList<>();
        int limit = lines.get(0).length();
        for(int n=0; n<limit; n++) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line.charAt(n));
            }
            transposedLines.add(sb.toString());
        }
        return transposedLines;
    }
}
