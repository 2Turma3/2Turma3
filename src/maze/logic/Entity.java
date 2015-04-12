package maze.logic;

import java.io.Serializable;

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = -2744807344964284190L;
	private Position position;
	
	Entity(Position position){
		setPosition(position);
	}
	
	public Position getPos() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
}
