package flashcards;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cards {
    Map<String, String> cards;
    Map<String, String> reverseCards;

    public Cards() {
        reverseCards = new LinkedHashMap<>();
        cards = new LinkedHashMap<>();
    }

    public void addCard(String face, String back) {
        cards.put(face, back);
        reverseCards.put(back, face);
    }

    public String getBack(String face) {
        return cards.get(face);
    }
}
