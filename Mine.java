package JHW;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;

public class Mine extends Weapon{

	public Mine(Coordinate cord, Controller controller, ID id) {
		super(cord, controller, id);
		this.timer = 2000;
	}

	public boolean reduceTimer() {
		timer--;
		if (timer < 0) {
			this.controller.ammo.weapons.remove(this);
			return true;
		}
		return false;
	}

	public Double getShape() {
		Ellipse2D.Double shape = new Ellipse2D.Double(cord.getx(),cord.gety(),30,30);
        return shape;
    }

	public void render(Graphics g) {
		if (this.timer <= 2000 && this.timer >= 1800) {
			g.setColor(Color.black);
			g.fillOval((int)cord.getx(), (int)cord.gety(), 30, 30);
		}
	}

	public void tick() {}

}
