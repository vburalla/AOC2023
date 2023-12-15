package day14;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;

public class Day14 {

    static char[][] matrix;
    static List<Integer> weights = new ArrayList<>();
    static int delta;
    static int patternSize;
    static final int NUMBER_OF_CYCLES = 1000000000;

    public static void main(String...args) {

        createMatrix(ReadFiles.getInputData("day14/input.txt"));
        tiltPlatform();
        System.out.println(String.format("Part 1: %d", getRocksWeight()));
        createMatrix(ReadFiles.getInputData("day14/input.txt"));

        boolean patternFound = false;
        while(!patternFound) {
            completeCycle();
            weights.add(getRocksWeight());
            if(weights.size() >= 200)
                patternFound = findPattern(4);
        }
        Integer position = (((NUMBER_OF_CYCLES - delta) % patternSize));
        System.out.println(String.format("Part 2: %d",weights.get(position + delta - 1)));

    }

    private static void tiltPlatform() {
        for(int i = 0; i < matrix.length -1; i++) {

            int j = i;
            while(j >= 0 && (intersectRows(j, j+1)) > 0) {
                j--;
            }
        }
    }

    private static void completeCycle() {

        for(int i=0; i<4; i++) {
            tiltPlatform();
            rotateMatrix();
        }
    }

    public static int intersectRows(int northRow, int southRow) {
        int intersections = 0;
        for(int i = 0; i < matrix[northRow].length; i++) {
            if(matrix[northRow][i] == '.' && matrix[southRow][i] == 'O') {
                matrix[northRow][i] = 'O';
                matrix[southRow][i] = '.';
                intersections++;
            }
        }
        return intersections;
    }

    private static void createMatrix(List<String> lines) {
        matrix = new char[lines.size()][];
        int i=0;
        for(String line : lines) {
            matrix[i] = line.toCharArray();
            i++;
        }
    }

    private static Integer getRocksWeight() {
        Integer rocksWeight = 0;
        for(int i=0; i< matrix.length; i++) {
            rocksWeight += (matrix.length - i) * countRocks(i);
        }
        return  rocksWeight;
    }

    private static int countRocks(int lineNumber) {
        int rocks = 0;
        for(char c : matrix[lineNumber]) {
            if(c == 'O')
                rocks++;
        }
        return rocks;
    }

    private static void rotateMatrix() {
        int rows = matrix.length;

        for(int i = 0; i < rows / 2; i++) {
            for(int j = i; j < rows - i - 1; j++) {
                char temp = matrix[i][j];
                matrix[i][j] = matrix[rows - 1 - j][i];
                matrix[rows - 1 - j][i] = matrix[rows - 1 - i][rows - 1 - j];
                matrix[rows - 1 - i][rows - 1 - j] = matrix[j][rows - 1 - i];
                matrix[j][rows - 1 - i] = temp;
            }
        }
    }

    private static boolean findPattern(int minWindow) {

        int i = 0;
        int j = 1;
        int oldPos = -1;
        int repetitions = 0;
        int firstOccurrence = -1;
        while(repetitions < minWindow) {
            while (i < j && repetitions < minWindow) {
                if (weights.get(i).equals(weights.get(j))) {
                    if(firstOccurrence < 0) {
                        oldPos = j;
                        firstOccurrence = i;
                    }
                    i++;
                    j++;
                    repetitions++;

                } else {
                    if(firstOccurrence > 0) {
                        j =  oldPos;
                        oldPos = -1;
                        firstOccurrence = -1;
                    }
                    repetitions = 0;
                    i++;
                }
            }
            j++;
            i = 0;
        }
        delta = firstOccurrence - 1;
        patternSize = oldPos - firstOccurrence;
        return true;
    }
}
