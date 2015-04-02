package maze.logic;

public class SleepEvent extends Event {

	private final Dragon dragon;
	private boolean sleeping;

	public SleepEvent(Dragon dragon, boolean sleeping) {
		this.dragon = dragon;
		this.sleeping = sleeping;
	}

	public Dragon getDragon() {
		return this.dragon;
	}
	
	public boolean isSleeping() {
		return this.sleeping;
	}

	@Override
	public String description() {
		return this.dragon.getClass().getSimpleName() +
			   (this.sleeping ? " is sleeping at " : " woke up at ")
			   + dragon.getPos(); 
	}

}
