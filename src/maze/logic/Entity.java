package maze.logic;

public abstract class Entity {
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
