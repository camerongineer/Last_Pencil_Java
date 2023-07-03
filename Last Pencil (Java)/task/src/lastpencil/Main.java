package lastpencil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
                System.out.printf("%s won!\n", getCurrentPlayer());
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
        System.out.printf("%s's turn%s\n", getCurrentPlayer(), isPlayer1Turn ? "!" : ":");
        int pencilsRemoved;
        if (!isPlayer1Turn) {
            pencilsRemoved = botMove(possibleValues);
            System.out.println(pencilsRemoved);
        } else {
            while (true) {
                try {
                    pencilsRemoved = Integer.parseInt(scanner.nextLine().trim());
                    if (pencilsRemoved < 1 || 3 < pencilsRemoved) {
                        throw new IllegalArgumentException();
                    } else if (pencilsRemoved > possibleValues) {
                        System.out.println("Too many pencils were taken");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Possible values: '1', '2' or '3'");
                }
            }
        }
        pencils -= pencilsRemoved;
        isPlayer1Turn = !isPlayer1Turn;
    }

    private static int botMove(int possibleValues) {
        if (pencils % 4 == 0) {
            return 3;
        } else if ((pencils + 1) % 4 == 0) {
            return 2;
        } else if ((pencils + 2) % 4 == 0) {
            return 1;
        } else {
            return new Random().nextInt(possibleValues) + 1;
        }
    }

    private static void printPencils() {
        System.out.println("|".repeat(pencils));
    }
}
