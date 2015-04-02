package maze.logic;

public class DeathEvent extends Event {

	private final Entity entity;

	public DeathEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
	
	@Override
	public String description() {
		return this.entity.getClass().getSimpleName() + " killed";
	}

}
