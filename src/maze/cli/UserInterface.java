package maze.cli;

import java.io.IOException;

import maze.logic.Game.Action;
import maze.logic.Game.Direction;
import maze.logic.MazeMap;

public interface UserInterface {
	public void getInput(Action action, Direction direction) throws IOException;
	public void displayMaze(MazeMap map);
	public void displayMessage(String message);
}
