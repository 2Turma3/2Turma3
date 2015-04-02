package maze.logic;

public class FireSpittingEvent extends Event {

	private final Dragon dragon;
	private final Position endPosition;

	public FireSpittingEvent(Dragon dragon, Position endPosition) {
		this.dragon = dragon;
		this.endPosition = endPosition;
	}

	public Dragon getDragon() {
		return this.dragon;
	}
	
	public Position getEndPosition() {
		return this.endPosition;
	}
	
	@Override
	public String description() {
		return this.dragon.getClass().getSimpleName() + " is spitting fire from " + this.dragon.getPos() +
				" to " + this.endPosition;
	}
}
