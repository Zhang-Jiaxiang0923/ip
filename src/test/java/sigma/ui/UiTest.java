package sigma.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class UiTest {

    @Test
    public void goodbyeTest() {
        Ui ui = new Ui();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ui.showGoodbye();
        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("Bye. Hope to see you again soon!"));

    }
}
