package JHW;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Controller {
	public int scoretank1;
	public int scoretank2;
	public boolean tank1_dead = false;
	public boolean tank2_dead = false;
	public BattleField battlefield;
	public Tank tank1;
	public Maze maze;
	public Tank tank2;
	public Ammo ammo;
	public WeaponSpawner spawner;
	
	public Controller(BattleField battlefield) {
		maze = new Maze(0, 0);
		int lowX = 0; 
		int lowY = 0;
		int highX = 500; 
		int highY = 600;
		
		int startX1 = Controller.getRandomNumberInRange(lowX, highX);
		int startY1 = Controller.getRandomNumberInRange(lowY, highY);
		int startX2 = Controller.getRandomNumberInRange(lowX, highX);
		int startY2 = Controller.getRandomNumberInRange(lowY, highY);
		int startSWX = Controller.getRandomNumberInRange(lowX, highX);
		int startSWY = Controller.getRandomNumberInRange(lowY, highY);
		
		spawner = new WeaponSpawner(new Coordinate(startSWX, startSWY), this);
		
		tank1 = new Tank(new Coordinate(startX1, startY1), ID.Tank1, this);
		tank2 = new Tank(new Coordinate(startX2, startY2), ID.Tank2, this);
		
		while(startX1 == startX2 && startY1 == startY2 && tank1.wallCollosion() == true && tank2.wallCollosion() == true && 
				spawner.wallCollosion() == true) {
			startX1 = Controller.getRandomNumberInRange(lowX, highX);
			startY1 = Controller.getRandomNumberInRange(lowY, highY);
			startX2 = Controller.getRandomNumberInRange(lowX, highX);
			startY2 = Controller.getRandomNumberInRange(lowY, highY);
			startSWX = Controller.getRandomNumberInRange(lowX, highX);
			startSWY = Controller.getRandomNumberInRange(lowY, highY);
			tank1 = new Tank(new Coordinate(startX1, startY1), ID.Tank1, this);
			tank2 = new Tank(new Coordinate(startX2, startY2), ID.Tank2, this);
			spawner = new WeaponSpawner(new Coordinate(startSWX, startSWY), this);
		}

		ammo = new Ammo();
		
		this.battlefield = battlefield;
		scoretank1 = 0;
		scoretank2 = 0;
	}
	
	public void init() {
		battlefield.addGameObject(tank1);
		battlefield.addGameObject(tank2);
		battlefield.addGameObject(spawner);
	}
	
	public void round() {
		if (this.tank1_dead) {
			this.scoretank2++;
			this.tank1_dead = false;
		} else if (this.tank2_dead) {
			this.scoretank1++;
			this.tank2_dead = false;
		}
	}
	
	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		return (int) ((Math.random()*((max-min)+1))+min);
	}
	
	public void renderScoring(Graphics g) {
		Color scoring_background = new Color(0, 0, 0, 110);
		g.setColor(scoring_background);
		g.fillRect(710, 100, 390, 100);
		Font font = new Font("Arial", Font.PLAIN, 30);
		g.setColor(Color.white);
		g.drawLine(900, 100, 900, 200);
        g.setFont(font);
        g.drawString(Integer.toString(scoretank1), 850, 165);
        g.drawString(Integer.toString(scoretank2), 935, 165);
        g.setColor(Color.red);
        g.drawString("Tank", 770, 90);
        g.setColor(Color.blue);
        g.drawString("Tank", 960, 90);
	}
	
	public void renderAmmo(Graphics g) {
		int numOfAmmo = 5;
		for(int i = 1; i <= numOfAmmo; ++i) {
			g.setColor(Color.GREEN.darker());
			g.fillOval(690 + i * 25, 146, 20, 20);
		}
		
		for(int i = 1; i <= numOfAmmo; ++i) {
			g.setColor(Color.GREEN.darker());
			g.fillOval(940 + i * 25, 146, 20, 20);
		}
		
		for (int i = 1; i <= this.tank1.getNumofbullets(); ++i) {
				g.setColor(Color.red.darker());
				g.fillOval(940 + i * 25, 146, 20, 20);
		}
		
		for (int i = 1; i <= this.tank2.getNumofbullets(); ++i) {
			g.setColor(Color.red.darker());
			g.fillOval(690 + i * 25, 146, 20, 20);
		}
	}
	
	public void renderControllerMaze(Graphics g) {
        maze.renderMaze(g);
	}
}
