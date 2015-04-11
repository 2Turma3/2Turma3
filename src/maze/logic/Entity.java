package maze.logic;

public abstract class Entity {
	private Position position;
	private boolean active;
	
	Entity(Position position, boolean active){
		setPosition(position);
		this.active = active;
	}
	
	public Position getPos() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
