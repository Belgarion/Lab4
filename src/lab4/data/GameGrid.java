package lab4.data;

import java.util.Observable;
import java.util.Arrays;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable {

	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;

	// Number of squares in a row required to win.
	private static final int INROW = 5;

	private int board[][];
	private int size;

	/**
	 * Constructor
	 *
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size) {
		this.size = size;
		board = new int[size][size];
	}

	/**
	 * Reads a location of the grid
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y) {
		return board[x][y];
	}

	/**
	 * Returns the size of the grid
	 *
	 * @return the grid size
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Enters a move in the game grid
	 *
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player) {
		if (board[x][y] != EMPTY) {
			// Cell is not empty
			return false;
		}
		board[x][y] = player;

		return true;
	}

	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid() {
		Arrays.fill(this.board, 0);

		setChanged();
		notifyObservers();
	}

	/**
	 * Check if a player has 5 in row
	 *
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player) {
		// I don't like this solution, should be able to solve this in a nicer way.
		for (int i = 0; i < board.length - INROW; i++) {
			for (int j = 0; j < board[i].length - INROW; j++) {
				if (board[i][j] != player) {
					continue;
				}

				// Check horizontal
				boolean match = true;
				for (int k = 0; k < INROW; k++) {
					if (board[i+k][j] != player) {
						match = false;
					}
				}
				if (match) {
					return true;
				}

				// Check vertical
				match = true;
				for (int k = 0; k < INROW; k++) {
					if (board[i][j+k] != player) {
						match = false;
					}
				}
				if (match) {
					return true;
				}

				// Check diagonal southwest
				match = true;
				for (int k = 0; k < INROW; k++) {
					if (board[i+k][j+k] != player) {
						match = false;
					}
				}
				if (match) {
					return true;
				}

				// Check diagonal northwest
				if (j >= INROW-1) {
					match = true;
					for (int k = 0; k < INROW; k++) {
						if (board[i+k][j-k] != player) {
							match = false;
						}
					}
					if (match) {
						return true;
					}
				}
			}
		}

		return false;
	}


}
