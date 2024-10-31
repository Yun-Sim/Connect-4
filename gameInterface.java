public interface gameInterface { //Interface containing common functions for board games.
	void printInstructions();
	void startGame();
	void resetBoard();
	void printBoard();
	boolean placeToken(char player, int column);
	boolean isBoardFull();
	boolean checkWin(char player);
}
