package maze.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import maze.logic.Weapon.Type;

public class Game {
	private Hero hero;
	private LinkedList<Dragon> dragons;
	private LinkedList<Weapon> weapons;
	private LinkedList<Flame> fire;
	private LinkedList<Event> events;
	
	public MazeMap map;
	private boolean gameOver;
	private boolean won;
	
	public enum Direction{UP, DOWN, LEFT, RIGHT}
	public enum Action{MOVE, ATTACK, STOP}
	
	public Game(int rows, int cols, int numDragons, boolean canMove, boolean canSleep, boolean canAttack){
		this.map = new MazeMap(rows, cols);
		this.setDragons(new LinkedList<Dragon>());
		this.setWeapons(new LinkedList<Weapon>());
		this.setFire(new LinkedList<Flame>());
		this.setEvents(new LinkedList<Event>());
		gameOver = false;
		won = false;
		
		ArrayList<Position> walkablePos = map.getWalkablePositions();

		while(true){
			try {
				generateDragons(numDragons, walkablePos, canMove, canSleep, canAttack);
				break;
			} catch (Exception e) {
				for(Dragon dragon : this.getDragons())
					map.removeEntity(dragon);
				this.getDragons().clear();
			}
		}
		
		
		generateWeapons(3, walkablePos);

		Random rnd = new Random();
		Position newPos;
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		setHero(new Hero(newPos,true));
		map.addEntity(getHero());
		walkablePos.remove(newPos);
	}
	
	

	public Game(MazeMap map,Hero hero, LinkedList<Dragon> dragons, LinkedList<Weapon> weapons){
		this.map = map;
		this.setDragons(dragons);
		this.setWeapons(weapons);
		this.setFire(new LinkedList<Flame>());
		gameOver = false;
		won = false;
		
		for(Dragon dragon : dragons)
			map.addEntity(dragon);

		
		for(Weapon weapon : weapons)
			map.addEntity(weapon);

		this.setHero(hero);
		map.addEntity(hero);

	}

	public void generateWeapons(int maxDarts,ArrayList<Position> walkablePos){
		Position newPos;
		Random rnd = new Random();
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		Weapon sword = new Weapon(Weapon.Type.SWORD, newPos, true);
		getWeapons().add(sword);
		
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		Weapon shield = new Weapon(Weapon.Type.SHIELD, newPos, true);
		getWeapons().add(shield);
		
		for(int i = rnd.nextInt(maxDarts); i > 0; i--){
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Weapon dart = new Weapon(Weapon.Type.DART, newPos, true);
			getWeapons().add(dart);
		}

		for(Weapon weapon : getWeapons())
			map.addEntity(weapon);



	}

	public void generateDragons(int numDragons,
			ArrayList<Position> walkablePos, boolean canMove, boolean canSleep, boolean canAttack) throws Exception {
		Random rnd = new Random();
		Position newPos;
		int tempNumDragons = numDragons;
		while(tempNumDragons > 0){
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Dragon newDragon = new Dragon(newPos,true, canMove, canSleep, canAttack);
			getDragons().push(newDragon);
			map.addEntity(newDragon);
			walkablePos.remove(newPos);
			tempNumDragons--;
		}
		
		if(this.getDragons().size() != numDragons)
			throw new Exception();
		
	}
	

	
	public boolean isFinished(){
		return this.getHero().getPos().equals(map.getExit());
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
           
            if(!map.isWalkable(newPos))
            	return false; 
            
            if(newPos.equals(getHero().getPos()))
            	return false;
            
            for(Dragon dragon : getDragons())
            	if(newPos.equals(dragon.getPos()))
            		return false;
            
    		events.add(new MoveEvent(entity,newPos));
            //setEntityPosition(entity, newPos);
            
            return true;
    }
   
