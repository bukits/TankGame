package JHW;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Game extends Canvas {
	
	private static final long serialVersionUID = -3807866137453037785L;
	public static final int width = 1100;
	public static final int height = 740;
	private boolean running = false;
	private BattleField battlefield;
	private Menu menu;
	private Controller controller;	
	private State gamestate = State.Menu;
	private KeyInput keyInput = new KeyInput(this);
	private boolean[] instructionsArray = new boolean[10];
			
	public Game() {
		battlefield = new BattleField();
		controller = new Controller(battlefield);
		menu = new Menu(this, battlefield, controller);
		
		this.addKeyListener(keyInput);
		this.addMouseListener(menu);
	}
	
	public void start() {
		running = true;
		long lasttime = System.nanoTime();
		double amountofticks = 60;
		double ns = 1000000000 / amountofticks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lasttime) /ns;
			lasttime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void render() {		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(224, 224, 224));
		g.fillRect(0, 0, width, height);
		
		if(gamestate == State.Menu || gamestate == State.Help) {
			menu.render(g);
		} else if (gamestate == State.Game) {
			this.battlefield.render(g);
			controller.renderControllerMaze(g);
			controller.renderScoring(g);
			controller.renderAmmo(g);
		}
		g.dispose();
		bs.show();
	}
	
	private void tick() {
		if (gamestate == State.Game) {
			if (instructionsArray[0]){
				controller.tank1.moveForward();
			} else if (instructionsArray[2]){
				controller.tank1.moveReverse();
			}
			if (instructionsArray[1]){
				controller.tank1.turnLeft();
			} else if (instructionsArray[3]){
				controller.tank1.turnRigth();		
			}
			if (instructionsArray[5]){
				controller.tank2.moveForward();
			} else if (instructionsArray[7]){
				controller.tank2.moveReverse();		
			}
			if (instructionsArray[6]){
				controller.tank2.turnLeft();			
			}else if (instructionsArray[8]){
					controller.tank2.turnRigth();		
			}
			if (instructionsArray[4]){
				controller.tank1.shoot();
				instructionsArray[4]=false;
			}
			if (instructionsArray[9]){
				controller.tank2.shoot();
				instructionsArray[9]=false;
			}
			controller.ammo.tick();
		}
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if (key==KeyEvent.VK_W){
			instructionsArray[0]=true;
		}else if (key==KeyEvent.VK_A){
			instructionsArray[1]=true;
		}else if (key==KeyEvent.VK_S){
			instructionsArray[2]=true;
		}else if (key==KeyEvent.VK_D){
			instructionsArray[3]=true;
		}else if (key==KeyEvent.VK_Q){
			instructionsArray[4]=true;
		}else if (key==KeyEvent.VK_UP){
			instructionsArray[5]=true;
		}else if (key==KeyEvent.VK_LEFT){
			instructionsArray[6]=true;
		}else if (key==KeyEvent.VK_DOWN){
			instructionsArray[7]=true;
		}else if (key==KeyEvent.VK_RIGHT){
			instructionsArray[8]=true;
		}else if (key==KeyEvent.VK_SPACE){
			instructionsArray[9]=true;
		}
	}
		
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if (key==KeyEvent.VK_W){
			instructionsArray[0]=false;
		}else if (key==KeyEvent.VK_A){
			instructionsArray[1]=false;
		}else if (key==KeyEvent.VK_S){
			instructionsArray[2]=false;
		}else if (key==KeyEvent.VK_D){
			instructionsArray[3]=false;
		}else if (key==KeyEvent.VK_Q){
			instructionsArray[4]=false;
		}else if (key==KeyEvent.VK_UP){
			instructionsArray[5]=false;
		}else if (key==KeyEvent.VK_LEFT){
			instructionsArray[6]=false;
		}else if (key==KeyEvent.VK_DOWN){
			instructionsArray[7]=false;
		}else if (key==KeyEvent.VK_RIGHT){
			instructionsArray[8]=false;
		}else if (key==KeyEvent.VK_SPACE){
			instructionsArray[9]=false;
		}
	}

	public void stop() {
		running = false;
	}
	
	public State getState() {
		return gamestate;
	}
	
	public void setState(State gamestate) {
		this.gamestate = gamestate;
	}
	
	public static void main(String[] args) {
		new Window(width, height, "Tank Game", new Game());
	}
}