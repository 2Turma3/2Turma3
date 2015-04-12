package maze.logic;

import java.util.LinkedList;

/**
 * The Class Dragon.
 */
public class Dragon extends Entity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8399521267325161472L;
	
	/** The sleeping. */
	private boolean sleeping;
	
	/**
	 * The Enum Action describes all the allowed actions for the Dragon
	 */
	public static enum Action { MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, STOP, SLEEP, ATTACK};
	
	/** The available actions. */
	private LinkedList<Action> availableActions;
	
	/** The Constant ATTACK_RADIUS represents the maximum range of the fire spit by the dragon */
	public static final int ATTACK_RADIUS = 3;
	
	/**
	 * Instantiates a new dragon.
	 *
	 * @param pos the pos
	 * @param canMove if the dragon can move
	 * @param canSleep if the dragon can sleep
	 * @param canAttack if the dragon can attack
	 */
	public Dragon(Position pos, boolean canMove, boolean canSleep, boolean canAttack) {
		super(pos);
		setSleeping(false);
		availableActions = new LinkedList<Action>();
		
		if(canMove){
			getAvailableActions().add(Action.MOVE_UP);
			getAvailableActions().add(Action.MOVE_DOWN);
			getAvailableActions().add(Action.MOVE_LEFT);
			getAvailableActions().add(Action.MOVE_RIGHT);
		}
		if(canSleep)
			getAvailableActions().add(Action.SLEEP);
		if(canAttack){
			getAvailableActions().add(Action.ATTACK);
		}
		
		getAvailableActions().add(Action.STOP);
	}
	
	/**
	 * Enable sleeping.
	 *
	 * @param canSleep the can sleep
	 */
	public void enableSleeping(boolean canSleep) {
		getAvailableActions().remove(Action.SLEEP);
		if (canSleep)
			getAvailableActions().add(Action.SLEEP);
	}
	
	/**
	 * Enable movement.
	 *
	 * @param canMove the can move
	 */
	public void enableMovement(boolean canMove) {
		getAvailableActions().remove(Action.MOVE_UP);
		getAvailableActions().remove(Action.MOVE_DOWN);
		getAvailableActions().remove(Action.MOVE_LEFT);
		getAvailableActions().remove(Action.MOVE_RIGHT);
		
		if (canMove) {
			getAvailableActions().add(Action.MOVE_UP);
			getAvailableActions().add(Action.MOVE_DOWN);
			getAvailableActions().add(Action.MOVE_LEFT);
			getAvailableActions().add(Action.MOVE_RIGHT);
		}
	}

	/**
	 * Enable attack.
	 *
	 * @param canAttack the can attack
	 */
	public void enableAttack(boolean canAttack) {
		getAvailableActions().remove(Action.ATTACK);
		
		if (canAttack)
			getAvailableActions().add(Action.ATTACK);
	}
	
	/**
	 * Checks if is sleeping.
	 *
	 * @return true, if is sleeping
	 */
	public boolean isSleeping() {
		return sleeping;
	}
	
	/**
	 * Sets the sleeping.
	 *
	 * @param sleeping the new sleeping
	 */
	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}
	
	/**
	 * Gets the available actions.
	 *
	 * @return the available actions
	 */
	public LinkedList<Action> getAvailableActions() {
		return availableActions;
	}

	
	

}
