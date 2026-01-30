package sigma.parser;

import org.junit.jupiter.api.Test;
import sigma.command.CommandType;
import sigma.parser.ParsedInput;
import sigma.parser.Parser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void parseInputTest() {
        ParsedInput deadline = Parser.parseInput("deadline do homework /by 2026-01-31");
        assertEquals(CommandType.DEADLINE, deadline.getCommand());
        assertEquals("do homework", deadline.getDescription());
        assertEquals(LocalDate.parse("2026-01-31"), deadline.getEnd());

        ParsedInput todo = Parser.parseInput("todo read books");
        assertEquals(CommandType.TODO, todo.getCommand());
        assertEquals("read books", todo.getDescription());

        ParsedInput event = Parser.parseInput("event project meeting /from 2026-01-30 /to 2026-01-30");
        assertEquals(CommandType.EVENT, event.getCommand());
        assertEquals("project meeting", event.getDescription());
        assertEquals(LocalDate.parse("2026-01-30"), event.getStart());
        assertEquals(LocalDate.parse("2026-01-30"), event.getEnd());
    }
}
