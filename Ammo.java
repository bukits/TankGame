package JHW;

import java.awt.Graphics;
import java.util.ArrayList;

public class Ammo {
	public ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	
	public void render(Graphics g) {
		for(int i = 0; i < weapons.size(); ++i) {
			Weapon tempWeapon = weapons.get(i);
			tempWeapon.render(g);
		}
	}
	
	public void tick() {
		for(int i = 0; i < weapons.size(); ++i) {
			Weapon tempWeapon = weapons.get(i);
			tempWeapon.tick();
			boolean removed = tempWeapon.reduceTimer();
			if (!removed) {
				tempWeapon.tankCollosion();
			}
		}
	}
	
	public void addWeapon(Weapon w) {
		weapons.add(w);
	}
	
	public void removeWeapon(Weapon w) {
		weapons.remove(w);
	}
	
}