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
		cli.displayMaze(game.map);
		
		assertEquals( new Position(1,3), game.getHeroPos());
	}

	public void testFalseMove(){
		MazeMap map = new MazeMap(3,5);
		map.setExit(new Position(1,0));
		UserInterface cli = new CommandLine();
		Hero hero = new Hero(new Position(1,3),true);
		
		Game game = new Game(map, hero,new LinkedList<Dragon>(), new LinkedList<Weapon>());
		
		game.heroTurn(Game.Action.MOVE, Game.Direction.RIGHT);
		cli.displayMaze(game.map);
		
		assertEquals( new Position(1,3), game.getHeroPos());
	}
	
}
