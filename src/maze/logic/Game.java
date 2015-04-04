package maze.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import maze.logic.Weapon.Type;

public class Game {
	public Hero hero;
	public LinkedList<Dragon> dragons;
	public LinkedList<Weapon> weapons;
	public LinkedList<Flame> fire;
	
	public MazeMap map;
	private boolean gameOver;
	private boolean won;
	
	public enum Direction{UP, DOWN, LEFT, RIGHT}
	public enum Action{MOVE, ATTACK, STOP}
	
	public Game(int rows, int cols, int numDragons, boolean canMove, boolean canSleep, boolean canAttack){
		this.map = new MazeMap(rows, cols);
		this.dragons = new LinkedList<Dragon>();
		this.weapons = new LinkedList<Weapon>();
		this.fire = new LinkedList<Flame>();
		gameOver = false;
		won = false;
		
		ArrayList<Position> walkablePos = map.getWalkablePositions();

		while(true){
			try {
				generateDragons(numDragons, walkablePos, canMove, canSleep, canAttack);
				break;
			} catch (Exception e) {
				for(Dragon dragon : this.dragons)
					map.removeEntity(dragon);
				this.dragons.clear();
			}
		}
		
		
		generateWeapons(3, walkablePos);

		Random rnd = new Random();
		Position newPos;
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		hero = new Hero(newPos,true);
		map.addEntity(hero);
		walkablePos.remove(newPos);
	}
	
	public Game(MazeMap map,Hero hero, LinkedList<Dragon> dragons, LinkedList<Weapon> weapons){
		this.map = map;
		this.dragons = dragons;
		this.weapons = weapons;
		this.fire = new LinkedList<Flame>();
		gameOver = false;
		won = false;
		
		for(Dragon dragon : dragons)
			map.addEntity(dragon);

		
		for(Weapon weapon : weapons)
			map.addEntity(weapon);

		this.hero = hero;
		map.addEntity(hero);

	}

	public void generateWeapons(int maxDarts,ArrayList<Position> walkablePos){
		Position newPos;
		Random rnd = new Random();
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		Weapon sword = new Weapon(Weapon.Type.SWORD, newPos, true);
		weapons.add(sword);
		
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		Weapon shield = new Weapon(Weapon.Type.SHIELD, newPos, true);
		weapons.add(shield);
		
		for(int i = rnd.nextInt(maxDarts); i > 0; i--){
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Weapon dart = new Weapon(Weapon.Type.DART, newPos, true);
			weapons.add(dart);
		}

		for(Weapon weapon : weapons)
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
			dragons.push(newDragon);
			map.addEntity(newDragon);
			walkablePos.remove(newPos);
			tempNumDragons--;
		}
		
