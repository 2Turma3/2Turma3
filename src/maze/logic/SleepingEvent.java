package maze.logic;

public class SleepingEvent extends Event {

	private final Dragon dragon;

	public SleepingEvent(Dragon dragon) {
		this.dragon = dragon;
	}

	public Dragon getDragon() {
		return dragon;
	}

	@Override
	public String description() {
		return this.dragon.getClass().getSimpleName() + " is sleeping at " + dragon.getPos(); 
	}

}
