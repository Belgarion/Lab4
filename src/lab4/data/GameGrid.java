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
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != player) {
					// Player has no mark here,
					// continue with next cell.
					continue;
				}

				if (checkHorizontal(player, i, j)) {
					return true;
				}

				if (checkVertical(player, i, j)) {
					return true;
				}

				if (checkSouthEast(player, i, j)) {
					return true;
				}

				if (checkNorthEast(player, i, j)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks for a match in horizontal direction starting at x, y.
	 *
	 * @param player Which player to check for.
	 * @param x Starting point in x.
	 * @param y Starting point in y.
	 * @return True if player has won, false otherwise.
	 */
	public boolean checkHorizontal(int player, int x, int y) {
		if (x > board.length - INROW) {
			return false;
		}

		for (int i = 0; i < INROW; i++) {
			if (board[x+i][y] != player) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks for a match in vertical direction starting at x, y.
	 *
	 * @param player Which player to check for.
	 * @param x Starting point in x.
	 * @param y Starting point in y.
	 * @return True if player has won, false otherwise.
	 */
	public boolean checkVertical(int player, int x, int y) {
		if (y > board[x].length-INROW) {
			return false;
		}

		for (int i = 0; i < INROW; i++) {
			if (board[x][y+i] != player) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks for a match in southeast direction starting at x, y.
	 *
	 * @param player Which player to check for.
	 * @param x Starting point in x.
	 * @param y Starting point in y.
	 * @return True if player has won, false otherwise.
	 */
	public boolean checkSouthEast(int player, int x, int y) {
		if (y > board[x].length-INROW || x > board.length-INROW) {
			return false;
		}

		for (int i = 0; i < INROW; i++) {
			if (board[x+i][y+i] != player) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks for a match in northeast direction starting at x, y.
	 *
	 * @param player Which player to check for.
	 * @param x Starting point in x.
	 * @param y Starting point in y.
	 * @return True if player has won, false otherwise.
	 */
	public boolean checkNorthEast(int player, int x, int y) {
		if (y < INROW-1 || x > board.length-INROW) {
			return false;
		}

		for (int i = 0; i < INROW; i++) {
			if (board[x+i][y-i] != player) {
				return false;
			}
		}
		return true;
	}
}
