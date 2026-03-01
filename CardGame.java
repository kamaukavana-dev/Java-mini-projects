import java.util.ArrayList;
import java.util.Collections;

public class CardGame {
    public static void main(String[] args) {
        ArrayList<String> cards = new ArrayList<>();
        cards.add("Heart");
        cards.add("Spade");
        cards.add("club");
        cards.add("diamond");
        Collections.shuffle(cards);
        for(String card : cards) {
            System.out.println(card);
        }

    }
}