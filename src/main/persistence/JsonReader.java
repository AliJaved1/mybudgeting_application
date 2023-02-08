package persistence;

import model.BudgetList;
import model.Expense;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Inspiration and template taken from JsonSerializationDemo obtained from CPSC 210 EdX page

// Represents a reader that reads BudgetList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads budgetList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BudgetList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBudgetList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses BudgetList from JSON object and returns it
    private BudgetList parseBudgetList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Double budget = jsonObject.getDouble("budget");
        BudgetList bl = new BudgetList(name, budget);
        addExpenses(bl, jsonObject);
        return bl;
    }

    // MODIFIES: bl
    // EFFECTS: parses expenses from JSON object and adds them to BudgetList
    private void addExpenses(BudgetList bl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expenseList");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(bl, nextExpense);
        }
    }

    // MODIFIES: bl
    // EFFECTS: parses expense from JSON object and adds it to budgetList
    private void addExpense(BudgetList bl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Double value = Double.valueOf(jsonObject.getDouble("value"));
        Expense expense = new Expense(name, value);
        bl.addExpense(expense);
    }
}
