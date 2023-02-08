package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BudgetListTest {
    private BudgetList testBudgetList;

    @BeforeEach
    void runBefore() {
        testBudgetList = new BudgetList("My Budget", 5000);
    }

    @Test
    void testConstructor() {
        assertEquals("My Budget", testBudgetList.getName());
        assertEquals(5000, testBudgetList.getBudget());
    }

    @Test
    void testAddExpense() {
        Expense testExpense1 = new Expense("Food", 500);
        testBudgetList.addExpense(testExpense1);
        assertEquals(testExpense1, testBudgetList.getExpense(0));
        Expense testExpense2 = new Expense("Electricity", 150);
        testBudgetList.addExpense(testExpense2);
        assertEquals(testExpense2, testBudgetList.getExpense(1));
    }

    @Test
    void testRemoveExpense() {
        Expense testExpense1 = new Expense("Food", 500);
        testBudgetList.addExpense(testExpense1);
        Expense testExpense2 = new Expense("Electricity", 150);
        testBudgetList.addExpense(testExpense2);
        int initSize = testBudgetList.listSize();
        testBudgetList.removeExpense(1);
        int finalSize = testBudgetList.listSize();
        assertEquals(initSize - 1, finalSize);
    }

    @Test
    void testGetExpense() {
        Expense testExpense1 = new Expense("Food", 500);
        testBudgetList.addExpense(testExpense1);
        assertEquals(testExpense1, testBudgetList.getExpense(0));
    }

    @Test
    void testEditExpense() {
        Expense testExpense1 = new Expense("Food", 500);
        testBudgetList.addExpense(testExpense1);
        testBudgetList.editExpense(0, "Groceries", 400);
        assertEquals("Groceries", testBudgetList.getExpense(0).getName());
        assertEquals(400, testBudgetList.getExpense(0).getValue());
    }

    @Test
    void testTotalExpenses() {
        Expense testExpense1 = new Expense("Food", 500);
        testBudgetList.addExpense(testExpense1);
        assertEquals(500.0, testBudgetList.totalExpenses());
        Expense testExpense2 = new Expense("Electricity", 150);
        testBudgetList.addExpense(testExpense2);
        assertEquals(500.0 + 150.0, testBudgetList.totalExpenses());
    }

    @Test
    void testExpensesString() {
        Expense testExpense1 = new Expense("Food", 500);
        testBudgetList.addExpense(testExpense1);
        List<String> list = new ArrayList<>();
        list.add("Food = $500.0");
        assertEquals(list, testBudgetList.expensesString());
    }

    @Test
    void testOverBudget() {
        Expense testExpense1 = new Expense("Food", 500);
        testBudgetList.addExpense(testExpense1);
        assertFalse(testBudgetList.overBudget());
        Expense testExpense2 = new Expense("Car", 4500);
        testBudgetList.addExpense(testExpense2);
        assertFalse(testBudgetList.overBudget());
        Expense testExpense3 = new Expense("Water bottle", 1);
        testBudgetList.addExpense(testExpense3);
        assertTrue(testBudgetList.overBudget());
    }

    @Test
    void testOverBudgetAmount() {
        Expense testExpense1 = new Expense("Food", 500);
        testBudgetList.addExpense(testExpense1);
        assertEquals(Math.abs(5000 - 500), testBudgetList.overBudgetAmount());
        Expense testExpense2 = new Expense("Food", 5000);
        testBudgetList.addExpense(testExpense2);
        assertEquals(Math.abs(5000 - 5500), testBudgetList.overBudgetAmount());
    }


}