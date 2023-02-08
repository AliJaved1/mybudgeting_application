package persistence;

import model.BudgetList;
import model.Expense;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderFileDoesNotExist() {
        JsonReader reader = new JsonReader("./data/fileDoesNotExist.json");
        try {
            BudgetList bl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected result
        }
    }

    @Test
    void testReaderBlankBudgetList() {
        JsonReader reader = new JsonReader("./data/TestReaderBlankBudgetList.json");
        try {
            BudgetList bl = reader.read();
            assertEquals("Empty_BudgetList", bl.getName());
            assertEquals(1000, bl.getBudget());
            assertEquals(0, bl.listSize());
        } catch (IOException e) {
            fail("Unable to read from file!");
        }
    }

    @Test
    void testReaderActualBudgetList() {
        JsonReader reader = new JsonReader("./data/testReaderActualBudgetList.json");
        try {
            BudgetList bl = reader.read();
            assertEquals("Average_BudgetList", bl.getName());
            assertEquals(250.0, bl.getBudget());
            List<Expense> expenses = bl.getExpenses();
            assertEquals(3, expenses.size());
            checkExpense("Netflix", 30.0, expenses.get(0));
            checkExpense("Water", 50.0, expenses.get(1));
            checkExpense("Internet", 90.0, expenses.get(2));
        } catch (IOException e) {
            fail("Unable to read from file!");
        }
    }
}
