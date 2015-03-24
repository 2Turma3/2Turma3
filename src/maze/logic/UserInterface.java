package maze.logic;

import java.io.IOException;

import maze.logic.Game.Action;
import maze.logic.Game.Direction;

public interface UserInterface {
	public void getInput(Action action, Direction direction) throws IOException;
	public void displayMaze(MazeMap map);
	public void displayMessage(String message);
}
