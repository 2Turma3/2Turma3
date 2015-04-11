package maze.test;

import static org.junit.Assert.*;

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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,2),true);
		
		Game game = new Game(map, hero,new LinkedList<Dragon>(), new LinkedList<Weapon>());
		
		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.RIGHT));
		//cli.displayMaze(game.map);
		
		assertEquals( new Position(1,3), game.getBoard().getHero().getPos());
	}

	public void testFalseMove(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		
		Game game = new Game(map, hero,new LinkedList<Dragon>(), new LinkedList<Weapon>());
		
		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.RIGHT));
		//cli.displayMaze(game.map);
		
		assertEquals( new Position(1,3), game.getBoard().getHero().getPos());
	}
	
	public void testCatchSword(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Weapon sword = new Weapon(Weapon.Type.SWORD, new Position(1,2), true);
		weapons.add(sword);
		
		Game game = new Game(map, hero,new LinkedList<Dragon>(),weapons);
		
		assertEquals(false, game.getBoard().getHero().hasSword());
	//	cli.displayMaze(game.getBoard().getMap());
		
		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.LEFT));
		game.dragonsTurn();
		game.resolutionPhase();
		//cli.displayMaze(game.map);
		
		assertEquals(true, game.getBoard().getHero().hasSword());	
	}
	
	
	public void testKilledByDragon(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		Hero hero = new Hero(new Position(1,3),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		
		Weapon sword = new Weapon(Weapon.Type.SWORD, null, true);
		hero.addWeapon(sword);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,98),true);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, true, false));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,198),true);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,45), true, true, false, false));
		dragons.add(new Dragon(new Position(1,46), true, true, false, false));
		dragons.add(new Dragon(new Position(1,47), true, true, false, false));
		dragons.add(new Dragon(new Position(1,48), true, true, false, false));
		dragons.add(new Dragon(new Position(1,49), true, true, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		boolean dragonInSamePosition = false;
		
		for(int i = 0; i < 1000; i++){
			game.heroTurn(new Game.Command(Game.Action.STOP, null));
			game.dragonsTurn();
			game.resolutionPhase();
			
			for(Dragon dragon1 : game.getBoard().getDragons()){
				for (Dragon dragon2 : game.getBoard().getDragons())
					if (dragon1 != dragon2 && dragon1.getPos().equals(dragon2.getPos()))
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		
		Weapon dart = new Weapon(Weapon.Type.DART, null, true);
		hero.addWeapon(dart);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,1),true);
		
		Weapon dart = new Weapon(Weapon.Type.DART, null, true);
		hero.addWeapon(dart);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,4), true, false, false, false));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(3,1),true);
		
		Weapon dart = new Weapon(Weapon.Type.DART, null, true);
		hero.addWeapon(dart);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,1),true);
		
		Weapon dart = new Weapon(Weapon.Type.DART, null, true);
		hero.addWeapon(dart);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(3,1), true, false, false, false));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Weapon sword = new Weapon(Weapon.Type.SWORD, new Position(1,2), true);
		weapons.add(sword);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		LinkedList<Weapon> weapons = new LinkedList<Weapon>();
		Weapon sword = new Weapon(Weapon.Type.SWORD, new Position(1,2), true);
		weapons.add(sword);
		
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		dragons.add(new Dragon(new Position(1,1), true, false, false, false));
		
		Game game = new Game(map, hero,dragons,weapons);
		
		assertEquals(false, game.getBoard().getDragons().isEmpty());

		game.heroTurn(new Game.Command(Game.Action.MOVE, Game.Direction.RIGHT));
		game.dragonsTurn();
		game.resolutionPhase();
		
		assertEquals(new Position(1,3), game.getBoard().getHero().getPos());
		assertEquals(false, game.isFinished());	
		assertEquals(false, game.isWon());
	}
	
	public void testKilledByFlamesRight(){
		MazeMap.Builder builder = new MazeMap.Builder();
		builder.setRows(3);
		builder.setCols(5);
		MazeMap map = builder.build();
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,1), true, false, false, true));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,1),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,3), true, false, false, true));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,1),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(3,1), true, false, false, true));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(3,1),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,1), true, false, false, true));
		
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,99),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(1,50), true, true, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		
		Position oldPos = game.getBoard().getDragons().get(0).getPos();
		boolean hasMoved = false;
		
		
		for(int i = 0; i < 1000; i++){
			game.heroTurn(new Game.Command(Game.Action.STOP, null));
			game.dragonsTurn();
			game.resolutionPhase();
			
			if(!oldPos.equals(game.getBoard().getDragons().get(0).getPos())){
				hasMoved = true;
				assertEquals(1, game.getBoard().getDragons().get(0).getPos().getRow());
				assertEquals(1, Math.abs(oldPos.getCol() - game.getBoard().getDragons().get(0).getPos().getCol()));
				oldPos = game.getBoard().getDragons().get(0).getPos();
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
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(99,1),true);
		LinkedList<Dragon> dragons = new LinkedList<Dragon>();
		
		dragons.add(new Dragon(new Position(50,1), true, true, false, false));
		
		Game game = new Game(map, hero,dragons,new LinkedList<Weapon>());
		
		
		Position oldPos = game.getBoard().getDragons().get(0).getPos();
		boolean hasMoved = false;
		
		
		for(int i = 0; i < 1000; i++){
			game.heroTurn(new Game.Command(Game.Action.STOP, null));
			game.dragonsTurn();
			game.resolutionPhase();
			
			if(!oldPos.equals(game.getBoard().getDragons().get(0).getPos())){
				hasMoved = true;
				assertEquals(1, game.getBoard().getDragons().get(0).getPos().getCol());
				assertEquals(1, Math.abs(oldPos.getRow() - game.getBoard().getDragons().get(0).getPos().getRow()));
				oldPos = game.getBoard().getDragons().get(0).getPos();
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
		
		
		
		UserInterface cli = new CommandLine();
		
		cli.displayBoard(game.getBoard());
	}
	
	
}
