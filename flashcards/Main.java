package flashcards;

public class Main {
    public static void main(String[] args) {

        Card card = new Card("purchase", "buy");
        System.out.println("Card:");
        System.out.println(card.getFace());
        System.out.println("Definition:");
        System.out.println(card.getBack());
    }
}
