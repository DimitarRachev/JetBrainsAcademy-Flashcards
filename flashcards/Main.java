package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       String face = scanner.nextLine();
       String back = scanner.nextLine();
       Cards cards = new Cards();
       cards.addCard(face, back);
       String answer = scanner.nextLine();
       String output = answer.equals(cards.getBack(face)) ? "Your answer is right!" : "Your answer is wrong..";
        System.out.println(output);

    }
}
