package day4;

import java.util.ArrayList;
import java.util.List;

public class ScratchCard {

    private List<Integer> numbers;
    private List<Integer> winningNumbers;
    private int copies;

    public ScratchCard() {
        this.numbers = new ArrayList<>();
        this.winningNumbers = new ArrayList<>();
        this.copies = 1;
    }

    public void addCopies(int copies) {
        this.copies+=copies;
    }

    public void addNumber(Integer number) {
        this.numbers.add(number);
    }

    public void addWinningNumber(Integer number) {
        this.winningNumbers.add(number);
    }

    public Integer getCardWorth() {

        Integer worth = 0;
        for(Integer number : numbers) {
            if(winningNumbers.contains(number))
                worth = worth.equals(0)? 1 : 2*worth;
        }
        return worth;
    }

    public Integer getWinningAmount() {
        Integer winning = 0;
        for(Integer number : numbers) {
            if(winningNumbers.contains(number))
                winning++;
        }
        return winning;
    }

    public Integer getCopies() {
        return this.copies;
    }

}
