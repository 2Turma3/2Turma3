package maze.logic;

public class FightEvent extends Event {

	private final Hero hero;
	private final Dragon dragon;

	public FightEvent(Hero hero, Dragon dragon) {
		this.hero = hero;
		this.dragon = dragon;
	}

	public Hero getHero() {
		return this.hero;
	}
	
	public Dragon getDragon() {
		return this.dragon;
	}
	
	@Override
	public String description() {
		return this.hero.getClass().getSimpleName() + " at " + hero.getPos() + " is fighting " +
			   this.dragon.getClass().getSimpleName() + " at " + this.dragon.getPos();
	}

}
