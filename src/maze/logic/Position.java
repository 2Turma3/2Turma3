package maze.logic;

import java.io.Serializable;

public class Position implements Serializable {

	private static final long serialVersionUID = -2540982461794799330L;
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
	
	private boolean equals(Position pos){
		return this.getRow() == pos.getRow() && this.getCol()== pos.getCol();
	} 
	
	public Position clone(){
		return new Position(this.getRow(), this.getCol());
	}
	
	public static double distance(Position pos1, Position pos2){
		return Math.sqrt(Math.pow(pos1.getRow() - pos2.getRow(), 2) + Math.pow(pos1.getCol() - pos2.getCol(), 2));
	}
	
	
	@Override
	public String toString() {
		return "(" + this.row + ", " + this.col +")";
	}
}
