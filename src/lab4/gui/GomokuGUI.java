package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer {
	private GomokuClient client;
	private GomokuGameState gamestate;

	private JFrame frame;
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
		gamestate.addObserver(this);

		frame = new JFrame("Gomoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponents();

		frame.setLayout(createLayout());

		frame.setLocation(0, 0);
		frame.setVisible(true);
		frame.setSize(370, 370);

	}

	private void addComponents() {
		gamePanel = new GamePanel(gamestate.getGameGrid());
		gamePanel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				int[] gridPos = gamePanel.getGridPosition(e.getX(), e.getY());
				gamestate.move(gridPos[0], gridPos[1]);
			}

			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
		});

		messageLabel = new JLabel();

		connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectionWindow cw = new ConnectionWindow(client);
			}
		});

		newGameButton = new JButton("New game");
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamestate.newGame();
			}
		});

		disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamestate.disconnect();
			}
		});

		frame.add(gamePanel);
		frame.getContentPane().add(messageLabel);
		frame.getContentPane().add(connectButton);
		frame.getContentPane().add(newGameButton);
		frame.getContentPane().add(disconnectButton);

	}

	private SpringLayout createLayout() {
		SpringLayout layout = new SpringLayout();
		layout.putConstraint(SpringLayout.NORTH, connectButton, 5,SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.NORTH, newGameButton, 5, SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.NORTH, disconnectButton, 5, SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.WEST, newGameButton, 5, SpringLayout.EAST, connectButton);
		layout.putConstraint(SpringLayout.WEST, disconnectButton, 5, SpringLayout.EAST, newGameButton);
		layout.putConstraint(SpringLayout.NORTH, messageLabel, 5, SpringLayout.SOUTH, connectButton);

		return layout;
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

		frame.repaint();

	}

}
