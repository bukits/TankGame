package JHW;

import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public abstract class Weapon extends GameObject {
	
	protected Controller controller;
	protected int timer;
	protected ID id;
	
	public Weapon(Coordinate cord, Controller controller, ID id) {
		super(cord);
		this.id = id;
		this.controller = controller;
		this.timer = 1000;
	}
	
	public abstract boolean reduceTimer();
		
	public abstract Ellipse2D.Double getShape();
	
	public void tankCollosion() {
		Area tank1Area = controller.tank1.getArea();
		Area tank2Area = controller.tank2.getArea();
		tank1Area.intersect(new Area(this.getShape()));
		tank2Area.intersect(new Area(this.getShape()));
		
		if (tank1Area.isEmpty() == false) {
			controller.tank1.death();
			controller.ammo.removeWeapon(this);
		} else if (tank2Area.isEmpty() == false) {
			controller.tank2.death();
			controller.ammo.removeWeapon(this);
		}
	}
	
	public abstract void render(Graphics g);
	
	public abstract void tick();
}
