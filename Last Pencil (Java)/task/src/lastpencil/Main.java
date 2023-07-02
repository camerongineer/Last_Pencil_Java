package lastpencil;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static HashMap<String, String> players = new HashMap<>(Map.of("player1", "John", "player2", "Jack"));
    static int pencils = 0;
    static boolean isPlayer1Turn;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many pencils would you like to use:");
        while (true) {
            try {
                pencils = Integer.parseInt(scanner.nextLine().trim());
                if (pencils < 1) {
                    throw new IllegalArgumentException("The number of pencils should be positive");
                }
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("The number of pencils should be numeric");
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }
        }
        System.out.printf("Who will be the first (%s, %s):\n", players.get("player1"), players.get("player2"));
        while (true) {
            try {
                String name = scanner.nextLine().trim().toLowerCase();
                if (name.matches(players.get("player1").toLowerCase())) {
                    isPlayer1Turn = true;
                } else if (name.matches(players.get("player2").toLowerCase())) {
                    isPlayer1Turn = false;
                } else {
                    throw new IllegalArgumentException(String.format("Choose between '%s' and '%s'",
                                                                     players.get("player1"), players.get("player2")));
                }
                while (pencils > 0) {
                    gameRound(scanner);
                }
                System.out.printf("%s won!", getCurrentPlayer());
                scanner.close();
                break;
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }
        }
    }

    public static String getCurrentPlayer() {
        return isPlayer1Turn ? players.get("player1") : players.get("player2");
    }

    public static void gameRound(Scanner scanner) {
        printPencils();
        int possibleValues = Integer.min(3, pencils);
        System.out.printf("%s's turn!\n", getCurrentPlayer());
        while (true) {
            try {
                int pencilsRemoved = Integer.parseInt(scanner.nextLine().trim());
                if (0 >= pencilsRemoved || pencilsRemoved > 3) {
                    throw new IllegalArgumentException();
                } else if (pencilsRemoved > possibleValues) {
                    System.out.println("Too many pencils were taken");
                } else {
                    pencils -= pencilsRemoved;
                    isPlayer1Turn = !isPlayer1Turn;
                    return;
                }
            } catch (Exception e) {
                System.out.println(getPossibleValuesString(possibleValues));
            }
        }
    }

    private static void printPencils() {
        System.out.println("|".repeat(pencils));
    }

    private static String getPossibleValuesString(int possibleValues) {
        StringBuilder possibleValueStr = new StringBuilder("Possible values: ");
        if (possibleValues == 1) {
            possibleValueStr.append("'1'");
        } else {
            for (int i = 1; i < possibleValues; i++) {
                possibleValueStr.append(String.format("'%s'", i));
                if (i < possibleValues - 1) {
                    possibleValueStr.append(", ");
                } else {
                    possibleValueStr.append(" ");
                }
            }
            possibleValueStr.append(String.format("or '%s'", possibleValues));
        }
        return possibleValueStr.toString();
    }
}
