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
	}
	
	@Override
	public Command getInput() throws IOException {
		String line = scanner.nextLine();
		if (!inputMapping.containsKey(line))
			throw new IOException();
		
		Command command = inputMapping.get(line);
		return command;
	}

	@Override
	public void displayMaze(MazeMap map) {
		char[][] printableMap = new char[map.getRows()][map.getCols()];
		boolean hasDragon;
		boolean hasSword;
		boolean hasShield;
		boolean hasDart;
		boolean hasHero;
		boolean heroHasSword;
		boolean dragonIsSleeping;
		boolean hasFlame;
		
		for(int i = 0; i < map.getRows(); i++)
			for (int j = 0; j < map.getCols(); j++)
			{
				if (map.isEmptyCell(new Position(i, j)))
				{
					printableMap[i][j] = map.isWalkable(i, j) ? cellMapping.get(CellData.EMPTY) : cellMapping.get(CellData.WALL);
					continue;
				}
				hasDragon = false;
				hasSword = false;
				hasShield = false;
				hasDart = false;
				hasHero = false;
				heroHasSword = false;
				dragonIsSleeping = false;
				hasFlame = false;
				
				LinkedList<Entity> entities = map.getEntities(new Position(i, j));
				for (Entity entity : entities)
				{
					if (entity instanceof Dragon)
					{
						hasDragon = true;
						dragonIsSleeping = ((Dragon) entity).isSleeping();
					}
					else if (entity instanceof Hero)
					{
						hasHero = true;
						for (Weapon weapon : ((Hero) entity).getWeapons())
							if (weapon.getType() == Weapon.Type.SWORD)
							{
								heroHasSword = true;
								break;
							}
					}
					else if (entity instanceof Weapon)
					{
						switch (((Weapon) entity).getType())
						{
							case SWORD:
								hasSword = true;
								break;
							case DART:
								hasDart = true;
								break;
							case SHIELD:
								hasShield = true;
								break;
						}
					}
					else if(entity instanceof Flame)
						hasFlame = true;
				}
				if(hasFlame){
					printableMap[i][j] = cellMapping.get(CellData.FLAME);
				}
				if (hasDragon)
				{
					if(hasSword || hasShield || hasDart)
						printableMap[i][j] = dragonIsSleeping ? cellMapping.get(CellData.DRAGON_SLEEP_WEAPON) : cellMapping.get(CellData.DRAGON_ON_WEAPON);
					else
						printableMap[i][j] = dragonIsSleeping ? cellMapping.get(CellData.DRAGON_SLEEP) : cellMapping.get(CellData.DRAGON);
				}
				
				else if(hasHero)
					printableMap[i][j] = heroHasSword ? cellMapping.get(CellData.HERO_SWORD) : cellMapping.get(CellData.HERO);
			
				else if (hasShield)
					printableMap[i][j] = cellMapping.get(CellData.SHIELD);
				else if (hasDart)					
					printableMap[i][j] = cellMapping.get(CellData.DART);
				else if (hasSword)
					printableMap[i][j] = cellMapping.get(CellData.SWORD);
			}

		printableMap[map.getExit().getRow()][map.getExit().getCol()] =  cellMapping.get(CellData.EXIT);
		
		
		
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
