import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;


public class Maze {

	public class Cell{
		private boolean visited;
		private boolean[] walls;
		private int row;
		private int col;
		Cell(int row, int col){
			setVisited(false);
			setWalls(new boolean[4]);
			for (int i = 0; i < getWalls().length; i++)
				getWalls()[i] = true;
			this.setRow(row);
			this.setCol(col);
		}
		public boolean isVisited() {
			return visited;
		}
		public void setVisited(boolean visited) {
			this.visited = visited;
		}
		public boolean[] getWalls() {
			return walls;
		}
		public void setWalls(boolean[] walls) {
			this.walls = walls;
		}
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		public int getCol() {
			return col;
		}
		public void setCol(int col) {
			this.col = col;
		}
	}
	
	private int rows;
	private int cols;
	private Cell exit;
	private Cell start;
	
	private Cell[][] cells;
	
	Maze(int rows, int cols){
		this.setRows(rows);
		this.setCols(cols);
		
	
		setCells(new Cell[rows][cols]);
		
		for (int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				getCells()[i][j] = new Cell(i,j);
		
		generateMaze();
	}
	
	
	private void selectExit(){
		Random rnd = new Random();
		switch(rnd.nextInt(4)){
		case 0:	//Upper row
			setExit(getCells()[0][rnd.nextInt(getCols())]);
			break;
		case 1: //Bottom row
			setExit(getCells()[getRows()-1][rnd.nextInt(getCols())]);
			break;
		case 2:	//Left column
			setExit(getCells()[rnd.nextInt(getRows())][0]);
			break;
		case 3: //Right column
			setExit(getCells()[rnd.nextInt(getRows())][getCols()-1]);
			break;
		}
		
		getExit().setVisited(true);
	}
	
	private void generateMaze(){
		
		
		Deque<Cell> path = new ArrayDeque<Cell>();

		selectExit();
		path.push(getExit());
		
		Random rnd = new Random();
		
		while(!(path.isEmpty()) ){
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			if (path.getFirst().getRow() - 1 >= 0 && !getCells()[path.getFirst().getRow()-1][path.getFirst().getCol()].isVisited())
				neighbors.add(getCells()[path.getFirst().getRow() - 1][path.getFirst().getCol()]);
			if (path.getFirst().getRow() + 1 < getRows() && !getCells()[path.getFirst().getRow()+1][path.getFirst().getCol()].isVisited())
				neighbors.add(getCells()[path.getFirst().getRow() + 1][path.getFirst().getCol()]);
			if (path.getFirst().getCol() - 1 >= 0 && !getCells()[path.getFirst().getRow()][path.getFirst().getCol() -1].isVisited())
				neighbors.add(getCells()[path.getFirst().getRow()][path.getFirst().getCol() - 1]);
			if (path.getFirst().getCol() + 1  < getCols() && !getCells()[path.getFirst().getRow()][path.getFirst().getCol() +1].isVisited())
				neighbors.add(getCells()[path.getFirst().getRow()][path.getFirst().getCol() + 1]);
			
			if (neighbors.isEmpty()){
				path.pop();
				continue;
			}
			
			Cell nextCell = neighbors.get(rnd.nextInt(neighbors.size()));
			
			if (path.getFirst().getRow() - 1 == nextCell.getRow())	//Upper cell visited
			{
				path.getFirst().getWalls()[0] = false;
				nextCell.getWalls()[1] = false;
			}
			else if(path.getFirst().getRow() + 1 == nextCell.getRow())	//Bottom cell visited
			{
				path.getFirst().getWalls()[1] = false;
				nextCell.getWalls()[0] = false;
			}
			else if(path.getFirst().getCol() - 1 == nextCell.getCol())	// Left cell visited
			{
				path.getFirst().getWalls()[2] = false;
				nextCell.getWalls()[3] = false;
			}
			else if(path.getFirst().getCol() + 1 == nextCell.getCol())	//Right cell visited
			{
				path.getFirst().getWalls()[3] = false;
				nextCell.getWalls()[2] = false;
			}
			
			nextCell.setVisited(true);
			path.push(nextCell);
		}
		do{
			setStart(getCells()[rnd.nextInt(getRows())][rnd.nextInt(getCols())]);
		}while(getStart().getCol() == getExit().getCol() && getStart().getRow() == getExit().getRow());
		
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


	public Cell getExit() {
		return exit;
	}


	public void setExit(Cell exit) {
		this.exit = exit;
	}


	public Cell getStart() {
		return start;
	}


	public void setStart(Cell start) {
		this.start = start;
	}


	public Cell[][] getCells() {
		return cells;
	}


	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	
	
	
	
}
