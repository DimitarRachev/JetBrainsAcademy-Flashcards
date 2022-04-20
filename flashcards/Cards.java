package flashcards;

import java.util.HashMap;
import java.util.Map;

public class Cards {
    private Map<String, String> cards;

    public Cards() {
        cards = new HashMap<>();
    }

    public void addCard(String face, String back) {
        cards.putIfAbsent(face, back);
    }


    public String getBack(String face) {
        return cards.get(face);
    }
}
