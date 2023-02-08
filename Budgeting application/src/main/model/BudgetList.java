package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents a budget list, with a name, list of expenses, and budget ($)
public class BudgetList implements Writable {
    private String name;
    private List<Expense> expenseList = new ArrayList<Expense>();
    private double budget;

    // REQUIRES: budgetName length > 0
    // EFFECTS: name is set to budgetName, budget is set to totalBudget if totalBudget >= 0,
    //          otherwise budget is set to 0.
    public BudgetList(String budgetName, double totalBudget) {
        name = budgetName;
        if (totalBudget >= 0) {
            budget = totalBudget;
        } else {
            budget = 0;
        }
    }

    // EFFECTS: returns BudgetList name
    public String getName() {
        return name;
    }

    // EFFECTS: returns BudgetList budget
    public double getBudget() {
        return budget;
    }

    // EFFECTS:  returns size (length) of BudgetList
    public int listSize() {
        return expenseList.size();
    }

    // EFFECTS: returns list of expenses
    public List<Expense> getExpenses() {
        return expenseList;
    }

    // MODIFIES: this
    // EFFECTS: adds an expense item to list
    public void addExpense(Expense expense) {
        expenseList.add(expense);
        EventLog.getInstance().logEvent(new Event("Added an expense to budget list: " + expense.getName()
                + " - $" + expense.getValue() + "."));
    }

    // REQUIRES: list must not be empty (list.size() > 0)
    // MODIFIES: this
    // EFFECTS: removes an expense item from list
    public void removeExpense(int index) {
        EventLog.getInstance().logEvent(new Event("Removed an expense from budget list: "
                + getExpense(index).getName() + " - $" + getExpense(index).getValue() + "."));
        expenseList.remove(index);
    }

    // EFFECTS: returns an expense item from list
    public Expense getExpense(int index) {
        return expenseList.get(index);
    }

    // MODIFIES: this
    // EFFECTS: modifies an expense item in list
    public void editExpense(int index, String name, double value) {
        Expense editor = getExpense(index);
        EventLog.getInstance().logEvent(new Event("Edited an expense. Original: " + editor.getName() + " - $"
                + editor.getValue() + ". New: " + name + " - $" + value + "."));
        if (name.length() > 0) {
            editor.changeName(name);
        }
        if (!(value == editor.getValue())) {
            editor.changeValue(value);
        }
    }


    // EFFECTS: returns list of string of each expense, and it's cost
    public List<String> expensesString() {
        List<String> expenses = new ArrayList<String>();
        for (Expense e : expenseList) {
            expenses.add(e.getName() + " = $" + e.getValue());
        }
        return expenses;
    }

    // EFFECTS: sums all the values ($) of each expense item in the list of expenses
    public double totalExpenses() {
        double sum = 0;
        if (expenseList.size() != 0) {
            for (Expense e : expenseList) {
                sum += e.getValue();
            }
        }
        return sum;
    }

    // EFFECTS: checks if totalExpenses > budget, and returns true if so, otherwise returns false
    public boolean overBudget() {
        if (totalExpenses() > budget) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns the difference between budget and total expenses
    public double overBudgetAmount() {
        double difference = budget - totalExpenses();
        return Math.abs(difference);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("expenseList", expensesToJson());
        json.put("budget", budget);
        return json;
    }

    // EFFECTS: returns things in the budgetList as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense e : expenseList) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }

    private EventLog printEventLog() {
        return EventLog.getInstance();
    }
}