	public void setEntityPosition(Entity entity, Position newPos){
		map.removeEntity(entity);
        entity.setPos(newPos);
		map.addEntity(entity);
				
	}
	
	
    public void moveDragon(Dragon dragon){
            Random rnd = new Random();
            Position newPos = dragon.getPos().clone();
            ArrayList<Dragon.Action> availableActions = dragon.getAvailableActions();
            
            
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
		if(pos.getCol() == getHero().getPos().getCol()){
			if(getHero().getPos().getRow() < pos.getRow())
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getRow() - i > 0 && map.isWalkable(pos.getRow() - i, pos.getCol()); i++)
					setOnFire(new Position(pos.getRow() - i, pos.getCol()));
			else
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getRow() + i < map.getRows() && map.isWalkable(pos.getRow() + i, pos.getCol()); i++)
					setOnFire(new Position(pos.getRow() + i, pos.getCol()));
		}
		
		if(pos.getRow() == getHero().getPos().getRow()){
			if(getHero().getPos().getCol() < pos.getCol())
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getCol() - i > 0 && map.isWalkable(pos.getRow(), pos.getCol() - i); i++)
					setOnFire(new Position(pos.getRow(), pos.getCol() - i));
			else
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getCol() + i < map.getCols() && map.isWalkable(pos.getRow(), pos.getCol() + i); i++)
					setOnFire(new Position(pos.getRow(), pos.getCol() + i));
		}
	}
	
	public void setOnFire(Position pos){
		Flame flame = new Flame(pos,true);
		getFire().add(flame);
		map.addEntity(flame);
	}

	public boolean shouldAttack(Dragon dragon) {
		if(dragon.getAvailableActions().contains(Dragon.Action.ATTACK)){
			if(map.isInLineOfSight(dragon.getPos(), getHero().getPos(), Dragon.ATTACK_RADIUS))
				return true;
		}
		return false;
	}
    
    public void resolutionPhase(){
    	for(Iterator<Weapon> it = getWeapons().descendingIterator(); it.hasNext(); ){
    		Weapon weapon = it.next();
    		if(weapon.getPos().equals(getHero().getPos())){
    			System.out.print(weapon.getType() + "\n");
    			getHero().addWeapon(weapon);
    			map.removeEntity(weapon);
    			it.remove();
    			break;
    		}
    	}
    	
    	for(Iterator<Dragon> it = getDragons().descendingIterator(); it.hasNext(); ){
    		Dragon dragon = it.next();
    		if(Position.isAdjacent(dragon.getPos(), getHero().getPos())){
    			if(getHero().hasSword()){
		    	map.removeEntity(dragon);
		    	it.remove();
    			}
    			else{
    				this.setGameOver(true);
        			this.setWon(false);
    				return;
    			}
    		}
    	}

    	for(Flame flame : getFire()){
    		if(flame.getPos().equals(getHero().getPos()) && !getHero().hasShield()){
    			this.setGameOver(true);
    			this.setWon(false);
    			System.out.println("Morreu queimado");
    			return;
    		}
    		map.removeEntity(flame);
    	}

    	getFire().clear();
    	
    	if (getDragons().isEmpty())
    		map.setWalkable(map.getExit(), true);
    	if(isFinished()){
    		this.setGameOver(true);
    		this.setWon(true);
    	}
    }

    // TODO Acabar throw_dart, usar iteradores para remover dragões
    public void throwDart(Direction direction)
    {
    	for (Weapon weapon : getHero().getWeapons()){
    		if (weapon.getType().equals(Weapon.Type.DART)) {
    			switch (direction){
    				case UP:
    					for (Dragon dragon : getDragons()) {
    						if (dragon.getPos().getCol() == getHero().getPos().getCol() && dragon.getPos().getRow() < getHero().getPos().getRow()) {
    							boolean canHit = true;
    							for (int row = getHero().getPos().getRow(); row > dragon.getPos().getRow(); --row)
    								if (!map.isWalkable(row, getHero().getPos().getCol())) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								map.removeEntity(dragon);
    								getDragons().remove(dragon);
    								break;
    							}
    						}
    					}
    				case DOWN:
    					for (Dragon dragon : getDragons()) {
    						if (dragon.getPos().getCol() == getHero().getPos().getCol() && dragon.getPos().getRow() > getHero().getPos().getRow()) {
    							boolean canHit = true;
    							for (int row = getHero().getPos().getRow(); row < dragon.getPos().getRow(); ++row)
    								if (!map.isWalkable(row, getHero().getPos().getCol())) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								map.removeEntity(dragon);
    								getDragons().remove(dragon);
    								break;
    							}
    						}
    					}
    				case LEFT:
    					for (Dragon dragon : getDragons()) {
    						if (dragon.getPos().getRow() == getHero().getPos().getRow() && dragon.getPos().getCol() < getHero().getPos().getCol()) {
    							boolean canHit = true;
    							for (int col = getHero().getPos().getCol(); col > dragon.getPos().getCol(); --col)
    								if (!map.isWalkable(getHero().getPos().getRow(), col)) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								map.removeEntity(dragon);
    								getDragons().remove(dragon);
    								break;
    							}
    						}
    					}
    				case RIGHT:
    					for (Dragon dragon : getDragons()) {
    						if (dragon.getPos().getRow() == getHero().getPos().getRow() && dragon.getPos().getCol() > getHero().getPos().getCol()) {
    							boolean canHit = true;
    							for (int col = getHero().getPos().getCol(); col < dragon.getPos().getCol(); ++col)
    								if (!map.isWalkable(getHero().getPos().getRow(), col)) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								map.removeEntity(dragon);
    								getDragons().remove(dragon);
    								break;
    							}
    						}
    					}
    			}
    			getHero().removeWeapon(weapon);
    			break;
    		}
    	}
    }

	public void dragonsTurn() {
		for(Dragon dragon : getDragons())
			moveDragon(dragon);
	}

	public void heroTurn(Action action, Direction direction) {
		switch(action){
    	case MOVE:
    		moveEntity(getHero(),direction);
    		break;
    	case ATTACK:
    		throwDart(direction);
    		break;
    	case STOP:
    		break;
    	}
	}
	
	public void update(){
		while(!events.isEmpty()){
			Event event = events.pollFirst();
			
			if (event instanceof MoveEvent){
				MoveEvent tempEvent = (MoveEvent) event;
				setEntityPosition(tempEvent.getEntity(), tempEvent.getNewPosition());
			}
		}
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}
	
	public Position getHeroPos(){
		return getHero().getPos();
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public LinkedList<Dragon> getDragons() {
		return dragons;
	}

	public void setDragons(LinkedList<Dragon> dragons) {
		this.dragons = dragons;
	}

	public LinkedList<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(LinkedList<Weapon> weapons) {
		this.weapons = weapons;
	}

	public LinkedList<Flame> getFire() {
		return fire;
	}

	public void setFire(LinkedList<Flame> fire) {
		this.fire = fire;
	}
	
	//TODO Tratar da questão de segurança da linkedlist não ser unmodifiable
	public LinkedList<Event> getEvents() {
		return events;
	}

	private void setEvents(LinkedList<Event> linkedList) {
		this.events = linkedList;
	}

	
}
