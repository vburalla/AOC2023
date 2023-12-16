package day15;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 {

    static Map<Integer, List<Lens>> boxes = new HashMap();

    public static void main(String... args) {

        var instructions = ReadFiles.getInputData("day15/input.txt").get(0).split(",");
        Long value = 0L;
        for (String instruction : instructions) {
            value += calc(instruction);
            processInstruction(instruction);
        }
        System.out.println(String.format("Part 1: %d", value));
        System.out.println(String.format("Part 2: %d", calcFocusingPower()));
    }

    private static Integer calc(String text) {

        Integer v = 0;
        for (int c : text.toCharArray()) {
            v = ((v + c) * 17) % 256;
        }
        return v;
    }

    private static void processInstruction(String instruction) {

        Lens lens = instruction.contains("=") ? new Lens(instruction.split("=")) : new Lens(instruction.split("-")[0], 0);
        var boxNumber = calc(lens.label);

        if (instruction.contains("=")) {

         if (boxes.containsKey(boxNumber)) {
         int position = boxes.get(boxNumber).indexOf(lens);
         if (position >= 0)
         boxes.get(boxNumber).set(position, lens);
         else
         boxes.get(boxNumber).add(lens);
         } else {
         List<Lens> lensList = new ArrayList<>();
         lensList.add(lens);
         boxes.put(boxNumber, lensList);
         }
         } else {
         if (boxes.containsKey(boxNumber)) {
         int position = boxes.get(boxNumber).indexOf(lens);
         if (position >= 0)
         boxes.get(boxNumber).remove(position);
         }
         }
        System.out.println();
    }

    private static Long calcFocusingPower() {
        Long total = 0L;
        for (Integer key : boxes.keySet()) {
            total += calcBoxFocusingPower(key);
        }
        return total;
    }

    private static Long calcBoxFocusingPower(int boxNumber) {
        Long value = 0L;
        List<Lens> l = boxes.get(boxNumber);
        if (l.size() > 0) {
            int i = 0;
            for (Lens lens : l) {
                value += (boxNumber + 1) * (i + 1) * (lens.focalLength);
                i++;
            }
        }
        return value;
    }
}
