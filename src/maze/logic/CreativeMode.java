package maze.logic;

public class CreativeMode {
	private GameBoard board;
	
	public CreativeMode(int rows, int columns) {
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(rows);
		builder.setCols(columns);
		setBoard(new GameBoard(builder.build(), null, null, null));
	}

	public CreativeMode(GameBoard board) {
		setBoard(board);
	}
	
	/**
	 * @return the board
	 */
	public GameBoard getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(GameBoard board) {
		this.board = board;
	}

	public void setExit(Position position) {
		if (position.getRow() == 0 || position.getRow() == getBoard().getMap().getRows() - 1)
		{
			if (!(position.getCol() == 0 || position.getCol() == getBoard().getMap().getCols() - 1))
				getBoard().getMap().setExit(position);
		}
		else if (position.getCol() == 0 || position.getCol() == getBoard().getMap().getCols() - 1)
			getBoard().getMap().setExit(position);
	}
	
	public void placeWall(Position position) {
		if (notInBorder(position))
		{
			removeEntityAt(position);
			getBoard().getMap().setWalkable(position, true);
		}
	}
	
	public void placeFloor(Position position) {
		if (notInBorder(position))
		{
			removeEntityAt(position);
			getBoard().getMap().setWalkable(position, true);
		}
	}
	
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
	
	public void placeDragon(Position position) {
		if (notInBorder(position)) {
			removeEntityAt(position);
			placeFloor(position);
			getBoard().getDragons().add(new Dragon(position, true, true, true));
		}
	}
	
	public void placeWeapon(Position position, Weapon.Type type) {
		if (notInBorder(position)) {
			removeEntityAt(position);
			placeFloor(position);
			getBoard().getWeapons().add(new Weapon(type, position));
		}
	}
	
	private boolean notInBorder(Position position) {
		return (0 < position.getCol() && position.getCol() < (getBoard().getMap().getCols() - 1)) &&
			   (0 < position.getRow() && position.getRow() < (getBoard().getMap().getRows() - 1));
	}
	
	private void removeEntityAt(Position position) {
		if (getBoard().getHero() != null && getBoard().getHero().getPos().equals(position))
		{
			getBoard().setHero(null);
			return;
		}
		
		for (Dragon dragon : getBoard().getDragons())
			if (dragon.getPos().equals(position)) {
				getBoard().getDragons().remove(dragon);
				return;
			}
		
		for (Weapon weapon : getBoard().getWeapons())
			if (weapon.getPos().equals(position)) {
				getBoard().getWeapons().remove(weapon);
				return;
			}
	}
}
