# 🗂️ Simulador de Sistema de Arquivos com Journaling

## 🔧 Como rodar a aplicação (Java)

### ✅ Pré-requisitos

- Java JDK 11 ou superior
- Terminal (cmd, PowerShell, Git Bash ou outro shell compatível)
- Estrutura de pastas:

### ▶️ Compilar

Abra o terminal na raiz do projeto e execute:

```bash
javac -d bin src/*.java
````

Isso compila todos os arquivos `.java` da pasta `src/` e coloca os arquivos `.class` em `bin/`.

### 🚀 Executar

Após compilar, execute o sistema com:

```bash
java -cp bin FileSystemSimulator
```

> O sistema criará automaticamente os arquivos `filesystem.beni` e `journal.log`.

---

## 📘 Descrição do Projeto

Este projeto implementa um **simulador de sistema de arquivos**, com suporte a:

* Criação, remoção e renomeação de arquivos e diretórios
* Navegação por diretórios
* Registro persistente do sistema de arquivos
* Histórico de comandos com data e hora (journaling)

---

## 🧠 Conceito de Journaling

Todas as operações executadas são registradas no arquivo `journal.log` com **data e hora**, funcionando como um log permanente do que foi feito no sistema.

### Exemplo de `journal.log`:

```
2025-06-03 21:15:00 - createdir documentos
2025-06-03 21:15:05 - createfile notas.txt
2025-06-03 21:15:12 - cd documentos
```

---

## 📦 Estrutura das Classes

| Arquivo                    | Função                                                  |
| -------------------------- | ------------------------------------------------------- |
| `Directory.java`           | Representa diretórios e gerencia arquivos/subdiretórios |
| `SimFile.java`             | Representa arquivos individuais                         |
| `Journal.java`             | Registra comandos executados com data e hora            |
| `FileSystemSimulator.java` | Classe principal com o shell interativo para o usuário  |

---

## 💬 Comandos disponíveis

| Comando                      | Descrição                                |
| ---------------------------- | ---------------------------------------- |
| `createdir <nome>`           | Cria um novo diretório                   |
| `deletedir <nome>`           | Remove um diretório vazio                |
| `renamedir <antigo> <novo>`  | Renomeia um diretório                    |
| `createfile <nome>`          | Cria um novo arquivo                     |
| `deletefile <nome>`          | Remove um arquivo                        |
| `renamefile <antigo> <novo>` | Renomeia um arquivo                      |
| `cd <nome>`                  | Entra em um subdiretório                 |
| `cd ..`                      | Volta para o diretório anterior          |
| `ls` ou `list`               | Lista o conteúdo do diretório atual      |
| `journal`                    | Exibe o histórico de comandos executados |
| `exit`                       | Salva o sistema e encerra o programa     |
| `help`                       | Exibe todos os comandos disponíveis      |

---

## 👨‍💻 Exemplo de uso

```
/root > createdir projetos
Diretório criado.

/root > cd projetos
/root/projetos > createfile plano.txt
Arquivo criado.

/root/projetos > journal
📓 Histórico de comandos executados:
 - 2025-06-03 21:20:05 - createdir projetos
 - 2025-06-03 21:20:10 - cd projetos
 - 2025-06-03 21:20:15 - createfile plano.txt
```

---

## 📝 Autores

* Nome 1 – RA 123456
* Nome 2 – RA 654321

> Substitua pelos nomes reais e RAs da dupla antes de enviar

---

## 🗃️ Observações

* O arquivo `filesystem.beni` armazena os dados do sistema de arquivos entre sessões.
* O `journal.log` registra todos os comandos executados com data e hora.
* Arquivos como `.class`, `.log` e o `filesystem.beni` devem ser ignorados pelo Git. Exemplo de `.gitignore`:

```gitignore
bin/
*.class
journal.log
filesystem.beni
```
