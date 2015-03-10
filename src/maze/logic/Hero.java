package maze.logic;

import java.util.ArrayList;

public class Hero extends Entity {
	private ArrayList<Weapon> weapons;
	
	public enum Action{MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT}
	
	
	public Hero(Position pos, boolean active) {
		super(pos, active);
	}
	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	
	public void addWeapon(Weapon weapon){
		weapons.add(weapon);
	}

	public void removeWeapon(Weapon weapon){
		weapons.remove(weapon);
	}
}
