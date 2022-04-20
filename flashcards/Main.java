package flashcards;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cards cards = new Cards();

        System.out.println("Input the number of cards:");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 1; i <= n; i++) {
            System.out.println("Card #" + i + ":");
            String face = scanner.nextLine();
            while (isUniq(face, cards.cards)) {
                System.out.println("The term \"" + face + "\" already exists. Try again:");
                face = scanner.nextLine();
            }
            System.out.println("The definition for card #" + i + ":");
            String back = scanner.nextLine();
            while (isUniq(back, cards.reverseCards)) {
                System.out.println("The term \"" + back + "\" already exists. Try again:");
                back = scanner.nextLine();
            }
            cards.addCard(face, back);

        }
        for (var entry : cards.cards.entrySet()) {
            System.out.println("Print the definition of \"" + entry.getKey() + "\":");
            String answer = scanner.nextLine();
            if (answer.equals(entry.getValue())) {
                System.out.println("Correct!");
            } else if (cards.reverseCards.containsKey(answer)) {
                System.out.println("Wrong. The right answer is \"" + entry.getValue() + "\", but your definition is correct for \"" + cards.reverseCards.get(answer) + "\".");
            } else {
                System.out.println("Wrong. The right answer is \"" + entry.getValue() + "\".");
            }
        }
    }

    private static boolean isUniq(String str, Map<String, String> map) {
        return map.containsKey(str);
    }
}
