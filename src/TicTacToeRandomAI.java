import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * -------------------------------------------------------------------------------------------------
 * An app that plays Tic Tac Toe Between a human player and a simple AI that chooses random spots
 * -------------------------------------------------------------------------------------------------
 * @author Francisco Reyna 
 * email: francis@cs.utexas.edu
 * The University of Texas at Austin 
 * College of Natural Sciences - Computer Science
 * 
 * -------------------------------------------------------------------------------------------------
 */


public class TicTacToeRandomAI {
    
    //Constants
    private final static int BOARD_SIZE = 3;
    private final static char EMPTY = '.';
    private final static char  PLAYER_ONE_PIECE = 'X';
    private final static char PLAYER_TWO_PIECE = 'O';
    private final static int PIECES_FOR_WIN = 3;
    private final static String AI_NAME = "Computer";

    //Plays a game of TicTacToe 
    public static void main(String[] args) throws Exception {
        char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
        Scanner keyboard = new Scanner(System.in);
        intro();
        String[] names = getPlayerNames(keyboard);
        fillBoard(board);
        printBoard(board);
        runGame(board, keyboard, names);
    }


    /**
     * Prompts for a name for player two and one
     * @param keyboard Scanner used to read user input
     * @return A String array containing the two names of the players
     */
    private static String[] getPlayerNames(Scanner keyboard) {
        String[] names = new String[2];
        //Asks the user for a name
        System.out.print("Please enter a name for the human player: ");
        names[0] = keyboard.nextLine();
        System.out.println();
        //Setting the 2nd player name to an AI.
        names[1] = AI_NAME;
        // Returns the packaged names within an array
        return names;
    }


    /**
     * Main method that runs the game, turn by turn
     * @param board 2D Array containing the pieces
     * @param keyboard Scanner used to read user input
     */
    private static void runGame(char[][] board, Scanner keyboard, String[] names) throws Exception {
        //Variables
        boolean someoneHasWon = false; 
        String player1 = names[0];
        String player2 = names[1];
        String winner = null;
        int maxTurns = board.length * board[0].length;
        int currentTurn = 0;

        //Main game turn by turn
        while(!someoneHasWon && (currentTurn < maxTurns)) {
            //Player 1's turn
            doPlayerTurn(keyboard, board, names, true);
            printBoard(board);
            currentTurn++;
            //Check if P1 has won
            someoneHasWon = checkIfVictory(board, true);
            //Player 1 wins
            if(someoneHasWon) {
                winner = player1;
            }
            //Player 2's turn if P1 hasn't won
            if(!someoneHasWon && (currentTurn < maxTurns)) {
                doComputerTurn(board, names);
                printBoard(board);
                someoneHasWon = checkIfVictory(board, false);
                currentTurn++;
                //Player 2 wins
                if(someoneHasWon) {
                    winner = player2;
                }
            }
        }
        printResults(someoneHasWon, winner, board);
    }


    /**
     * Does the turn of a computer, currently according to a random spot algorithm
     * @param board 2D Array containing the game board's pieces
     * @param names Used to get the computer's name
     * @throws Exception Exception in case the received coordinates are incorrect
     */
    private static void doComputerTurn(char[][] board, String[] names) throws Exception {
        String name = names[1];
        placePiece(board, getRandomSpot(board, new Random()), false);
        System.out.println("--------------------------");
        System.out.println(name + " has made its turn");
        System.out.println("--------------------------");   
    }


