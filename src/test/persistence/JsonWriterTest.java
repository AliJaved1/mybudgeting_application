package persistence;

import model.BudgetList;
import model.Expense;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterFileNotValid() {
        try {
            BudgetList bl = new BudgetList("MyList", 5000);
            JsonWriter writer = new JsonWriter("./data/my\0invalid:filename.json");
            writer.open();
            fail("IOException was expected!");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterBlankBudgetList() {
        try {
            BudgetList bl = new BudgetList("MyList", 5000);
            JsonWriter writer = new JsonWriter("./data/TestWriterBlankBudgetList.json");
            writer.open();
            writer.write(bl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterBlankBudgetList.json");
            bl = reader.read();
            assertEquals("MyList", bl.getName());
            assertEquals(5000, bl.getBudget());
            assertEquals(0, bl.listSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown!");
        }
    }

    @Test
    void testWriterActualBudgetList() {
        try {
            BudgetList bl = new BudgetList("MyList", 2000);
            bl.addExpense(new Expense("Food", 40.0));
            bl.addExpense(new Expense("Gas", 70.0));
            JsonWriter writer = new JsonWriter("./data/testWriterActualBudgetList.json");
            writer.open();
            writer.write(bl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterActualBudgetList.json");
            bl = reader.read();
            assertEquals("MyList", bl.getName());
            List<Expense> expenses = bl.getExpenses();
            assertEquals(2, expenses.size());
            checkExpense("Food", 40.0, expenses.get(0));
            checkExpense("Gas", 70.0, expenses.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown!");
        }
    }
}
