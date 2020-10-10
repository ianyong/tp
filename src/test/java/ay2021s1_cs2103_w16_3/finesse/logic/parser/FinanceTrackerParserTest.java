package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static ay2021s1_cs2103_w16_3.finesse.testutil.Assert.assertThrows;
import static ay2021s1_cs2103_w16_3.finesse.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ClearCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ExitCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.HelpCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.TitleContainsKeywordsPredicate;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.testutil.EditTransactionDescriptorBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionBuilder;
import ay2021s1_cs2103_w16_3.finesse.testutil.TransactionUtil;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;

public class FinanceTrackerParserTest {

    private final FinanceTrackerParser parser = new FinanceTrackerParser();
    // TODO: Once the UI-dependent command behavior is added, the corresponding tests should be added too.
    private final OverviewUiStateStub overviewUiStateStub = new OverviewUiStateStub();
    private final IncomeUiStateStub incomeUiStateStub = new IncomeUiStateStub();
    private final ExpensesUiStateStub expensesUiStateStub = new ExpensesUiStateStub();
    private final AnalyticsUiStateStub analyticsUiStateStub = new AnalyticsUiStateStub();

    @Test
    public void parseCommand_add() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        AddCommand command =
                (AddCommand) parser.parseCommand(TransactionUtil.getAddCommand(transaction), overviewUiStateStub);
        assertEquals(new AddCommand(transaction), command);
    }

    @Test
    public void parseCommand_addExpense() throws Exception {
        Expense expense = new TransactionBuilder().buildExpense();
        AddExpenseCommand command =
            (AddExpenseCommand) parser.parseCommand(TransactionUtil.getAddExpenseCommand(expense), overviewUiStateStub);
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_addIncome() throws Exception {
        Income income = new TransactionBuilder().buildIncome();
        AddIncomeCommand command =
                (AddIncomeCommand) parser.parseCommand(TransactionUtil.getAddIncomeCommand(income),
                overviewUiStateStub);
        assertEquals(new AddIncomeCommand(income), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, overviewUiStateStub) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TRANSACTION.getOneBased(), overviewUiStateStub);
        assertEquals(new DeleteCommand(INDEX_FIRST_TRANSACTION), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(transaction).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TRANSACTION.getOneBased() + " "
                + TransactionUtil.getEditTransactionDescriptorDetails(descriptor), overviewUiStateStub);
        assertEquals(new EditCommand(INDEX_FIRST_TRANSACTION, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, overviewUiStateStub) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")), overviewUiStateStub);
        assertEquals(new FindCommand(new TitleContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, overviewUiStateStub) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, overviewUiStateStub) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ListCommand);
    }

    @Test
    public void parseCommand_listExpense() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_listIncome() throws Exception {
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD, overviewUiStateStub)
                instanceof ListIncomeCommand);
        assertTrue(parser.parseCommand(ListIncomeCommand.COMMAND_WORD + " 3",
                overviewUiStateStub) instanceof ListIncomeCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", overviewUiStateStub));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand", overviewUiStateStub));
    }

    /**
     * A default {@code UiState} stub that has all of the methods failing.
     */
    private static class UiStateStub extends UiState {
        @Override
        public Tab getCurrentTab() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentTab(Tab currentTab) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Overview' tab as the current tab.
     */
    public static class OverviewUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.OVERVIEW;
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Income' tab as the current tab.
     */
    public static class IncomeUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.INCOME;
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Expenses' tab as the current tab.
     */
    public static class ExpensesUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.EXPENSES;
        }
    }

    /**
     * A {@code UiState} stub that always returns the 'Analytics' tab as the current tab.
     */
    public static class AnalyticsUiStateStub extends UiStateStub {
        @Override
        public Tab getCurrentTab() {
            return Tab.ANALYTICS;
        }
    }
}
