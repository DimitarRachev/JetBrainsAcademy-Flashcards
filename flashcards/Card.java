package flashcards;

public class Card {
   private String face;
   private String back;

    public Card(String face, String back) {
        this.face = face;
        this.back = back;
    }

    public String getFace() {
        return face;
    }

    public String getBack() {
        return back;
    }
}
