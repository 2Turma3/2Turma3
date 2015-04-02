package maze.logic;

import java.util.LinkedList;

public class ThrowingDartEvent extends Event {

	private final Hero hero;
	private final Position endPosition;

	public ThrowingDartEvent(Hero hero, Position endPosition) {
		this.hero = hero;
		this.endPosition = endPosition;
	}
	
	public Hero getHero() {
		return this.hero;
	}
	
	public Position getEndPosition() {
		return this.endPosition;
	}
		@Override
	public String description() {
		return this.hero.getClass().getSimpleName() + " is throwing a dart from " + hero.getPos() +
			   " to " + this.endPosition;
	}

}
