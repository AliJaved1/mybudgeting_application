package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an expense name, and monthly cost ($)
public class Expense implements Writable {
    private String name;
    private double value;

    // REQUIRES: expenseName length > 0
    // EFFECTS: constructs an expense with name = expenseName, and value = expenseValue if provided, else value = 0
    public Expense(String expenseName, double expenseValue) {
        name = expenseName;
        if (expenseValue >= 0) {
            value = expenseValue;
        } else {
            value = 0;
        }
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    // REQUIRES: newName length > 0
    // MODIFIES: this
    // EFFECTS: changes the name of the Expense item to a new name.
    public void changeName(String newName) {
        name = newName;
    }

    // MODIFIES: this
    // EFFECTS: changes the value of the Expense item to a new value.
    public void changeValue(double newValue) {
        value = newValue;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("value", value);
        return json;
    }
}
