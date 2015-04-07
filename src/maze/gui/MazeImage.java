package maze.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import maze.logic.*;


public class MazeImage {
	
	public static final int CELL_HEIGHT = 50;
	public static final int CELL_WIDTH = 50;
	
	private static BufferedImage wallTile;
	private static BufferedImage floorTile;
	private static BufferedImage heroSprite;
	private static BufferedImage dragonSprite;
	private static BufferedImage sleepingDragonSprite;
	private static BufferedImage swordSprite;
	private static BufferedImage shieldSprite;
	private static BufferedImage dartSprite;
	private static BufferedImage closedExit;
	private static BufferedImage openExit;
	
	private Position exitPosition;
	
	private BufferedImage exit;
	private BufferedImage mazeTiles;
	private BufferedImage mazeImage;
	
	static {
		try {
			wallTile = new BufferedImage(CELL_WIDTH, CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			wallTile.getGraphics().drawImage(ImageIO.read(new File("src1/images/Mossy_Wall.png")), 0, 0, wallTile.getWidth(), wallTile.getHeight(), null);			
			
			floorTile = new BufferedImage(CELL_WIDTH, CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			floorTile.getGraphics().drawImage(ImageIO.read(new File("src1/images/Stone_Wall.jpg")), 0, 0, floorTile.getWidth(), floorTile.getHeight(), null);
			
			heroSprite = new BufferedImage(CELL_WIDTH, CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			heroSprite.getGraphics().drawImage(ImageIO.read(new File("src1/images/hero.png")), 0, 0, heroSprite.getWidth(), heroSprite.getHeight(), null);
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public MazeImage(MazeMap map) {
		this.mazeTiles = mazeToImage(map);
		this.mazeImage = this.mazeTiles;
	}
	
	public void clear() {
		this.mazeImage = this.mazeTiles;
	}
	
	public BufferedImage getImage() {
		return mazeImage;
	}
	
	public void addEntity(Entity entity) {
		BufferedImage img = null;
		
		if (entity instanceof Hero)
			img = heroSprite;
		
		if (img == null)
			return;
		
		mazeImage.getGraphics().drawImage(img, entity.getPos().getCol() * CELL_WIDTH, entity.getPos().getRow() * CELL_HEIGHT, img.getWidth(), img.getHeight(), null);
	}
	
	public void openExit() {
		this.exit = openExit;
		
	}
	
	public void closeExit() {
		this.exit = closedExit;
	}
	
	private BufferedImage mazeToImage(MazeMap map) {
		BufferedImage image = new BufferedImage(map.getCols()*CELL_WIDTH, map.getRows()*CELL_WIDTH, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = image.createGraphics();
		
		for(int row = 0; row < map.getRows(); ++row)
			for(int col = 0; col < map.getCols(); ++col){
				if(map.isWalkable(row, col))
					g.drawImage(floorTile, col*CELL_WIDTH, row*CELL_HEIGHT,null);
				else
					g.drawImage(wallTile, col*CELL_WIDTH, row*CELL_HEIGHT,null);
			}
		return image;
	}

}
