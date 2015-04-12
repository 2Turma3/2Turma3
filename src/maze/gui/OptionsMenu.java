package maze.gui;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;

import java.awt.GridLayout;

import javax.swing.JSlider;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;
import javax.swing.JTextField;

import maze.gui.MainMenuPanel.Options;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class OptionsMenu extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField rowSelection;
	private JTextField colSelection;
	Options unsavedOptions;
	AssignedKeys unsavedKeys;

	JSlider rowSlider;
	JSlider colSlider;
	JSpinner spinner;
	JCheckBox chckbxDragonMove;
	JCheckBox chckbxDragonSleep;
	JCheckBox chckbxDragonAttack;


	public static void main(String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptionsMenu frame = new OptionsMenu(new MainMenuPanel.Options(), new AssignedKeys());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	private void SaveOptions(Options options, AssignedKeys AS){
		options.setRows(unsavedOptions.getRows());
		options.setCols(unsavedOptions.getCols());
		options.setNumberDragons(unsavedOptions.getNumberDragons());
		options.setDragonMove(unsavedOptions.isDragonMove());
		options.setDragonSleep(unsavedOptions.isDragonSleep());
		options.setDragonAttack(unsavedOptions.isDragonAttack());
		
		
		AS.up = unsavedKeys.up;
		AS.down = unsavedKeys.down;
		AS.left = unsavedKeys.left;
		AS.right = unsavedKeys.right;
		AS.spAttack = unsavedKeys.spAttack;
		AS.skip = unsavedKeys.skip;

		/*System.out.println("Rows: " + options.getRows());
		System.out.println("Cols: " + options.getCols());
		System.out.println("NumberDragons: " + options.getNumberDragons());
		System.out.println("DMove: " + options.isDragonMove());
		System.out.println("DSleep: " + options.isDragonSleep());
		System.out.println("Dattack: " + options.isDragonAttack());*/


	}

	/**
	 * Create the frame.
	 */
	public OptionsMenu(final MainMenuPanel.Options options, final AssignedKeys AS) {
		unsavedOptions = options.clone();
		unsavedKeys = AS;
		setBounds(100, 100, 329, 448);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel optionsPane = new JPanel();
		add(optionsPane);
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

		this.rowSlider = new JSlider();
		panel_1.add(rowSlider);
		rowSlider.setPaintLabels(true);
		rowSlider.setMaximum(20);
		rowSlider.setMinimum(9);
		rowSlider.setValue(unsavedOptions.getRows());
		rowSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				JSlider source = (JSlider) arg0.getSource();
				unsavedOptions.setRows((Integer) source.getValue());
				rowSelection.setText(((Integer) source.getValue()).toString());
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

		this.colSlider = new JSlider();
		panel_2.add(colSlider);
		colSlider.setMaximum(20);
		colSlider.setMinimum(9);
		colSlider.setValue(unsavedOptions.getCols());
		colSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				JSlider source = (JSlider) arg0.getSource();

				unsavedOptions.setCols( ((Integer)source.getValue()).intValue() );
				colSelection.setText(((Integer) source.getValue()).toString());
			}

		});
		colSelection = new JTextField();
		colSelection.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(colSelection);
		colSelection.setEditable(false);
		colSelection.setText(((Integer)unsavedOptions.getCols()).toString());
		colSelection.setColumns(10);

		JPanel dragonNumberPanel = new JPanel();
		optionsPane.add(dragonNumberPanel);
		dragonNumberPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

		JLabel lblNumberOfDragons = new JLabel("Number of Dragons");
		lblNumberOfDragons.setEnabled(false);
		lblNumberOfDragons.setHorizontalAlignment(SwingConstants.CENTER);
		dragonNumberPanel.add(lblNumberOfDragons);

		this.spinner = new JSpinner();
		//TODO Mudar o spinner para mudar o seu máximo consuante as rows e cols
		spinner.setModel(new SpinnerNumberModel(0, 0, ((unsavedOptions.getCols()-3)*(unsavedOptions.getRows()-3)/2), 1));
		spinner.setValue(unsavedOptions.getNumberDragons());
		spinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				JSpinner source = (JSpinner) arg0.getSource();
				unsavedOptions.setNumberDragons(((Integer)source.getValue()).intValue());				
			}

		});
		dragonNumberPanel.add(spinner);

		JPanel dragonAttributesPanel = new JPanel();
		optionsPane.add(dragonAttributesPanel);
		dragonAttributesPanel.setLayout(new GridLayout(3, 0, 0, 0));

		this.chckbxDragonMove = new JCheckBox("Dragons can move");
		dragonAttributesPanel.add(chckbxDragonMove);
		chckbxDragonMove.setSelected(unsavedOptions.isDragonMove());
		chckbxDragonMove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox source = (JCheckBox) arg0.getSource();
				unsavedOptions.setDragonMove(source.isSelected());
			}
		});

		this.chckbxDragonSleep = new JCheckBox("Dragons can sleep");
		dragonAttributesPanel.add(chckbxDragonSleep);
		chckbxDragonSleep.setSelected(unsavedOptions.isDragonSleep());
		chckbxDragonSleep.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox source = (JCheckBox) arg0.getSource();
				unsavedOptions.setDragonSleep(source.isSelected());
			}
		});

		this.chckbxDragonAttack = new JCheckBox("Dragons can attack");
		dragonAttributesPanel.add(chckbxDragonAttack);
		chckbxDragonAttack.setSelected(unsavedOptions.isDragonAttack());
		chckbxDragonAttack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox source = (JCheckBox) arg0.getSource();
				unsavedOptions.setDragonAttack(source.isSelected());
			}
		});

		JPanel assignKeysPanel = new JPanel();
		add(assignKeysPanel);
		assignKeysPanel.setLayout(new GridLayout(6, 2, 0, 2));

		JLabel lblUpKey = new JLabel("Up key");
		assignKeysPanel.add(lblUpKey);
		
				final JButton btnUp = new JButton(KeyEvent.getKeyText(AS.up));
				assignKeysPanel.add(btnUp);
				btnUp.setPreferredSize(new Dimension(50,25));
				btnUp.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						PressKeyWindow pkWindow = new PressKeyWindow(unsavedKeys, AssignedKeys.Key.UP);
						btnUp.setText(KeyEvent.getKeyText(AS.up));
					}
				});

		JLabel lblDownKey = new JLabel("Down key");
		assignKeysPanel.add(lblDownKey);
		
				final JButton btnDown = new JButton(KeyEvent.getKeyText(AS.down));
				assignKeysPanel.add(btnDown);
				btnDown.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						PressKeyWindow pkWindow = new PressKeyWindow(unsavedKeys, AssignedKeys.Key.DOWN);
						btnDown.setText(KeyEvent.getKeyText(AS.down));
					}
				});

		JLabel lblLeftKey = new JLabel("Left key");
		assignKeysPanel.add(lblLeftKey);
		
				final JButton btnLeft = new JButton(KeyEvent.getKeyText(AS.left));
				assignKeysPanel.add(btnLeft);
				btnLeft.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						PressKeyWindow pkWindow = new PressKeyWindow(unsavedKeys, AssignedKeys.Key.LEFT);
						btnLeft.setText(KeyEvent.getKeyText(AS.left));
					}
				});

		JLabel lblRightKey = new JLabel("Right key");
		assignKeysPanel.add(lblRightKey);
		
				final JButton btnRight = new JButton(KeyEvent.getKeyText(AS.right));
				assignKeysPanel.add(btnRight);
				btnRight.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						PressKeyWindow pkWindow = new PressKeyWindow(unsavedKeys, AssignedKeys.Key.RIGHT);
						btnRight.setText(KeyEvent.getKeyText(AS.right));
					}
				});

		JLabel lblSPAtkKey = new JLabel("Special Attack key");
		assignKeysPanel.add(lblSPAtkKey);
		
				final JButton btnSPAtk = new JButton(KeyEvent.getKeyText(AS.spAttack));
				assignKeysPanel.add(btnSPAtk);
				btnSPAtk.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						PressKeyWindow pkWindow = new PressKeyWindow(unsavedKeys, AssignedKeys.Key.ATTACK);
						btnSPAtk.setText(KeyEvent.getKeyText(AS.spAttack));
					}
				});

		JLabel lblSkip = new JLabel("Skip key");
		assignKeysPanel.add(lblSkip);
		
				final JButton btnSkip = new JButton(KeyEvent.getKeyText(AS.skip));
				assignKeysPanel.add(btnSkip);
				btnSkip.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						PressKeyWindow pkWindow = new PressKeyWindow(unsavedKeys, AssignedKeys.Key.SKIP);
						btnSkip.setText(KeyEvent.getKeyText(AS.skip));
					}
				});

		JPanel buttonPane = new JPanel();
		add(buttonPane);
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnSave = new JButton("Save");
		buttonPane.add(btnSave);
		btnSave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.showConfirmDialog(null,"Warning!","Are you sure you want to save this options?" , JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.YES_OPTION){
					SaveOptions(options, AS);
				}
			}

		});
		
		JButton btnDefault = new JButton("Default");
		buttonPane.add(btnDefault);
		btnDefault.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				unsavedOptions = new Options();

				rowSlider.setValue(unsavedOptions.getRows());
				colSlider.setValue(unsavedOptions.getCols());
				spinner.setValue(unsavedOptions.getNumberDragons());
				chckbxDragonMove.setSelected(unsavedOptions.isDragonMove());
				chckbxDragonSleep.setSelected(unsavedOptions.isDragonSleep());
				chckbxDragonAttack.setSelected(unsavedOptions.isDragonAttack());

			}

		});



	}
}
