package maze.logic;

import maze.helper.Maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;



public class MazeMap {
	
	public class Cell{
		private boolean walkable;
		private LinkedList<Entity> entities;
		
		Cell(boolean walkable){
			this.setWalkable(walkable);
			entities = new LinkedList<Entity>();
		}

		public boolean isEmpty(){
			return entities.isEmpty();
		}
		
		public boolean isWalkable() {
			return walkable;
		}

		public void setWalkable(boolean walkable) {
			this.walkable = walkable;
		}

		public LinkedList<Entity> getEntities() {
			return entities;
		}

		public void addEntity(Entity entity){
			entities.add(entity);
		}
		public void removeEntity(Entity entity){
			entities.remove(entity);
		}
	}
	
	private int rows;
	private int cols;
	
	private Position exit;
	
	Cell[][] map;
	
	MazeMap(int rows, int cols){
		this.setRows(rows);
		this.setCols(cols);
		
		convertMaze(new Maze((rows-1)/2,(cols-1)/2));
	}
	
	public ArrayList<Position> getWalkablePositions(){
		ArrayList<Position> walkablePos = new ArrayList<Position>();
		
		for (int row = 0; row < map.length; row++)
			for(int col = 0; col < map[row].length; col++){
				if(map[row][col].isWalkable())
					walkablePos.add(new Position(row,col));
			}
		return walkablePos;
	}
	
	public boolean isWalkable(int row, int col){
		return map[row][col].isWalkable();
	}
	
	public boolean isWalkable(Position position){
		return isWalkable(position.getRow(), position.getCol());
	}
	
	public void setWalkable(Position position, boolean walkable){
		map[position.getRow()][position.getCol()].setWalkable(walkable);;
	}
	
	public void addEntity(Entity entity){
		map[entity.getPos().getRow()][entity.getPos().getCol()].addEntity(entity);
	}
	
	public void removeEntity(Entity entity){
		map[entity.getPos().getRow()][entity.getPos().getCol()].removeEntity(entity);
	}
	
	public LinkedList<Entity> getEntities(Position position)
	{
		return map[position.getRow()][position.getCol()].getEntities();
	}
	
	public boolean isEmptyCell(Position position){
		return map[position.getRow()][position.getCol()].isEmpty();
	}
		
	
	private void convertMaze(Maze thinMaze)
	{
		map = new Cell[getRows()][getCols()];
		
		for(int i = 0; i < getRows(); i++)
			for(int j = 0; j < getCols(); j++){
				map[i][j] = new Cell(false);
			}
		
		Maze.Cell[][] cellData = thinMaze.getCells();
		
		Random rnd = new Random();
		
		//Mapeamento do mapa com paredes finas
		for(int i = 0; i < cellData.length; i++)
			for(int j = 0; j < cellData[i].length; j++){
				map[i*2+1][j*2+1].setWalkable(true);
				map[i*2+2][j*2+1].setWalkable(!cellData[i][j].getWalls()[1]);
				map[i*2+1][j*2+2].setWalkable(!cellData[i][j].getWalls()[3]);
			}
		
		//Correção para número de colunas ou filas pares
		if(getRows()%2 == 0){
			int divisor = 1 + rnd.nextInt(getRows() - 2);
			for(int i = getRows() - 1; i >= divisor; i--){
				for(int j = 0; j < getCols(); j++){
					map[i][j].setWalkable(map[i-1][j].isWalkable());
				}
			}
			
			for(int i = 1; i < getCols() -1; i++){
				if(!map[divisor - 1][i].isWalkable() && !map[divisor+1][i].isWalkable())
					map[divisor][i].setWalkable(false);
				else if(map[divisor - 1][i].isWalkable() && map[divisor+1][i].isWalkable())
					map[divisor][i].setWalkable(true);
				else if(map[divisor][i-1].isWalkable())
					map[divisor][i].setWalkable(false);
				else
					map[divisor][i].setWalkable(rnd.nextBoolean());
			}
			
		}
		
		if( getCols()%2 == 0){
			int divisor = 1 + rnd.nextInt(getCols() - 2);
			for(int i = getCols() - 1; i >= divisor; i--){
				for(int j = 0; j < getRows(); j++){
					map[j][i].setWalkable(map[j][i-1].isWalkable());
				}
			}
			
			for(int i = 1; i < getRows() -1; i++){
				if(!map[i][divisor - 1].isWalkable() && !map[i][divisor+1].isWalkable())
					map[i][divisor].setWalkable(false);
				else if(map[i][divisor - 1].isWalkable() && map[i][divisor+1].isWalkable())
					map[i][divisor].setWalkable(true);
				else if(map[i-1][divisor].isWalkable())
					map[i][divisor].setWalkable(false);
				else
					map[i][divisor].setWalkable(rnd.nextBoolean());
			}	
		}
		
		switch(rnd.nextInt(4)){
		case 0:
			do{
				setExit(new Position(0,rnd.nextInt(cols-2) + 1 ));
				if(map[getExit().getRow()+1][getExit().getCol()].isWalkable())
					break;
			} while(true);
			break;
		case 1:
			do{
				setExit(new Position(rows-1, rnd.nextInt(cols-2) + 1));
				if(map[getExit().getRow()-1][getExit().getCol()].isWalkable())
					break;
			} while(true);
			break;
		case 2:
			do{
				setExit(new Position(rnd.nextInt(rows-2) + 1, 0));
				if(map[getExit().getRow()][getExit().getCol()+1].isWalkable())
					break;
			} while(true);
			break;
		case 3:
			do{
				setExit(new Position(rnd.nextInt(rows-2) + 1, cols-1));
				if(map[getExit().getRow()][getExit().getCol()-1].isWalkable())
					break;
			} while(true);
			break;
		}
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public Position getExit() {
		return exit;
	}

	public void setExit(Position exit) {
		this.exit = exit;
	}
}
