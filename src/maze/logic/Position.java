package maze.logic;

import java.io.Serializable;

/**
 * The Class Position represents the position of an object in the maze as (row, column) coordinates
 */
public class Position implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2540982461794799330L;
	
	/** The row. */
	private int row;
	
	/** The column */
	private int column;
	
	/**
	 * Instantiates a new position.
	 *
	 * @param row the row
	 * @param col the column
	 */
	public Position(int row, int col){
		this.setRow(row);
		this.setColumn(col);
	}

	/**
	 * Gets the row.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Sets the row.
	 *
	 * @param row the new row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Sets the column.
	 *
	 * @param col the new column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/** 
	 * Checks if the object is a Position instance and if it is equal to the caller of this function
	 * @param obj an Object
	 * @return true if they are equal, false otherwise
	 */
	public boolean equals(Object obj){
		return obj != null && 
				obj instanceof Position && 
				this.equals((Position)obj);
	}
	
	/**
	 * Two positions are equal if they have the same row and column.
	 *
	 * @param pos the position
	 * @return true if they are equal, false otherwise 
	 */
	private boolean equals(Position pos){
		return this.getRow() == pos.getRow() && this.getColumn()== pos.getColumn();
	} 
	
	/** 
	 * Creates an instance of Position with the same row and column of the caller.
	 * @return Position object
	 */
	public Position clone(){
		return new Position(this.getRow(), this.getColumn());
	}
	
	/**
	 * Distance between two positions.
	 *
	 * @param pos1 the pos1
	 * @param pos2 the pos2
	 * @return the distance
	 */
	public static double distance(Position pos1, Position pos2){
		return Math.sqrt(Math.pow(pos1.getRow() - pos2.getRow(), 2) + Math.pow(pos1.getColumn() - pos2.getColumn(), 2));
	}
	
	
	/** 
	 * String representation of Position
	 * @return "(row, column)"
	 */
	@Override
	public String toString() {
		return "(" + this.row + ", " + this.column +")";
	}
}
