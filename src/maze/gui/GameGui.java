package maze.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import maze.cli.CommandLine;
import maze.logic.Game;
import maze.logic.Hero;
import maze.logic.MazeMap;
import maze.logic.UserInterface;

public class GameGui extends JFrame {

	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args ) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GameGui frame = new GameGui();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		
		Game newGame = new Game(10, 10, 0, false, false, false);
		
		UserInterface cli = new CommandLine();
		
		cli.displayMaze(newGame.map);
		
		MazeImage img = new MazeImage(newGame.map);
		img.addEntity(newGame.getHero());
		
		try {
			ImageIO.write( img.getImage(), "png", new File("test.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the frame.
	 */
	public GameGui(Game game) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		
		
		setBounds(100, 100, MazeImage.CELL_WIDTH*game.map.getCols() + 20 , MazeImage.CELL_WIDTH*game.map.getRows()+35);
		contentPane = new JPanel();
		contentPane.setBounds(10,0, MazeImage.CELL_WIDTH*game.map.getCols(), MazeImage.CELL_WIDTH*game.map.getRows());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		JPanel buttonPane = new JPanel();
		//buttonPane.setBounds(10, CELL_WIDTH*game.map.getRows(), 35, CELL_WIDTH*game.map.getCols());
		
		
		JButton btnSaveGame = new JButton("Save Game");
		
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int option = JOptionPane.showConfirmDialog(null,"Caution!","Are you sure you want to save?" , JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		
		

		
		buttonPane.add(btnSaveGame);
		
	}

}
