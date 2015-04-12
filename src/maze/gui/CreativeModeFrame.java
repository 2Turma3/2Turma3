package maze.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;

import maze.logic.CreativeMode;
import maze.logic.GameBoard;

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
					CreativeModeFrame frame = new CreativeModeFrame(null, new MainMenuPanel.Options());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void mousePressed(int x, int y){
		
	}

	
	/**
	 * Create the frame.
	 */
	public CreativeModeFrame(JFrame parentFrame, MainMenuPanel.Options options) {
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
		
		JButton btnReset = new JButton("Reset");
		buttonsPane.add(btnReset);
		
		JButton btnExit = new JButton("Exit");
		buttonsPane.add(btnExit);
		
		mapPanel = new JPanel();
		contentPane.add(mapPanel, BorderLayout.CENTER);
		mapPanel.setPreferredSize(new Dimension( MazeImage.CELL_WIDTH*options.getCols(),  MazeImage.CELL_HEIGHT*options.getRows()));
		mapPanel.addMouseListener(new MouseListener(){
			
			boolean isOnPanel = false;
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				isOnPanel = true;
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				isOnPanel = false;
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if(isOnPanel)
					;
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
		
		JRadioButton rdbtnFloor = new JRadioButton(Element.FLOOR.toString());
		selectionPane.add(rdbtnFloor);
		buttonGroup.add(rdbtnFloor);
		
		JRadioButton rdbtnExit = new JRadioButton(Element.EXIT.toString());
		selectionPane.add(rdbtnExit);
		buttonGroup.add(rdbtnExit);
		
		JRadioButton rdbtnHero = new JRadioButton(Element.HERO.toString());
		selectionPane.add(rdbtnHero);
		buttonGroup.add(rdbtnHero);
		
		JRadioButton rdbtnDragon = new JRadioButton(Element.DRAGON.toString());
		selectionPane.add(rdbtnDragon);
		buttonGroup.add(rdbtnDragon);
		
		JRadioButton rdbtnSword = new JRadioButton(Element.SWORD.toString());
		selectionPane.add(rdbtnSword);
		buttonGroup.add(rdbtnSword);
		
		JRadioButton rdbtnShield = new JRadioButton(Element.SHIELD.toString());
		selectionPane.add(rdbtnShield);
		buttonGroup.add(rdbtnShield);
		
		JRadioButton rdbtnDart = new JRadioButton(Element.DART.toString());
		selectionPane.add(rdbtnDart);
		buttonGroup.add(rdbtnDart);
		
		buttonGroup.setSelected(rdbtnWall.getModel(), true);
			
		
	}

}
