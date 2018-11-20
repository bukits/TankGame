package JHW;

import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class WeaponSpawner extends GameObject{

    private BufferedImage icon;
    public int bulletType;
    public Rectangle2D.Double rect;
    public String[] bulletIcons = {"bombRect","mineRect"};
    private Random rand = new Random();
    private Controller controller;

	public WeaponSpawner(Coordinate cord, Controller controller) {
		super(cord);
		this.controller = controller;
		bulletType = rand.nextInt(2);
		rect = new Rectangle2D.Double(cord.getx(),cord.gety(),30,30);
		try {
			icon = ImageIO.read(new File("C://test//" + bulletIcons[bulletType] + ".png"));
		} catch (IOException e) {
			System.out.println("icon not found");
		}
	}

	public void render(Graphics g) {
        g.drawImage(icon, (int) cord.getx(), (int) cord.gety(), null);
	}
	
	public boolean wallCollosion() {
		for(int i = 0; i < controller.maze.walls.size(); ++i) {
			Area WeaponArea = this.getArea();
			WeaponArea.intersect(new Area(controller.maze.walls.get(i)));
			if (WeaponArea.isEmpty() == false) {
				return true;
			}
		}
		return false;
	}
	
	public Area getArea() {
		Area rect = new Area(new Rectangle2D.Double(cord.getx(),cord.gety(),30,30));
		return rect;
	}
	
	public void tick() {}
	
	
}
