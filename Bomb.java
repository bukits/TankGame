package JHW;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

public class Bomb extends Bullet{
	
	private int pieces;

	public Bomb(Coordinate cord, double angle, Controller controller, ID id) {
		super(cord, true, angle, controller, id);
		this.pieces = 20;
	}
	
	public boolean reduceTimer() {
		timer--;
		return false;
	}

	public Double getShape() {
		Ellipse2D.Double shape = new Ellipse2D.Double(cord.getx(),cord.gety(),10,10);
        return shape;
	}

	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillOval((int)cord.getx(), (int)cord.gety(), 10, 10);
	}

	public void tick() {
		if (this.timer > 900) {
			super.tick();
		} else {
			double angStep = 360.0/pieces;
			double ang = 0;
			for(int i = 0; i < pieces; ++i) {
				Coordinate startCord = new Coordinate(
						cord.getx() + 10 * Math.cos(Math.toRadians(ang)),
						cord.gety() + 10 * Math.sin(Math.toRadians(ang)));
				Bullet temp = new Bullet(startCord, false, ang, controller, id);
				
				ang += angStep;
				controller.ammo.addWeapon(temp);
				
				if (id == ID.Tank1) {
		    		controller.tank1.increaseNumberofbullets();
		    	} else {
		    		controller.tank2.increaseNumberofbullets();
		    	}
			}
			controller.ammo.removeWeapon(this);
		}
	}
}
