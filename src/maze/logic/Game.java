package maze.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Game {
	private LinkedList<Flame> fire;
	
	private GameBoard board;
	private boolean finished;
	private boolean won;

	private boolean exitOpen;
	
	public enum Direction{UP, DOWN, LEFT, RIGHT}
	public enum Action{MOVE, ATTACK, STOP}
	public static class Command {
		private Action action;
		private Direction direction;
		
		public Command(Action action, Direction direction) {
			this.setAction(action);
			this.setDirection(direction);
		}

		/**
		 * @return the action
		 */
		public Action getAction() {
			return action;
		}

		/**
		 * @param action the action to set
		 */
		public void setAction(Action action) {
			this.action = action;
		}

		/**
		 * @return the direction
		 */
		public Direction getDirection() {
			return direction;
		}

		/**
		 * @param direction the direction to set
		 */
		public void setDirection(Direction direction) {
			this.direction = direction;
		}
	}
	
	public Game(int rows, int cols, int numDragons, boolean canMove, boolean canSleep, boolean canAttack){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(rows);
		builder.setCols(cols);
		MazeMap map = builder.build();
		setFire(new LinkedList<Flame>());
		finished = false;
		won = false;
		
		LinkedList<Position> walkablePos = map.getWalkablePositions();
		LinkedList<Dragon> dragons;
		dragons = generateDragons(numDragons, walkablePos, canMove, canSleep, canAttack);	
		
		walkablePos = map.getWalkablePositions();
		LinkedList<Weapon> weapons = generateWeapons(numDragons, walkablePos);

		Random rnd = new Random();
		Position newPos;
		walkablePos = map.getWalkablePositions();
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		Hero hero = new Hero(newPos,true);
		walkablePos.remove(newPos);
		
		

		
		setBoard(new GameBoard(map, hero, dragons, weapons));
		this.setExitOpen(this.getBoard().getDragons().isEmpty());
	}

	public Game(MazeMap map, Hero hero, LinkedList<Dragon> dragons, LinkedList<Weapon> weapons){
		this(new GameBoard(map, hero, dragons, weapons));
	}
	
	public Game(GameBoard board) {
		setBoard(board);
		setFire(new LinkedList<Flame>());
		this.setExitOpen(board.getDragons().isEmpty());
		finished = false;
		won = false;
	}

	public LinkedList<Weapon> generateWeapons(int maxDarts,LinkedList<Position> walkablePos){
		Position newPos;
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Random rnd = new Random();
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		Weapon sword = new Weapon(Weapon.Type.SWORD, newPos, true);
		weapons.add(sword);
		
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		Weapon shield = new Weapon(Weapon.Type.SHIELD, newPos, true);
		weapons.add(shield);
		
		for(int i = maxDarts; i > 0; i--){
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Weapon dart = new Weapon(Weapon.Type.DART, newPos, true);
			weapons.add(dart);
		}
		
		return weapons;
	}

	public LinkedList<Dragon> generateDragons(int numDragons,
		LinkedList<Position> walkablePos, boolean canMove, boolean canSleep, boolean canAttack) {
		Random rnd = new Random();
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		Position newPos;
		int tempNumDragons = numDragons;
		while(tempNumDragons > 0){
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Dragon newDragon = new Dragon(newPos,true, canMove, canSleep, canAttack);
			dragons.add(newDragon);
			walkablePos.remove(newPos);
			tempNumDragons--;
		}
		
		return dragons;
	}
	
	public boolean isFinished(){
		return finished;
	}
	
	public boolean moveEntity(Entity entity, Direction direction)
    {
            Position newPos = entity.getPos().clone();
            switch (direction)
            {
                    case UP:
                            newPos.setRow(newPos.getRow() - 1);
                            break;
                    case DOWN:
                        	newPos.setRow(newPos.getRow() + 1);
                            break;
                    case LEFT:
                        	newPos.setCol(newPos.getCol() - 1);
                            break;
                    case RIGHT:
                    		newPos.setCol(newPos.getCol() + 1);
                            break;
            }
           
            if(!getBoard().getMap().isWalkable(newPos))
            	return false;
            
            if(newPos.equals(getBoard().getHero().getPos()))
            	return false;
            
            for(Dragon dragon : getBoard().getDragons())
            	if(newPos.equals(dragon.getPos()))
            		return false;
            
            entity.setPosition(newPos);
            
            return true;
    }	
	
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

	public void dragonAttack(Dragon dragon) {
		Position pos = dragon.getPos();
		if(pos.getCol() == getBoard().getHero().getPos().getCol()){
			if(getBoard().getHero().getPos().getRow() < pos.getRow())
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getRow() - i > 0 && getBoard().getMap().isWalkable(pos.getRow() - i, pos.getCol()); i++)
					setOnFire(new Position(pos.getRow() - i, pos.getCol()));
			else
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getRow() + i < getBoard().getMap().getRows() && getBoard().getMap().isWalkable(pos.getRow() + i, pos.getCol()); i++)
					setOnFire(new Position(pos.getRow() + i, pos.getCol()));
		}
		
		if(pos.getRow() == getBoard().getHero().getPos().getRow()){
			if(getBoard().getHero().getPos().getCol() < pos.getCol())
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getCol() - i > 0 && getBoard().getMap().isWalkable(pos.getRow(), pos.getCol() - i); i++)
					setOnFire(new Position(pos.getRow(), pos.getCol() - i));
			else
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getCol() + i < getBoard().getMap().getCols() && getBoard().getMap().isWalkable(pos.getRow(), pos.getCol() + i); i++)
					setOnFire(new Position(pos.getRow(), pos.getCol() + i));
		}
	}
	
	public void setOnFire(Position pos){
		Flame flame = new Flame(pos,true);
		getFire().add(flame);
	}

	public boolean shouldAttack(Dragon dragon) {
		if(dragon.getAvailableActions().contains(Dragon.Action.ATTACK)){
			if(getBoard().getMap().isInLineOfSight(dragon.getPos(), getBoard().getHero().getPos(), Dragon.ATTACK_RADIUS))
				return true;
		}
		return false;
	}
    
    public void resolutionPhase(){
    	for(Iterator<Weapon> it = getBoard().getWeapons().descendingIterator(); it.hasNext(); ){
    		Weapon weapon = it.next();
    		if(weapon.getPos().equals(getBoard().getHero().getPos())){
    			System.out.print(weapon.getType() + "\n");
    			getBoard().getHero().addWeapon(weapon);
    			it.remove();
    			break;
    		}
    	}
    	
    	for(Iterator<Dragon> it = getBoard().getDragons().descendingIterator(); it.hasNext(); ){
    		Dragon dragon = it.next();
    		if(Position.distance(dragon.getPos(), getBoard().getHero().getPos()) == 1.0){
    			if(getBoard().getHero().hasSword()){
		    	it.remove();
    			}
    			else{
    				this.setFinished(true);
        			this.setWon(false);
    				return;
    			}
    		}
    	}

    	for(Flame flame : getFire()){
    		if(flame.getPos().equals(getBoard().getHero().getPos()) && !getBoard().getHero().hasShield()){
    			setFinished(true);
    			setWon(false);
    			System.out.println("Morreu queimado");
    			return;
    		}
    	}

    	getFire().clear();
    	
    	if (getBoard().getDragons().isEmpty()){
    		this.setExitOpen(true);
    		getBoard().getMap().setWalkable(getBoard().getMap().getExit(), true);
    	}
    	if(getBoard().getHero().getPos().equals(getBoard().getMap().getExit())){
    		this.setFinished(true);
    		this.setWon(true);
    	}
    }

    // TODO Acabar throw_dart, usar iteradores para remover dragões
    public void throwDart(Direction direction)
    {
    	for (Weapon weapon : board.getHero().getWeapons()){
    		if (weapon.getType().equals(Weapon.Type.DART)) {
    			switch (direction){
    				case UP:
    					for (Dragon dragon : board.getDragons()) {
    						if (dragon.getPos().getCol() == getBoard().getHero().getPos().getCol() && dragon.getPos().getRow() < getBoard().getHero().getPos().getRow()) {
    							boolean canHit = true;
    							for (int row = board.getHero().getPos().getRow(); row > dragon.getPos().getRow(); --row)
    								if (!getBoard().getMap().isWalkable(row, getBoard().getHero().getPos().getCol())) {
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
    						if (dragon.getPos().getCol() == getBoard().getHero().getPos().getCol() && dragon.getPos().getRow() > getBoard().getHero().getPos().getRow()) {
    							boolean canHit = true;
    							for (int row = getBoard().getHero().getPos().getRow(); row < dragon.getPos().getRow(); ++row)
    								if (!getBoard().getMap().isWalkable(row, getBoard().getHero().getPos().getCol())) {
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
    						if (dragon.getPos().getRow() == getBoard().getHero().getPos().getRow() && dragon.getPos().getCol() < getBoard().getHero().getPos().getCol()) {
    							boolean canHit = true;
    							for (int col = getBoard().getHero().getPos().getCol(); col > dragon.getPos().getCol(); --col)
    								if (!getBoard().getMap().isWalkable(getBoard().getHero().getPos().getRow(), col)) {
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
    						if (dragon.getPos().getRow() == getBoard().getHero().getPos().getRow() && dragon.getPos().getCol() > getBoard().getHero().getPos().getCol()) {
    							boolean canHit = true;
    							for (int col = getBoard().getHero().getPos().getCol(); col < dragon.getPos().getCol(); ++col)
    								if (!getBoard().getMap().isWalkable(getBoard().getHero().getPos().getRow(), col)) {
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

	public void dragonsTurn() {
		for(Dragon dragon : board.getDragons())
			moveDragon(dragon);
	}

	public void heroTurn(Command command) {
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


	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public LinkedList<Flame> getFire() {
		return fire;
	}

	public void setFire(LinkedList<Flame> fire) {
		this.fire = fire;
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

	public boolean isExitOpen() {
		return exitOpen;
	}

	public void setExitOpen(boolean exitOpen) {
		this.getBoard().getMap().setWalkable(this.getBoard().getMap().getExit(), exitOpen);
		this.exitOpen = exitOpen;
	}
}
