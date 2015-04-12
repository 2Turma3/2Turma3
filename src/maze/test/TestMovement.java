package maze.test;


import java.util.LinkedList;

import junit.framework.TestCase;
import maze.cli.CommandLine;
import maze.logic.*;
import maze.logic.Game.Command;

import org.junit.Test;

public class TestMovement extends TestCase {

	@Test
	public void testMove(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,2));
		
		Game game = new Game(map, hero,new LinkedList<Dragon>(), new LinkedList<Weapon>());
		
		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.RIGHT));
		
		assertEquals( new Position(1,3), game.getBoard().getHero().getPosition());
	}

	public void testFalseMove(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,3));
		
		Game game = new Game(map, hero,new LinkedList<Dragon>(), new LinkedList<Weapon>());
		
		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.RIGHT));

		assertEquals( new Position(1,3), game.getBoard().getHero().getPosition());
	}
	
	public void testCatchSword(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,3));
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Weapon sword = new Weapon(Weapon.Type.SWORD, new Position(1,2));
		weapons.add(sword);
		
		Game game = new Game(map, hero,new LinkedList<Dragon>(),weapons);
		
		assertEquals(false, game.getBoard().getHero().hasSword());
		
		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.LEFT));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.getBoard().getHero().hasSword());	
	}
	
	
	public void testKilledByDragon(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,3));
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,1), false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.isFinished());
		
		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.LEFT));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.isFinished());	
		assertEquals(false, game.isWon());
	}
	
	public void testKillDragon(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,3));
		
		Weapon sword = new Weapon(Weapon.Type.SWORD, null);
		hero.addWeapon(sword);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.getBoard().getDragons().isEmpty());
		
		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.LEFT));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.getBoard().getDragons().isEmpty());
	}
	
	public void testDragonSleep(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(100);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,98));
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), false, true, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		boolean dragonSlept = false;
		
		for(int i = 0; i < 1000; i++){
			game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.LEFT));
			game.dragonsTurn();
			game.resolutionPhase();
			
			for(Dragon dragon : game.getBoard().getDragons()){
				if(dragon.isSleeping()){
					dragonSlept = true;
					break;
				}
			}
			if(dragonSlept)
				break;
		}
		
		assertEquals(true, dragonSlept);
	}
	
	public void testTwoDragonsInSamePosition(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(200);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,198));
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,45), true, false, false));
		dragons.add(new Dragon(new Position(1,46), true, false, false));
		dragons.add(new Dragon(new Position(1,47), true, false, false));
		dragons.add(new Dragon(new Position(1,48), true, false, false));
		dragons.add(new Dragon(new Position(1,49), true, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		boolean dragonInSamePosition = false;
		
		for(int i = 0; i < 1000; i++){
			game.heroTurn(new Game.Command(Game.Action.STOP, null));
			game.dragonsTurn();
			game.resolutionPhase();
			
			for(Dragon dragon1 : game.getBoard().getDragons()){
				for (Dragon dragon2 : game.getBoard().getDragons())
					if (dragon1 != dragon2 && dragon1.getPosition().equals(dragon2.getPosition()))
						dragonInSamePosition = true;
			}
			if(dragonInSamePosition)
				break;
		}
		assertEquals(false, dragonInSamePosition);
	}
	
	public void testThrowDartLeft(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,3));
		
		Weapon dart = new Weapon(Weapon.Type.DART, null);
		assertEquals(0, hero.getDartsNumber());
		hero.addWeapon(dart);
		assertEquals(1, hero.getDartsNumber());
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.getBoard().getDragons().isEmpty());
		
		game.heroTurn(new Game.Command(Game.Action.ATTACK, Game.Direction.LEFT));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.getBoard().getDragons().isEmpty());
	}
	
	public void testThrowDartRight(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,1));
		

		Weapon dart = new Weapon(Weapon.Type.DART, null);
		assertEquals(0, hero.getDartsNumber());
		hero.addWeapon(dart);
		assertEquals(1, hero.getDartsNumber());
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,4), false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.getBoard().getDragons().isEmpty());
		
		game.heroTurn(new Game.Command(Game.Action.ATTACK, Game.Direction.RIGHT));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.getBoard().getDragons().isEmpty());
	}
	
	public void testThrowDartUp(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(5);
		builder.setCols(3);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(3,1));
		
		Weapon dart = new Weapon(Weapon.Type.DART, null);
		assertEquals(0, hero.getDartsNumber());
		hero.addWeapon(dart);
		assertEquals(1, hero.getDartsNumber());
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.getBoard().getDragons().isEmpty());
		
		game.heroTurn(new Game.Command(Game.Action.ATTACK, Game.Direction.UP));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.getBoard().getDragons().isEmpty());
	}
	
	public void testThrowDartDown(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(5);
		builder.setCols(3);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,1));
		
		Weapon dart = new Weapon(Weapon.Type.DART, null);
		assertEquals(0, hero.getDartsNumber());
		hero.addWeapon(dart);
		assertEquals(1, hero.getDartsNumber());
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(3,1), false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.getBoard().getDragons().isEmpty());
		
		game.heroTurn(new Command(Game.Action.ATTACK, Game.Direction.DOWN));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.getBoard().getDragons().isEmpty());
	}
	
	
	public void testWinGame(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,3));
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Weapon sword = new Weapon(Weapon.Type.SWORD, new Position(1,2));
		weapons.add(sword);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), false, false, false));
		
		Game game = new Game(map, hero,dragons,weapons);
		
		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.LEFT));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.getBoard().getDragons().isEmpty());
		
		for(int i = 0; i < 2; i++){
			game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.LEFT));
			game.dragonsTurn();
			game.resolutionPhase();
		}
		
		assertEquals(true, game.isFinished());	
		assertEquals(true, game.isWon());

	}
	
	
	public void testReachExitFailed(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,4));
		Hero hero = new Hero(new Position(1,3));
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Weapon sword = new Weapon(Weapon.Type.SWORD, new Position(1,2));
		weapons.add(sword);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), false, false, false));
		
		Game game = new Game(map, hero,dragons,weapons);
		
		assertEquals(false, game.getBoard().getDragons().isEmpty());

		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.RIGHT));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(new Position(1,3), game.getBoard().getHero().getPosition());
		assertEquals(false, game.isFinished());	
		assertEquals(false, game.isWon());
	}
	
	public void testKilledByFlamesRight(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,3));
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,1), false, false, true));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.isFinished());
		
		game.heroTurn(new Game.Command(Game.Action.STOP, null));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.isFinished());	
		assertEquals(false, game.isWon());
	}
	
	public void testKilledByFlamesLeft(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,1));
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,3), false, false, true));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.isFinished());
		
		game.heroTurn(new Game.Command(Game.Action.STOP, null));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.isFinished());	
		assertEquals(false, game.isWon());
	}
	
	public void testKilledByFlamesUp(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(5);
		builder.setCols(3);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,1));
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(3,1), false, false, true));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.isFinished());
		
		game.heroTurn(new Game.Command(Game.Action.STOP, null));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.isFinished());	
		assertEquals(false, game.isWon());
	}
	
	public void testKilledByFlamesDown(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(5);
		builder.setCols(3);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(3,1));
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,1), false, false, true));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.isFinished());
		
		game.heroTurn(new Game.Command(Game.Action.STOP, null));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.isFinished());	
		assertEquals(false, game.isWon());
	}
	
	public void testRandomHorizontalMoveDragon(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(100);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,99));
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,50), true, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		
		Position oldPos = game.getBoard().getDragons().get(0).getPosition();
		boolean hasMoved = false;
		
		
		for(int i = 0; i < 1000; i++){
			game.heroTurn(new Game.Command(Game.Action.STOP, null));
			game.dragonsTurn();
			game.resolutionPhase();
			
			if(!oldPos.equals(game.getBoard().getDragons().get(0).getPosition())){
				hasMoved = true;
				assertEquals(1, game.getBoard().getDragons().get(0).getPosition().getRow());
				assertEquals(1, Math.abs(oldPos.getColumn() - game.getBoard().getDragons().get(0).getPosition().getColumn()));
				oldPos = game.getBoard().getDragons().get(0).getPosition();
			}
		}
		
		assertEquals(true, hasMoved);
	}
	
	
	public void testRandomVerticalMoveDragon(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(100);
		builder.setCols(3);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(99,1));
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(50,1), true, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		
		Position oldPos = game.getBoard().getDragons().get(0).getPosition();
		boolean hasMoved = false;
		
		
		for(int i = 0; i < 1000; i++){
			game.heroTurn(new Game.Command(Game.Action.STOP, null));
			game.dragonsTurn();
			game.resolutionPhase();
			
			if(!oldPos.equals(game.getBoard().getDragons().get(0).getPosition())){
				hasMoved = true;
				assertEquals(1, game.getBoard().getDragons().get(0).getPosition().getColumn());
				assertEquals(1, Math.abs(oldPos.getRow() - game.getBoard().getDragons().get(0).getPosition().getRow()));
				oldPos = game.getBoard().getDragons().get(0).getPosition();
			}
		}
		
		assertEquals(true, hasMoved);
	}
	
	public void testGenerateRandomMap(){
		Game game = new Game(20,30, 5, false, false, false);
		
		assertEquals(20, game.getBoard().getMap().getRows());
		assertEquals(30, game.getBoard().getMap().getCols());
		
		assertEquals(5, game.getBoard().getDragons().size());
		assertEquals(true, game.getBoard().getWeapons().size() >= 2);
		
		
		
		CommandLine cli = new CommandLine();
		
		cli.displayBoard(game.getBoard());
	}
	
	
}
