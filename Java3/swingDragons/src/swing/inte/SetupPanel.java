package swing.inte;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetupPanel extends JPanel {
	JTextField dragonsField;
	JLabel dragonsLabel;
	JLabel moveLabel;
	JComboBox<String> moveDropDown;
	public SetupPanel() {
		GridBagLayout manager = new GridBagLayout();
		setLayout(new GridBagLayout());
		manager.columnWidths = new int[]{12, 10, 275};
		manager.rowHeights = new int[]{22, 22};
		setLayout(manager);
		setBackground(SystemColor.inactiveCaptionBorder);
		
		GridBagConstraints gb = new GridBagConstraints();
		/////First column/////
		gb.gridx = 0;    
		gb.anchor = GridBagConstraints.LINE_END;
		
		dragonsLabel = new JLabel("Nº dragões: ");
		gb.gridy = 1;
		add(dragonsLabel,gb);
		
		moveLabel = new JLabel("Movimento: ");
		gb.gridy = 2;
		add(moveLabel,gb);
		
		/////Second column/////
		gb.gridx = 1;
		gb.anchor = GridBagConstraints.LINE_START;
		
		dragonsField = new JTextField();
		gb.gridy = 1;
		dragonsField.setColumns(5);
		add(dragonsField,gb);

		gb.gridy = 2;
		moveDropDown = new JComboBox<String>();
		moveDropDown.setModel(new DefaultComboBoxModel<String>(new String[] {"Sleep", "Random"}));
		add(moveDropDown,gb);
	}
}
