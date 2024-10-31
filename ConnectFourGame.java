import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class ConnectFourGame extends Board implements gameInterface{ //Inherit board class & implement the interface
	private BufferedReader input;
    private Random random;
	private char currentPlayer;
	String RED = "\u001B[31m";
	String YELLOW = "\u001B[33m";
	String GREEN = "\u001B[32m";
	String RESET = "\u001B[0m";
	
	public ConnectFourGame() {
        input = new BufferedReader(new InputStreamReader(System.in));
        random = new Random();
        
	}
	
	@Override
	public void startGame() { //Will override gameInterface method.
		printInstructions();
        resetBoard();
        printBoard();
        boolean win = false;
        currentPlayer = firstPlayer();
        
        while (!win) {
            if (currentPlayer == 'r') {
                int move = getUserMove();
                if (move < 1 || move > 7) {
                    System.out.println("Invalid input. Please enter a number between 1 and 7.");
                    continue;
                }

                if (!placeToken(currentPlayer, move - 1)) { //Account for 0 Index.
                    System.out.println("Column is full. Please choose another column.");
                    continue;
                }
            }
            else {
            	getComputerMove();
            }

            printBoard();
            win = checkWin(currentPlayer);

            if (win) { // Check for win.
            	System.out.println(GREEN + "******************************" + RESET);
                System.out.println((currentPlayer == 'r' ? RED + "          Red" : YELLOW + "Yellow") + " wins!" + RESET);
                System.out.println(GREEN + "******************************" + RESET);
                if (restartGame()) { //Ask user to play again after a win.
                	win = false;
                    resetBoard(); 
                    currentPlayer = firstPlayer();
                    continue; // Restart the loop.
                }
            } else if (isBoardFull()) { ///Ask user to play again after a draw.
                System.out.println("The board is full. It's a draw!");
                if (restartGame()) {
                    resetBoard();
                    printBoard();
                    currentPlayer = firstPlayer();
                    continue;
                }
            }

            currentPlayer = (currentPlayer == 'r') ? 'y' : 'r'; //Switch players after every turn.
        }
	}
	
	public void printInstructions() {
    	System.out.println("****************************************************************************************");
        System.out.println(GREEN + "                            Connect Four Instructions              " + RESET);
        System.out.println("****************************************************************************************");
        System.out.println("Players take turns dropping their colored token (" + RED + "Red  " + RESET + "or " + YELLOW + "Yellow" + RESET + ") into one of the columns.");
        System.out.println("The goal is to be the first to connect four of your tokens in a row.");
        System.out.println("This can be done horizontally, vertically, or diagonally.");
        System.out.println("The game ends when a player connects four counters or the board is full.");
        System.out.println("****************************************************************************************");
        System.out.println("");
    }
	
	
	
	private int getUserMove() {
		System.out.print(" Choose a column (1-7): ");
		System.out.println("");
		try {
			String input = this.input.readLine();
			return Integer.parseInt(input);
		} catch (Exception e) {
			return -1;
		}
	}
	
	private int getComputerMove() { //Computer chooses random slot.
		int move;
		do {
			move = random.nextInt(COLUMNS);
		} while (!placeToken(currentPlayer, move));
		System.out.println("Computer places in column " + (move + 1)); //Adjust for 0 index.
		System.out.println("");
		return move;
	}
	
    private char firstPlayer() {//Option to choose which player starts the game.
        char color;
        while (true) {
            try {
                System.out.println("Choosing starting player:");
                System.out.println( "Enter "+ RED + " 'r' for Red(Player)" + RESET  + " or " + YELLOW + "'y' for Yellow(CPU) " + RESET);
                String input = this.input.readLine();
                color = input.charAt(0);
                if (color == 'r' || color == 'y') {
                    return color;
                } else {
                    System.out.println("Error: Please choose "+ RED +"'r' for Red(Player) "+ RESET +" or "+ YELLOW +"'y' for Yellow.(CPU)" + RESET);
                }
            } catch (IOException e) {
                System.out.println("Error reading input. Try again.");
            }
        }
    }
    
	@Override
	public boolean checkWin(char player) { //Check 3 win functions.
        return checkHorizontalWin(player) || checkVerticalWin(player) || checkDiagonalWin(player);
    }

    private boolean checkHorizontalWin(char player) {
        for (int i = 0; i < board.length; i++) {
            int count = 0; //Keep count of tokens in a horizontal line.
            for (int j = 0; j < board[i].length; j++) {
                count = (board[i][j] == player) ? count + 1 : 0;
                if (count >= 4) return true;
            }
        }
        return false;
    }

    private boolean checkVerticalWin(char player) {
        for (int j = 0; j < board[0].length; j++) {
            int count = 0; //Keep count of tokens in a vertical line.
            for (int i = 0; i < board.length; i++) {
                count = (board[i][j] == player) ? count + 1 : 0;
                if (count >= 4) return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalWin(char player) { //Check possible diagonal combinations of at least 4 tokens.
        for (int i = 0; i < board.length - 3; i++) { //Bottom to top.
            for (int j = 0; j < board[i].length - 3; j++) {
                if (board[i][j] == player && board[i + 1][j + 1] == player &&
                    board[i + 2][j + 2] == player && board[i + 3][j + 3] == player) {
                    return true;
                }
            }
        }

        for (int i = 3; i < board.length; i++) { //Top to bottom.
            for (int j = 0; j < board[i].length - 3; j++) {
                if (board[i][j] == player && board[i - 1][j + 1] == player &&
                    board[i - 2][j + 2] == player && board[i - 3][j + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }
	
    private boolean restartGame() {
        while (true) {
            System.out.print("Would you like to play again? ");
            System.out.print("Enter 'y' for yes or 'n' for no ");
            
            try {
                String response = this.input.readLine().trim().toLowerCase();
                if (response.equals("y")) {
                    return true; // Player wants to play again
                } else if (response.equals("n")) {
                    return false; // Player does not want to play again
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'");
                }
            } catch (IOException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
    }

}
