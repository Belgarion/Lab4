package lab4;

import lab4.data.GomokuGameState;
import lab4.client.GomokuClient;
import lab4.gui.GomokuGUI;

public class GomokuMain {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java GomokuMain <portnumber>");
			return;
		}

		int port = Integer.parseInt(args[0]);
		GomokuClient client = new GomokuClient(port);
		GomokuGameState gameState = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gameState, client);
	}
}
