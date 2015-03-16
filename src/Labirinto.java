import maze.cli.CommandLine;
import maze.logic.*;

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
	
	
	public static void main(String[] args){
		Game game = new Game(10,10,3);
		CommandLine cli	= new CommandLine();
		cli.displayMaze(game.map);
	}
}
