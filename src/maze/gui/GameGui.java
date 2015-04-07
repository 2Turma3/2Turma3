package maze.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import maze.logic.Game;

public class GameGui extends JFrame {

	private JPanel contentPane;
	private static final int CELL_HEIGHT = 50;
	private static final int CELL_WIDTH = 50;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGui frame = new GameGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public GameGui(Game game) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		
		
		setBounds(100, 100, CELL_WIDTH*game.map.getCols() + 20 , CELL_WIDTH*game.map.getRows()+35);
		contentPane = new JPanel();
		contentPane.setBounds(10,0, CELL_WIDTH*game.map.getCols(), CELL_WIDTH*game.map.getRows());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(10, CELL_WIDTH*game.map.getRows(), 35, CELL_WIDTH*game.map.getCols());
		
		
		JButton btnSaveGame = new JButton("Save Game");
		
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int option = JOptionPane.showConfirmDialog(null,"Are you sure!","Are you sure you want to exit?" , JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		buttonPane.add(btnSaveGame);
		
	}

}
