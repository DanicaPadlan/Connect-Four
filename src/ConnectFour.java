import java.util.Scanner;

/**
 * CS312 Assignment 10.
 *
 * On my honor, Danica Padlan, this programming assignment is my own work and I have
 * not shared my solution with any other student in the class.
 *
 *  email address: danica_padlan@yahoo.com
 *  UTEID: dmp3357
 *  TA name: Nikhil Kumar
 *  Number of slip days used on this assignment: 0
 *
 * Program that allows two people to play Connect Four.
 */


public class ConnectFour {

    // CS312 Students, add you constants here
    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final int FOUR = 4;
    public static final int MIN_LIMIT = -1;

    //order of direction int: {horizontal change, vertical change}
    //checks for {vertical, horizontal, down diagonal, up diagonal} wins
    public static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {1, 1}, {11, 1}};
    public static final String[] PIECES = new String[] {"r","b"};

    public static void main(String[] args) {
        intro();

        // complete this method
        // Recall make and use one Scanner connected to System.in
        Scanner keyboard = new Scanner(System.in);
        setGame(keyboard);

    }

    // CS312 Students, add your methods

    //sets up board and takes in player names
    public static void setGame(Scanner scan){

        //sets up board and player name array
        String[][] board = new String[ROWS][COLS];
        board = setBoard(board);
        String[] players = new String[2];

        //asks users for names and sets to array to be referenced later
        System.out.print("Player 1 enter your name: ");
        players[0] = scan.nextLine();
        System.out.println();
        System.out.print("Player 2 enter your name: ");
        players[1] = scan.nextLine();

        playGame(scan, board, players);
    }

    //compiles game and plays rounds
    public static void playGame(Scanner scan, String[][] board,String[] players){

        //loops through rounds of game until there is a winner or filled board
        boolean win = false;
        boolean filled = false;
        String curState = "Current Board";
        while(!win && !filled){

            //loops through player array taking turns between each player
            for(int x = 0; x < players.length; x++){
                displayBoard(board, curState);
                getPlayerCol(scan, players[x], PIECES[x], board);

                //checks for wins or filled board
                win = checkWin(board, PIECES[x]);
                filled = isFilled(board);

                //if win or filled is true,
                if(win || filled){

                    //prepares to show final results
                    curState = "Final Board";
                    winnerResults(players[x], board, curState, win);

                    //adds to x to stop the for loop automatically
                    x+= players.length;
                }
            }
        }
    }

    //sets up blank board in beginning of game
    public static String[][] setBoard(String[][] board){
        for(int r = 0; r < ROWS; r++){
            for(int c = 0; c < COLS; c++){
                board[r][c] = ".";
            }
        }
        return board;
    }

    //displays the current board
    public static void displayBoard(String[][] board, String curState){
        System.out.println();
        System.out.println(curState);
        System.out.println("1 2 3 4 5 6 7  column numbers");

        for(int r = 0; r < ROWS; r++){
            String displayRow = board[r][0] + " ";
            for(int c = 1; c < COLS; c++){
                displayRow +=  board[r][c] + " ";
            }
            System.out.println(displayRow);
        }
    }

    //asks player for column input
    public static void getPlayerCol(Scanner scan, String player, String piece, String[][] board){
        int col = MIN_LIMIT;
        boolean isInt = false;
        System.out.println();
        System.out.println(player + " it is your turn.");
        System.out.println("Your pieces are the " + piece + "'s.");
        String prompt = player + ", enter the column to drop your checker: ";

        //checks for valid inputs
        while(!isInt){
            System.out.print(prompt);
            //gets user input from getInt()
            col = getInt(scan, prompt);

            //checks if user input is valid and fits in the array
            if(col > 0 && col <= COLS){
                //checks if the upper array is empty, confirmed that there will be no holes in columns
                if(!board[0][col-1].equals(".")){
                    System.out.println();
                    System.out.println(col + " is not a legal column. That column is full");
                } else{
                    isInt = true;
                }
            } else{
                System.out.println();
                System.out.println(col + " is not a valid column.");
            }
        }
        updateBoard(col - 1, piece, board);
    }

    //places pieces on the lowest possible row
    public static void updateBoard(int col, String piece, String[][] board){
        for(int r = ROWS - 1; r >= 0; r--){
            if(board[r][col].equals(".")){
                board[r][col] = piece;
                r-= ROWS;
            }
        }
    }

    //checks if all spots are filled
    public static boolean isFilled(String[][] board){
        int emptySpots = 0;
        for(String[] r: board){
            for(String piece: r){
                if(piece.equals(".")){
                    emptySpots++;
                }
            }
        }
        return (emptySpots == 0);
    }

    //checks for possible player wins
    public static boolean checkWin(String[][] board, String piece){

        //goes through for the board
        for(int r = 0; r < ROWS; r++){
            for(int c = 0; c < COLS; c++){
                //if array element matches checked piece
                if(board[r][c].equals(piece)){

                    //go through DIRECTION loop to check for possible wins
                    for(int d = 0; d < DIRECTIONS.length; d++){

                        if(isWin(board, r, c,  DIRECTIONS[d], piece)){
                            return true;
                        }
                        //if not true, continue checking
                    }
                }
            }
        }
        return false;
    }

    //checks for potential wins in 4 directions
    public static boolean isWin(String[][] board, int row, int col, int[] direction, String piece){
        int count = 0;
        int curRow = row;
        int curCol = col;

        //checks if in 2d array's range and matching piece
        while(curRow > MIN_LIMIT && curRow < ROWS && curCol > MIN_LIMIT && curCol < COLS
                && board[curRow][curCol].equals(piece)){
            count++;
            curRow += direction[0];
            curCol += direction[1];
        }

        return (count == FOUR);
    }

    //prints out final results, checks for win or draw statements
    public static void winnerResults(String player, String[][] board, String curState, boolean win){
        System.out.println();
        if(win){
            System.out.println(player + " wins!!");
        } else{
            System.out.println("The game is a draw.");
        }
        displayBoard(board, curState);
    }
    
    // show the intro
    public static void intro() {
        System.out.println("This program allows two people to play the");
        System.out.println("game of Connect four. Each player takes turns");
        System.out.println("dropping a checker in one of the open columns");
        System.out.println("on the board. The columns are numbered 1 to 7.");
        System.out.println("The first player to get four checkers in a row");
        System.out.println("horizontally, vertically, or diagonally wins");
        System.out.println("the game. If no player gets fours in a row and");
        System.out.println("and all spots are taken the game is a draw.");
        System.out.println("Player one's checkers will appear as r's and");
        System.out.println("player two's checkers will appear as b's.");
        System.out.println("Open spaces on the board will appear as .'s.\n");
    }

    // Prompt the user for an int. The String prompt will
    // be printed out. I expect key is connected to System.in.
    public static int getInt(Scanner key, String prompt) {
        while(!key.hasNextInt()) {
            String notAnInt = key.nextLine();
            System.out.println();
            System.out.println(notAnInt + " is not an integer.");
            System.out.print(prompt);
        }
        int result = key.nextInt();
        key.nextLine();
        return result;
    }
}
