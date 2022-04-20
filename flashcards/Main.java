package flashcards;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cards cards = new Cards();

        System.out.println("Input the number of cards:");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 1; i <= n; i++) {
            System.out.println("Card #" + i + ":");
            String face = scanner.nextLine();
            System.out.println("The definition for card #" + i + ":");
            String back = scanner.nextLine();
            cards.addCard(face, back);

        }
        for (var entry : cards.cards.entrySet()) {
            System.out.println("Print the definition of \"" + entry.getKey() + "\":");
            String answer = scanner.nextLine();
            String output = answer.equals(entry.getValue()) ? "Correct!" : "Wrong. The right answer is \"" + entry.getValue() + "\".";
            System.out.println(output);
        }


    }
}
