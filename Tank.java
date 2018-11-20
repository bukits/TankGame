package JHW;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;


public class Tank extends GameObject{
	
	private int direction;
	private static final int rotationSpeed = 3;
	private static final double forwardSpeed = 2;
	private static final double reverseSpeed = -.65;
	private static final int width = 30;
	private static final int height = 55;
	private ID id;
	private int weaponType;
	private int numberOfBulletsFired;
	private Controller controller;
	private boolean moovingForward = false;

	public Tank(Coordinate cord, ID id, Controller controller) {
		super(cord);
		this.id = id;
		this.direction = (int) (Math.random() * 360);
		numberOfBulletsFired = 0;
		this.controller = controller;
		this.weaponType = 2;
	}
	
	public void turnRigth() {
		this.direction += rotationSpeed;
		if(this.direction >= 359) {
			this.direction -= 360;
		}
	}
	
	public void turnLeft() {
		this.direction -= rotationSpeed;
		if(this.direction < 0) {
			this.direction += 360;
		}
	}
	
	public void move(double speed) {
		Coordinate position = new Coordinate(this.cord.getx() + (speed * Math.cos(Math.toRadians(90-this.direction))),
				this.cord.gety() - (speed * Math.sin(Math.toRadians(90-this.direction))));
		if (this.wallCollosion() == true) {
			if (this.moovingForward == true) {
			position = new Coordinate(this.cord.getx() - (5 * Math.cos(Math.toRadians(90- this.direction))) ,
					this.cord.gety() + (5 * Math.sin(Math.toRadians(90-this.direction))));
			} else if (this.moovingForward == false) {
				position = new Coordinate(this.cord.getx() + (5 * Math.cos(Math.toRadians(90- this.direction))) ,
						this.cord.gety() - (5 * Math.sin(Math.toRadians(90-this.direction))));
			}
		}
		this.cord = position;
		this.weaponCollosion();
	}
	
	public void moveForward() {
		move(forwardSpeed);
		this.moovingForward = true;
	}
	
	public void moveReverse() {
		move(reverseSpeed);
		this.moovingForward = false;
	}
	
	public void shoot() {
		Coordinate startBullet = null;
		if (direction < 270 && direction > 90) {
			startBullet = new Coordinate(cord.getx() + 56 * Math.cos(Math.toRadians(90-this.direction)),
					cord.gety() - 56 * Math.sin(Math.toRadians(90-this.direction)));
		} else {
			startBullet = new Coordinate(cord.getx() + 32 * Math.cos(Math.toRadians(90-this.direction)),
					cord.gety() - 33 * Math.sin(Math.toRadians(90-this.direction)));
		}
		Weapon weapon = null;
		if (this.weaponType == 2) {
				weapon = new Bullet(startBullet, true, direction, controller, id);
				this.numberOfBulletsFired += 1;
		} else if (this.weaponType == 0) {
			weapon = new Bomb(startBullet, direction, controller, id); 
		} else if (this.weaponType == 1) {
			weapon = new Mine(startBullet, controller, id);
		}
		controller.ammo.addWeapon(weapon);
		this.weaponType = 2;
		
		/**
		if (this.numberOfBulletsFired < 5) {
			Coordinate startBullet = null;
			if (direction < 270 && direction > 90) {
				startBullet = new Coordinate(cord.getx() + 56 * Math.cos(Math.toRadians(90-this.direction)),
						cord.gety() - 56 * Math.sin(Math.toRadians(90-this.direction)));
			} else {
				startBullet = new Coordinate(cord.getx() + 32 * Math.cos(Math.toRadians(90-this.direction)),
						cord.gety() - 33 * Math.sin(Math.toRadians(90-this.direction)));
			}
			this.numberOfBulletsFired += 1;			
			Weapon weapon = null;
			
			weapon = new Bullet(startBullet, true, direction, controller, id);
			
			switch(this.weaponType) {
				case 0:
					weapon = new Bomb(startBullet, direction, controller, id);
				break;
				case 1:
					weapon = new Mine(startBullet, controller, id);
				break;
				case 2:
					weapon = new Bullet(startBullet, true, direction, controller, id);
				break;
			}
			controller.ammo.addWeapon(weapon);
		}
		**/
	}
	
	public boolean wallCollosion() {
		for(int i = 0; i < controller.maze.walls.size(); ++i) {
			Area tankArea = this.getArea();
			tankArea.intersect(new Area(controller.maze.walls.get(i)));
			if (tankArea.isEmpty() == false) {
				return true;
			}
		}
		return false;
	}
	
	public void weaponCollosion() {
		Area tankArea = this.getArea();
		tankArea.intersect(new Area(controller.spawner.rect));
		if (tankArea.isEmpty() == false) {
			this.setWeapon(controller.spawner.bulletType);
			controller.battlefield.removeGameObject(controller.spawner);
		}
	}
	
	public void render(Graphics g) {
		controller.ammo.render(g);

		Graphics2D g2d = (Graphics2D)g;
		AffineTransform savedTransform = g2d.getTransform();

		if (id == ID.Tank1) {
	        g2d.setColor(Color.blue);
		}
		else if (id == ID.Tank2) {
	        g2d.setColor(Color.red);
		}
		g2d.rotate(Math.toRadians(this.direction), cord.getx(), cord.gety());
        g2d.fillRect((int)cord.getx() - width/2, (int)cord.gety() - height/2, width, height);
        g.setColor(new Color(37, 37, 37));
        g2d.fillRect((int)cord.getx() - 5/2, (int)cord.gety() - 74/2, 5, 40);
        g2d.drawRect((int)cord.getx() - 7, (int)cord.gety() - 5, 14, 14);
        g2d.setTransform(savedTransform);
	}
	
	public Area getArea() {
		double side = Math.min(height, width);
		Area rect1 = new Area(new Rectangle2D.Double(cord.getx()-side/2, cord.gety()-side/2, side, side));
		return rect1;
	}
	
	public void death() {
		if (this.getID() == ID.Tank1) {
			controller.tank1_dead = true;
		} else if (this.getID() == ID.Tank2) {
			controller.tank2_dead = true;
		}
		controller.round();
	}
	
	public void increaseNumberofbullets() {
		this.numberOfBulletsFired++;
	}
	
	public void decreaseNumberofbullets() {
		if(this.numberOfBulletsFired > 0) {
			this.numberOfBulletsFired -= 1;
		}
	}
	
	public int getNumofbullets()  {
		return this.numberOfBulletsFired;
	}

	public void tick() {}
	
	public void setWeapon(int type) {
		this.weaponType = type;
	}
	
	public ID getID() {
		return id;
	}
}