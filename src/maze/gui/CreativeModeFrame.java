package maze.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;

import maze.logic.CreativeMode;
import maze.logic.Game;
import maze.logic.Position;
import maze.logic.Weapon;

public class CreativeModeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel mapPanel;
	private CreativeMode creative;
	private ButtonGroup buttonGroup;

	
	private enum Element{WALL, FLOOR, EXIT, HERO, DRAGON, SWORD, SHIELD, DART};
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreativeModeFrame frame = new CreativeModeFrame(null, new MainMenuPanel.Options(), new AssignedKeys());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void mousePressed(int x, int y){
		int row = (int) (y*((double)creative.getBoard().getMap().getRows()) / mapPanel.getHeight()) ;
		int col = (int) (x * ((double) creative.getBoard().getMap().getCols()) / mapPanel.getWidth());
		
		String selectedElem = buttonGroup.getSelection().getActionCommand();
		Position pos = new Position(row, col);
		
		
		if(selectedElem.equals(Element.WALL.toString()))
			creative.placeWall(pos);
		else if(selectedElem.equals(Element.FLOOR.toString()))
			creative.placeFloor(pos);
		else if(selectedElem.equals(Element.EXIT.toString()))
			creative.setExit(pos);
		else if(selectedElem.equals(Element.HERO.toString()))
			creative.placeHero(pos);
		else if(selectedElem.equals(Element.DRAGON.toString()))
			creative.placeDragon(pos);
		else if(selectedElem.equals(Element.SWORD.toString()))
			creative.placeWeapon(pos, Weapon.Type.SWORD);
		else if(selectedElem.equals(Element.SHIELD.toString()))
			creative.placeWeapon(pos, Weapon.Type.SHIELD);
		else if(selectedElem.equals(Element.DART.toString()))
			creative.placeWeapon(pos, Weapon.Type.DART);
		repaint();
	}

	
	/**
	 * Create the frame.
	 */
	public CreativeModeFrame(final JFrame parentFrame, final MainMenuPanel.Options options, final AssignedKeys keys) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, MazeImage.CELL_WIDTH*options.getCols() + 4*20 , MazeImage.CELL_HEIGHT*options.getRows() + 35);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.creative = new CreativeMode(options.getRows(), options.getCols());
		
		JPanel buttonsPane = new JPanel();
		contentPane.add(buttonsPane, BorderLayout.SOUTH);
		
		JButton btnPlay = new JButton("Play");
		buttonsPane.add(btnPlay);
		btnPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Game game = new Game(creative.getBoard(), options.isDragonMove(),options.isDragonSleep(),options.isDragonAttack());
				GameGui gameGuiFrame = new GameGui(parentFrame,game,keys);
				gameGuiFrame.setVisible(true);
				CreativeModeFrame.this.setVisible(false);
			}

		});
		
		JButton btnReset = new JButton("Reset");
		buttonsPane.add(btnReset);
		btnReset.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				creative = new CreativeMode(options.getRows(),options.getCols());
				repaint();
			}
			
		});
		
		JButton btnBack = new JButton("Back");
		buttonsPane.add(btnBack);
		btnBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CreativeModeFrame.this.setVisible(false);
				parentFrame.setVisible(true);
			}
			
		});
		
		mapPanel = new JPanel(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			protected void paintComponent(Graphics g){
				MazeImage map = new MazeImage(creative.getBoard().getMap());
				map.updateEntities(creative.getBoard());
				BufferedImage img = map.getImage();
				g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
				System.out.println("Painted");
			}
			
		};
		contentPane.add(mapPanel, BorderLayout.CENTER);
		mapPanel.setPreferredSize(new Dimension( MazeImage.CELL_WIDTH*options.getCols(),  MazeImage.CELL_HEIGHT*options.getRows()));
		mapPanel.addMouseListener(new MouseAdapter(){			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//CreativeModeFrame.this.mousePressed(arg0.getX(), arg0.getY());
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				CreativeModeFrame.this.mousePressed(arg0.getX(), arg0.getY());
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JPanel selectionPane = new JPanel();
		contentPane.add(selectionPane, BorderLayout.EAST);
		selectionPane.setLayout(new BoxLayout(selectionPane, BoxLayout.Y_AXIS));
		
		buttonGroup = new ButtonGroup();
		
		JRadioButton rdbtnWall = new JRadioButton(Element.WALL.toString());
		selectionPane.add(rdbtnWall);
		buttonGroup.add(rdbtnWall);
		rdbtnWall.setActionCommand(rdbtnWall.getText());
		
		JRadioButton rdbtnFloor = new JRadioButton(Element.FLOOR.toString());
		selectionPane.add(rdbtnFloor);
		buttonGroup.add(rdbtnFloor);
		rdbtnFloor.setActionCommand(rdbtnFloor.getText());
		
		JRadioButton rdbtnExit = new JRadioButton(Element.EXIT.toString());
		selectionPane.add(rdbtnExit);
		buttonGroup.add(rdbtnExit);
		rdbtnExit.setActionCommand(rdbtnExit.getText());
		
		JRadioButton rdbtnHero = new JRadioButton(Element.HERO.toString());
		selectionPane.add(rdbtnHero);
		buttonGroup.add(rdbtnHero);
		rdbtnHero.setActionCommand(rdbtnHero.getText());
		
		JRadioButton rdbtnDragon = new JRadioButton(Element.DRAGON.toString());
		selectionPane.add(rdbtnDragon);
		buttonGroup.add(rdbtnDragon);
		rdbtnDragon.setActionCommand(rdbtnDragon.getText());
		
		JRadioButton rdbtnSword = new JRadioButton(Element.SWORD.toString());
		selectionPane.add(rdbtnSword);
		buttonGroup.add(rdbtnSword);
		rdbtnSword.setActionCommand(rdbtnSword.getText());
		
		JRadioButton rdbtnShield = new JRadioButton(Element.SHIELD.toString());
		selectionPane.add(rdbtnShield);
		buttonGroup.add(rdbtnShield);
		rdbtnShield.setActionCommand(rdbtnShield.getText());
		
		JRadioButton rdbtnDart = new JRadioButton(Element.DART.toString());
		selectionPane.add(rdbtnDart);
		buttonGroup.add(rdbtnDart);
		rdbtnDart.setActionCommand(rdbtnDart.getText());
		
		buttonGroup.setSelected(rdbtnWall.getModel(), true);
			
		
	}

}
