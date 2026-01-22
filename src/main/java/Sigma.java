import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Sigma {
    public static void main(String[] args) {
        String logo =
                " ____   ___    ____   __  __     _    \n"
                        + "/ ___| |_ _|  / ___| |  \\/  |   / \\   \n"
                        + "\\___ \\  | |  | |  _  | |\\/| |  / _ \\  \n"
                        + " ___) | | |  | |_| | | |  | | / ___ \\ \n"
                        + "|____/ |___|  \\____| |_|  |_|/_/   \\_\\\n";
        String name = "Sigma";
        int width = 60;
        String indentation = "    ";
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello from\n" + logo);

        List<String> todo = new ArrayList<>();

        System.out.println(indentation + "_".repeat(width));
        System.out.println(indentation + "Hello! I'm " + name);
        System.out.println(indentation + "What can I do for you?");
        System.out.println(indentation + "_".repeat(width));
        while (true) {
            String s = sc.nextLine();
            System.out.println(indentation + "_".repeat(width));
            if (s.equals("Bye") || s.equals("bye")) break;
            if (s.equals("list") || s.equals("List")) {
                Integer num = 1;
                if (todo.isEmpty()){
                    System.out.println(indentation + "Todo list is empty :)");
                } else {
                    for (String task : todo) {
                        System.out.println(indentation + String.valueOf(num) + ". " + task);
                        num++;
                    }
                }
            } else {
                System.out.println(indentation + "added: " + s);
                todo.add(s);
            }
            System.out.println(indentation + "_".repeat(width));
        }
        System.out.println(indentation + "Bye. Hope to see you again soon!");
        System.out.println(indentation + "_".repeat(width));
    }
}
