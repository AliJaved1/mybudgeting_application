package ui;

import model.BudgetList;
import model.Event;
import model.EventLog;
import model.Expense;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Heavy inspiration taken from:
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
// https://www.youtube.com/watch?v=5o3fMLPY7qY&t=45s
// https://stackoverflow.com/questions/8176965/how-to-add-element-to-existing-jlist
// https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html

// will create the GUi needed for BudgetList, extends JPanel and implements ListSelectionListener
public class BudgetAppGUI extends JPanel implements ListSelectionListener {
    private static final String JSON_STORE = "./data/budgetList.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DefaultListModel model;
    private JList modelList;
    private JPanel buttonPanel;
    private JTextField expenseName;
    private JTextField expenseValue;
    private BudgetList list;
    private AddExpenseListener expenseListener;
    private JButton addExpenseButton;
    private JButton deleteExpenseButton;
    private JButton saveButton;
    private JButton loadButton;

    // EFFECTS: constructs a GUI for the JList
    public BudgetAppGUI() {
        super(new BorderLayout());
        model = new DefaultListModel();
        model.addElement("Expenses");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        list = new BudgetList("BudgetList", 0);
        createModelList();
        JScrollPane scrollPane = new JScrollPane(modelList);
        addExpenseButton = new JButton("Add Expense");
        EventLog.getInstance();

        createButtons();

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: calls button generator methods
    private void createButtons() {
        createExpenseListener();
        createDeleteButton();
        createSaveButton();
        createLoadButton();
        createButtonPanel();
    }

    // MODIFIES: this, modelList
    // EFFECTS: creates new JList and sets it as a visible list in GUI
    private void createModelList() {
        modelList = new JList(model);
        modelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modelList.setSelectedIndex(0);
        modelList.addListSelectionListener(this);
        modelList.setVisibleRowCount(5);
    }

    // MODIFIES: this, expenseListener, expenseName, expenseValue
    // EFFECTS: creates expenseListener to add actionListeners for button functionality to Add Expense
    private void createExpenseListener() {
        expenseListener = new AddExpenseListener(addExpenseButton);
        addExpenseButton.setActionCommand("Add Expense");
        addExpenseButton.addActionListener(expenseListener);
        addExpenseButton.setEnabled(false);

        expenseName = new JTextField(20);
        expenseName.addActionListener(expenseListener);
        expenseName.getDocument().addDocumentListener(expenseListener);

        expenseValue = new JTextField(10);
        expenseValue.addActionListener(expenseListener);
        expenseValue.getDocument().addDocumentListener(expenseListener);
    }

    // MODIFIES: this, deleteExpenseButton
    // EFFECTS: instantiates new JButton and sets ActionListener for DeleteExpenseListener
    private void createDeleteButton() {
        deleteExpenseButton = new JButton("Delete Expense");
        deleteExpenseButton.setActionCommand("Delete Expense");
        deleteExpenseButton.addActionListener(new DeleteExpenseListener());
    }

    // MODIFIES: this, saveButton
    // EFFECTS: instantiates new JButton and sets ActionListener for SaveListener
    private void createSaveButton() {
        saveButton = new JButton("Save");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(new SaveListener());
    }

    // MODIFIES: this, loadButton
    // EFFECTS: instantiates new JButton and sets ActionListener for loadListener
    private void createLoadButton() {
        loadButton = new JButton("Load");
        loadButton.setActionCommand("Load");
        loadButton.addActionListener(new LoadListener());
    }

    // MODIFIES: this
    // EFFECTS: creates button panel for AddExpenseButton, saveButton, loadButton, and deleteExpenseButton
    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

        buttonPanel.add(saveButton);
        buttonPanel.add(addExpenseButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(expenseName);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(expenseValue);
        buttonPanel.add(deleteExpenseButton);
        buttonPanel.add(loadButton);
    }


    // action listener class for deleteExpenseButton
    private class DeleteExpenseListener implements ActionListener {
        // MODIFIES: FinishedGUI, BudgetList
        // EFFECTS: removes selected expense from FinishedGUI and BudgetList
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = modelList.getSelectedIndex();
            list.removeExpense(index - 1);
            model.remove(index);

            int modelSize = model.getSize();

            if (modelSize == 0) {
                deleteExpenseButton.setEnabled(false);
            } else {
                if (index == model.getSize()) {
                    index--;
                }

                modelList.setSelectedIndex(index);
                modelList.ensureIndexIsVisible(index);
            }
        }
    }

    // Performs add Expense action when the AddExpense button is pressed
    private class AddExpenseListener implements ActionListener, DocumentListener {
        private boolean enabled = false;
        private JButton btn;

        public AddExpenseListener(JButton btn) {
            this.btn = btn;
        }

         // MODIFIES: FinishedGUI, BudgetList
         // EFFECTS: Action listener for BudgetList
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = expenseName.getText();
            String value = expenseValue.getText();

            int index = modelList.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            model.addElement(name + ": $" + value);
            list.addExpense(new Expense(name, Double.parseDouble(value)));

            expenseName.requestFocusInWindow();
            expenseName.setText("Name");

            expenseValue.requestFocusInWindow();
            expenseValue.setText("Cost");

            modelList.setSelectedIndex(index);
            modelList.ensureIndexIsVisible(index);
        }

         // MODIFIES: this
         // EFFECTS: enables button
        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

         // MODIFIES: this
         // EFFECTS: calls blankFieldHandler for empty fields
        @Override
        public void removeUpdate(DocumentEvent e) {
            blankFieldHandler(e);
        }

        // MODIFIES: this
        // EFFECTS: calls enableButton() if both fields are filled
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!blankFieldHandler(e)) {
                enableButton();
            }
        }

        // MODIFIES: this
        // EFFECTS: sets button as enabled
        private void enableButton() {
            if (!enabled) {
                btn.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: if fields are blank, disables button
        private boolean blankFieldHandler(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                btn.setEnabled(false);
                enabled = false;
                return true;
            }
            return false;
        }
    }

    // performs the save method with SaveListener is called
    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveBudgetList();
        }
    }

    // performs the load method when LoadListener is called
    private class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadBudgetList();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current budgetList to file
    private void saveBudgetList() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: BudgetList
    // EFFECTS: loads the budgetList from file
    private void loadBudgetList() {
        try {
            list = jsonReader.read();
            model.removeAllElements();
            model.addElement("Expenses");
            for (int i = 0; i < list.listSize(); i++) {
                model.addElement(list.getExpense(i).getName() + ": $" + list.getExpense(i).getValue());
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, deleteExpenseButton
    // EFFECTS: disables deleteExpenseButton if selected index is less than 0;
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (modelList.getSelectedIndex() == -1) {
                deleteExpenseButton.setEnabled(false);
            } else {
                deleteExpenseButton.setEnabled(true);
            }
        }
    }

    // EFFECTS: prints EventLog to terminal
    public static void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }


    // EFFECTS: method for generating the GUI
    public static void generateGUI() {
        JFrame frame = new JFrame("Private Budget App");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });

        JComponent newContentPane = new BudgetAppGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        generateGUI();
    }

}

