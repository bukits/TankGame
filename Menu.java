package JHW;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter{
	private Game game;
	private BattleField battlefield;
	private Controller controller = new Controller(battlefield);
	
	public Menu(Game game, BattleField battlefield, Controller controller) {
		this.game = game;
		this.battlefield = battlefield;
		this.controller = controller;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(mouseOver(mx, my, 450, 150, 200, 50)) {
			game.setState(State.Game);
			controller.init();
		}
		else if (mouseOver(mx, my, 450, 250, 200, 50)) {
			game.setState(State.Help);
		}
		else if (game.getState() == State.Help) {
			if (mouseOver(my, my, 450, 490, 200, 50)) {
				game.setState(State.Menu);
			}
		}
		else if (mouseOver(mx, my, 450, 350, 200, 50 )) {
			System.exit(1);
		}
	}
	
	public void mouseReleased(MouseEvent e) {}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my < y + height && my > y) {
				return true;
			}
			return false;
		}
		return false;
	}
		
	public void render(Graphics g) {
		Font font = new Font("Apple Casual",1, 35);
		g.setFont(font);
		g.setColor(Color.black);
		if (game.getState() == State.Menu) {
			g.drawString("Tank Game", 456, 100);
			
			g.drawString("Play", 515, 186);
			g.drawRect(450, 150, 200, 50);
			
			g.drawString("Help", 515, 286);
			g.drawRect(450, 250, 200, 50);
			
			g.drawString("Exit", 515, 386);
			g.drawRect(450, 350, 200, 50);
		}
		else if (game.getState() == State.Help) {
			g.drawString("Help", 510, 100);
			
			g.drawString("Back", 510, 525);
			g.drawRect(450, 490, 200, 50);
		}
		else if (game.getState() == State.Game) {
			g.drawRect(450, 490, 200, 50);
		}
	}
}
