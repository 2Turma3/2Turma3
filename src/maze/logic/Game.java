package maze.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import maze.logic.Weapon.Type;

public class Game {
	Hero hero;
	LinkedList<Dragon> dragons;
	LinkedList<Weapon> weapons;
	
	public MazeMap map;
	private boolean gameOver;
	private boolean won;
	
	public enum Direction{UP, DOWN, LEFT, RIGHT}
	public enum Action{MOVE, ATTACK}
	
	public Game(int rows, int cols, int numDragons){
		map = new MazeMap(rows, cols);
		dragons = new LinkedList<Dragon>();
		weapons = new LinkedList<Weapon>();
		gameOver = false;
		won = false;
		
		ArrayList<Position> walkablePos = map.getWalkablePositions();
		Random rnd = new Random();
		Position newPos;
		generateDragons(numDragons, walkablePos);
				
		generateWeapons(3, walkablePos);
		
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		hero = new Hero(newPos,true);
		map.addEntity(hero);
		walkablePos.remove(newPos);
	}

	private void generateWeapons(int maxDarts,ArrayList<Position> walkablePos){
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
		
		for(Weapon weapon : weapons){
			map.addEntity(weapon);
		}
		
	}

	private void generateDragons(int numDragons,
			ArrayList<Position> walkablePos) {
		Random rnd = new Random();
		Position newPos;
		while(numDragons > 0){
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Dragon newDragon = new Dragon(newPos,true, true, true); // TODO Modificar para aceitar as opções de modo dos dragões
			dragons.push(newDragon);
			map.addEntity(newDragon);
			walkablePos.remove(newPos);
			numDragons--;
		}
	}
	
	
	private void generateDragons(int numDragons){
		
	}
	
	public boolean isFinished(){
		return this.hero.getPos().equals(map.getExit());
	}
	
	private boolean moveEntity(Entity entity, Direction direction)
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
            
            map.removeEntity(entity);
            entity.setPos(newPos);
            map.addEntity(entity);
            return true;
    }
   
	
	
	
    private void moveDragon(Dragon dragon){
            Random rnd = new Random();
            Position newPos = dragon.getPos().clone();
            ArrayList<Dragon.Action> availableActions = dragon.getAvailableActions();
            
            boolean validAction = true;

            do {
            	switch(availableActions.get(rnd.nextInt(availableActions.size()))){
            	case MOVE_UP:
            		System.out.print(availableActions.size());
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
            	}
            } while (!validAction);
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
    			System.out.print("dragon " + dragon.hashCode() + "is adjacent to hero\n" );
//    			try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//
//				}
    			
    			if(hero.hasSword()){
		    	map.removeEntity(dragon);
		    	it.remove();
    			}
    			else{
    			gameOver = true;
    			won = false;
    			return;
    			}
    		}
    	}
    	
    	if (dragons.isEmpty())
    		map.setWalkable(map.getExit(), true);
    	if(isFinished()){
    		gameOver = true;
    		won = true;
    	}
    	
    	System.out.println("Hero (" + hero.getPos());
    	for (Weapon weapon : hero.getWeapons())
    		System.out.println(weapon.getType());
    	for (Dragon dragon : dragons)
    		System.out.println("" + dragon.hashCode() + dragon.getPos());
    	for (Weapon weapon : weapons)
    		System.out.println(weapon.getType() + " " + weapon.getPos());
    	System.out.println("gameOver: " + gameOver);
    	System.out.println("won: " + won);
    }

    // TODO Acabar throw_dart, usar iteradores para remover dragões
    private void throwDart(Direction direction)
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
	
    public void turn(Action action, Direction direction){

    	switch(action){
    	case MOVE:
    		moveEntity(hero,direction);
    		break;
    	case ATTACK:
    		throwDart(direction);
    		break;
    	}


		
		for(Dragon dragon : dragons)
			moveDragon(dragon);
			
		resolutionPhase();
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

	
}
