package maze.logic;

import java.util.ArrayList;

public class Dragon extends Entity {
	private boolean sleeping;
	
	public static enum Action {MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, STOP, SLEEP, ATTACK};
	private ArrayList<Action> availableActions;
	public static final int ATTACK_RADIUS = 3;
	
	
	public Dragon(Position pos, boolean active, boolean canMove, boolean canSleep, boolean canAttack) {
		super(pos, active);
		setSleeping(false);
		availableActions = new ArrayList<Action>();
		
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
	public boolean isSleeping() {
		return sleeping;
	}
	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}
	public ArrayList<Action> getAvailableActions() {
		return availableActions;
	}

	
	

}
