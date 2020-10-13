package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ay2021s1_cs2103_w16_3.finesse.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalFinanceTracker;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalTransactions.getTypicalTransactions;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTransactionCommand.
 */
public class ListTransactionCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
        expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
    }

    @Test
    public void execute_showsAllTransactions() {
        expectedModel.updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        assertCommandSuccess(new ListTransactionCommand(), model,
                new CommandResult(ListTransactionCommand.MESSAGE_SUCCESS, Tab.OVERVIEW), expectedModel);
        assertEquals(getTypicalTransactions().size(), model.getFilteredTransactionList().size());
    }

    @Test
    public void execute_hasSomeIncome() {
        model.addTransaction(new TransactionBuilder().buildIncome());
        expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.updateFilteredTransactionList(transaction -> true);
        assertCommandSuccess(new ListTransactionCommand(), model,
                new CommandResult(ListTransactionCommand.MESSAGE_SUCCESS, Tab.OVERVIEW), expectedModel);
        assertEquals(getTypicalTransactions().size() + 1, model.getFilteredTransactionList().size());
    }

    @Test
    public void execute_hasSomeExpense() {
        model.addTransaction(new TransactionBuilder().buildExpense());
        expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.updateFilteredTransactionList(transaction -> true);
        assertCommandSuccess(new ListTransactionCommand(), model,
                new CommandResult(ListTransactionCommand.MESSAGE_SUCCESS, Tab.OVERVIEW), expectedModel);
        assertEquals(getTypicalTransactions().size() + 1, model.getFilteredTransactionList().size());
    }

}