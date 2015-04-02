package maze.test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import junit.framework.TestCase;
import maze.cli.CommandLine;
import maze.logic.*;

import org.junit.Test;

public class TestMovement extends TestCase {

	public void testMove(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,2),true);
		
		Game game = new Game(map, hero,new LinkedList<Dragon>(), new LinkedList<Weapon>());
		
		game.heroTurn(Game.Action.MOVE, Game.Direction.RIGHT);
		//cli.displayMaze(game.map);
		
		assertEquals( new Position(1,3), game.getHeroPos());
	}

	public void testFalseMove(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		
		Game game = new Game(map, hero,new LinkedList<Dragon>(), new LinkedList<Weapon>());
		
		game.heroTurn(Game.Action.MOVE, Game.Direction.RIGHT);
		//cli.displayMaze(game.map);
		
		assertEquals( new Position(1,3), game.getHeroPos());
	}
	
	public void testCatchSword(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Weapon sword = new Weapon(Weapon.Type.SWORD, new Position(1,2), true);
		weapons.add(sword);
		
		Game game = new Game(map, hero,new LinkedList<Dragon>(),weapons);
		
		assertEquals(false, game.hero.hasSword());
		//cli.displayMaze(game.map);
		
		game.heroTurn(Game.Action.MOVE, Game.Direction.LEFT);
		game.dragonsTurn();
		game.resolutionPhase();
		//cli.displayMaze(game.map);
		
		assertEquals(true, game.hero.hasSword());	
	}
	
	
	public void testKilledByDragon(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.isGameOver());
		
		game.heroTurn(Game.Action.MOVE, Game.Direction.LEFT);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.isGameOver());	
		assertEquals(false, game.isWon());
	}
	
	public void testKillDragon(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		
		Weapon sword = new Weapon(Weapon.Type.SWORD, new Position(1,2), true);
		hero.addWeapon(sword);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.dragons.isEmpty());
		
		game.heroTurn(Game.Action.MOVE, Game.Direction.LEFT);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.dragons.isEmpty());
	}
	
	public void testThrowDartLeft(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		
		Weapon dart = new Weapon(Weapon.Type.DART, null, true);
		hero.addWeapon(dart);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.dragons.isEmpty());
		
		game.heroTurn(Game.Action.ATTACK, Game.Direction.LEFT);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.dragons.isEmpty());
	}
	
	public void testThrowDartRight(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,1),true);
		
		Weapon dart = new Weapon(Weapon.Type.DART, null, true);
		hero.addWeapon(dart);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,4), true, false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.dragons.isEmpty());
		
		game.heroTurn(Game.Action.ATTACK, Game.Direction.RIGHT);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.dragons.isEmpty());
	}
	
	public void testThrowDartUp(){
		MazeMap map = new MazeMap(5,3);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(3,1),true);
		
		Weapon dart = new Weapon(Weapon.Type.DART, null, true);
		hero.addWeapon(dart);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.dragons.isEmpty());
		
		game.heroTurn(Game.Action.ATTACK, Game.Direction.UP);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.dragons.isEmpty());
	}
	
	public void testThrowDartDown(){
		MazeMap map = new MazeMap(5,3);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,1),true);
		
		Weapon dart = new Weapon(Weapon.Type.DART, null, true);
		hero.addWeapon(dart);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(3,1), true, false, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.dragons.isEmpty());
		
		game.heroTurn(Game.Action.ATTACK, Game.Direction.DOWN);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.dragons.isEmpty());
	}
	
	public void testWinGame(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Weapon sword = new Weapon(Weapon.Type.SWORD, new Position(1,2), true);
		weapons.add(sword);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
		Game game = new Game(map, hero,dragons,weapons);
		
		game.heroTurn(Game.Action.MOVE, Game.Direction.LEFT);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.dragons.isEmpty());
		
		for(int i = 0; i < 2; i++){
			game.heroTurn(Game.Action.MOVE, Game.Direction.LEFT);
			game.dragonsTurn();
			game.resolutionPhase();
		}
		
		assertEquals(true, game.isGameOver());	
		assertEquals(true, game.isWon());

	}
	
	
	public void testReachExitFailed(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,4));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Weapon sword = new Weapon(Weapon.Type.SWORD, new Position(1,2), true);
		weapons.add(sword);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
		Game game = new Game(map, hero,dragons,weapons);
		
		assertEquals(false, game.dragons.isEmpty());

		game.heroTurn(Game.Action.MOVE, Game.Direction.RIGHT);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(new Position(1,3), game.getHeroPos());
		assertEquals(false, game.isGameOver());	
		assertEquals(false, game.isWon());
	}
	
	public void testKilledByFlamesRight(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,1), true, false, false, true));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.isGameOver());
		
		game.heroTurn(Game.Action.STOP, null);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.isGameOver());	
		assertEquals(false, game.isWon());
	}
	
	public void testKilledByFlamesLeft(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,1),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,3), true, false, false, true));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.isGameOver());
		
		game.heroTurn(Game.Action.STOP, null);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.isGameOver());	
		assertEquals(false, game.isWon());
	}
	
	public void testKilledByFlamesUp(){
		MazeMap map = new MazeMap(5,3);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,1),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(3,1), true, false, false, true));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.isGameOver());
		
		game.heroTurn(Game.Action.STOP, null);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.isGameOver());	
		assertEquals(false, game.isWon());
	}
	
	public void testKilledByFlamesDown(){
		MazeMap map = new MazeMap(5,3);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(3,1),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,1), true, false, false, true));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		assertEquals(false, game.isGameOver());
		
		game.heroTurn(Game.Action.STOP, null);
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(true, game.isGameOver());	
		assertEquals(false, game.isWon());
	}
	
	public void testRandomHorizontalMoveDragon(){
		MazeMap map = new MazeMap(3,100);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,99),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,50), true, true, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		
		Position oldPos = game.dragons.get(0).getPos();
		boolean hasMoved = false;
		
		
		for(int i = 0; i < 1000; i++){
			game.heroTurn(Game.Action.STOP, null);
			game.dragonsTurn();
			game.resolutionPhase();
			
			if(!oldPos.equals(game.dragons.get(0).getPos())){
				hasMoved = true;
				assertEquals(1, game.dragons.get(0).getPos().getRow());
				assertEquals(1, Math.abs(oldPos.getCol() - game.dragons.get(0).getPos().getCol()));
				oldPos = game.dragons.get(0).getPos();
			}
		}
		
		assertEquals(true, hasMoved);
	}
	
	
	public void testRandomVerticalMoveDragon(){
		MazeMap map = new MazeMap(100,3);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(99,1),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(50,1), true, true, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		
		Position oldPos = game.dragons.get(0).getPos();
		boolean hasMoved = false;
		
		
		for(int i = 0; i < 1000; i++){
			game.heroTurn(Game.Action.STOP, null);
			game.dragonsTurn();
			game.resolutionPhase();
			
			if(!oldPos.equals(game.dragons.get(0).getPos())){
				hasMoved = true;
				assertEquals(1, game.dragons.get(0).getPos().getCol());
				assertEquals(1, Math.abs(oldPos.getRow() - game.dragons.get(0).getPos().getRow()));
				oldPos = game.dragons.get(0).getPos();
			}
		}
		
		assertEquals(true, hasMoved);
	}
	
	public void testGenerateRandomMap(){
		Game game = new Game(20,20, 5, false, false, false);
		UserInterface cli = new CommandLine();
		
		cli.displayMaze(game.map);
	}
	
	
}
