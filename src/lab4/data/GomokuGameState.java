/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer {

   // Game variables
	private static final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;

    //Possible game states
	private static final int NOT_STARTED = 0;
	private static final int MY_TURN = 2;
	private static final int OTHER_TURN = 4;
	private static final int FINISHED = 8;
	private int currentState;

	private GomokuClient client;

	private String message;

	/**
	 * The constructor
	 *
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc) {
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);

		currentState = NOT_STARTED;
	}


	/**
	 * Returns the message string
	 *
	 * @return the message string
	 */
	public String getMessageString() {
		return message;
	}

	/**
	 * Returns the game grid
	 *
	 * @return the game grid
	 */
	public GameGrid getGameGrid() {
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y) {
		if (currentState != MY_TURN){
			if (currentState == NOT_STARTED) {
				message = "The game has not been started.";
			}
			if (currentState == OTHER_TURN) {
				message = "It is not your turn.";
			}
			if (currentState == FINISHED) {
				message = "The game is allready finished.";
			}
			setChanged();
			notifyObservers();
			return;
		}

		if (gameGrid.move(x, y, GameGrid.ME)) {
			client.sendMoveMessage(x, y);
			if (gameGrid.isWinner(GameGrid.ME)) {
				message = "You won!";
				currentState = FINISHED;
			} else {
				message = "Other players turn";
				currentState = OTHER_TURN;
			}
			client.setGameState(this);
		} else {
			message = "Invalid move";
		}

		setChanged();
		notifyObservers();
	}

	/**
	 * Starts a new game with the current client
	 */
	public void newGame() {
		if( currentState == NOT_STARTED){
			return;
		}
		currentState = OTHER_TURN;
		gameGrid.clearGrid();
		message = "New game";
		client.sendNewGameMessage();
		setChanged();
		notifyObservers();
	}

	/**
	 * Other player has requested a new game, so the
	 * game state is changed accordingly
	 */
	public void receivedNewGame() {
		currentState = MY_TURN;
		gameGrid.clearGrid();
		message = "New game";
		setChanged();
		notifyObservers();
	}

	/**
	 * The connection to the other player is lost,
	 * so the game is interrupted
	 */
	public void otherGuyLeft() {
		currentState = FINISHED;
		gameGrid.clearGrid();
		message = "The other player left";
		setChanged();
		notifyObservers();
	}

	/**
	 * The player disconnects from the client
	 */
	public void disconnect() {
		currentState = FINISHED;
		gameGrid.clearGrid();
		message = "You left";
		setChanged();
		notifyObservers();
		client.disconnect();
	}

	/**
	 * The player receives a move from the other player
	 *
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {
		gameGrid.move(x, y, GameGrid.OTHER);
		if (gameGrid.isWinner(GameGrid.OTHER)) {
			message = "You lose";
			currentState = FINISHED;
		} else {
			currentState = MY_TURN;
			message = "Your turn";
		}
		setChanged();
		notifyObservers();
	}

	public void update(Observable o, Object arg) {

		switch(client.getConnectionStatus()) {
			case GomokuClient.CLIENT:
				message = "Game started, it is your turn!";
				currentState = MY_TURN;
				break;
			case GomokuClient.SERVER:
				message = "Game started, waiting for other player...";
				currentState = OTHER_TURN;
				break;
		}

		setChanged();
		notifyObservers();
	}

}
