package maze.logic;

/**
 * The Class CreativeMode provides functions to create a game board from scratch.
 */
public class CreativeMode {
	
	/** The board. */
	private GameBoard board;
	
	/**
	 * Instantiates a new creative mode.
	 *
	 * @param rows the rows of the maze
	 * @param columns the columns of the maze
	 */
	public CreativeMode(int rows, int columns) {
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(rows);
		builder.setCols(columns);
		setBoard(new GameBoard(builder.build(), null, null, null));
	}
	
	/**
	 * Instantiates a new creative mode.
	 *
	 * @param board board already edited
	 */
	public CreativeMode(GameBoard board) {
		setBoard(board);
	}
	
	/**
	 * Gets the board.
	 *
	 * @return the board
	 */
	public GameBoard getBoard() {
		return board;
	}

	/**
	 * Sets the board.
	 *
	 * @param board the board to set
	 */
	public void setBoard(GameBoard board) {
		this.board = board;
	}

	/**
	 * Sets the exit.
	 *
	 * @param position the new exit
	 */
	public void setExit(Position position) {
		if (position.getRow() == 0 || position.getRow() == getBoard().getMap().getRows() - 1)
		{
			if (!(position.getColumn() == 0 || position.getColumn() == getBoard().getMap().getCols() - 1))
				getBoard().getMap().setExit(position);
		}
		else if (position.getColumn() == 0 || position.getColumn() == getBoard().getMap().getCols() - 1)
			getBoard().getMap().setExit(position);
	}
	
	/**
	 * Place wall.
	 *
	 * @param position the position
	 */
	public void placeWall(Position position) {
		if (notInBorder(position))
		{
			removeEntityAt(position);
			getBoard().getMap().setWalkable(position, false);
		}
	}
	
	/**
	 * Place floor.
	 *
	 * @param position the position
	 */
	public void placeFloor(Position position) {
		if (notInBorder(position))
		{
			removeEntityAt(position);
			getBoard().getMap().setWalkable(position, true);
		}
	}
	
	/**
	 * Place hero.
	 *
	 * @param position the position
	 */
	public void placeHero(Position position) {
		if (notInBorder(position)) {
			removeEntityAt(position);
			
			placeFloor(position);
			if (getBoard().getHero() == null)
				getBoard().setHero(new Hero(position));
			else
				getBoard().getHero().setPosition(position);
		}
	}
	
	/**
	 * Place dragon.
	 *
	 * @param position the position
	 */
	public void placeDragon(Position position) {
		if (notInBorder(position)) {
			removeEntityAt(position);
			placeFloor(position);
			getBoard().getDragons().add(new Dragon(position, true, true, true));
		}
	}
	
	/**
	 * Place weapon.
	 *
	 * @param position the position
	 * @param type the type
	 */
	public void placeWeapon(Position position, Weapon.Type type) {
		if (notInBorder(position)) {
			removeEntityAt(position);
			placeFloor(position);
			getBoard().getWeapons().add(new Weapon(type, position));
		}
	}
	
	/**
	 * This function returns whether the position provided is one that allows entities to be placed
	 *
	 * @param position the position
	 * @return true if not in the borders of the maze, false otherwise
	 */
	private boolean notInBorder(Position position) {
		return (0 < position.getColumn() && position.getColumn() < (getBoard().getMap().getCols() - 1)) &&
			   (0 < position.getRow() && position.getRow() < (getBoard().getMap().getRows() - 1));
	}
	
	/**
	 * Removes the entity at the requested position
	 *
	 * @param position the position
	 */
	private void removeEntityAt(Position position) {
		if (getBoard().getHero() != null && getBoard().getHero().getPosition().equals(position))
		{
			getBoard().setHero(null);
			return;
		}
		
		for (Dragon dragon : getBoard().getDragons())
			if (dragon.getPosition().equals(position)) {
				getBoard().getDragons().remove(dragon);
				return;
			}
		
		for (Weapon weapon : getBoard().getWeapons())
			if (weapon.getPosition().equals(position)) {
				getBoard().getWeapons().remove(weapon);
				return;
			}
	}
}
