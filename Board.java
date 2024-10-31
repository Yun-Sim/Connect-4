public abstract class Board { //Create 6x7 board (for standard version of Connect-4). Use interface to implement functions.
	protected char[][] board;
	protected static final int ROWS = 6;
	protected static final int COLUMNS = 7;
	protected static int filledSlots = 0;
	String RED = "\u001B[31m";
	String YELLOW = "\u001B[33m";
	String GREEN = "\u001B[32m";
	String RESET = "\u001B[0m";
	
	public Board() {
		board = new char[ROWS][COLUMNS];
	}
	
	public void resetBoard() { //Clear the board.
		for(int i =0; i<ROWS; i++) {
			for(int j = 0; j<COLUMNS; j++) {
				board[i][j] = '\u0000';
			}
		}
	}
	
	public void printBoard() {
		for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'r') {
                    System.out.print("| " + RED + "r " + RESET);
                } else if (board[i][j] == 'y') {
                    System.out.print("| " + YELLOW + "y " + RESET);
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
            System.out.println("-----------------------------");
        }
        System.out.println(GREEN + "  1   2   3   4   5   6   7" + RESET);
        System.out.println(" ");
	}
	
	public boolean placeToken(char player, int column) { //If possible place token in chosen column & increment counter for filled slots.
		for (int i = ROWS-1; i >= 0; i--) {
			if(board[i][column] == '\u0000') {
				board[i][column] = player;
				filledSlots++;
				return true;
			}
		}
		return false;
	}
	
	public boolean isBoardFull() { //Board is full if all slots on board are full.
	    return filledSlots == (board.length * board[0].length);
	}
}
