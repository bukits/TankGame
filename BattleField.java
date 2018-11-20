package JHW;

import java.awt.Graphics;
import java.util.LinkedList;

public class BattleField {
	public LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
	
	public void render(Graphics g) {
		for(int i = 0; i < gameObjects.size(); ++i) {
			GameObject tempObject = gameObjects.get(i);
			tempObject.render(g);
		}
	}
	
	public void tick() {
		for(int i = 0; i < gameObjects.size(); ++i) {
			GameObject tempObject = gameObjects.get(i);
			tempObject.tick();
		}
	}
	
	public void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}
	
	public void removeGameObject(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}
}