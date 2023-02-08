package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {
    private Expense testExpense;

    @BeforeEach
    void runBefore() {
        testExpense = new Expense("Subscription", 15.0);
    }

    @Test
    void testConstructor() {
        assertEquals("Subscription", testExpense.getName());
        assertEquals(15.0, testExpense.getValue());
    }

    @Test
    void testChangeName() {
        testExpense.changeName("Netflix");
        assertEquals("Netflix", testExpense.getName());
    }

    @Test
    void testChangeValue() {
        testExpense.changeValue(19.99);
        assertEquals(19.99, testExpense.getValue());
        testExpense.changeValue(-200);
        assertEquals(-200, testExpense.getValue());
    }
}
