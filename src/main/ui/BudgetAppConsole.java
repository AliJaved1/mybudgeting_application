package ui;

import model.BudgetList;
import model.Expense;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Budgeting application
public class BudgetAppConsole {
    private static final String JSON_STORE = "./data/budgetList.json";
    private BudgetList list;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the budgeting app console version
    public BudgetAppConsole() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBudgetApp();
    }

    // MODIFIES: this
    // EFFECTS: generates new console based input
    private void runBudgetApp() {
        input = new Scanner(System.in);
        boolean running = true;
        String userInput;
        start();

        while (running) {
            homeScreen();
            userInput = input.next();
            userInput = userInput.toLowerCase();

            if (userInput.equals("e")) {
                System.out.print("\nThank you for using this Application. Goodbye!");
                running = false;
            } else {
                executeProcess(userInput);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS:  prints each expense as a list, for all expenses in the list
    private void printExpenses() {
        int i = 0;
        for (String s : list.expensesString()) {
            i++;
            System.out.println(i + ". " + s);
        }
    }

    // MODIFIES: this
    // EFFECTS: initial interface presented to user, prints command prompts and completes prompts
    private void start() {
        input = new Scanner(System.in);
        System.out.print("Please enter the budget list Name: ");
        String listName = input.next();
        System.out.print("Please enter your budget: ");
        double myBudget = input.nextDouble();
        list = new BudgetList(listName, myBudget);
        System.out.println("New budget list \"" + listName + "\" has been created!");
        System.out.println("Your Budget is $" + myBudget + ".");
    }

    // EFFECTS: main interface screen, presented after start(), lists out options for the user
    private void homeScreen() {
        System.out.println("\nMain menu.");
        System.out.println("Your budget list: \"" + list.getName() + "\". Your budget is: $" + list.getBudget() + ".");
        printExpenses();
        System.out.println("\nChoose:");
        System.out.println("\tAdd expense: a");
        System.out.println("\tRemove expense: r");
        System.out.println("\tModify expense: m");
        System.out.println("\tCheck budget: b");
        System.out.println("\tView list: v");
        System.out.println("\tSave list: s");
        System.out.println("\tLoad list: l");
        System.out.println("\tExit: e");
    }

    // MODIFIES: this
    // EFFECTS: executes commands inputted by the user, presented in homeScreen()
    private void executeProcess(String userInput) {
        if (userInput.equals("a")) {
            newExpense();
        } else if (userInput.equals("r")) {
            deleteExpense();
        } else if (userInput.equals("v")) {
            printExpenses();
        } else if (userInput.equals("m")) {
            modifyExpense();
        } else if (userInput.equals("b")) {
            checkBudget();
        } else if (userInput.equals("s")) {
            saveBudgetList();
        } else if (userInput.equals("l")) {
            loadBudgetList();
        } else {
            System.out.println("\nError! Incorrect user input. Please try again.");
        }
    }

    // MODIFIES: this, BudgetList
    // EFFECTS: generates a new expense in BudgetList using inputs from user
    private void newExpense() {
        System.out.println("\nExpense Name:");
        String itemName = input.next();
        System.out.println("\nExpense Cost:");
        double itemValue = input.nextDouble();
        Expense e = new Expense(itemName, itemValue);
        list.addExpense(e);
        System.out.println("\nExpense added successfully.");
    }

    // REQUIRES: inputted index exists
    // MODIFIES: this, BudgetList
    // EFFECTS: removes an existing expense in BudgetList using inputs from user
    private void deleteExpense() {
        System.out.println("\nExpense Number in list to delete:");
        int inputIndex = input.nextInt() - 1;
        list.removeExpense(inputIndex);
        System.out.println("\nExpense removed successfully.");
    }

    // REQUIRES: inputted index exists
    // MODIFIES: this, BudgetList, Expense
    // EFFECTS: modifies an existing expense in BudgetList using inputs from user
    private void modifyExpense() {
        System.out.println("\nExpense Number in list to modify:");
        int inputIndex = input.nextInt() - 1;
        System.out.println("New Expense name:");
        String inputName = input.next();
        System.out.println("New Expense cost:");
        double inputCost = input.nextDouble();
        list.editExpense(inputIndex, inputName, inputCost);
        System.out.println("\nExpense edited successfully.");
    }

    // EFFECTS: prints out if the list is over budget or not, and by how much
    private void checkBudget() {
        if (list.overBudget()) {
            System.out.println("You are over your budget by $" + list.overBudgetAmount());
        } else {
            System.out.println("You are still within your budget! You have an extra $" + list.overBudgetAmount());
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current budgetList to file
    private void saveBudgetList() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
            System.out.println("Saved " + list.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the budgetList from file
    private void loadBudgetList() {
        try {
            list = jsonReader.read();
            System.out.println("Loaded " + list.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
