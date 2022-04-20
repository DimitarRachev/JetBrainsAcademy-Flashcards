package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cards cards = new Cards();
        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            String command = scanner.nextLine();
            switch (command) {
                case "add":
                    addCard(scanner, cards);
                    break;
                case "remove":
                    System.out.println("Which card?");
                    String card = scanner.nextLine();
                    if (cards.remove(card)) {
                        System.out.println("The card has been removed.");
                    } else {
                        System.out.println("Can't remove \"" + card + "\": there is no such card.");
                    }
                    break;
                case "import":
                    System.out.println("File name:");
                    try {
                        File file = new File(scanner.nextLine());
                        Scanner scanner1 = new Scanner(file);
                        System.out.println(importedCards(scanner1, cards) + " cards have been loaded.");
                        scanner1.close();

                    } catch (FileNotFoundException e) {
                        System.out.println("File not found.");
                    }
                    break;
                case "export":
                    System.out.println("File name:");
                    String fileName = scanner.nextLine();
                    System.out.println(exportedCards(fileName, cards.cards) + " cards have been saved.");
                    break;
                case "ask":
                    System.out.println("How many times to ask?");
                    int n = Integer.parseInt(scanner.nextLine());
                    askCards(n, cards, scanner);
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    return;
            }
        }
    }

    private static int importedCards(Scanner scanner, Cards cards) {
        int count = 0;
        while (scanner.hasNext()) {
            String[] input = scanner.nextLine().split("-");
            String face = input[0];
            String back = input[1];
            cards.addCard(face, back);
            count++;
        }
        return count;
    }

    private static int exportedCards(String fileName, Map<String, String> map) {
        int count = 0;
        try {
            FileWriter writer = new FileWriter(fileName);
            for (var entry : map.entrySet()) {
                writer.write(entry.getKey() + "-" + entry.getValue() + System.lineSeparator());
                count++;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private static void askCards(int n, Cards cards, Scanner scanner) {
        for (var entry : cards.cards.entrySet()) {
            if (n-- > 0) {
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
    }

    private static boolean addCard(Scanner scanner, Cards cards) {
        System.out.println("The card:");
        String face = scanner.nextLine();
        if (isUniq(face, cards.cards)) {
            System.out.println("The card \"" + face + "\" already exists.");
            return false;
        }
        System.out.println("The definition of the card:");
        String back = scanner.nextLine();
        if (isUniq(back, cards.reverseCards)) {
            System.out.println("The definition \"" + back + "\" already exists.");
            return false;
        }
        cards.addCard(face, back);
        System.out.println("The pair (\"" + face + "\":\"" + back + "\") has been added");
        return true;
    }

    private static boolean isUniq(String str, Map<String, String> map) {
        return map.containsKey(str);
    }
}
