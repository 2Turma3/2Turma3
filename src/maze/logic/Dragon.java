package maze.logic;

import java.util.LinkedList;

public class Dragon extends Entity {

	private static final long serialVersionUID = -8399521267325161472L;
	private boolean sleeping;
	
	public static enum Action {MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, STOP, SLEEP, ATTACK};
	private LinkedList<Action> availableActions;
	public static final int ATTACK_RADIUS = 3;
	
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
	
	public void enableSleeping(boolean canSleep) {
		getAvailableActions().remove(Action.SLEEP);
		if (canSleep)
			getAvailableActions().add(Action.SLEEP);
	}
	
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

	public void enableAttack(boolean canAttack) {
		getAvailableActions().remove(Action.ATTACK);
		
		if (canAttack)
			getAvailableActions().add(Action.ATTACK);
	}
	
	public boolean isSleeping() {
		return sleeping;
	}
	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}
	public LinkedList<Action> getAvailableActions() {
		return availableActions;
	}

	
	

}
