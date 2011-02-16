package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer {
	private GomokuClient client;
	private GomokuGameState gamestate;

	private GamePanel gamePanel;
	private JLabel messageLabel;
	private JButton connectButton;
	private JButton newGameButton;
	private JButton disconnectButton;

	/**
	 * The constructor
	 *
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c) {
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);


		gamePanel = new GamePanel(g.getGameGrid());
		messageLabel = new JLabel();
		connectButton = new JButton("Connect");
		newGameButton = new JButton("New game");
		disconnectButton = new JButton("Disconnect");

		JFrame frame = new JFrame("Gomoku");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		frame.add(gamePanel);
		frame.getContentPane().add(messageLabel);
		frame.getContentPane().add(connectButton);
		frame.getContentPane().add(newGameButton);
		frame.getContentPane().add(disconnectButton);


		frame.pack();
		frame.setLocation(0, 0);
		frame.setVisible(true);

		gamestate.addObserver(this);
	}


	public void update(Observable arg0, Object arg1) {

		// Update the buttons if the connection status has changed
		if (arg0 == client) {
			if (client.getConnectionStatus() == GomokuClient.UNCONNECTED) {
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			} else {
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}

		// Update the status text if the gamestate has changed
		if (arg0 == gamestate) {
			messageLabel.setText(gamestate.getMessageString());
		}

	}

}
