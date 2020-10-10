package ay2021s1_cs2103_w16_3.finesse.model.category;

import static ay2021s1_cs2103_w16_3.finesse.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Category in the finance tracker.
 * Guarantees: immutable; name is valid as declared in {@link #isValidCategoryName(String)}
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS = "Category names should be printable.";
    public static final String VALIDATION_REGEX = "\\p{Print}+";

    public final String categoryName;

    /**
     * Constructs a {@code Category}.
     *
     * @param categoryName A valid category name.
     */
    public Category(String categoryName) {
        requireNonNull(categoryName);
        checkArgument(isValidCategoryName(categoryName), MESSAGE_CONSTRAINTS);
        this.categoryName = categoryName;
    }

    /**
     * Returns true if a given string is a valid category name.
     */
    public static boolean isValidCategoryName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && categoryName.equals(((Category) other).categoryName)); // state check
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + categoryName + ']';
    }

}
