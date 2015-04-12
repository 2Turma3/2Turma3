package maze.logic;

/**
 * The Class Weapon.
 */
public class Weapon extends Entity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 838252283178966858L;

	/**
	 * The Enum Type discriminates all the possible weapons that can exist in the game.
	 */
	public enum Type{SWORD, DART, SHIELD}
	
	/** The type. */
	private Type type;
	
	/**
	 * Instantiates a new weapon.
	 *
	 * @param type the type
	 * @param pos the position
	 */
	public Weapon(Type type, Position pos) {
		super(pos);
		this.setType(type);
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Type type) {
		this.type = type;
	}
}
