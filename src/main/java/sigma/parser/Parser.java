package sigma.parser;

import sigma.command.CommandType;
import sigma.exception.MissingElementException;
import sigma.exception.UnknownCommandException;

/**
 * Parses the user input of Sigma application.
 */
public class Parser {
    private Parser() {

    }

    /**
     * Returns parsed input of user unstructured input.
     *
     * @param input Input of Sigma application.
     * @return Parsed input.
     * @throws MissingElementException If some element of input is missed.
     * @throws UnknownCommandException If input command type is unknown.
     * @throws NumberFormatException   If an index in the input is not a valid integer.
     */
    public static ParsedInput parseInput(String input)
            throws MissingElementException, UnknownCommandException, NumberFormatException {
        String[] parts = input.trim().split("\\s+", 2);
        assert parts.length >= 1 : "length of parts should >= 1";
        String s = parts[0];
        switch (s) {
        case "bye": {
            return new ParsedInput(CommandType.BYE);
        }
        case "list": {
            return new ParsedInput(CommandType.LIST);
        }
        case "mark": {
            if (parts.length == 1) {
                throw new MissingElementException("Oops, missing number. Which task do you want to mark?");
            } else {
                String taskNum = parts[1];
                int index = Integer.parseInt(taskNum) - 1;
                return new ParsedInput(CommandType.MARK, index);
            }
        }
        case "unmark": {
            if (parts.length == 1) {
                throw new MissingElementException("Oops, missing number. Which task do you want to unmark?");
            } else {
                String taskNum = parts[1];
                int index = Integer.parseInt(taskNum) - 1;
                return new ParsedInput(CommandType.UNMARK, index);
            }
        }
        case "delete": {
            if (parts.length == 1) {
                throw new MissingElementException("Oops, missing number. Which task do you want to delete?");
            } else {
                String taskNum = parts[1];
                int index = Integer.parseInt(taskNum) - 1;
                return new ParsedInput(CommandType.DELETE, index);
            }
        }
        case "look": {
            if (parts.length == 1) {
                throw new MissingElementException("Oops, missing keywords. Which keyword do you want to look for?");
            } else {
                return new ParsedInput(CommandType.LOOK, parts[1]);
            }
        }
        case "todo": {
            if (parts.length == 1) {
                throw new MissingElementException("Oops, could you give me the todo description?");
            } else {
                String description = parts[1];
                return new ParsedInput(CommandType.TODO, description);
            }
        }
        case "deadline": {
            if (parts.length == 1) {
                throw new MissingElementException("Could you give me task description and deadline?");
            } else {
                String[] p2 = parts[1].split("(?:^|\\s+)/by\\s+", 2);
                if (p2.length == 1) {
                    throw new MissingElementException("Could you give me the deadline?");
                } else if (p2[0].trim().isEmpty()) {
                    throw new MissingElementException("Could you give me task description?");
                } else {
                    return new ParsedInput(CommandType.DEADLINE, p2[0].trim(), p2[1].trim());
                }
            }
        }
        case "event": {
            if (parts.length == 1) {
                throw new MissingElementException(
                        "Could you give me the task description, start time and end time?"
                );
            } else {
                String[] p2 = parts[1].split("(?:^|\\s+)/from\\s+", 2);
                if (p2.length == 1) {
                    throw new MissingElementException("Could you give me the start time?");
                } else if (p2[0].trim().isEmpty()) {
                    throw new MissingElementException("Could you give me the task description?");
                } else {
                    String[] p3 = p2[1].trim().split("(?:^|\\s+)/to\\s+", 2);
                    if (p3.length == 1) {
                        throw new MissingElementException("Could you give me the end time");
                    } else if (p3[0].trim().isEmpty()) {
                        throw new MissingElementException("Oops, the start time is empty QAQ");
                    } else if (p3[1].trim().isEmpty()) {
                        throw new MissingElementException("Oops, the end time is empty QAQ");
                    } else {
                        return new ParsedInput(
                                CommandType.EVENT, p2[0].trim(), p3[0].trim(), p3[1].trim()
                        );

                    }
                }
            }
        }
        default: {
            throw new UnknownCommandException("unknown command");
        }
        }

    }

}
