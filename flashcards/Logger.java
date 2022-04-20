package flashcards;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<String> log;

    public Logger() {
        this.log = new ArrayList<>();
    }

    String log(String str) {
        log.add(str);
        return str;
    }

    void saveLog(String fileName) throws IOException {
        File file = new File(fileName);
        Writer writer = new FileWriter(file);
        for (String s : log) {
            writer.write(s + System.lineSeparator());
        }
        writer.close();
    }
}
