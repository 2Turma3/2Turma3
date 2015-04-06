package maze.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.miginfocom.swing.MigLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuPanel extends JPanel {

	BufferedImage background;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null); // see javadoc for more info on the parameters            
	}

	public MainMenuPanel() {
		
		try{
			background = ImageIO.read(new File("src1/images/Main Menu Background.png"));
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		repaint();
		setLayout(new BorderLayout(0, 0));
		this.setSize(background.getWidth()/4, background.getHeight());
		
		setOpaque(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBounds(3*background.getWidth()/4, 0, background.getWidth()/4, background.getHeight());
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		panel_1.setBorder(new EmptyBorder(300,0,100,60));
		add(panel_1, BorderLayout.EAST);
		
		
		JButton btnNewGame = new JButton("New Game");
		
		panel_1.add(btnNewGame);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
				
		JButton btnSaveGame = new JButton("Save Game");
		panel_1.add(btnSaveGame);
		
		JButton btnLoadGame = new JButton("Load Game");
		panel_1.add(btnLoadGame);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel_1.add(btnQuit);
		
		
	}
}
