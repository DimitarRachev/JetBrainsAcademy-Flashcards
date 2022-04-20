package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    static Logger logger;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cards cards = new Cards();
        logger = new Logger();

        boolean mustExport = false;
        String exportFile = "";
        for (int i = 0; i < args.length - 1; i += 2) {
            if ("-import".equals(args[i])) {
                importCardFromFile(cards, args[i + 1]);
            }
            if ("-export".equals(args[i])) {
                mustExport = true;
                exportFile = args[i + 1];
            }
        }

        while (true) {
            System.out.println(logger.log("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):"));
            String command = logger.log(scanner.nextLine());
            switch (command) {
                case "add":
                    addCard(scanner, cards);
                    break;
                case "remove":
                    System.out.println(logger.log("Which card?"));
                    String card = logger.log(scanner.nextLine());
                    if (cards.remove(card)) {
                        System.out.println(logger.log("The card has been removed."));
                    } else {
                        System.out.println(logger.log("Can't remove \"" + card + "\": there is no such card."));
                    }
                    break;
                case "import":
                    System.out.println(logger.log("File name:"));
                    String filePath = logger.log(scanner.nextLine());
                    importCardFromFile(cards, filePath);
                    break;
                case "export":
                    System.out.println(logger.log("File name:"));
                    String fileName = logger.log(scanner.nextLine());
                    System.out.println(logger.log(exportedCards(fileName, cards.terms) + " cards have been saved."));
                    break;
                case "ask":
                    System.out.println(logger.log("How many times to ask?"));
                    int n = Integer.parseInt(logger.log(scanner.nextLine()));
                    askCards(n, cards, scanner);
                    break;
                case "log":
                    System.out.println(logger.log("File name:"));
                    String name = logger.log(scanner.nextLine());
                    try {
                        logger.saveLog(name);
                        System.out.println(logger.log("The log has been saved."));
                    } catch (IOException e) {
                        System.out.println(logger.log("Cannot save the log."));
                    }
                    break;
                case "reset stats":
                    cards.resetStats();
                    System.out.println(logger.log("Card statistics have been reset."));
                    break;
                case "hardest card":
                    printHardest(cards.getMaxErrors());
                    break;
                case "exit":
                    if (mustExport) {
                        System.out.println(logger.log(exportedCards(exportFile, cards.terms) + " cards have been saved."));
                    }
                    System.out.println(logger.log("Bye bye!"));
                    return;
            }
        }
    }

    private static void importCardFromFile(Cards cards, String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner1 = new Scanner(file);
            System.out.println(logger.log(importedCards(scanner1, cards) + " cards have been loaded."));
            scanner1.close();

        } catch (FileNotFoundException e) {
            System.out.println(logger.log("File not found."));
        }
    }

    private static void printHardest(List<Card> maxErrors) {
        if (maxErrors == null) {
            System.out.println(logger.log("There are no cards with errors."));
        } else if (maxErrors.size() == 1) {
            System.out.println(logger.log("The hardest card is \"" + maxErrors.get(0).getTerm() + "\"."));
            System.out.println(logger.log("The hardest card is \"" + maxErrors.get(0).getTerm() + "\". You have " + maxErrors.get(0).getErrors() + " errors answering it."));
        } else {
            System.out.println(logger.log("The hardest cards are " + getHardestTermsToString(maxErrors) + ". You have " + maxErrors.get(0).getErrors() + " errors answering them."));
        }
    }

    private static String getHardestTermsToString(List<Card> maxErrors) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxErrors.size(); i++) {
            sb.append("\"").append(maxErrors.get(i).getTerm()).append("\"");
            if (i < maxErrors.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private static int importedCards(Scanner scanner, Cards cards) {
        int count = 0;
        while (scanner.hasNext()) {
            String[] input = scanner.nextLine().split("-");
            String face = input[0];
            String back = input[1];
            int errors = Integer.parseInt(input[2]);
            cards.addCard(face, back, errors);
            count++;
        }
        return count;
    }

    private static int exportedCards(String fileName, Map<String, Card> map) {
        int count = 0;
        try {
            FileWriter writer = new FileWriter(fileName);
            for (Card card : map.values()) {
                writer.write(card.getTerm() + "-" + card.getDefinition() + "-" + card.getErrors() + System.lineSeparator());
                count++;
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(logger.log(e.getStackTrace().toString()));
             }
        return count;
    }

    private static void askCards(int n, Cards cards, Scanner scanner) {
        for (var entry : cards.terms.entrySet()) {
            if (n-- > 0) {
                System.out.println(logger.log("Print the definition of \"" + entry.getValue().getTerm() + "\":"));
                String answer = logger.log(scanner.nextLine());
                if (answer.equals(entry.getValue().getDefinition())) {
                    System.out.println(logger.log("Correct!"));
                } else if (cards.definitions.containsKey(answer)) {
                    System.out.println(logger.log("Wrong. The right answer is \"" + entry.getValue().getDefinition() + "\", but your definition is correct for \"" + cards.definitions.get(answer).getTerm() + "\"."));
                    entry.getValue().addError();
                } else {
                    System.out.println(logger.log("Wrong. The right answer is \"" + entry.getValue().getDefinition() + "\"."));
                    entry.getValue().addError();
                }
            }
        }
    }

    private static boolean addCard(Scanner scanner, Cards cards) {
        System.out.println(logger.log("The card:"));
        String term = logger.log(scanner.nextLine());
        if (isUniq(term, cards.terms)) {
            System.out.println(logger.log("The card \"" + term + "\" already exists."));
            return false;
        }
        System.out.println(logger.log("The definition of the card:"));
        String definition = logger.log(scanner.nextLine());
        if (isUniq(definition, cards.definitions)) {
            System.out.println(logger.log("The definition \"" + definition + "\" already exists."));
            return false;
        }
        cards.addCard(term, definition);
        System.out.println(logger.log("The pair (\"" + term + "\":\"" + definition + "\") has been added"));
        return true;
    }

    private static boolean isUniq(String str, Map<String, Card> map) {
        return map.containsKey(str);
    }
}
