package maze.logic;

public abstract class Entity {
	private Position pos;
	private boolean active;
	
	Entity(Position pos, boolean active){
		this.pos = pos;
		this.active = active;
	}
	
	public Position getPos() {
		return pos;
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
