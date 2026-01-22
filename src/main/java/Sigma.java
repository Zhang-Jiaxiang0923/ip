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

        System.out.println("_".repeat(width));
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        System.out.println("_".repeat(width));
        while (true) {
            String s = sc.nextLine();
            if (s.equals("Bye") || s.equals("bye")) break;
            System.out.println(indentation + "_".repeat(width-4));
            System.out.println(indentation + s);
            System.out.println(indentation + "_".repeat(width-4));
        }
        System.out.println(indentation + "_".repeat(width-4));
        System.out.println(indentation + "Bye. Hope to see you again soon!");
        System.out.println(indentation + "_".repeat(width-4));
    }
}
