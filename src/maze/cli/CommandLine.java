package maze.cli;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

import maze.logic.Game.Action;
import maze.logic.Game.Direction;
import maze.logic.MazeMap;

public class CommandLine implements UserInterface{
	private Scanner scanner;
	private Map<String, Command> inputMapping;
	private enum cellData {EMPTY, WALL, HERO, HERO_SWORD, DRAGON, DRAGON_SLEEP, SWORD, DART, SHIELD, DRAGON_ON_WEAPON, DRAGON_SLEEP_WEAPON, EXIT}
	private Map<cellData, Character> cellMapping;
	
	private class Command {
		private Action action;
		private Direction direction;
		
		Command(Action action, Direction direction) {
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
	
	public CommandLine() {
		scanner = new Scanner(System.in);
		inputMapping = new HashMap<String, Command>();
		initInputMapping();
		initCellMapping();
	}
	
	private void initCellMapping(){
		cellMapping.put(cellData.EMPTY, ' ');
		cellMapping.put(cellData.WALL, 'X');
		cellMapping.put(cellData.HERO, 'H');
		cellMapping.put(cellData.HERO_SWORD, 'A');
		cellMapping.put(cellData.DRAGON, 'D');
		cellMapping.put(cellData.DRAGON_SLEEP, 'd');
		cellMapping.put(cellData.DRAGON_ON_WEAPON, 'F');
		cellMapping.put(cellData.DRAGON_SLEEP_WEAPON, 'f');
		cellMapping.put(cellData.SWORD, 'E');
		cellMapping.put(cellData.SHIELD, 'O');
		cellMapping.put(cellData.DART, 'I');
		cellMapping.put(cellData.EXIT, 'S');
	}
	
	private void initInputMapping()
	{
		inputMapping.put("w", new Command(Action.MOVE, Direction.UP));
		inputMapping.put("a", new Command(Action.MOVE, Direction.LEFT));
		inputMapping.put("s", new Command(Action.MOVE, Direction.DOWN));
		inputMapping.put("d", new Command(Action.MOVE, Direction.RIGHT));
		inputMapping.put("wt", new Command(Action.ATTACK, Direction.UP));
		inputMapping.put("at", new Command(Action.ATTACK, Direction.LEFT));
		inputMapping.put("st", new Command(Action.ATTACK, Direction.DOWN));
		inputMapping.put("dt", new Command(Action.ATTACK, Direction.RIGHT));
	}
	
	@Override
	public void getInput(Action action, Direction direction) throws IOException {
		String line = scanner.nextLine();
		if (!inputMapping.containsKey(line))
			throw new IOException();
		
		Command command = inputMapping.get(line);
		action = command.getAction();
		direction = command.getDirection();
	}

	@Override
	public void displayMaze(MazeMap map) {
		
	}

	@Override
	public void displayMessage(String message) {
		System.out.println(message);
	}
	
}
