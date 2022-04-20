package flashcards;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cards {
     Map<String, String> cards;

    public Cards() {
        cards = new LinkedHashMap<>();
    }

    public void addCard(String face, String back) {
        cards.putIfAbsent(face, back);
    }


    public String getBack(String face) {
        return cards.get(face);
    }


}
