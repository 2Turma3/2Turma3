import maze.cli.CommandLine;
import maze.logic.*;
import maze.logic.Game.Action;
import maze.logic.Game.Direction;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class Labirinto {

//	public static void printMaze(MazeMap map){
//		char[][] printableMap = new char[map.getRows()][map.getCols()];
//		
//		for(int i = 0; i < map.getRows(); i++)
//			for (int j = 0; j < map.getCols(); j++)
//				printableMap[i][j] = map.isWalkable(i, j) ? ' ' : 'X';
//
//		printableMap[map.getExit().getRow()][map.getExit().getCol()] =  'S';
//		
//		for(int i = 0; i < printableMap.length; i++){
//			for (int j = 0; j < printableMap[i].length; j++)
//				System.out.print(printableMap[i][j]);
//			System.out.println();
//		}
//		
//		
//	}
	
	
	public static void main(String[] args) throws IOException{
//		Game game = new Game(10,10,3, true, true, true);
//		UserInterface cli	= new CommandLine();
//		Command command = null;
//		//while(!game.isGameOver()){
//			cli.displayMaze(game.map);
//			try{
//			command = cli.getInput();
//			}catch(IOException e){
//				cli.displayMessage("Input Inválido\n");
//				continue;
//			}
//			
//			
//			playTurn(game, cli, command);
//		}
//		if(game.isWon())
//			cli.displayMessage("Good job!\n");
//		else{
//			cli.displayMessage("Too bad :(\n");
//		}
	}

	public static void playTurn(Game game, CommandLine cli, Game.Command command) {
//		game.heroTurn(command.getAction(), command.getDirection());	
//		
//		cli.displayMaze(game.map);
//		
//		
//		
//		try{
//			Thread.sleep(1000);
//		}catch(InterruptedException e){}
//		
//		game.dragonsTurn();
//		cli.displayMaze(game.map);
//		
//		try{
//			Thread.sleep(1000);
//		}catch(InterruptedException e){}
//		
//		game.resolutionPhase();
//		cli.displayMaze(game.map);
//		
//		try{
//			Thread.sleep(1000);
//		}catch(InterruptedException e){}
	}
}
