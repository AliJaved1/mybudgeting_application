package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new FixedGUI();
            //new BudgetAppConsole();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run the BudgetList application: file not found!");
        }
    }
}