		if(this.dragons.size() != numDragons)
			throw new Exception();
		
	}
	

	
	public boolean isFinished(){
		return this.hero.getPos().equals(map.getExit());
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
            
            if(newPos.equals(hero.getPos()))
            	return false;
            
            for(Dragon dragon : dragons)
            	if(newPos.equals(dragon.getPos()))
            		return false;
            
            setEntityPosition(entity, newPos);
            
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
		if(pos.getCol() == hero.getPos().getCol()){
			if(hero.getPos().getRow() < pos.getRow())
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getRow() - i > 0 && map.isWalkable(pos.getRow() - i, pos.getCol()); i++)
					setOnFire(new Position(pos.getRow() - i, pos.getCol()));
			else
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getRow() + i < map.getRows() && map.isWalkable(pos.getRow() + i, pos.getCol()); i++)
					setOnFire(new Position(pos.getRow() + i, pos.getCol()));
		}
		
		if(pos.getRow() == hero.getPos().getRow()){
			if(hero.getPos().getCol() < pos.getCol())
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getCol() - i > 0 && map.isWalkable(pos.getRow(), pos.getCol() - i); i++)
					setOnFire(new Position(pos.getRow(), pos.getCol() - i));
			else
				for(int i = 1; i <= Dragon.ATTACK_RADIUS && pos.getCol() + i < map.getCols() && map.isWalkable(pos.getRow(), pos.getCol() + i); i++)
					setOnFire(new Position(pos.getRow(), pos.getCol() + i));
		}
	}
	
	public void setOnFire(Position pos){
		Flame flame = new Flame(pos,true);
		fire.add(flame);
		map.addEntity(flame);
	}

	public boolean shouldAttack(Dragon dragon) {
		if(dragon.getAvailableActions().contains(Dragon.Action.ATTACK)){
			if(map.isInLineOfSight(dragon.getPos(), hero.getPos(), Dragon.ATTACK_RADIUS))
				return true;
		}
		return false;
	}
    
    public void resolutionPhase(){
    	for(Iterator<Weapon> it = weapons.descendingIterator(); it.hasNext(); ){
    		Weapon weapon = it.next();
    		if(weapon.getPos().equals(hero.getPos())){
    			System.out.print(weapon.getType() + "\n");
    			hero.addWeapon(weapon);
    			map.removeEntity(weapon);
    			it.remove();
    			break;
    		}
    	}
    	
    	for(Iterator<Dragon> it = dragons.descendingIterator(); it.hasNext(); ){
    		Dragon dragon = it.next();
    		if(Position.isAdjacent(dragon.getPos(), hero.getPos())){
    			if(hero.hasSword()){
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

    	for(Flame flame : fire){
    		if(flame.getPos().equals(hero.getPos()) && !hero.hasShield()){
    			this.setGameOver(true);
    			this.setWon(false);
    			System.out.println("Morreu queimado");
    			return;
    		}
    		map.removeEntity(flame);
    	}

    	fire.clear();
    	
    	if (dragons.isEmpty())
    		map.setWalkable(map.getExit(), true);
    	if(isFinished()){
    		this.setGameOver(true);
    		this.setWon(true);
    	}
    }

    // TODO Acabar throw_dart, usar iteradores para remover dragões
    public void throwDart(Direction direction)
    {
    	for (Weapon weapon : hero.getWeapons()){
    		if (weapon.getType().equals(Weapon.Type.DART)) {
    			switch (direction){
    				case UP:
    					for (Dragon dragon : dragons) {
    						if (dragon.getPos().getCol() == hero.getPos().getCol() && dragon.getPos().getRow() < hero.getPos().getRow()) {
    							boolean canHit = true;
    							for (int row = hero.getPos().getRow(); row > dragon.getPos().getRow(); --row)
    								if (!map.isWalkable(row, hero.getPos().getCol())) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								map.removeEntity(dragon);
    								dragons.remove(dragon);
    								break;
    							}
    						}
    					}
    				case DOWN:
    					for (Dragon dragon : dragons) {
    						if (dragon.getPos().getCol() == hero.getPos().getCol() && dragon.getPos().getRow() > hero.getPos().getRow()) {
    							boolean canHit = true;
    							for (int row = hero.getPos().getRow(); row < dragon.getPos().getRow(); ++row)
    								if (!map.isWalkable(row, hero.getPos().getCol())) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								map.removeEntity(dragon);
    								dragons.remove(dragon);
    								break;
    							}
    						}
    					}
    				case LEFT:
    					for (Dragon dragon : dragons) {
    						if (dragon.getPos().getRow() == hero.getPos().getRow() && dragon.getPos().getCol() < hero.getPos().getCol()) {
    							boolean canHit = true;
    							for (int col = hero.getPos().getCol(); col > dragon.getPos().getCol(); --col)
    								if (!map.isWalkable(hero.getPos().getRow(), col)) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								map.removeEntity(dragon);
    								dragons.remove(dragon);
    								break;
    							}
    						}
    					}
    				case RIGHT:
    					for (Dragon dragon : dragons) {
    						if (dragon.getPos().getRow() == hero.getPos().getRow() && dragon.getPos().getCol() > hero.getPos().getCol()) {
    							boolean canHit = true;
    							for (int col = hero.getPos().getCol(); col < dragon.getPos().getCol(); ++col)
    								if (!map.isWalkable(hero.getPos().getRow(), col)) {
    									canHit = false;
    									break;
    								}
    							if (canHit){
    								map.removeEntity(dragon);
    								dragons.remove(dragon);
    								break;
    							}
    						}
    					}
    			}
    			hero.removeWeapon(weapon);
    			break;
    		}
    	}
    }

	public void dragonsTurn() {
		for(Dragon dragon : dragons)
			moveDragon(dragon);
	}

	public void heroTurn(Action action, Direction direction) {
		switch(action){
    	case MOVE:
    		moveEntity(hero,direction);
    		break;
    	case ATTACK:
    		throwDart(direction);
    		break;
    	case STOP:
    		break;
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
		return hero.getPos();
	}

	
}
