package maze.logic;

import java.util.LinkedList;

public class Hero extends Entity {
	private LinkedList<Weapon> weapons;
	
	public enum Action{MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT}
	
	
	public Hero(Position pos, boolean active) {
		super(pos, active);
		weapons = new LinkedList<Weapon>();
	}
	public LinkedList<Weapon> getWeapons() {
		return weapons;
	}
	
	public void addWeapon(Weapon weapon){
		weapons.add(weapon);
	}

	public void removeWeapon(Weapon weapon){
		weapons.remove(weapon);
	}
	
	public boolean hasSword(){
		for(Weapon weapon: weapons)
			if(weapon.getType().equals(Weapon.Type.SWORD))
				return true;
		return false;
	}
	
	public int getDartsNumber(){
		int sum = 0;
		for(Weapon weapon : weapons)
			if(weapon.getType().equals(Weapon.Type.DART))
				sum++;
		return sum;
	}
	
	public boolean hasShield(){
		for(Weapon weapon: weapons)
			if(weapon.getType().equals(Weapon.Type.SHIELD))
				return true;
		return false;
	}
}
