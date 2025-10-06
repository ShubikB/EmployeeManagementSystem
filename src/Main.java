import java.util.Scanner;

import ui.GraphicalUI;
import ui.TextUI;

public class Main {
    public static void main(String[] args) {
        System.out.println("Choose your interface:");
        System.out.println("1. Text-Based Interface (TBI)");
        System.out.println("2. Graphical User Interface (GUI)");
        System.out.print("Enter your choice: ");
        
        try (Scanner startupScanner = new Scanner(System.in)) {
            String choice = startupScanner.nextLine();

            if ("2".equals(choice)) {
                // Launch the GUI
                javax.swing.SwingUtilities.invokeLater(() -> new GraphicalUI().setVisible(true));
            } else {
                // Launch the TBI
                TextUI textUI = new TextUI();
                textUI.run();
            }
        }
    }
}