    /**
     * Gets a random unfilled spot for the computer's choice.
     * @param board 2D Array of the game pieces
     * @param rand  Random object used to get a random coordinate
     * @return The 2 coordinates of a random unfilled spot in the board
     * @throws Exception Whenever the coordinates do not line up/mess up for some reason
     */
    private static int[] getRandomSpot(char[][] board, Random rand) throws Exception {
        ArrayList<Integer> unfilledX = new ArrayList<Integer>();
        ArrayList<Integer> unfilledY = new ArrayList<Integer>();

        //Gets all the empty spots in the board and adds them to the list.
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(board[i][j] == EMPTY) {
                    unfilledX.add(i);
                    unfilledY.add(j);
                }
            }
        }
        if(unfilledX.size() != unfilledY.size()) {
            throw new Exception("--ERROR: Coordinate arrays are not of same size--");
        } 
        int whichCoord = rand.nextInt(unfilledX.size());
        return new int[] {unfilledY.get(whichCoord), unfilledX.get(whichCoord)};
    }
    

    /**
     * Prints the results at the end of a match
     * @param someoneHasWon Whether the game is a draw or win
     * @param winner Whoever the winner is
     * @param board 2D Array that contains the pieces of the board
     */
    private static void printResults(boolean someoneHasWon, String winner, char[][] board) {
        if(someoneHasWon) {
            System.out.println("----------------");
            System.out.println(winner + " wins!");
            System.out.println("----------------");
        } else {
            System.out.println("-------------------");
            System.out.println("The game is a draw.");
            System.out.println("-------------------");
        }
        printBoard(board);
    } 


    /**
     * Does a player's turn by informing them then asking for coordinates.
     * @param keyboard Scanner used to read user input
     * @param board 2D Array representing the board of the game
     * @param names Size 2 array of the two player's names
     * @param isPlayerOne Used to decide which player's turn should be done
     */
    private static void doPlayerTurn(Scanner keyboard, char[][] board, String[] names, 
        boolean isPlayerOne) {
        String name;
        if(isPlayerOne) {
            name = names[0];
        } else {
            name = names[1];
        }
        System.out.println(name + ", it is your turn.");
        int[] coordinates = getPlayerChoice(board, keyboard, isPlayerOne);
        placePiece(board, coordinates, isPlayerOne);
    }


    /**
     * Places a piece at the selected coordinate spot
     * @param board 2D Array representing the board of the game
     * @param coordinates Size 2 int array of the x and y coordinates 
     * @param isPlayerOne Used to decide which piece should be placed
     */
    private static void placePiece(char[][] board, int[] coordinates, boolean isPlayerOne) {
        char piece;
        if(isPlayerOne) {
            piece = PLAYER_ONE_PIECE;
        } else {
            piece = PLAYER_TWO_PIECE;
        }
        board[coordinates[1]][coordinates[0]] = piece;
    }


    /**
     * Checks to see if a victory has been made for the specific player
     * @param board 2D Array that holds all the pieces
     * @param isPlayerOne Which player's turn it is
     * @return Whether or not a victory has been found
     */
    private static boolean checkIfVictory(char[][] board, boolean isPlayerOne) {
        int maxRows = board.length;
        int maxColumns = board[0].length;

        //Select which player's piece we are looking for
        char teamPiece;
        if(isPlayerOne) {
            teamPiece = PLAYER_ONE_PIECE;
        } else {
            teamPiece = PLAYER_TWO_PIECE;
        }

        //Checks each spot of the board where there is a teamPiece for a victory.
        for(int pieceRow = 0; pieceRow < maxRows; pieceRow++) {
            for(int pieceCol = 0; pieceCol < maxColumns; pieceCol++) {
                char currentPiece = board[pieceRow][pieceCol];
                if(currentPiece == teamPiece) {
                    boolean isVictory = checkFourDir(board, pieceRow, pieceCol, teamPiece);
                    if(isVictory){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Checks in four directions of a piece to see if there's a victory
     * @param board 2D Array that contains all the pieces
     * @param pieceY The Row of the current piece
     * @param pieceX The Column of the current piece
     * @param checkP The piece of which team we are supposed to check for
     * @return Whether or not a victory was found at this piece's directions
     */
    private static boolean checkFourDir(char[][] board, int pieceY, int pieceX, char checkP) {
        int[] rowDirections = {1, 1, 0, -1};
        int[] colDirections = {0, 1, 1, -1};

        //For loop that checks for a victory in the 4 necessary directions
        for(int rowDir = 0; rowDir < rowDirections.length; rowDir++) {
            //Starts at 1 because of the piece we are already on and analyzing
            int count = 1;
            boolean isConsecutive = true;
            //Reset the initial rows and columns for each direction
            int curRow = pieceY;
            int curCol = pieceX;

            //While we need to keep looking for 4 in a row
            while(checkIfValidBounds(board, curRow) && checkIfValidBounds(board, curCol)
                && count != PIECES_FOR_WIN && isConsecutive) {
                int newRow = curRow + rowDirections[rowDir];
                int newCol = curCol + colDirections[rowDir];
                //If the new piece is in range, check to see if it's consecutive to the previous one
                if(checkIfValidBounds(board, newRow) &&
                    checkIfValidBounds(board, newCol)) {
                    isConsecutive = board[curRow][curCol] == board[newRow][newCol];
                }
                curRow += rowDirections[rowDir];
                curCol += colDirections[rowDir];
                //If our row and column are within range and the piece there is of the same team,
                //increment count
                if(checkIfValidBounds(board, curRow) && checkIfValidBounds(board, curCol)
                    && isConsecutive) {
                    if(board[curRow][curCol] == checkP) {
                        count++;
                    }
                }
                //If the count = 4, return that the game has been won!
                if(count == PIECES_FOR_WIN) {
                    return true;
                }
            }
        }
        //Returns false after all 4 directions were checked and not one made count = 4.
        return false;
    }


    /**
     * Checks to see if a player input is within bounds of the game
     * @param board 2D Array representing the board of the game
     * @param num The number which is to be checked
     * @return Whether or not it is in bounds/valid.
     */
    private static boolean checkIfValidBounds(char[][] board, int num) {
        return num < board.length && num >= 0;
    }


    /**
     * Gets the X and Y Coordinate of where in the array a piece must be placed
     * @param board 2D Array of the board
     * @param keyboard Scanner used to read user input
     * @return X and Y Coordinates packaged in an array
     */
    private static int[] getPlayerChoice(char[][] board, Scanner keyboard, boolean isPlayerOne) {
        boolean isValidCoords = false;
        int x = -1;
        int y = -1;
        //While we do not have valid coordinates, ask for them.
        while(!isValidCoords) {
            System.out.print("Select a column: ");
            x = keyboard.nextInt();
            System.out.println();
            if(!checkIfValidBounds(board, x - 1)) {
                x = whileLoop(keyboard, board, true, x);
            }
            System.out.print("Select a row: ");
            y = keyboard.nextInt(); 
            System.out.println();
            if(!checkIfValidBounds(board, y - 1)) {
                y = whileLoop(keyboard, board, false, y);
            }
            isValidCoords = board[y - 1][x - 1] == EMPTY;
            //If they are not valid, tell them.
            if(!isValidCoords) {
                System.out.println("The selected coordinates occupy a piece.\n" +
                "Please select a new pair.\n");
            }
        }
        //Two coordinates
        return new int[] {x - 1, y - 1};
    }


    /**
     * Prompts the user for a coordinate until they enter a valid one.
     * @param keyboard Scanner used to read user input
     * @param board 2D Array containing the pieces
     * @param isCol Whether to use "column" or "row"
     * @param coord The x or y is being recorded
     * @return The valid integer coordinate x or y.
     */
    private static int whileLoop(Scanner keyboard, char[][] board, boolean isCol, int coord) {
        boolean notValid = true;
        String item;
        if(isCol) {
            item = "column";
        } else {
            item = "row";
        }
        while(notValid) {
            System.out.println("That is not a valid " + item + ".");
            System.out.print("Select a " + item + ": ");
            coord = keyboard.nextInt();
            notValid = !checkIfValidBounds(board, coord - 1);
            System.out.println();
        }
        return coord;
    }


    //Prints the introduction to the match.
    private static void intro() {
        System.out.println("---------------------------------------------");
        System.out.println("Welcome to the game of Tic Tac Toe");
        System.out.println("The current board size is set to " + BOARD_SIZE);
        System.out.println("---------------------------------------------");
        System.out.println();
    }


    /**
     * Fills the board with empty pieces.
     * @param board The gameboard array.
     */
    private static void fillBoard(char[][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                board[i][j] = EMPTY;
            }
        }
    }


    /**
     * Prints the current iteration of the game board.
     * @param board 2D Array containing the pieces of the board
     */
    private static void printBoard(char[][] board) {
        System.out.println("Current Board: ");
        System.out.print(" ");
        for(int a = 1; a <= board.length; a++) {
            System.out.print(a + " ");
        }
        System.out.println();
        for(int i = 0; i < board.length - 1; i++) {
            //Prints the pieces and vertical lines at each horizontal line
            System.out.print(i + 1);
            for(int j = 0; j < board.length - 1; j++) {
                System.out.print(board[i][j]);
                System.out.print("|");
            }
            //Horizontal pieces 
            System.out.print(board[i][2]);
            System.out.println();
            //Horizontal Lines
            for(int k = 0; k < BOARD_SIZE + (BOARD_SIZE); k++) {
                System.out.print("-");
            }
            System.out.println();
        }
        System.out.print(BOARD_SIZE);
        for(int j = 0; j < board.length - 1; j++) {
            System.out.print(board[2][j]);
            System.out.print("|");
        }
        System.out.println(board[2][2]);
        System.out.println();
    }
 

    /**
     * Checks to see if the gameBoard is full
     * @param board 2D Array of the board
     * @return Whether or not 2D Array is full
     */
    private static boolean checkIfFull(char[][] board) {
        //The moment a piece is empty, returns that the board is indeed NOT full
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board.length; col++) {
                if(board[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        //Returns that the board is indeed full if no empty piece was found
        return true;
    }
}