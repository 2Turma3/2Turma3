package maze.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import java.awt.FlowLayout;

import javax.swing.JCheckBox;

import java.awt.GridLayout;

import javax.swing.JSlider;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JTextField;

import maze.gui.MainMenuPanel.Options;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class OptionsMenu extends JFrame {

	private JPanel contentPane;
	private JTextField rowSelection;
	private JTextField colSelection;
	private boolean changed;
	Options unsavedOptions;

	
	public static void main(String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptionsMenu frame = new OptionsMenu(new MainMenuPanel.Options());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public OptionsMenu(MainMenuPanel.Options options) {
		unsavedOptions = options.clone();
		changed = false;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 329, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Save");
		buttonPane.add(btnSave);
		
		JButton btnDefault = new JButton("Default");
		buttonPane.add(btnDefault);
		
		JPanel optionsPane = new JPanel();
		contentPane.add(optionsPane, BorderLayout.CENTER);
		optionsPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel rowPanel = new JPanel();
		optionsPane.add(rowPanel);
		rowPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		JLabel Rows = new JLabel("Rows");
		Rows.setHorizontalAlignment(SwingConstants.CENTER);
		rowPanel.add(Rows);
		
		JPanel panel_1 = new JPanel();
		rowPanel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JSlider rowSlider = new JSlider();
		panel_1.add(rowSlider);
		rowSlider.setMajorTickSpacing(5);
		rowSlider.setPaintTicks(true);
		rowSlider.setPaintLabels(true);
		rowSlider.setMaximum(30);
		rowSlider.setMinimum(10);
		rowSlider.setValue(unsavedOptions.getRows());
		rowSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				JSlider source = (JSlider) arg0.getSource();
				unsavedOptions.setRows((Integer) source.getValue());
				rowSelection.setText(((Integer) source.getValue()).toString());
				changed = true;
			}
			
		});
		
		rowSelection = new JTextField();
		panel_1.add(rowSelection);
		rowSelection.setHorizontalAlignment(SwingConstants.CENTER);
		rowSelection.setEditable(false);
		rowSelection.setColumns(10);
		rowSelection.setText(((Integer)unsavedOptions.getRows()).toString());

		JPanel ColPanel = new JPanel();
		ColPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		ColPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		optionsPane.add(ColPanel);
		ColPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblCol = new JLabel("Columns");
		lblCol.setHorizontalAlignment(SwingConstants.CENTER);
		lblCol.setAlignmentY(Component.TOP_ALIGNMENT);
		ColPanel.add(lblCol);
		
		JPanel panel_2 = new JPanel();
		ColPanel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JSlider colSlider = new JSlider();
		panel_2.add(colSlider);
		colSlider.setMaximum(20);
		colSlider.setMinimum(7);
		colSlider.setValue(unsavedOptions.getCols());
		colSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				JSlider source = (JSlider) arg0.getSource();
				
				unsavedOptions.setCols( ((Integer)source.getValue()).intValue() );
				colSelection.setText(((Integer) source.getValue()).toString());
				changed = true;
			}
			
		});
		colSelection = new JTextField();
		colSelection.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(colSelection);
		colSelection.setEditable(false);
		colSelection.setText(((Integer)unsavedOptions.getCols()).toString());
		colSelection.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		optionsPane.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNumberOfDragons = new JLabel("Number of Dragons");
		lblNumberOfDragons.setEnabled(false);
		lblNumberOfDragons.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblNumberOfDragons);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, (unsavedOptions.getCols()*unsavedOptions.getRows()/2) - 3, 1));
		spinner.setValue(unsavedOptions.getNumberDragons());
		spinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				JSpinner source = (JSpinner) arg0.getSource();
				unsavedOptions.setNumberDragons(((Integer)source.getValue()).intValue());
				changed = true;				
			}
			
		});
		panel_3.add(spinner);
		
		JPanel panel = new JPanel();
		optionsPane.add(panel);
		panel.setLayout(new GridLayout(3, 0, 0, 0));
		
		JCheckBox chckbxDragonMove = new JCheckBox("Dragons can move");
		panel.add(chckbxDragonMove);
		chckbxDragonMove.setSelected(unsavedOptions.isDragonMove());
		chckbxDragonMove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox source = (JCheckBox) arg0.getSource();
				unsavedOptions.setDragonMove(source.isSelected());
				changed = true;
			}
		});
		
		JCheckBox chckbxDragonSleep = new JCheckBox("Dragons can asleep");
		panel.add(chckbxDragonSleep);
		chckbxDragonSleep.setSelected(unsavedOptions.isDragonSleep());
		chckbxDragonSleep.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox source = (JCheckBox) arg0.getSource();
				unsavedOptions.setDragonSleep(source.isSelected());
				changed = true;
			}
		});
		
		JCheckBox chckbxDragonAttack = new JCheckBox("Dragons can attack");
		panel.add(chckbxDragonAttack);
		chckbxDragonAttack.setSelected(unsavedOptions.isDragonAttack());
		chckbxDragonAttack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox source = (JCheckBox) arg0.getSource();
				unsavedOptions.setDragonAttack(source.isSelected());
				changed = true;
			}
		});
	}
}
