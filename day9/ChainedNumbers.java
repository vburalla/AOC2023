package day9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ChainedNumbers {

    private static final Integer ENDING_VALUE = 0;
    private Integer finalNumber = null;

    public Integer getFirstNumber() {
        return firstNumber;
    }

    private Integer firstNumber = null;

    public Integer getFinalNumber() {
        return finalNumber;
    }

    List<LinkedList<Integer>> numbersList = new ArrayList<>();

    public ChainedNumbers(String numbers) {

        numbersList.add(Arrays.stream(numbers.split(" ")).map(Integer::valueOf).collect(Collectors.toCollection(LinkedList::new)));
        fillDownwardNumbers();
        calculateNextNumbers();
        calculatePrevNumbers();
        System.out.println();
    }

    private void fillDownwardNumbers() {

        List<Integer> currentList = this.numbersList.get(0);
        while(!currentList.stream().allMatch(n -> ( n == null || n.equals(ENDING_VALUE)))) {
            int i=0;
            LinkedList<Integer> newList = new LinkedList<>();
            while(currentList.get(i) == null) {
                newList.add(null);
                i++;
            }
            i++;
            newList.add(null);
            while(i < currentList.size()) {
                newList.add(currentList.get(i) - currentList.get(i-1));
                i++;
            }
            numbersList.add(newList);
            currentList = newList.stream().collect(Collectors.toCollection(LinkedList::new));
        }
    }

    private void calculateNextNumbers() {

        Integer val = 0;
        this.numbersList.get(numbersList.size()-1).add(val);
        int i = this.numbersList.size()-2;
        while(i >= 0) {
            val = val + numbersList.get(i).get(numbersList.get(i).size()-1);
            this.numbersList.get(i).add(val);
            i--;
        }
        this.finalNumber = this.numbersList.get(0).get(this.numbersList.get(0).size()-1);
    }

    private void calculatePrevNumbers() {

        Integer val = 0;
        int i = this.numbersList.size()-1;
        int position = getLastNull(this.numbersList.get(i));
        this.numbersList.get(numbersList.size()-1).set(position, val);
        i--;
        while(i >= 0) {
            position = getLastNull(this.numbersList.get(i));
            val = numbersList.get(i).get(position) - val;
            this.numbersList.get(i).addFirst(val);
            i--;
        }
        this.firstNumber = this.numbersList.get(0).get(0);

    }

    private int getLastNull(LinkedList<Integer> list) {
        int i = 0;
        while(list.get(i) == null) {
            i++;
        }
        return i;
    }
}
