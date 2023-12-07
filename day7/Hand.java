package day7;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Hand implements Comparable<Hand> {

    public static int part;
    private static final String POWER_SCORE = "AKQJT98765432";
    private static final String POWER_SCORE2 = "AKQT98765432J";

    private String cards;
    private Integer bid;

    public Hand(String cards, Integer bid) {
        this.cards = cards;
        this.bid = bid;
    }

    public Integer getBid() {
        return this.bid;
    }

    @Override
    public int compareTo(Hand o) {
        Integer result = 0;
        if (o == null || o.cards == null || o.cards.length() < 5) {
            result = 1;
        } else {
            result = part != 2? compareResults(this.cards, o.cards) : compareResults(this.transformInBestHand().cards, o.transformInBestHand().cards);
            if (result == 0) {
                String scoring = part == 2? POWER_SCORE2 : POWER_SCORE;
                return compareCardByCard(this.cards, o.cards, scoring);
            }
        }
        return result;
    }

    private Integer compareResults(String cards1, String cards2) {

        Integer result = 0;
        Map<String, Long> score1 = cards1.chars().mapToObj(Character::toString).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> score2 = cards2.chars().mapToObj(Character::toString).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        var values1 = score1.values().stream().collect(Collectors.toList());
        var values2 = score2.values().stream().collect(Collectors.toList());

        Collections.sort(values1, Collections.reverseOrder());
        Collections.sort(values2, Collections.reverseOrder());

        Iterator i1 = values1.iterator();
        Iterator i2 = values2.iterator();
        while (i1.hasNext() && i2.hasNext() && result == 0) {
            Long s1 = (Long) i1.next();
            Long s2 = (Long) i2.next();
            result = s1.compareTo(s2);
        }
        return result;
    }

    private Integer compareCardByCard(String cards1, String cards2, String scoring) {

        var c1 = cards1.toCharArray();
        var c2 = cards2.toCharArray();

        int result = 0;
        int i = 0;
        while (i < c1.length && result == 0) {

            result = ((Integer) scoring.indexOf(c2[i])).compareTo((Integer) scoring.indexOf(c1[i]));
            i++;
        }
        return result;
    }

    private Hand copy() {
        return new Hand(this.cards, this.bid);
    }


    public Hand transformInBestHand() {

        Hand newHand = this.copy();
        if (newHand.cards.contains("J")) {
            String workingCards = newHand.cards.replaceAll("J", "");
            if(workingCards.length() >0) {
                Map<String, Long> score = workingCards.chars().mapToObj(Character::toString).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                var values = score.values().stream().collect(Collectors.toList());
                Collections.sort(values, Collections.reverseOrder());
                newHand.cards = newHand.cards.replaceAll("J", getOptimalCard(score, values.get(0)));
            } else {
                newHand.cards = newHand.cards.replaceAll("J","A");
            }
        }
        return newHand;
    }

    private String getOptimalCard(Map<String, Long> map, Long value) {
        String a = "";
        try {
            a = map
                    .entrySet()
                    .stream()
                    .filter(entry -> value.equals(entry.getValue())).collect(Collectors.toList()).get(0).getKey();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return a;
    }

}
