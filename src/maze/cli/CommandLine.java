package maze.cli;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Map;

import maze.logic.Entity;
import maze.logic.Game.Action;
import maze.logic.Game.Direction;
import maze.logic.*;

public class CommandLine implements UserInterface{
	private Scanner scanner;
	private Map<String, Command> inputMapping;
	private enum CellData {EMPTY, WALL, HERO, HERO_SWORD, DRAGON, DRAGON_SLEEP, SWORD, DART, SHIELD, DRAGON_ON_WEAPON, DRAGON_SLEEP_WEAPON, EXIT, FLAME}
	private Map<CellData, Character> cellMapping;
	

	public CommandLine() {
		scanner = new Scanner(System.in);
		inputMapping = new HashMap<String, Command>();
		cellMapping = new HashMap<CellData, Character>();
		initInputMapping();
		initCellMapping();
	}
	
	private void initCellMapping(){
		cellMapping.put(CellData.EMPTY, ' ');
		cellMapping.put(CellData.WALL, 'X');
		cellMapping.put(CellData.HERO, 'H');
		cellMapping.put(CellData.HERO_SWORD, 'A');
		cellMapping.put(CellData.DRAGON, 'D');
		cellMapping.put(CellData.DRAGON_SLEEP, 'd');
		cellMapping.put(CellData.DRAGON_ON_WEAPON, 'F');
		cellMapping.put(CellData.DRAGON_SLEEP_WEAPON, 'f');
		cellMapping.put(CellData.SWORD, 'E');
		cellMapping.put(CellData.SHIELD, 'O');
		cellMapping.put(CellData.DART, 'I');
		cellMapping.put(CellData.EXIT, 'S');
		cellMapping.put(CellData.FLAME, 'Y');
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
		inputMapping.put(" ", new Command(Action.STOP, null));
	}
	
	@Override
	public Command getInput() throws IOException {
		String line = scanner.nextLine();
		if (!inputMapping.containsKey(line))
			throw new IOException();
		
		Command command = inputMapping.get(line);
		return command;
	}

	public void displayBoard(GameBoard board) {
		MazeMap map = board.getMap();
		char[][] printableMap = new char[map.getRows()][map.getCols()];
		
		for(int i = 0; i < map.getRows(); i++)
			for (int j = 0; j < map.getCols(); j++)
				printableMap[i][j] = map.isWalkable(i, j) ? cellMapping.get(CellData.EMPTY) : cellMapping.get(CellData.WALL);
			
		printableMap[map.getExit().getRow()][map.getExit().getCol()] =  cellMapping.get(CellData.EXIT);	
		
		if (board.getHero() != null)
			printableMap[board.getHero().getPos().getRow()][board.getHero().getPos().getCol()] = board.getHero().hasSword() ? cellMapping.get(CellData.HERO_SWORD) : cellMapping.get(CellData.HERO);
		
		for (Dragon dragon : board.getDragons())
			printableMap[dragon.getPos().getRow()][dragon.getPos().getCol()] = dragon.isSleeping() ? cellMapping.get(CellData.DRAGON_SLEEP) : cellMapping.get(CellData.DRAGON);
		
		for (Weapon weapon : board.getWeapons())
		{
			if (printableMap[weapon.getPos().getRow()][weapon.getPos().getCol()] == cellMapping.get(CellData.DRAGON))
				printableMap[weapon.getPos().getRow()][weapon.getPos().getCol()] = cellMapping.get(CellData.DRAGON_ON_WEAPON);
			else if (printableMap[weapon.getPos().getRow()][weapon.getPos().getCol()] == cellMapping.get(CellData.DRAGON_SLEEP))
				printableMap[weapon.getPos().getRow()][weapon.getPos().getCol()] = cellMapping.get(CellData.DRAGON_SLEEP_WEAPON);
			else
				switch (weapon.getType()) {
					case SWORD:
						printableMap[weapon.getPos().getRow()][weapon.getPos().getCol()] = cellMapping.get(CellData.SWORD);
						break;
					case SHIELD:
						printableMap[weapon.getPos().getRow()][weapon.getPos().getCol()] = cellMapping.get(CellData.SHIELD);
						break;
					case DART:
						printableMap[weapon.getPos().getRow()][weapon.getPos().getCol()] = cellMapping.get(CellData.DART);
						break;
			}
		}
		
		for(int i = 0; i < printableMap.length; i++){
			for (int j = 0; j < printableMap[i].length; j++)
				System.out.print(printableMap[i][j]);
			System.out.println();
		}
		
	}

	@Override
	public void displayMessage(String message) {
		System.out.println(message);
	}
	
}
