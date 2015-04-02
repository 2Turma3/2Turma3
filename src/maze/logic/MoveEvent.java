package maze.logic;

public class MoveEvent extends Event {

	private final Entity entity;
	private final Position newPosition;

	public MoveEvent(Entity entity, Position newPosition) {
		this.entity = entity;
		this.newPosition = newPosition;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public Position getNewPosition() {
		return this.newPosition;
	}
	
	@Override
	public String description() {
		return this.entity.getClass().getSimpleName() + " is moving from " + this.entity.getPos() +
				" to " + this.newPosition;
	}

}
