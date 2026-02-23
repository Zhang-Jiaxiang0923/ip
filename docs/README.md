# Sigma User Guide

![Sigma GUI Screenshot](docs/Ui.png)

Sigma is a lightweight task chatbot that helps you manage **todos**, **deadlines**, and **events** through a simple command interface.  
It supports both a **Command Line Interface (CLI)** and a **Graphical User Interface (GUI)** chat window.

---
## Features

- Add tasks: `todo`, `deadline`, `event`
- Manage tasks: `list`, `mark`, `unmark`, `delete`
- Archive specific task: `archive`
- Help and guidance: `help`
- Persistent storage (tasks are saved to disk)
- Available in **CLI** and **GUI**

---

## Quick Start

### Prerequisites
- **Java 17** (recommended: OpenJDK 17)
- Gradle wrapper is included (`./gradlew`)

### Run (CLI)
```bash
./gradlew run
```

## Usage Guide
### `help`

#### Shows a summary of all available commands, their formats, and examples.

Format:
- `help`

Example: 
- `help`
---
### `list`

#### Lists all tasks currently stored.

Format:
- `list`

Example:
- `list`
---

### `todo`

#### Adds a todo task.

Format:
- `todo <description>`

Example:
- `todo buy milk`

---

### `deadline` 

#### Adds a deadline task with a due date/time.

Format:
- `deadline <description> /by <date time>`

Example:
- `deadline return book /by 2026-02-22`

---

### `event`

#### Adds an event task with a start and end date/time.

Format:
- `event <description> /from <date time> /to <date time>`

Example:
- `event team meeting /from 2026-02-22 1400 /to 2026-02-22`

---

### `mark`
#### Marks a task as done.

Format:
- `mark <index>`

Example:
- `mark 1`
---

### `unmark`
#### Unmarks a task (sets it back to not done).

Format:
- `unmark <index>`

Example:
- `unmark 1`

---

### `delete`
#### Deletes a task by index.

Format:
- `delete <index>`

Example:
- `delete 1`

---

### `bye`
#### Exits Sigma

Format
- `bye`

Example
- `bye`

---

## Project Structure (High-Level)

Typical components:
- **Parser / ParsedInput**: parses user input into structured commands
- **TaskList**: stores tasks in memory 
- **Storage**: loads/saves tasks to disk
- **Ui**: formats responses for CLI and GUI
- **MainWindow / DialogBox(GUI)**: renders the chat UI and handles user interactions