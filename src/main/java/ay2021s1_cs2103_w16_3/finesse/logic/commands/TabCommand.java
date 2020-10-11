package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

/**
 * Switches UI tabs.
 */
public class TabCommand extends Command {

    /** The number of tabs in the UI. */
    public static final int NUM_OF_TABS = 4;

    public static final String COMMAND_WORD = "tab";

    // TODO: Update this
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches to the specified tab by index.\n"
            + "Parameters: INDEX (must be a positive integer between 1 to " + NUM_OF_TABS + " inclusive)";

    public static final String MESSAGE_SWITCH_TABS_SUCCESS = "Switched to %1$s tab.";
    public static final String MESSAGE_TAB_DOES_NOT_EXIST = "The specified tab does not exist.";

    /** The tab to switch to. */
    private final Tab tabToSwitchTo;

    /**
     * Constructs a {@code TabCommand} with the specified tab to switch to.
     *
     * @param tabToSwitchTo The tab to switch to.
     */
    public TabCommand(Tab tabToSwitchTo) {
        requireNonNull(tabToSwitchTo);

        this.tabToSwitchTo = tabToSwitchTo;
    }

    @Override
    public CommandResult execute(Model model) {
        String formattedSuccessMessage = String.format(MESSAGE_SWITCH_TABS_SUCCESS, tabToSwitchTo);
        return new CommandResult(formattedSuccessMessage, tabToSwitchTo);
    }
}
