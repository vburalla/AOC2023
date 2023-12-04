package day4;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {

    private static final String NUMBER_PATTERN = "([\\d]+|[\\|])";
    private static List<ScratchCard> cards = new ArrayList<>();

    public static void main(String[] args) {

        createOriginalCards(ReadFiles.getInputData("day4/input.txt"));
        Integer cardsNumber = processCards();

        System.out.println(String.format("Part 1: %d", cards.stream().map(ScratchCard::getCardWorth).mapToInt(Integer::intValue).sum()));
        System.out.println(String.format("Part 2: %d", cardsNumber));
    }

    private static void createOriginalCards(List<String> lines) {

        Pattern pattern = Pattern.compile(NUMBER_PATTERN);

        for (String line : lines) {
            Matcher m = pattern.matcher(line.split(":")[1]);
            boolean isWinningNumbers = false;
            ScratchCard card = new ScratchCard();

            while (m.find()) {
                var element = m.group(0);
                if (element.equals("|"))
                    isWinningNumbers = true;
                else {
                    if (isWinningNumbers)
                        card.addWinningNumber(Integer.valueOf(element));
                    else
                        card.addNumber(Integer.valueOf(element));
                }
            }
            cards.add(card);
        }

    }

    private static Integer processCards() {

        List<ScratchCard> original = new ArrayList<>(cards);
        int line = 0;
        for (ScratchCard card : original) {
            var winnerPositions = card.getWinningAmount();
            for (int i=1; i<=winnerPositions; i++) {
                original.get(i+line).addCopies(card.getCopies());
            }
            line++;
        }
        return original.stream().map(ScratchCard::getCopies).mapToInt(Integer::intValue).sum();
    }
}
