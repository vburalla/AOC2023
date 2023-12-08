package utils;

import java.util.Map;

public class NumberUtils {

    public static final Map<String, String> WORD_NUMBER_VALUES = Map.of("one","1", "two", "2", "three", "3", "four", "4", "five", "5", "six", "6", "seven", "7", "eight", "8", "nine", "9");
    public static final Map<String, String> NUMBER_NAMES = Map.of("1","one","2", "two", "3","three", "4",  "four", "5", "five", "6","six", "7",  "seven", "8", "eight", "9", "nine");

    public static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    public static long lcm(Integer[] numbers) {
        long mcm = 1;
        for (long num : numbers) {
            mcm = lcm(mcm, num);
        }
        return mcm;
    }
}
