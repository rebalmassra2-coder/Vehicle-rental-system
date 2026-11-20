package Project_java2;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

/**
 * Utility class for reading validated input from the console.
 * It owns the ONLY Scanner on System.in in the whole program.
 */
public class SafeInput {

    // Global static Scanner object for the entire program
    private static final Scanner in = new Scanner(System.in);

    // Kinds of text accepted by readOptionalString
    public static final String NAME = "name";
    public static final String LABEL = "label";
    public static final String DIGITS = "digits";

    // Prevents instantiation of this utility class
    private SafeInput() {
    }

    // ------------------------------------------------------------------
    // Character validators
    // ------------------------------------------------------------------

    /** Letters (any language), spaces and underscores. */
    private static boolean isName(String s) {
        return s.chars().allMatch(c -> Character.isLetter(c) || c == '_' || c == ' ');
    }

    /** Letters, digits, spaces, underscores, hyphens and dots (addresses, models, brands). */
    private static boolean isLabel(String s) {
        return s.chars().allMatch(c -> Character.isLetterOrDigit(c) || c == '_' || c == ' ' || c == '-' || c == '.');
    }

    /** Letters, digits and underscores, no spaces (IDs). */
    private static boolean isId(String s) {
        return s.chars().allMatch(c -> Character.isLetterOrDigit(c) || c == '_');
    }

    /** Digits only. */
    private static boolean isDigits(String s) {
        return s.chars().allMatch(Character::isDigit);
    }

    private static boolean matches(String kind, String s) {
        switch (kind) {
            case NAME:
                return isName(s);
            case LABEL:
                return isLabel(s);
            default:
                return isDigits(s);
        }
    }

    private static String errorFor(String kind) {
        switch (kind) {
            case NAME:
                return "Error: Only letters, spaces and underscores are allowed (no digits or symbols).";
            case LABEL:
                return "Error: Only letters, digits, spaces and - _ . are allowed.";
            default:
                return "Error: Only digits are allowed (no spaces or symbols).";
        }
    }

    // ------------------------------------------------------------------
    // Numbers
    // ------------------------------------------------------------------

    /*
     * Reads a valid integer from the user.
     */
    public static int readInt(String prompt) {

        while (true) {

            System.out.print(prompt);

            if (in.hasNextInt()) {

                int value = in.nextInt();
                in.nextLine(); // Clear input buffer

                return value;

            } else {

                System.out.println("Error: Please enter a valid integer number.");
                in.nextLine(); // Clear input buffer
            }
        }
    }

    /*
     * Reads a valid double from the user.
     */
    public static double readDouble(String prompt) {

        while (true) {

            System.out.print(prompt);

            if (in.hasNextDouble()) {

                double value = in.nextDouble();
                in.nextLine(); // Clear input buffer

                return value;

            } else {

                System.out.println("Error: Please enter a valid decimal number.");
                in.nextLine(); // Clear input buffer
            }
        }
    }

    /*
     * Reads a valid integer within a specific range (inclusive).
     * Re-prompts the user until a valid value is entered.
     */
    public static int readIntRange(String prompt, int min, int max) {

        while (true) {

            int value = readInt(prompt);

            if (value >= min && value <= max) {
                return value;
            }

            System.out.println("Error: Enter a value between " + min + " and " + max + ".");
        }
    }

    /*
     * Reads a valid double within a specific range (inclusive).
     */
    public static double readDoubleRange(String prompt, double min, double max) {

        while (true) {

            double value = readDouble(prompt);

            if (value >= min && value <= max) {
                return value;
            }

            System.out.println("Error: Enter a value between " + min + " and " + max + ".");
        }
    }

    /*
     * Reads a double that must be greater than zero.
     */
    public static double readPositiveDouble(String prompt) {

        while (true) {

            double value = readDouble(prompt);

            if (value > 0) {
                return value;
            }

            System.out.println("Error: The value must be greater than zero.");
        }
    }

    // ------------------------------------------------------------------
    // Text
    // ------------------------------------------------------------------

