package maze.logic;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * The Class GameBoard.
 */
public class GameBoard implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8459426522971236426L;
	
	/** The map. */
	private MazeMap map;
	
	/** The hero. */
	private Hero hero;
	
	/** The dragons. */
	private LinkedList<Dragon> dragons;
	
	/** The weapons. */
	private LinkedList<Weapon> weapons;
	
	/**
	 * Instantiates a new game board.
	 *
	 * @param map the map
	 * @param hero the hero
	 * @param dragons the dragons
	 * @param weapons the weapons
	 */
	public GameBoard(MazeMap map, Hero hero, LinkedList<Dragon> dragons, LinkedList<Weapon> weapons) {
		setMap(map);
		setHero(hero);
		setDragons(dragons == null ? new LinkedList<Dragon>() : dragons);
		setWeapons(weapons == null ? new LinkedList<Weapon>() : weapons);
	}

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public MazeMap getMap() {
		return map;
	}

	/**
	 * Sets the map.
	 *
	 * @param map the map to set
	 */
	public void setMap(MazeMap map) {
		this.map = map;
	}

	/**
	 * Gets the hero.
	 *
	 * @return the hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Sets the hero.
	 *
	 * @param hero the hero to set
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/**
	 * Gets the dragons.
	 *
	 * @return the dragons
	 */
	public LinkedList<Dragon> getDragons() {
		return dragons;
	}

	/**
	 * Sets the dragons.
	 *
	 * @param dragons the dragons to set
	 */
	public void setDragons(LinkedList<Dragon> dragons) {
		this.dragons = dragons;
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
	 * Sets the weapons.
	 *
	 * @param weapons the weapons to set
	 */
	public void setWeapons(LinkedList<Weapon> weapons) {
		this.weapons = weapons;
	}
	
}
