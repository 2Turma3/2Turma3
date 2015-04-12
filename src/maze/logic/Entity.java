package maze.logic;

import java.io.Serializable;

/**
 * The Class Entity represents an element that can be placed inside the maze
 */
public abstract class Entity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2744807344964284190L;
	
	/** The position. */
	private Position position;
	
	/**
	 * Instantiates a new entity.
	 *
	 * @param position the position
	 */
	Entity(Position position){
		setPosition(position);
	}
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
}
