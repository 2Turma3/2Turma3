package maze.logic;

import java.util.LinkedList;

/**
 * The Class Hero.
 */
public class Hero extends Entity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3062730048433352896L;
	
	/** The weapons. */
	private LinkedList<Weapon> weapons;
	
	/**
	 * Instantiates a new hero.
	 *
	 * @param pos the position
	 */
	public Hero(Position pos) {
		super(pos);
		weapons = new LinkedList<Weapon>();
	}
	
	/**
	 * Gets the weapons.
	 *
	 * @return the weapons
	 */
	public LinkedList<Weapon> getWeapons() {
		return weapons;
	}
	
	/**
	 * Adds the weapon.
	 *
	 * @param weapon the weapon
	 */
	public void addWeapon(Weapon weapon){
		weapons.add(weapon);
	}

	/**
	 * Removes the weapon.
	 *
	 * @param weapon the weapon
	 */
	public void removeWeapon(Weapon weapon){
		weapons.remove(weapon);
	}
	
	/**
	 * Checks if the hero has a sword.
	 *
	 * @return true, if successful
	 */
	public boolean hasSword(){
		for(Weapon weapon: weapons)
			if(weapon.getType().equals(Weapon.Type.SWORD))
				return true;
		return false;
	}
	
	/**
	 * Gets the number of darts carried by the hero.
	 *
	 * @return the darts number
	 */
	public int getDartsNumber(){
		int sum = 0;
		for(Weapon weapon : weapons)
			if(weapon.getType().equals(Weapon.Type.DART))
				sum++;
		return sum;
	}
	
	/**
	 * Checks if the hero has a shield.
	 *
	 * @return true, if successful
	 */
	public boolean hasShield(){
		for(Weapon weapon: weapons)
			if(weapon.getType().equals(Weapon.Type.SHIELD))
				return true;
		return false;
	}
}
