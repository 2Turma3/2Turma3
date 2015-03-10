import java.util.Random;
import java.util.Scanner;


public class Labirinto {

	public static void printMaze(MazeMap map){
		char[][] printableMap = new char[map.getRows()][map.getCols()];
		
		for(int i = 0; i < map.getRows(); i++)
			for (int j = 0; j < map.getCols(); j++)
				printableMap[i][j] = map.isWalkable(i, j) ? ' ' : 'X';

		printableMap[map.getExit().getRow()][map.getExit().getCol()] =  'S';
		
		for(int i = 0; i < printableMap.length; i++){
			for (int j = 0; j < printableMap[i].length; j++)
				System.out.print(printableMap[i][j]);
			System.out.println();
		}
		
		
	}
	
	
	public static void main(String[] args){
		
		/*
		char[][] map = {{'X','X','X','X','X','X','X','X','X','X'},
						{'X','H',' ',' ',' ',' ',' ',' ',' ','X'},
						{'X',' ','X','X',' ','X',' ','X',' ','X'},
						{'X',' ','X','X',' ','X',' ','X',' ','X'},
						{'X',' ','X','X',' ','X',' ','X',' ','X'},
						{'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
						{'X',' ','X','X',' ','X',' ','X',' ','X'},
						{'X',' ','X','X',' ','X',' ','X',' ','X'},
						{'X',' ','X','X',' ',' ',' ',' ',' ','X'},
						{'X','X','X','X','X','X','X','X','X','X'}};
		int x,y;
		x=y=1;
		char type = 'H';
		int sword_x=1; int sword_y = 8;
		map[sword_y][sword_x] = 'E';
		int exit_x = 9; int exit_y = 5;
		int dragon_x = 1; int dragon_y = 3;
//		int dragon_x = 1; int dragon_y = 8;
		map[dragon_y][dragon_x] = 'D';
		Boolean armado = false;
		Boolean dragon = true;
		Scanner keyboard = new Scanner(System.in);
		String opt;
		Random moveDragon = new Random();
		
		while(!(x == exit_x && y == exit_y)){
			
			
			
			map[y][x]=type;
			if(!armado)
				map[sword_y][sword_x] = 'E';
			if(dragon)
				map[dragon_y][dragon_x] = 'D';
			if(sword_x==dragon_x && sword_y == dragon_y)
				map[sword_y][sword_x] = 'F';
			for(int i = 0; i < map.length; i++){
				for(int j = 0; j <map[i].length; j++)
					System.out.print(map[i][j]);
				System.out.println();
			}
			
			
		 
			System.out.println("Use the WASD keys to move!");
			opt = keyboard.nextLine().toUpperCase();
			switch(opt){
			case "W":
				if(y-1>=0)
					if(map[y-1][x] != 'X'){
						map[y][x] = ' ';
						y--;
					}
				break;
			case "A":
				if(x-1>=0)
					if(map[y][x-1] != 'X'){
						map[y][x] = ' ';
						x--;
					}
				break;
			case "S":
				if(y+1<10)
					if(map[y+1][x] != 'X'){
						map[y][x] = ' ';
						y++;
					}
				break;
			case "D":
				if(x+1<)
					if(map[y][x+1] != 'X'){
						map[y][x] = ' ';
						x++;
					}
				break;
			default:
				break;
			}
			

			

			switch(moveDragon.nextInt(4)){
			case 0:
				if(dragon_y-1 >=0)
					if(map[dragon_y-1][dragon_x] != 'X'){
						map[dragon_y][dragon_x] = ' ';
						dragon_y--;
					}
				break;
			case 1:
				if(dragon_y+1 < 10)
					if(map[dragon_y+1][dragon_x] != 'X'){
						map[dragon_y][dragon_x] = ' ';
						dragon_y++;
					}
				break;
			case 2:
				if(dragon_x+1 < 10)
					if(map[dragon_y][dragon_x+1] != 'X'){
						map[dragon_y][dragon_x] = ' ';
						dragon_x++;
					}
				break;
			case 3: 
				if(dragon_x-1 >= 0)
					if(map[dragon_y][dragon_x-1] != 'X'){
						map[dragon_y][dragon_x] = ' ';
						dragon_x--;
					}
				break;
			default: 
				break;
			}
			
			if(x == dragon_x && y == dragon_y && type != 'A'){
				System.out.println("Morreste :(");
				break;
			}
			if(x == exit_x && y == exit_y && type != 'A')
				x--;
			if(x == sword_x && y == sword_y){
				type = 'A';
				armado = true;
			}

			
			
		}
		*/
		
		MazeMap map = new MazeMap(10,10);
		printMaze(map);
	}
}
