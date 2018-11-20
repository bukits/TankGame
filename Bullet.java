package JHW;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Rectangle2D;

public class Bullet extends Weapon {
	
	private boolean bounce;
	private double angle;
	private static final double speed = 2;
    public Rectangle2D previousWall;

	public Bullet(Coordinate cord, boolean bounce, double angle, Controller controller, ID id) {
		super(cord, controller, id);
		this.bounce = bounce;
		this.angle = angle;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillOval((int)cord.getx(), (int)cord.gety(), 6, 6);
	}
	
	public void wallCollosion()  {
		for(int j=0;j<controller.maze.walls.size();j++) { 
            Area bulletArea = new Area(this.getShape()); 
            Area temp = bulletArea; 
            temp.intersect(new Area(controller.maze.walls.get(j))); 
            if(temp.isEmpty()==false) {
            	if (bounce) {
                    if(this.previousWall==null) { 
                        this.previousWall=controller.maze.walls.get(j); 
                        if(controller.maze.wallType.get(j)==1) { 
                            this.setAngle(360-this.getAngle()); 
                        } else { 
                            this.setAngle(180-this.getAngle()); 
                        } 
                    } 
                    else if(!this.previousWall.equals(controller.maze.walls.get(j))) { 
                        this.previousWall=controller.maze.walls.get(j); 
                        if(controller.maze.wallType.get(j)==1) { 
                            this.setAngle(360-this.getAngle()); 
                        } else { 
                            this.setAngle(180-this.getAngle());
                        } 
                    }
            	} else {
            		this.removeBullet();
        			return;
        		}
            }
        } 	
	}

	public void tick() {
		Coordinate nextcord = new Coordinate(cord.getx() + (speed * Math.cos(Math.toRadians(90-this.angle))),
				cord.gety() - (speed * Math.sin(Math.toRadians(90-this.angle))));		
		this.wallCollosion();
		this.cord = nextcord;
	}
	
	public void setAngle(double a) {
        angle = (a+360)%360;
    }

    public double getAngle() {
        return angle;
    }
    
    public void removeBullet() {
    	if (id == ID.Tank1) {
    		controller.tank1.decreaseNumberofbullets();
    	} else {
    		controller.tank2.decreaseNumberofbullets();
    	}
    	controller.ammo.removeWeapon(this);
    }

    
	public Double getShape() {
		Ellipse2D.Double shape = new Ellipse2D.Double(cord.getx(), cord.gety(), 6, 6);
		return shape;	
	}

	public boolean reduceTimer() {
		timer--;
		if (timer < 0) {
			this.removeBullet();
			return true;
		}
		return false;
	}
}