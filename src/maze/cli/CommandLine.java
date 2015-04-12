package maze.cli;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

import maze.logic.Game.Action;
import maze.logic.Game.Direction;
import maze.logic.*;

public class CommandLine {
	private Scanner scanner;
	private Map<String, Game.Command> inputMapping;
	private enum CellData {EMPTY, WALL, HERO, HERO_SWORD, DRAGON, DRAGON_SLEEP, SWORD, DART, SHIELD, DRAGON_ON_WEAPON, DRAGON_SLEEP_WEAPON, EXIT, FLAME}
	private Map<CellData, Character> cellMapping;
	private Game game;
	
	public static void main(String[] args) {
		CommandLine cli = new CommandLine();
		cli.run();
	}

	public void run() {
		boolean validInput;
		boolean canSleep = false;
		boolean canMove = false;
		boolean canAttack = false;
		boolean staticMap = false;
		int numDragons = 0;
		String input;
		
		validInput = false;
		while (!validInput) {
			System.out.print("Number of dragons? ");
			input = scanner.nextLine();
			
			try {
				numDragons = Integer.parseUnsignedInt(input);
				validInput = true;
			}
			catch (Exception e) {
			}
		}

		if (numDragons > 0) {
			validInput = false;
			while (!validInput) {
				System.out.print("Dragons can move (y or n)? ");
				input = scanner.nextLine();
				
				if (input.equals("y")) {
					canMove = true;
					validInput = true;
				}
				else if (input.equals("n")) {
					canMove = false;
					validInput = true;
				}
			}
			
			validInput = false;
			while (!validInput) {
				System.out.print("Dragons can sleep (y or n)? ");
				input = scanner.nextLine();
				
				if (input.equals("y")) {
					canSleep = true;
					validInput = true;
				}
				else if (input.equals("n")) {
					canSleep = false;
					validInput = true;
				}
			}
			validInput = false;
			while (!validInput) {
				System.out.print("Dragons can spit fire (y or n)? ");
				input = scanner.nextLine();
				
				if (input.equals("y")) {
					canAttack = true;
					validInput = true;
				}
				else if (input.equals("n")) {
					canAttack = false;
					validInput = true;
				}
			}
		}
		validInput = false;
		while (!validInput) {
			System.out.print("Use default map (y or n)? ");
			input = scanner.nextLine();
			
			if (input.equals("y")) {
				staticMap = true;
				validInput = true;
			}
			else if (input.equals("n")) {
				staticMap = false;
				validInput = true;
			}
		}
		
		if (!staticMap) {
			int rows = 10;
			int columns = 10;
			
			validInput = false;
			while (!validInput) {
				System.out.print("Number of rows (>= 10)? ");
				input = scanner.nextLine();
				
				try {
					rows = Integer.parseUnsignedInt(input);
					validInput = rows >= 10;
				}
				catch (Exception e) {
				}
			}
			
			validInput = false;
			while (!validInput) {
				System.out.print("Number of columns (>= 10)? ");
				input = scanner.nextLine();
				
				try {
					columns = Integer.parseUnsignedInt(input);
					validInput = columns >= 10;
				}
				catch (Exception e) {
				}
			}
			
			game = new Game(rows, columns, numDragons, canMove, canSleep, canAttack);
		}
		else
			game = new Game(numDragons, canMove, canSleep, canAttack);
		
		play();
	}
		

	private void play() {
		System.out.println("Commands: w - UP, a - LEFT, s - DOWN, d - RIGHT, [directio]t - throw dart, SPACE - Skip turn");
		
		while (!game.isFinished()) {
			displayBoard(game.getBoard());
			
			Game.Command command = null;
			while (command == null) {
				try {
					System.out.print("Command? ");
					command = getInput();
				} catch (IOException e) {
				}
			}
			
			game.heroTurn(command);
			game.dragonsTurn();
			game.resolutionPhase();
		}
		
		System.out.println(game.getEndOfGameMessage());
		if (game.isWon())
			System.out.println("Congratulations, you won!");
		else
			System.out.println("Better luck next time...");
	}

