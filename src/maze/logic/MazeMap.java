package maze.logic;

import maze.helper.Maze;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

/**
 * The Class MazeMap.
 * A Mazemap instance represents a maze with accessible and non-accessible cells
 */
public class MazeMap implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6551901065595105913L;

	/**
	 * The Class Builder.
	 * Objects of this class can create a new MazeMap object based on definitions provided by its functions.
	 */
	public static class Builder {
		
		/** The rows. */
		private int rows;
		
		/** The columns. */
		private int cols;
		
		/** The map. */
		private Cell[][] map;
		
		/** The exit. */
		private Position exit;
		
		/** If the default maze is to be used */
		private boolean defaultMaze;
		
		/** The rows of the default maze. */
		private static final int defaultRows = 10;
		
		/** The columns of the default maze. */
		private static final int defaulCols = 10;
		
		/** The exit of the default maze. */
		private static Position defaultExit = new Position(5, 9);
		
		/** The default map. */
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
		
		/**
		 * Instantiates a new builder.
		 */
		public Builder() {
			useDefault();
		}
		
		/**
		 * Use default maze.
		 */
		public void useDefault() {
			this.defaultMaze = true;
			this.rows = Builder.defaultRows;
			this.cols = Builder.defaulCols;
			this.map = Builder.defaultMap;
			this.exit = Builder.defaultExit;
		}
		
		/**
		 * Sets the rows.
		 *
		 * @param rows the new rows
		 * @throws IllegalArgumentException the illegal argument exception
		 */
		public void setRows(int rows) throws IllegalArgumentException{
			if (rows < 3)
				throw new IllegalArgumentException("Number of rows must be greater or equal to 3");
			this.defaultMaze = false;
			this.rows = rows;
		}
		
		/**
		 * Gets the rows.
		 *
		 * @return the rows
		 */
		public int getRows() {
			return this.rows;
		}
		
		/**
		 * Sets the cols.
		 *
		 * @param cols the new cols
		 * @throws IllegalArgumentException the illegal argument exception
		 */
		public void setCols(int cols) throws IllegalArgumentException{
			if (rows < 3)
				throw new IllegalArgumentException("Number of collumns must be greater or equal to 3");
			this.defaultMaze = false;
			this.cols = cols;
		}
		
		/**
		 * Gets the cols.
		 *
		 * @return the cols
		 */
		public int getCols() {
			return this.cols;
		}
		
		/**
		 * Generate maze.
		 */
		private void generateMaze()
		{
			// Create a thin-wall version of the maze
			Maze thinMaze = new Maze((getRows() - 1) / 2, (getCols() - 1) / 2);
			
			// Create a thick-wall version of the maze filled with walls
			map = new Cell[getRows()][getCols()];
			for(int i = 0; i < getRows(); i++)
				for(int j = 0; j < getCols(); j++){
					map[i][j] = new Cell(false);
				}
			
			Maze.Cell[][] cellData = thinMaze.getCells();
			
			Random rnd = new Random();
			
			// Map the thin-wall maze with the thick-wall maze
			for(int i = 0; i < cellData.length; i++)
				for(int j = 0; j < cellData[i].length; j++){
					map[i*2+1][j*2+1].setWalkable(true);
					map[i*2+2][j*2+1].setWalkable(!cellData[i][j].getWalls()[1]);
					map[i*2+1][j*2+2].setWalkable(!cellData[i][j].getWalls()[3]);
				}
			
			// Add extra row and/or column for even number of rows/columns
			if(getRows()%2 == 0){
				int divisor = 1 + rnd.nextInt(getRows() - 2);
				for(int i = getRows() - 1; i > divisor; i--){
					for(int j = 0; j < getCols(); j++){
						map[i][j].setWalkable(map[i-1][j].isWalkable());
					}
				}
				
				for(int i = 1; i < getCols() -1; i++)
					map[divisor][i].setWalkable(map[divisor - 1][i].isWalkable() && map[divisor+1][i].isWalkable());
				
			}
			
			if( getCols()%2 == 0){
				int divisor = 1 + rnd.nextInt(getCols() - 2);
				for(int i = getCols() - 1; i > divisor; i--){
					for(int j = 0; j < getRows(); j++){
						map[j][i].setWalkable(map[j][i-1].isWalkable());
					}
				}
				
				for(int i = 1; i < getRows() -1; i++)
					map[i][divisor].setWalkable(map[i][divisor - 1].isWalkable() && map[i][divisor+1].isWalkable());
			}
			
			// Select the exit
			boolean exitChosen = false;
			while (!exitChosen) {
				
				switch(Game.Direction.values()[rnd.nextInt(Game.Direction.values().length)]){
				case UP:
					this.exit = new Position(0,rnd.nextInt(cols-2) + 1 );
					if(map[this.exit.getRow()+1][this.exit.getColumn()].isWalkable())
						exitChosen = true;
					break;
				case DOWN:
					this.exit = new Position(rows-1, rnd.nextInt(cols-2) + 1);
					if(map[this.exit.getRow()-1][this.exit.getColumn()].isWalkable())
						exitChosen = true;
					break;
				case LEFT:
					this.exit = new Position(rnd.nextInt(rows-2) + 1, 0);
					if(map[this.exit.getRow()][this.exit.getColumn()+1].isWalkable())
						exitChosen = true;
					break;
				case RIGHT:
					this.exit = new Position(rnd.nextInt(rows-2) + 1, cols-1);
					if(map[this.exit.getRow()][this.exit.getColumn()-1].isWalkable())
						exitChosen = true;
					break;
				}
			}
		}
		
		/**
		 * Builds the MazeMap object
		 *
		 * @return the maze map
		 */
		public MazeMap build() {
			if (this.defaultMaze)
				this.map = Builder.defaultMap;
			else
				generateMaze();
				
			return new MazeMap(this.rows, this.cols, this.map, this.exit);
		}
	}
	
	/**
	 * The Class Cell.
	 */
	public static class Cell{
		
		/** The walkable. */
		private boolean walkable;
		
		/**
		 * Instantiates a new cell.
		 *
		 * @param walkable the walkable
		 */
		Cell(boolean walkable){
			this.setWalkable(walkable);
		}

		/**
		 * Checks if is walkable.
		 *
		 * @return true, if is walkable
		 */
		public boolean isWalkable() {
			return walkable;
		}

		/**
		 * Sets the walkable.
		 *
		 * @param walkable the new walkable
		 */
		public void setWalkable(boolean walkable) {
			this.walkable = walkable;
		}
	}
	
	/** The rows. */
	private int rows;
	
	/** The cols. */
	private int cols;
	
	/** The exit. */
	private Position exit;
	
	/** The map. */
	private Cell[][] map;
	
	/**
	 * Instantiates a new maze map.
	 *
	 * @param rows the rows
	 * @param cols the cols
	 * @param map the map
	 * @param exit the exit
	 */
	private MazeMap(int rows, int cols, Cell[][] map, Position exit){
		this.setRows(rows);
		this.setCols(cols);
		this.map = map;
		this.exit = exit;
	}
	
	/**
	 * Gets the walkable positions.
	 *
	 * @return the walkable positions
	 */
	public LinkedList<Position> getWalkablePositions(){
		LinkedList<Position> walkablePos = new LinkedList<Position>();
		
		for (int row = 0; row < map.length; row++)
			for(int col = 0; col < map[row].length; col++){
				if(map[row][col].isWalkable())
					walkablePos.add(new Position(row,col));
			}
		return walkablePos;
	}
	
	/**
	 * Checks if is walkable.
	 *
	 * @param row the row
	 * @param col the col
	 * @return true, if is walkable
	 */
	public boolean isWalkable(int row, int col){
		return map[row][col].isWalkable();
	}
	
	/**
	 * Checks if is walkable.
	 *
	 * @param position the position
	 * @return true, if is walkable
	 */
	public boolean isWalkable(Position position){
		return isWalkable(position.getRow(), position.getColumn());
	}
	
	/**
	 * Sets the walkable.
	 *
	 * @param position the position
	 * @param walkable the walkable
	 */
	public void setWalkable(Position position, boolean walkable){
		map[position.getRow()][position.getColumn()].setWalkable(walkable);;
	}
		
	/**
	 * Checks if is in line of sight.
	 *
	 * @param pos1 the pos1
	 * @param pos2 the pos2
	 * @param distance the distance
	 * @return true, if is in line of sight
	 */
	public boolean isInLineOfSight(Position pos1, Position pos2, int distance){
		
		if( Math.pow((pos1.getColumn() - pos2.getColumn()), 2) + Math.pow(pos1.getRow() - pos2.getRow(), 2) > Math.pow(distance,2))
			return false;
		
		
		
		if(pos1.getColumn() == pos2.getColumn()){
			for(int i = 1; i <= distance; i++){
				if(pos1.getRow() + i < rows)
					if(this.isWalkable(pos1.getRow() + i, pos1.getColumn())){
						if(pos1.getRow() + i == pos2.getRow())
							return true;
					}
					else{
						break;
					}
			}
			for(int i = 1; i <= distance; i++){
				if(pos1.getRow() - i > 0)
					if(this.isWalkable(pos1.getRow() - i, pos1.getColumn())){
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
				if(pos1.getColumn() + i < cols)
					if(this.isWalkable(pos1.getRow(), pos1.getColumn() + i)){
						if(pos1.getColumn() + i == pos2.getColumn())
							return true;
					}
					else{
						break;
					}
						
			}
			
			for(int i = 1; i <= distance; i++){
				if(pos1.getColumn() - i > 0)
					if(this.isWalkable(pos1.getRow(), pos1.getColumn() - i)){
						if( pos1.getColumn() - i == pos2.getColumn())
							return true;
					}
					else{
						break;
					}
						
			}
		}
		
		return false;
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Sets the rows.
	 *
	 * @param rows the new rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Gets the cols.
	 *
	 * @return the cols
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Sets the cols.
	 *
	 * @param cols the new cols
	 */
	public void setCols(int cols) {
		this.cols = cols;
	}

	/**
	 * Gets the exit.
	 *
	 * @return the exit
	 */
	public Position getExit() {
		return exit;
	}

	/**
	 * Sets the exit.
	 *
	 * @param exit the new exit
	 */
	public void setExit(Position exit) {
		this.exit = exit;
	}
}
