package JHW;

import java.awt.Canvas;
import javax.swing.JFrame;

public class Window extends Canvas{
	private static final long serialVersionUID = 1029908189926096743L;

	public Window(int width, int height, String title, Game game) {
		JFrame f = new JFrame(title);
		f.requestFocus();
		f.setSize(width, height);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(game);
		game.start();
	}
}
