package maze.logic;


import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * The Class Game.
 */
public class Game implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1104019170956261774L;

	/** The fire. */
	private LinkedList<Flame> fire;
	
	/** The board. */
	private GameBoard board;
	
	/** If the game is finished */
	private boolean finished;
	
	/** If the player won the game*/
	private boolean won;
	
	/** The end of game message. */
	private String endOfGameMessage = "";

	/** If the exit is open */
	private boolean exitOpen;
	
	/**
	 * The Enum Direction.
	 */
	public enum Direction{UP, DOWN, LEFT, RIGHT}
	
	/**
	 * The Enum Action.
	 */
	public enum Action{MOVE, ATTACK, STOP}
	
	/**
	 * The Class Command describes the action to be performed by the hero in a turn
	 */
	public static class Command {
		
		/** The action. */
		private Action action;
		
		/** The direction. */
		private Direction direction;
		
		/**
		 * Instantiates a new command.
		 *
		 * @param action the action
		 * @param direction the direction
		 */
		public Command(Action action, Direction direction) {
			this.setAction(action);
			this.setDirection(direction);
		}

		/**
		 * Gets the action.
		 *
		 * @return the action
		 */
		public Action getAction() {
			return action;
		}

		/**
		 * Sets the action.
		 *
		 * @param action the action to set
		 */
		public void setAction(Action action) {
			this.action = action;
		}

		/**
		 * Gets the direction.
		 *
		 * @return the direction
		 */
		public Direction getDirection() {
			return direction;
		}

		/**
		 * Sets the direction.
		 *
		 * @param direction the direction to set
		 */
		public void setDirection(Direction direction) {
			this.direction = direction;
		}
	}
	
	/**
	 * Instantiates a new game.
	 *
	 * @param map the map
	 * @param numDragons the num dragons
	 * @param canMove the can move
	 * @param canSleep the can sleep
	 * @param canAttack the can attack
	 */
	public Game(MazeMap map, int numDragons, boolean canMove, boolean canSleep, boolean canAttack) {
		finished = false;
		won = false;
		Random rnd = new Random();
		Position newPos;
		LinkedList<Position> walkablePos = map.getWalkablePositions();
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		Hero hero = new Hero(newPos);
		walkablePos.remove(newPos);
		
		LinkedList<Weapon> weapons = generateWeapons(numDragons, walkablePos);
		
		LinkedList<Dragon> dragons = generateDragons(numDragons, walkablePos, canMove, canSleep, canAttack);	
		
		setBoard(new GameBoard(map, hero, dragons, weapons));
		this.setExitOpen(this.getBoard().getDragons().isEmpty() && getBoard().getHero().hasSword());
	}
	
	/**
	 * Instantiates a new game.
	 *
	 * @param numDragons the num dragons
	 * @param canMove the can move
	 * @param canSleep the can sleep
	 * @param canAttack the can attack
	 */
	public Game(int numDragons, boolean canMove, boolean canSleep, boolean canAttack) {
		MazeMap.Builder builder = new MazeMap.Builder();
		MazeMap map = builder.build();
		setFire(new LinkedList<Flame>());
		finished = false;
		won = false;
		Random rnd = new Random();
		Position newPos;
		LinkedList<Position> walkablePos = map.getWalkablePositions();
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		Hero hero = new Hero(newPos);
		walkablePos.remove(newPos);
		
		LinkedList<Weapon> weapons = generateWeapons(numDragons, walkablePos);
		
		LinkedList<Dragon> dragons = generateDragons(numDragons, walkablePos, canMove, canSleep, canAttack);	
		
		setBoard(new GameBoard(map, hero, dragons, weapons));
		this.setExitOpen(this.getBoard().getDragons().isEmpty() && getBoard().getHero().hasSword());
	}
	
	/**
	 * Instantiates a new game.
	 *
	 * @param rows the rows
	 * @param cols the cols
	 * @param numDragons the num dragons
	 * @param canMove the can move
	 * @param canSleep the can sleep
	 * @param canAttack the can attack
	 */
	public Game(int rows, int cols, int numDragons, boolean canMove, boolean canSleep, boolean canAttack){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(rows);
		builder.setCols(cols);
		MazeMap map = builder.build();
		setFire(new LinkedList<Flame>());
		finished = false;
		won = false;
		
		Random rnd = new Random();
		Position newPos;
		LinkedList<Position> walkablePos = map.getWalkablePositions();
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		Hero hero = new Hero(newPos);
		walkablePos.remove(newPos);
		
		LinkedList<Weapon> weapons = generateWeapons(numDragons, walkablePos);
		
		LinkedList<Dragon> dragons = generateDragons(numDragons, walkablePos, canMove, canSleep, canAttack);	
		
		setBoard(new GameBoard(map, hero, dragons, weapons));
		this.setExitOpen(this.getBoard().getDragons().isEmpty() && getBoard().getHero().hasSword());
	}

	/**
	 * Instantiates a new game.
	 *
	 * @param map the map
	 * @param hero the hero
	 * @param dragons the dragons
	 * @param weapons the weapons
	 */
	public Game(MazeMap map, Hero hero, LinkedList<Dragon> dragons, LinkedList<Weapon> weapons, boolean canMove, boolean canSleep, boolean canAttack){
		this(new GameBoard(map, hero, dragons, weapons), canMove, canSleep, canAttack);
	}
	
	/**
	 * Instantiates a new game.
	 *
	 * @param board the board
	 */
	public Game(GameBoard board, boolean canMove, boolean canSleep, boolean canAttack) {
		setBoard(board);
		for (Dragon dragon: getBoard().getDragons()) {
			dragon.enableMovement(canMove);
			dragon.enableSleeping(canSleep);
			dragon.enableAttack(canAttack);
		}
		setFire(new LinkedList<Flame>());
		this.setExitOpen(board.getDragons().isEmpty());
		finished = false;
		won = false;
	}

	/**
	 * Generate weapons.
	 *
	 * @param maxDarts the max darts
	 * @param walkablePos the walkable pos
	 * @return the linked list
	 */
	public LinkedList<Weapon> generateWeapons(int maxDarts,LinkedList<Position> walkablePos){
		Position newPos;
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Random rnd = new Random();
		

		if (!walkablePos.isEmpty()) {
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Weapon sword = new Weapon(Weapon.Type.SWORD, newPos);
			weapons.add(sword);
			walkablePos.remove(newPos);
		}
		
		if (!walkablePos.isEmpty()) {
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Weapon shield = new Weapon(Weapon.Type.SHIELD, newPos);
			weapons.add(shield);
			walkablePos.remove(newPos);
		}
		
		for(int i = maxDarts; i > 0 && !walkablePos.isEmpty(); --i){
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Weapon dart = new Weapon(Weapon.Type.DART, newPos);
			weapons.add(dart);
			walkablePos.remove(newPos);
		}
		
		return weapons;
	}

	/**
	 * Generate dragons.
	 *
	 * @param numDragons the num dragons
	 * @param walkablePos the walkable pos
	 * @param canMove the can move
	 * @param canSleep the can sleep
	 * @param canAttack the can attack
	 * @return the linked list
	 */
	public LinkedList<Dragon> generateDragons(int numDragons,
		LinkedList<Position> walkablePos, boolean canMove, boolean canSleep, boolean canAttack) {
		Random rnd = new Random();
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		Position newPos;
		for(int i = numDragons; i > 0 && !walkablePos.isEmpty(); --i){
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Dragon newDragon = new Dragon(newPos, canMove, canSleep, canAttack);
			dragons.add(newDragon);
			walkablePos.remove(newPos);
		}
		
		return dragons;
	}
	
	/**
	 * Checks if is finished.
	 *
	 * @return true, if is finished
	 */
	public boolean isFinished(){
		return finished;
	}
	
	/**
	 * Move entity.
	 *
	 * @param entity the entity
	 * @param direction the direction
	 * @return true, if successful
	 */
	public boolean moveEntity(Entity entity, Direction direction)
    {
            Position newPos = entity.getPosition().clone();
            switch (direction)
            {
                    case UP:
                            newPos.setRow(newPos.getRow() - 1);
                            break;
                    case DOWN:
                        	newPos.setRow(newPos.getRow() + 1);
                            break;
                    case LEFT:
                        	newPos.setColumn(newPos.getColumn() - 1);
                            break;
                    case RIGHT:
                    		newPos.setColumn(newPos.getColumn() + 1);
                            break;
            }
           
            if(!getBoard().getMap().isWalkable(newPos))
            	return false;
            
            if(newPos.equals(getBoard().getHero().getPosition()))
            	return false;
            
            for(Dragon dragon : getBoard().getDragons())
            	if(newPos.equals(dragon.getPosition()))
            		return false;
            
            entity.setPosition(newPos);
            
            return true;
    }	
	
    /**
     * Move dragon.
     *
     * @param dragon the dragon
     */
    public void moveDragon(Dragon dragon){
            Random rnd = new Random();
            LinkedList<Dragon.Action> availableActions = dragon.getAvailableActions();
            
            if(shouldAttack(dragon)){
            	System.out.println("Vai atacar");
            	dragonAttack(dragon);
            }
            else{
                boolean validAction = true;
            	do {
            		switch(availableActions.get(rnd.nextInt(availableActions.size()))){
            		case MOVE_UP:
            			//            		System.out.print("Dragon " + dragon.hashCode() + "moved up\n");
            			validAction = moveEntity(dragon, Direction.UP);
            			dragon.setSleeping(false);
            			break;
            		case MOVE_DOWN:
            			//            		System.out.print("Dragon " + dragon.hashCode() + "moved down\n");
            			validAction = moveEntity(dragon, Direction.DOWN);
            			dragon.setSleeping(false);
            			break;

            		case MOVE_LEFT:
            			//            		System.out.print("Dragon " + dragon.hashCode() + "moved left\n");
            			validAction = moveEntity(dragon, Direction.LEFT);
            			dragon.setSleeping(false);
            			break;

            		case MOVE_RIGHT:
            			//            		System.out.print("Dragon " + dragon.hashCode() + "moved right\n");
            			validAction = moveEntity(dragon, Direction.RIGHT);
            			dragon.setSleeping(false);
            			break;

            		case STOP:
            			dragon.setSleeping(false);
            			validAction = true;
            			break;

            		case SLEEP:
            			dragon.setSleeping(true);
            			validAction = true;
            			break;
            		default:
            				break;
            		}
            	} while (!validAction);
            }
    }

	/**
	 * Dragon attack.
	 *
	 * @param dragon the dragon
	 */
	public void dragonAttack(Dragon dragon) {
		Position pos = dragon.getPosition();
		if(pos.getColumn() == getBoard().getHero().getPosition().getColumn()){
			if(getBoard().getHero().getPosition().getRow() < pos.getRow())
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getRow() - i > 0 && getBoard().getMap().isWalkable(pos.getRow() - i, pos.getColumn()); i++)
					setOnFire(new Position(pos.getRow() - i, pos.getColumn()));
			else
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getRow() + i < getBoard().getMap().getRows() && getBoard().getMap().isWalkable(pos.getRow() + i, pos.getColumn()); i++)
					setOnFire(new Position(pos.getRow() + i, pos.getColumn()));
		}
		
		if(pos.getRow() == getBoard().getHero().getPosition().getRow()){
			if(getBoard().getHero().getPosition().getColumn() < pos.getColumn())
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getColumn() - i > 0 && getBoard().getMap().isWalkable(pos.getRow(), pos.getColumn() - i); i++)
					setOnFire(new Position(pos.getRow(), pos.getColumn() - i));
			else
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getColumn() + i < getBoard().getMap().getCols() && getBoard().getMap().isWalkable(pos.getRow(), pos.getColumn() + i); i++)
					setOnFire(new Position(pos.getRow(), pos.getColumn() + i));
		}
	}
	
	/**
	 * Sets the on fire.
	 *
	 * @param pos the new on fire
	 */
	public void setOnFire(Position pos){
		Flame flame = new Flame(pos);
		getFire().add(flame);
	}

	/**
	 * Should attack.
	 *
	 * @param dragon the dragon
	 * @return true, if successful
	 */
	public boolean shouldAttack(Dragon dragon) {
		if(dragon.getAvailableActions().contains(Dragon.Action.ATTACK)){
			if(getBoard().getMap().isInLineOfSight(dragon.getPosition(), getBoard().getHero().getPosition(), Dragon.ATTACK_RADIUS))
				return true;
		}
		return false;
	}
    
    /**
     * Resolution phase.
     */
    public void resolutionPhase(){
    	if (isFinished())
			return;
    	
    	for(Iterator<Weapon> it = getBoard().getWeapons().descendingIterator(); it.hasNext(); ){
    		Weapon weapon = it.next();
    		if(weapon.getPosition().equals(getBoard().getHero().getPosition())){
    			System.out.print(weapon.getType() + "\n");
    			getBoard().getHero().addWeapon(weapon);
    			it.remove();
    			break;
    		}
    	}
    	
    	for(Flame flame : getFire()){
    		if(flame.getPosition().equals(getBoard().getHero().getPosition()) && !getBoard().getHero().hasShield()){
    			setFinished(true);
    			setWon(false);
    			setEndOfGameMessage("A dragon turned our hero into coal...");
    			return;
    		}
    	}

    	getFire().clear();
    	
    	for(Iterator<Dragon> it = getBoard().getDragons().descendingIterator(); it.hasNext(); ){
    		Dragon dragon = it.next();
    		if(Position.distance(dragon.getPosition(), getBoard().getHero().getPosition()) == 1.0){
    			if(getBoard().getHero().hasSword()){
		    	it.remove();
    			}
    			else if (!dragon.isSleeping()){
    				this.setFinished(true);
        			this.setWon(false);
        			setEndOfGameMessage("A dragon killed our hero...");
    				return;
    			}
    		}
    	}
    	
    	if (getBoard().getDragons().isEmpty() && getBoard().getHero().hasSword()){
    		this.setExitOpen(true);
    		getBoard().getMap().setWalkable(getBoard().getMap().getExit(), true);
    	}
    	if(getBoard().getHero().getPosition().equals(getBoard().getMap().getExit())){
    		this.setFinished(true);
    		this.setWon(true);
    		setEndOfGameMessage("You reached the exit of the maze!");
    	}
    }

    
    /**
     * Throw dart.
     *
     * @param direction the direction
     */
    public void throwDart(Direction direction)
    {
    	for (Weapon weapon : board.getHero().getWeapons()){
    		if (weapon.getType().equals(Weapon.Type.DART)) {
    			switch (direction){
    				case UP:
    					for (Dragon dragon : board.getDragons()) {
    						if (dragon.getPosition().getColumn() == getBoard().getHero().getPosition().getColumn() && dragon.getPosition().getRow() < getBoard().getHero().getPosition().getRow()) {
    							boolean canHit = true;
    							for (int row = board.getHero().getPosition().getRow(); row > dragon.getPosition().getRow(); --row)
    								if (!getBoard().getMap().isWalkable(row, getBoard().getHero().getPosition().getColumn())) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								board.getDragons().remove(dragon);
    								break;
    							}
    						}
    					}
    				case DOWN:
    					for (Dragon dragon : getBoard().getDragons()) {
    						if (dragon.getPosition().getColumn() == getBoard().getHero().getPosition().getColumn() && dragon.getPosition().getRow() > getBoard().getHero().getPosition().getRow()) {
    							boolean canHit = true;
    							for (int row = getBoard().getHero().getPosition().getRow(); row < dragon.getPosition().getRow(); ++row)
    								if (!getBoard().getMap().isWalkable(row, getBoard().getHero().getPosition().getColumn())) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								getBoard().getDragons().remove(dragon);
    								break;
    							}
    						}
    					}
    				case LEFT:
    					for (Dragon dragon : getBoard().getDragons()) {
    						if (dragon.getPosition().getRow() == getBoard().getHero().getPosition().getRow() && dragon.getPosition().getColumn() < getBoard().getHero().getPosition().getColumn()) {
    							boolean canHit = true;
    							for (int col = getBoard().getHero().getPosition().getColumn(); col > dragon.getPosition().getColumn(); --col)
    								if (!getBoard().getMap().isWalkable(getBoard().getHero().getPosition().getRow(), col)) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								getBoard().getDragons().remove(dragon);
    								break;
    							}
    						}
    					}
    				case RIGHT:
    					for (Dragon dragon : getBoard().getDragons()) {
    						if (dragon.getPosition().getRow() == getBoard().getHero().getPosition().getRow() && dragon.getPosition().getColumn() > getBoard().getHero().getPosition().getColumn()) {
    							boolean canHit = true;
    							for (int col = getBoard().getHero().getPosition().getColumn(); col < dragon.getPosition().getColumn(); ++col)
    								if (!getBoard().getMap().isWalkable(getBoard().getHero().getPosition().getRow(), col)) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								getBoard().getDragons().remove(dragon);
    								break;
    							}
    						}
    					}
    			}
    			getBoard().getHero().removeWeapon(weapon);
    			break;
    		}
    	}
    }

	/**
	 * Dragons turn.
	 */
	public void dragonsTurn() {
		if (isFinished())
			return;
		for(Dragon dragon : board.getDragons())
			moveDragon(dragon);
	}

	/**
	 * Hero turn.
	 *
	 * @param command the command
	 */
	public void heroTurn(Command command) {
		if (isFinished())
			return;
		switch(command.getAction()){
    	case MOVE:
    		moveEntity(board.getHero(),command.getDirection());
    		break;
    	case ATTACK:
    		throwDart(command.getDirection());
    		break;
    	case STOP:
    		break;
    	}
	}


	/**
	 * Sets the finished.
	 *
	 * @param finished the new finished
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * Checks if is won.
	 *
	 * @return true, if is won
	 */
	public boolean isWon() {
		return won;
	}

	/**
	 * Sets the won.
	 *
	 * @param won the new won
	 */
	public void setWon(boolean won) {
		this.won = won;
	}

	/**
	 * Gets the fire.
	 *
	 * @return the fire
	 */
	public LinkedList<Flame> getFire() {
		return fire;
	}

	/**
	 * Sets the fire.
	 *
	 * @param fire the new fire
	 */
	public void setFire(LinkedList<Flame> fire) {
		this.fire = fire;
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
	 * Checks if is exit open.
	 *
	 * @return true, if is exit open
	 */
	public boolean isExitOpen() {
		return exitOpen;
	}

	/**
	 * Sets the exit open.
	 *
	 * @param exitOpen the new exit open
	 */
	public void setExitOpen(boolean exitOpen) {
		this.getBoard().getMap().setWalkable(this.getBoard().getMap().getExit(), exitOpen);
		this.exitOpen = exitOpen;
	}

	/**
	 * Gets the end of game message.
	 *
	 * @return the endOfGameMessage
	 */
	public String getEndOfGameMessage() {
		return endOfGameMessage;
	}

	/**
	 * Sets the end of game message.
	 *
	 * @param endOfGameMessage the endOfGameMessage to set
	 */
	public void setEndOfGameMessage(String endOfGameMessage) {
		this.endOfGameMessage = endOfGameMessage;
	}
}
