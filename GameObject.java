package JHW;

import java.awt.Graphics;

public abstract class GameObject {
	public Coordinate cord;
	
	public GameObject(Coordinate cord) {
		this.cord = cord;
	}
	
	public abstract void render(Graphics g);
	public abstract void tick();
	
	public void setCord(Coordinate cord) {
		this.cord = cord;
	}
	
	public Coordinate getCord() {
		return cord;
	}
}
