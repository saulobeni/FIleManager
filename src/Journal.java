import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Journal {
    private final String journalFile = "journal.log";
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void log(String command) {
        String timestamp = formatter.format(new Date());
        String entry = timestamp + " - " + command;

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(journalFile, true)))) {
            out.println(entry);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no journal: " + e.getMessage());
        }
    }

    public List<String> getEntries() {
        List<String> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(journalFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                entries.add(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o journal: " + e.getMessage());
        }
        return entries;
    }

    public void printJournal() {
        List<String> entries = getEntries();
        if (entries.isEmpty()) {
            System.out.println("Nenhuma operação registrada.");
            return;
        }

        System.out.println("📓 Histórico de comandos executados:");
        for (String entry : entries) {
            System.out.println(" - " + entry);
        }
    }
}
