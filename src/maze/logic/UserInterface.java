package maze.logic;

import java.io.IOException;

import maze.logic.Game.Action;
import maze.logic.Game.Direction;

public interface UserInterface {
	public Command getInput() throws IOException;
	public void displayMaze(MazeMap map);
	public void displayMessage(String message);
	
	public class Command {
		private Action action;
		private Direction direction;
		
		public Command(Action action, Direction direction) {
			this.setAction(action);
			this.setDirection(direction);
		}
		public Action getAction() {
			return action;
		}
		public void setAction(Action action) {
			this.action = action;
		}
		public Direction getDirection() {
			return direction;
		}
		public void setDirection(Direction direction) {
			this.direction = direction;
		}
	}
	
	
}
