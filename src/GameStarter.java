import java.util.List;
import java.util.Scanner;

public class GameStarter {
    private static final int DEFAULT_NUMBER_RANGE = 90;
    private static final int DEFAULT_NUMBER_PER_LINE = 5;
    private static final int DEFAULT_TICKET_ROWS = 3;
    private static final int DEFAULT_TICKET_COLS = 10;
    private static final int MAXIMUM_PLAYERS = 10;

    private static boolean gameIsRunning;
    private static GameOrganizer organizer;
    private static Scanner scanner;
    private static GameConfigData configData;
    private static int step;

    public static void main (String[] args) {
        runGame();
        gameOver();
    }

    private static void runGame() {
        initializeGame();

        while (gameIsRunning) {
            if (step <= 5) {
                configureGame();
            } else {
                executeGame();
            }
        }
    }

    private static void executeGame() {
        String input = scanner.nextLine().trim();
        checkExit(input);
        if (input.equals(Constants.CALL_NUMBER)) {
            int calledNumber = organizer.callNumber();
            System.out.println("Next number is: " + calledNumber);
            List<String> currentWinners = organizer.getCurrentWinners();
            if (!currentWinners.isEmpty()) {
                printCurrentWinnerStatement(currentWinners);
            }
            if (organizer.checkGameOver()) {
                terminateGame();
            }
        }
    }

    private static void initializeGame() {
        scanner = new Scanner(System.in);
        configData = new GameConfigData();
        gameIsRunning = true;
        step = 1;

        System.out.println( "**** Lets Play Housie *****" );
        System.out.println();
        System.out.println( "Note: - Press 'Q' to quit any time." );
    }

    private static void printCurrentWinnerStatement(List<String> winners) {
        for (String winner : winners) {
            System.out.println(winner);
        }
    }

    private static void gameOver() {
        if (step > 5) {
            printFinalResult(organizer.getFinalResult());
        }
    }

    private static void printFinalResult(List<String> finalResult) {
        System.out.println( "======================" );
        System.out.println( "Summary:" );
        for (String playerFinalResultStatement : finalResult) {
            System.out.println(playerFinalResultStatement);
        }
        System.out.println( "======================" );
    }

    private static void configureGame() {
        switch (step) {
            case 1 :
                System.out.print( "Enter the number range(1-n), default value 90, maximum 99, minimum 30: " );
                String input = scanner.nextLine().trim();
                Utils.DEBUG(input);
                checkExit(input);
                if (input.equals("")) {
                    configData.setNumberRange(DEFAULT_NUMBER_RANGE);
                    step++;
                } else if (Utils.isValidNum(input) && Integer.parseInt(input) >= 30) {
                    configData.setNumberRange(Integer.parseInt(input));
                    step++;
                } else {
                    System.out.print("Invalid input : ");
                }
                break;

            case 2 :
                System.out.print( "Enter Number of players playing the game, maximum 10: " );
                input = scanner.nextLine().trim();
                Utils.DEBUG(input);
                checkExit(input);

                if (!input.isEmpty() && Utils.isValidNum(input)) {
                    int players = Integer.parseInt(input);
                    Utils.DEBUG(input);
                    if (players <= MAXIMUM_PLAYERS) {
                        configData.setPlayerNum(players);
                        step++;
                    }
                }
                break;

            case 3 :
                System.out.print( "Enter Ticket Size: Default value 3X10 ( 3 rows and 10 columns), please follow pattern 'RowXCol', e.g. 3X10 : " );
                input = scanner.nextLine().trim();
                checkExit(input);

                if (input.isEmpty()) {
                    configData.setTicketRows(DEFAULT_TICKET_ROWS);
                    configData.setTicketCols(DEFAULT_TICKET_COLS);
                    step++;
                } else if (Utils.isValidSizeFormat(input)) {
                    String[] size = input.split("X");
                    configData.setTicketRows(Integer.parseInt(size[0]));
                    configData.setTicketCols(Integer.parseInt(size[1]));
                    step++;
                }
                break;

            case 4 :
                System.out.print( "Enter numbers per row. Default to 5 : " );
                input = scanner.nextLine().trim();
                checkExit(input);

                if (input.isEmpty()) {
                    configData.setNumberPerRow(DEFAULT_NUMBER_PER_LINE);
                    step++;
                } else if(Utils.isValidNum(input)) {
                    int numPerLine = Integer.parseInt(input);
                    if (numPerLine <= configData.getTicketCols() && numPerLine * configData.getTicketRows() <= configData.getNumberRange() && numPerLine > 0) {
                        configData.setNumberPerRow(numPerLine);
                        step++;
                    } else {
                        System.out.print( "Invalid input" );
                    }
                }
                break;

            case 5 :
                organizer  = GameOrganizer.getInstance(configData);
                step++;

                System.out.println("***Ticket Created Successfully ****");
                System.out.println(">> Press 'N' to generate next number.");
                break;
        }
    }

    private static void checkExit(String input) {
        if (input.equals(Constants.EXIT)) {
            terminateGame();
        }
    }

    private static void terminateGame() {
        gameIsRunning = false;
    }
}
