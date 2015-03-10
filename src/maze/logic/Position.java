package maze.logic;

public class Position {
	private int row;
	private int col;
	
	public Position(int row, int col){
		this.setRow(row);
		this.setCol(col);
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
	
	public boolean equals(Object obj){
		return obj != null && 
				obj instanceof Position && 
				this.equals((Position)obj);
	}
	
	public boolean equals(Position pos){
		return this.getRow() == pos.getRow() && this.getCol()== pos.getCol();
	} 
	
	public Position clone(){
		return new Position(this.getRow(), this.getCol());
	}
}
