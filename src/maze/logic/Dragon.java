package maze.logic;

import java.util.ArrayList;

public class Dragon extends Entity {
	private boolean sleeping;
	
	public static enum Action {MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, STOP, SLEEP};
	private ArrayList<Action> availableActions;
	
	
	
	public Dragon(Position pos, boolean active, boolean canMove, boolean canSleep) {
		super(pos, active);
		setSleeping(false);
		
		if(canMove){
			getAvailableActions().add(Action.MOVE_UP);
			getAvailableActions().add(Action.MOVE_DOWN);
			getAvailableActions().add(Action.MOVE_LEFT);
			getAvailableActions().add(Action.MOVE_RIGHT);
		}
		if(canSleep)
			getAvailableActions().add(Action.SLEEP);
		
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
