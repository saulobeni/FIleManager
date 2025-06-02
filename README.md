# 🗂️ Simulador de Sistema de Arquivos com Journaling

## 📄 Resumo

Este projeto apresenta um simulador de sistema de arquivos desenvolvido em Java, com suporte a operações básicas como criação, exclusão, renomeação e navegação entre arquivos e diretórios. O sistema implementa **journaling real (write-ahead logging)**, garantindo integridade dos dados ao registrar e confirmar operações antes de sua execução definitiva.

---

## 📚 Introdução

O gerenciamento de arquivos é um componente essencial dos sistemas operacionais modernos. Entender como os arquivos e diretórios são organizados, manipulados e protegidos contra falhas é crucial para compreender o funcionamento interno do sistema operacional.

Este projeto simula o comportamento de um sistema de arquivos, incluindo um mecanismo de **journaling**, que registra operações críticas antes de serem efetivamente aplicadas, garantindo consistência em caso de falhas.

---

## 🎯 Objetivo

Desenvolver um simulador de sistema de arquivos com as seguintes características:

- Linguagem: **Java**
- Interface: Modo **shell interativo**
- Suporte a:
  - Criação e remoção de arquivos
  - Renomeação de arquivos e diretórios
  - Criação, remoção e navegação entre diretórios
  - Listagem de conteúdo de diretórios
- Journaling persistente (arquivo `journal.log`)
- Salvamento do sistema de arquivos (`filesystem.beni`)

---

## 🛠️ Metodologia

A aplicação é composta por classes Java que representam os elementos do sistema de arquivos. As operações do usuário são registradas no **journal** antes de serem executadas. O sistema só aplica a ação quando o usuário confirma com o comando `commit`, simulando um processo de journaling real.

### Tecnologias

- Java (JDK 11+)
- Interface em terminal
- Persistência com serialização (`ObjectOutputStream`)
- Arquivo de log (`journal.log`) para o journaling

---

## 🧱 Arquitetura do Sistema

### 📁 Estruturas de Dados

- `SimFile`: representa um arquivo.
- `Directory`: representa um diretório com arquivos e subdiretórios.
- `FileSystemSimulator`: classe principal que controla a execução.
- `Journal`: registra operações pendentes em disco.

### 🧠 Journaling (Write-Ahead Logging)

O journal funciona da seguinte forma:

1. O usuário executa um comando (`createdir pasta1`)
2. O comando é salvo no `journal.log`
3. O sistema **aguarda** confirmação via `commit`
4. Ao usar `commit`, o sistema executa a operação e limpa o journal

Isso garante que **nenhuma operação crítica será perdida** ou aplicada de forma incorreta em caso de falhas.

---

## 💻 Funcionalidades

### Comandos disponíveis:

| Comando                        | Descrição                                    |
|-------------------------------|----------------------------------------------|
| `createfile <nome>`           | Cria um novo arquivo                         |
| `deletefile <nome>`           | Apaga um arquivo                             |
| `renamefile <antigo> <novo>`  | Renomeia um arquivo                          |
| `createdir <nome>`            | Cria um novo diretório                       |
| `deletedir <nome>`            | Remove um diretório vazio                    |
| `renamedir <antigo> <novo>`   | Renomeia um diretório                        |
| `cd <nome>`                   | Entra em um subdiretório                     |
| `cd ..`                       | Volta para o diretório pai                   |
| `ls` ou `list`                | Lista arquivos e diretórios                  |
| `commit`                      | Executa a operação pendente do journal       |
| `exit`                        | Salva e encerra o sistema                    |
| `help`                        | Mostra a lista de comandos                   |

---

## 🔄 Fluxo de Operação

```text
> createdir projetos
📝 Operação 'createdir projetos' registrada. Use 'commit' para aplicar.
> commit
✅ Diretório criado.
