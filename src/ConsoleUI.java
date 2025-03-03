import java.sql.Connection;
import java.util.Scanner;

public class ConsoleUI {
    private static QueryTreeBuilder queryTreeBuilder = new QueryTreeBuilder();

    public static void runQueryTool(Scanner scanner, Connection connection) {

        while (true) {
            System.out.println("\n=== Query Tool ===");
            System.out.println("1. Set Base Table");
            System.out.println("2. Add Select Option");
            System.out.println("3. Add Filter Condition");
            System.out.println("4. Add JOIN Condition");
            System.out.println("5. Undo Last Operation");
            System.out.println("6. Execute Query");
            System.out.println("7. Reset Input");
            System.out.println("8. Return to Main Menu");
            System.out.print("Select an option (1-7): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    setBaseTable(scanner);
                    break;
                case 2:
                    addSelectOption(scanner);
                    break;
                case 3:
                    addFilterCondition(scanner);
                    break;
                case 4:
                    addJoinCondition(scanner);
                    break;
                case 5:
                    undoLastOperation();
                    break;
                case 6:
                    executeQuery();
                    break;
                case 7:
                	resetQueryTool();
                case 8:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void setBaseTable(Scanner scanner) {
        System.out.print("Enter base table: ");
        String table = scanner.next();
        queryTreeBuilder.setBaseTable(table);
    }

    private static void addSelectOption(Scanner scanner) {
        System.out.print("Enter the number of columns to select: ");
        int numColumns = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over
        StringBuilder columns = new StringBuilder();

        for (int i = 1; i <= numColumns; i++) {
            System.out.print("Enter column name " + i + ": ");
            String column = scanner.nextLine();
            columns.append(column);
            if (i < numColumns) {
                columns.append(", ");
            }
        }

        queryTreeBuilder.addSelectOption(columns.toString());
    }

    private static void addFilterCondition(Scanner scanner) {
        System.out.print("Enter column to filter by: ");
        String column = scanner.next();
        System.out.print("Enter operation (<, >, =): ");
        String operation = scanner.next();
        System.out.print("Enter value to filter by: ");
        String value = scanner.next();
        String condition = String.format("%s %s '%s'", column, operation, value);
        queryTreeBuilder.addFilterCondition(condition);
    }

    private static void addJoinCondition(Scanner scanner) {
        System.out.print("Enter join type (INNER, LEFT, RIGHT, FULL): ");
        String joinType = scanner.next().toUpperCase();
        joinType = joinType + " JOIN";
        scanner.nextLine();
        System.out.print("Enter table to join the " + queryTreeBuilder.getBaseTable() + " table with: ");
        String table = scanner.next();
        System.out.print("Enter first table column to join on: ");
        String column1 = scanner.next();
        System.out.print("Enter second table column to join on: ");
        String column2 = scanner.next();
        String condition = String.format("%s.%s = %s.%s", queryTreeBuilder.getBaseTable(), column1, table, column2);
        queryTreeBuilder.addJoinCondition(joinType, table, condition);
    }

    private static void undoLastOperation() {
        queryTreeBuilder.undoLastOperation();
    }

    private static void executeQuery() {
        String query = queryTreeBuilder.buildQuery();
        System.out.println("\nExecuting Query: " + query);
        JoinOperations.executeSQLQuery(query);
    }
    private static void resetQueryTool() {
        queryTreeBuilder = new QueryTreeBuilder();
        System.out.println("All inputs have been reset.");
    }
}


