package maze.logic;

import java.io.Serializable;
import java.util.LinkedList;

public class GameBoard implements Serializable {

	private static final long serialVersionUID = 8459426522971236426L;
	private MazeMap map;
	private Hero hero;
	private LinkedList<Dragon> dragons;
	private LinkedList<Weapon> weapons;
	
	public GameBoard(MazeMap map, Hero hero, LinkedList<Dragon> dragons, LinkedList<Weapon> weapons) {
		setMap(map);
		setHero(hero);
		setDragons(dragons == null ? new LinkedList<Dragon>() : dragons);
		setWeapons(weapons == null ? new LinkedList<Weapon>() : weapons);
	}

	/**
	 * @return the map
	 */
	public MazeMap getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(MazeMap map) {
		this.map = map;
	}

	/**
	 * @return the hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * @param hero the hero to set
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/**
	 * @return the dragons
	 */
	public LinkedList<Dragon> getDragons() {
		return dragons;
	}

	/**
	 * @param dragons the dragons to set
	 */
	public void setDragons(LinkedList<Dragon> dragons) {
		this.dragons = dragons;
	}

	/**
	 * @return the weapons
	 */
	public LinkedList<Weapon> getWeapons() {
		return weapons;
	}

	/**
	 * @param weapons the weapons to set
	 */
	public void setWeapons(LinkedList<Weapon> weapons) {
		this.weapons = weapons;
	}
	
}
