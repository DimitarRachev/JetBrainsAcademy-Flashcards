package flashcards;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cards {
    Map<String, Card> terms;
    Map<String, Card> definitions;

    public Cards() {
        definitions = new LinkedHashMap<>();
        terms = new LinkedHashMap<>();
    }

    public void addCard(String term, String definition) {
        Card card = new Card(term, definition);
        terms.put(term, card);
        definitions.put(definition, card);
    }

    public void addCard(String term, String definition, int errors) {
        Card card = new Card(term, definition, errors);
        terms.put(term, card);
        definitions.put(definition, card);
    }

    public boolean remove(String term) {
        if (terms.containsKey(term)) {
            definitions.remove(terms.get(term).getDefinition());
            terms.remove(term);
            return true;
        }
        return false;
    }

    public void resetStats() {
        terms.values().forEach(Card::resetError);
    }

    public List<Card> getMaxErrors(){
        List<Card> cards = new ArrayList<>();
        int maxErrors = 0;
        for (Card card : terms.values()) {
            if (card.getErrors() > maxErrors) {
                maxErrors = card.getErrors();
                cards.clear();
                cards.add(card);
            } else if (card.getErrors() == maxErrors) {
                cards.add(card);
            }
        }
        if (maxErrors == 0) {
            return null;
        }
        return cards;
    }
}
