package maze.logic;

import maze.helper.Maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;



public class MazeMap {
	
	public static class Builder {
		private int rows;
		private int cols;
		private Cell[][] map;
		private Position exit;
		
		private boolean defaultMaze;
		private static final int defaultRows = 10;
		private static final int defaulCols = 10;
		private static Position defaultExit = new Position(5, 9);
		private static Cell[][] defaultMap = {{new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false)},
											  {new Cell(false), new Cell(true) , new Cell(true) , new Cell(true) , new Cell(true) , new Cell(true) , new Cell(true) , new Cell(true) , new Cell(true) , new Cell(false)},
									          {new Cell(false), new Cell(true) , new Cell(false), new Cell(false), new Cell(true) , new Cell(false), new Cell(true) , new Cell(false), new Cell(true) , new Cell(false)},
									          {new Cell(false), new Cell(true) , new Cell(false), new Cell(false), new Cell(true) , new Cell(false), new Cell(true) , new Cell(false), new Cell(true) , new Cell(false)},
									          {new Cell(false), new Cell(true) , new Cell(false), new Cell(false), new Cell(true) , new Cell(false), new Cell(true) , new Cell(false), new Cell(true) , new Cell(false)},
									          {new Cell(false), new Cell(true) , new Cell(true) , new Cell(true) , new Cell(true) , new Cell(true) , new Cell(true) , new Cell(false), new Cell(true) , new Cell(false)},
									          {new Cell(false), new Cell(true) , new Cell(false), new Cell(false), new Cell(true) , new Cell(false), new Cell(true) , new Cell(false), new Cell(true) , new Cell(false)},
									          {new Cell(false), new Cell(true) , new Cell(false), new Cell(false), new Cell(true) , new Cell(false), new Cell(true) , new Cell(false), new Cell(true) , new Cell(false)},
									          {new Cell(false), new Cell(true) , new Cell(false), new Cell(false), new Cell(true) , new Cell(true) , new Cell(true) , new Cell(true) , new Cell(true) , new Cell(false)},
									          {new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false), new Cell(false)}};
		
		public Builder() {
			useDefault();
		}
		
		public void useDefault() {
			this.defaultMaze = true;
			this.rows = Builder.defaulCols;
			this.cols = Builder.defaulCols;
			this.map = Builder.defaultMap;
			this.exit = Builder.defaultExit;
		}
		
		public void setRows(int rows) throws IllegalArgumentException{
			if (rows < 3)
				throw new IllegalArgumentException("Number of rows must be greater or equal to 3");
			this.defaultMaze = false;
			this.rows = rows;
		}
		
		public int getRows() {
			return this.rows;
		}
		
		public void setCols(int cols) throws IllegalArgumentException{
			if (rows < 3)
				throw new IllegalArgumentException("Number of collumns must be greater or equal to 3");
			this.defaultMaze = false;
			this.cols = cols;
		}
		
		public int getCols() {
			return this.cols;
		}
		
		private void generateMaze()
		{
			Maze thinMaze = new Maze((getRows() - 1) / 2, (getCols() - 1) / 2);
			
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
			
			//Correcao para numero de colunas ou filas pares
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
					this.exit = new Position(0,rnd.nextInt(cols-2) + 1 );
					if(map[this.exit.getRow()+1][this.exit.getCol()].isWalkable())
						break;
				} while(true);
				break;
			case 1:
				do{
					this.exit = new Position(rows-1, rnd.nextInt(cols-2) + 1);
					if(map[this.exit.getRow()-1][this.exit.getCol()].isWalkable())
						break;
				} while(true);
				break;
			case 2:
				do{
					this.exit = new Position(rnd.nextInt(rows-2) + 1, 0);
					if(map[this.exit.getRow()][this.exit.getCol()+1].isWalkable())
						break;
				} while(true);
				break;
			case 3:
				do{
					this.exit = new Position(rnd.nextInt(rows-2) + 1, cols-1);
					if(map[this.exit.getRow()][this.exit.getCol()-1].isWalkable())
						break;
				} while(true);
				break;
			}
		}
		
		public MazeMap build() {
			if (this.defaultMaze)
				this.map = Builder.defaultMap;
			else
				generateMaze();
				
			return new MazeMap(this.rows, this.cols, this.map, this.exit);
		}
	}
	
	public static class Cell{
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

		public void addEntity(Entity entity) throws Exception{
			for(Entity tempEntity : entities)
				if(tempEntity.hashCode() == entity.hashCode())
					throw new Exception();
			entities.add(entity);
		}
		public void removeEntity(Entity entity){
			entities.remove(entity);
		}
	}
	
	private int rows;
	private int cols;
	
	private Position exit;
	
	private Cell[][] map;
	
	private MazeMap(int rows, int cols, Cell[][] map, Position exit){
		this.setRows(rows);
		this.setCols(cols);
		this.map = map;
		this.exit = exit;
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
	
	public void addEntity(Entity entity) {
		boolean loop = true;
		do{
			try {
				map[entity.getPos().getRow()][entity.getPos().getCol()].addEntity(entity);
				loop = false;
				break;
			} catch (Exception e) {
				map[entity.getPos().getRow()][entity.getPos().getCol()].removeEntity(entity);
			}
		}
		while(loop);
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
		
	public boolean isInLineOfSight(Position pos1, Position pos2, int distance){
		
		if( Math.pow((pos1.getCol() - pos2.getCol()), 2) + Math.pow(pos1.getRow() - pos2.getRow(), 2) > Math.pow(distance,2))
			return false;
		
		
		
		if(pos1.getCol() == pos2.getCol()){
			for(int i = 1; i <= distance; i++){
				if(pos1.getRow() + i < rows)
					if(this.isWalkable(pos1.getRow() + i, pos1.getCol())){
						if(pos1.getRow() + i == pos2.getRow())
							return true;
					}
					else{
						break;
					}
			}
			for(int i = 1; i <= distance; i++){
				if(pos1.getRow() - i > 0)
					if(this.isWalkable(pos1.getRow() - i, pos1.getCol())){
						if(pos1.getRow() - i == pos2.getRow())
							return true;
					}
					else{
						break;
					}
						
			}
		}
		
		if(pos1.getRow() == pos2.getRow()){
			for(int i = 1; i <= distance; i++){
				if(pos1.getCol() + i < cols)
					if(this.isWalkable(pos1.getRow(), pos1.getCol() + i)){
						if(pos1.getCol() + i == pos2.getCol())
							return true;
					}
					else{
						break;
					}
						
			}
			
			for(int i = 1; i <= distance; i++){
				if(pos1.getCol() - i > 0)
					if(this.isWalkable(pos1.getRow(), pos1.getCol() - i)){
						if( pos1.getCol() - i == pos2.getCol())
							return true;
					}
					else{
						break;
					}
						
			}
		}
		
		return false;
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
