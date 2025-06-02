import java.io.*;
import java.util.Scanner;

public class FileSystemSimulator {
    private Directory root;
    private Directory currentDir;
    private Journal journal;
    private Scanner scanner;
    private static final String DATA_FILE = "filesystem.beni";

    public FileSystemSimulator() {
        this.journal = new Journal();
        this.root = loadFromFile();
        this.currentDir = root;
    }

    private Directory loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (Directory) ois.readObject();
        } catch (Exception e) {
            System.out.println("Iniciando novo sistema de arquivos...");
            return new Directory("Saulo");
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(root);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o sistema de arquivos: " + e.getMessage());
        }
    }

    public void start() {
        scanner = new Scanner(System.in);
        System.out.println("Simulador de Sistema de Arquivos - .beni persistência");
        System.out.println("Digite 'help' para ajuda.");

        while (true) {
            System.out.print(currentPath(currentDir) + " > ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            if (journal.hasPendingOperation()) {
                if (input.equalsIgnoreCase("commit")) {
                    applyJournalOperation();
                    journal.clear();
                } else {
                    System.out.println("⚠️ Há uma operação pendente: '" + journal.getPendingOperation() + "'. Use 'commit' antes de continuar.");
                }
                continue;
            }

            String[] args = input.split("\\s+");
            String command = args[0].toLowerCase();

            switch (command) {
                case "exit":
                    saveToFile();
                    System.out.println("Sistema salvo em " + DATA_FILE);
                    return;
                case "help":
                    showHelp();
                    break;
                case "commit":
                    System.out.println("Nenhuma operação pendente.");
                    break;
                case "cd":
                    if (args.length < 2) {
                        System.out.println("Uso: cd <nome_dir> ou cd ..");
                    } else if (args[1].equals("..")) {
                        if (currentDir.getParent() != null) {
                            currentDir = currentDir.getParent();
                        } else {
                            System.out.println("Já está no diretório raiz.");
                        }
                    } else {
                        Directory target = currentDir.getDirectory(args[1]);
                        if (target != null) {
                            currentDir = target;
                        } else {
                            System.out.println("Diretório não encontrado.");
                        }
                    }
                    break;
                case "list":
                case "ls":
                    listCurrentDirectory();
                    break;
                default:
                    journal.saveOperation(input);
                    System.out.println("📝 Operação '" + input + "' registrada. Use 'commit' para aplicar.");
            }
        }
    }

    private void applyJournalOperation() {
        String op = journal.getPendingOperation();
        if (op == null || op.isEmpty()) return;

        String[] args = op.split("\\s+");
        String command = args[0].toLowerCase();

        switch (command) {
            case "createfile":
                if (args.length >= 2 && currentDir.createFile(args[1])) {
                    System.out.println("✅ Arquivo criado.");
                } else {
                    System.out.println("❌ Erro ao criar arquivo.");
                }
                break;
            case "deletefile":
                if (args.length >= 2 && currentDir.deleteFile(args[1])) {
                    System.out.println("✅ Arquivo apagado.");
                } else {
                    System.out.println("❌ Erro ao apagar arquivo.");
                }
                break;
            case "renamefile":
                if (args.length >= 3 && currentDir.renameFile(args[1], args[2])) {
                    System.out.println("✅ Arquivo renomeado.");
                } else {
                    System.out.println("❌ Erro ao renomear arquivo.");
                }
                break;
            case "createdir":
                if (args.length >= 2 && currentDir.createDirectory(args[1])) {
                    System.out.println("✅ Diretório criado.");
                } else {
                    System.out.println("❌ Erro ao criar diretório.");
                }
                break;
            case "deletedir":
                if (args.length >= 2 && currentDir.deleteDirectory(args[1])) {
                    System.out.println("✅ Diretório apagado.");
                } else {
                    System.out.println("❌ Erro ao apagar diretório (pode estar vazio?).");
                }
                break;
            case "renamedir":
                if (args.length >= 3 && currentDir.renameDirectory(args[1], args[2])) {
                    System.out.println("✅ Diretório renomeado.");
                } else {
                    System.out.println("❌ Erro ao renomear diretório.");
                }
                break;
            default:
                System.out.println("⚠️ Comando não reconhecido no journal: " + op);
        }
    }

    private void listCurrentDirectory() {
        System.out.println("Diretórios:");
        for (Directory d : currentDir.getDirectories()) {
            System.out.println(" [D] " + d.getName());
        }
        System.out.println("Arquivos:");
        for (SimFile f : currentDir.getFiles()) {
            System.out.println(" [F] " + f.getName());
        }
    }

    private void showHelp() {
        System.out.println("Comandos disponíveis:");
        System.out.println(" createdir <nome>          - Criar diretório");
        System.out.println(" deletedir <nome>          - Apagar diretório (deve estar vazio)");
        System.out.println(" renamedir <velho> <novo>  - Renomear diretório");
        System.out.println(" createfile <nome>         - Criar arquivo");
        System.out.println(" deletefile <nome>         - Apagar arquivo");
        System.out.println(" renamefile <velho> <novo> - Renomear arquivo");
        System.out.println(" cd <nome> | cd ..         - Entrar em diretório ou voltar ao anterior");
        System.out.println(" ls                        - Listar conteúdo");
        System.out.println(" commit                    - Executar a operação pendente");
        System.out.println(" help                      - Mostrar comandos");
        System.out.println(" exit                      - Salvar e sair");
    }

    private String currentPath(Directory dir) {
        if (dir.getParent() == null) return "/root";
        return currentPath(dir.getParent()) + "/" + dir.getName();
    }

    public static void main(String[] args) {
        FileSystemSimulator simulator = new FileSystemSimulator();
        simulator.start();
    }
}
