package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable {
	private int board[][];
	private int size;
	
	/**
	 * Constructor
	 *
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size) {
		this.size = 0;
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
		// Move player
		
		// if move not possible:
		// 		return false;

		return true;
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid() {
		/* Set every position in array to 0 */
	}
	
	/**
	 * Check if a player has 5 in row
	 *
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player) {
		// if player has 5 in row:
		// 		return true

		return false;
	}
	
	
}