    /*
     * Reads a non-empty text of the given kind (NAME, LABEL or DIGITS).
     */
    private static String readText(String prompt, String kind) {

        while (true) {

            System.out.print(prompt);

            String input = in.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Error: Input cannot be empty.");
                continue;
            }

            if (matches(kind, input)) {
                return input;
            }

            System.out.println(errorFor(kind));
        }
    }

    /*
     * Reads a name: letters (Arabic/English), spaces and underscores only.
     */
    public static String readString(String prompt) {
        return readText(prompt, NAME);
    }

    /*
     * Reads a free label such as an address, brand or model:
     * letters, digits, spaces and - _ .
     */
    public static String readLabel(String prompt) {
        return readText(prompt, LABEL);
    }

    /*
     * Reads a string made only of digits (phone numbers, plate numbers).
     */
    public static String readStringButOnlyNumbers(String prompt) {
        return readText(prompt, DIGITS);
    }

    /*
     * Reads an ID: letters, digits and underscores, no spaces.
     */
    public static String readNumericString(String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = in.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Error: Input cannot be empty.");
                continue;
            }

            if (isId(input)) {
                return input;
            }

            System.out.println("Error: Only letters, numbers, and underscore are allowed (no spaces or symbols).");
        }
    }

    /*
     * Like the text readers above, but the user may type "u" to keep the old value.
     * Returns null when the user chose to keep it.
     */
    public static String readOptionalString(String prompt, String kind) {

        while (true) {

            System.out.print(prompt + " (u = keep current): ");

            String input = in.nextLine().trim();

            if (input.equalsIgnoreCase("u")) {
                return null;
            }

            if (input.isEmpty()) {

                System.out.println("Error: Input cannot be empty.");
                continue;
            }

            if (matches(kind, input)) {
                return input;
            }

            System.out.println(errorFor(kind));
        }
    }

    /*
     * Reads a boolean value from the user based on yes/no confirmations.
     */
    public static boolean readBoolean(String prompt) {

        while (true) {

            System.out.print(prompt + " (yes/no): ");

            String input = in.nextLine().strip().toLowerCase();

            if (input.equals("yes") || input.equals("y") || input.equals("true")) {

                return true;

            } else if (input.equals("no") || input.equals("n") || input.equals("false")) {

                return false;

            } else {

                System.out.println("Error: Please enter (yes) or (no).");
            }
        }
    }

    /*
     * Reads the fuel type of a car: petrol, diesel or electric.
     */
    public static String readFuelType(String prompt) {

        while (true) {

            System.out.print(prompt + " (petrol/diesel/electric): ");

            String input = in.nextLine().trim();

            if (input.equalsIgnoreCase("petrol") || input.equalsIgnoreCase("diesel")
                    || input.equalsIgnoreCase("electric")) {
                return input.toLowerCase();
            }

            System.out.println("Error: Please enter petrol, diesel or electric.");
        }
    }

    // ------------------------------------------------------------------
    // Dates
    // ------------------------------------------------------------------

    /*
     * Reads a valid LocalDate from the user.
     * The year may be up to 100 years in the past and 10 years in the future,
     * so it works for birth dates, rental periods and return dates alike.
     */
    public static LocalDate readDate(String prompt) {

        while (true) {

            System.out.println(prompt);

            int currentYear = LocalDate.now().getYear();
            int minYear = currentYear - 100;
            int maxYear = currentYear + 10;

            int year = readIntRange("Enter year (" + minYear + "-" + maxYear + "): ", minYear, maxYear);
            int month = readIntRange("Enter month: ", 1, 12);
            int day = readIntRange("Enter day: ", 1, 31);

            // Validate the actual calendar date (e.g. 31 February does not exist)
            try {

                return LocalDate.of(year, month, day);

            } catch (DateTimeException e) {

                System.out.println("Error: The entered date does not exist. Please try again.");
            }
        }
    }

    /*
     * Reads a birth date and checks that the age is between 18 and 80.
     */
    public static LocalDate readValidBirthDate(String prompt) {

        while (true) {

            LocalDate birth = readDate(prompt);

            int age = Period.between(birth, LocalDate.now()).getYears();

            if (!birth.isAfter(LocalDate.now()) && age >= 18 && age <= 80) {
                return birth;
            }

            System.out.println("Error: Age must be between 18 and 80 years.");
        }
    }
}
