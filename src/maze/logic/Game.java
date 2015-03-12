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
	
	MazeMap map;
	
	
	public enum Direction{UP, DOWN, LEFT, RIGHT}
	public enum Action{MOVE, ATTACK}
	
	Game(int rows, int cols, int numDragons){
		map = new MazeMap(rows, cols);
		
		
		ArrayList<Position> walkablePos = map.getWalkablePositions();
		Random rnd = new Random();
		Position newPos;
		while(numDragons > 0){
			newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
			Dragon newDragon = new Dragon(newPos,true, true, true); // TODO Modificar para aceitar as opções de modo dos dragões
			walkablePos.remove(newPos);
			numDragons--;
		}
		
		
		
		newPos = walkablePos.get(rnd.nextInt(walkablePos.size())); 
		hero = new Hero(newPos,true);
		walkablePos.remove(newPos);
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
            		validAction = moveEntity(dragon, Direction.UP);
            		dragon.setSleeping(false);
            		break;
            	case MOVE_DOWN:
            		validAction = moveEntity(dragon, Direction.DOWN);
            		dragon.setSleeping(false);
            		break;

            	case MOVE_LEFT:
            		validAction = moveEntity(dragon, Direction.LEFT);
            		dragon.setSleeping(false);
            		break;

            	case MOVE_RIGHT:
            		validAction = moveEntity(dragon, Direction.RIGHT);
            		dragon.setSleeping(false);
            		break;

            	case STOP:
            		dragon.setSleeping(false);
            		break;

            	case SLEEP:
            		dragon.setSleeping(true);
            		break;
            	}
            } while (!validAction);
    }
    
    public void resolutionPhase(){
    	for(Iterator<Weapon> it = weapons.descendingIterator(); it.hasNext(); ){
    		Weapon weapon = it.next();
    		if(weapon.getPos().equals(hero.getPos())){
    			hero.addWeapon(weapon);
    			it.remove();
    			break;
    		}
    	}
    	
    	for(Iterator<Dragon> it = dragons.descendingIterator(); it.hasNext(); ){
    		Dragon dragon = it.next();
    		if(Position.isAdjacent(dragon.getPos(), hero.getPos())){
    			for(Weapon weapon : weapons)
    				if(weapon.getType() == Weapon.Type.SWORD){
    			    	map.removeEntity(dragon);
    			    	it.remove();
    			    	break;
    				}

    		}
    	}
    	
    	if (dragons.isEmpty())
    		map.setWalkable(map.getExit(), true);
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
	
	
}
