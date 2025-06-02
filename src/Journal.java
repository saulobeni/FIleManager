import java.io.*;

public class Journal {
    private String currentOperation;
    private final String journalFile = "journal.log";

    public Journal() {
        loadPendingOperation();
    }

    public boolean hasPendingOperation() {
        return currentOperation != null && !currentOperation.isEmpty();
    }

    public String getPendingOperation() {
        return currentOperation;
    }

    public void saveOperation(String operation) {
        currentOperation = operation;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(journalFile))) {
            bw.write(operation);
        } catch (IOException e) {
            System.out.println("Erro ao salvar no journal: " + e.getMessage());
        }
    }

    public void clear() {
        currentOperation = null;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(journalFile))) {
            bw.write("");  // Limpa o arquivo
        } catch (IOException e) {
            System.out.println("Erro ao limpar journal: " + e.getMessage());
        }
    }

    private void loadPendingOperation() {
        File file = new File(journalFile);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(journalFile))) {
            String line = br.readLine();
            if (line != null && !line.trim().isEmpty()) {
                currentOperation = line.trim();
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar o journal: " + e.getMessage());
        }
    }
}