	public CommandLine() {
		scanner = new Scanner(System.in);
		inputMapping = new HashMap<String, Game.Command>();
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
		inputMapping.put("w", new Game.Command(Action.MOVE, Direction.UP));
		inputMapping.put("a", new Game.Command(Action.MOVE, Direction.LEFT));
		inputMapping.put("s", new Game.Command(Action.MOVE, Direction.DOWN));
		inputMapping.put("d", new Game.Command(Action.MOVE, Direction.RIGHT));
		inputMapping.put("wt", new Game.Command(Action.ATTACK, Direction.UP));
		inputMapping.put("at", new Game.Command(Action.ATTACK, Direction.LEFT));
		inputMapping.put("st", new Game.Command(Action.ATTACK, Direction.DOWN));
		inputMapping.put("dt", new Game.Command(Action.ATTACK, Direction.RIGHT));
		inputMapping.put(" ", new Game.Command(Action.STOP, null));
	}
	
	public Game.Command getInput() throws IOException {
		String line = scanner.nextLine();
		if (!inputMapping.containsKey(line))
			throw new IOException();
		
		Game.Command command = inputMapping.get(line);
		return command;
	}

	public void displayBoard(GameBoard board) {
		MazeMap map = board.getMap();
		char[][] printableMap = new char[map.getRows()][map.getCols()];
		
		for(int i = 0; i < map.getRows(); i++)
			for (int j = 0; j < map.getCols(); j++)
				printableMap[i][j] = map.isWalkable(i, j) ? cellMapping.get(CellData.EMPTY) : cellMapping.get(CellData.WALL);
			
		printableMap[map.getExit().getRow()][map.getExit().getColumn()] =  cellMapping.get(CellData.EXIT);	
		
		if (board.getHero() != null)
			printableMap[board.getHero().getPosition().getRow()][board.getHero().getPosition().getColumn()] = board.getHero().hasSword() ? cellMapping.get(CellData.HERO_SWORD) : cellMapping.get(CellData.HERO);
		
		for (Dragon dragon : board.getDragons())
			printableMap[dragon.getPosition().getRow()][dragon.getPosition().getColumn()] = dragon.isSleeping() ? cellMapping.get(CellData.DRAGON_SLEEP) : cellMapping.get(CellData.DRAGON);
		
		for (Weapon weapon : board.getWeapons())
		{
			if (printableMap[weapon.getPosition().getRow()][weapon.getPosition().getColumn()] == cellMapping.get(CellData.DRAGON))
				printableMap[weapon.getPosition().getRow()][weapon.getPosition().getColumn()] = cellMapping.get(CellData.DRAGON_ON_WEAPON);
			else if (printableMap[weapon.getPosition().getRow()][weapon.getPosition().getColumn()] == cellMapping.get(CellData.DRAGON_SLEEP))
				printableMap[weapon.getPosition().getRow()][weapon.getPosition().getColumn()] = cellMapping.get(CellData.DRAGON_SLEEP_WEAPON);
			else
				switch (weapon.getType()) {
					case SWORD:
						printableMap[weapon.getPosition().getRow()][weapon.getPosition().getColumn()] = cellMapping.get(CellData.SWORD);
						break;
					case SHIELD:
						printableMap[weapon.getPosition().getRow()][weapon.getPosition().getColumn()] = cellMapping.get(CellData.SHIELD);
						break;
					case DART:
						printableMap[weapon.getPosition().getRow()][weapon.getPosition().getColumn()] = cellMapping.get(CellData.DART);
						break;
			}
		}
		
		for(int i = 0; i < printableMap.length; i++){
			for (int j = 0; j < printableMap[i].length; j++)
				System.out.print(printableMap[i][j]);
			System.out.println();
		}
		
	}	
}
