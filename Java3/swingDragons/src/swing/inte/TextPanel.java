package swing.inte;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextPanel extends JPanel {
	JLabel status;
	public TextPanel() {
		//////Texto Auxiliar//////
		status = new JLabel("Welcome!");
		status.setFont(new Font("Tahoma", Font.PLAIN, 25));
		setBackground(SystemColor.inactiveCaptionBorder);
		
		GridBagLayout manager = new GridBagLayout();
		manager.rowHeights = new int[]{100,50};
		manager.columnWidths = new int[] {275,275};
		setLayout(manager);
		
		GridBagConstraints gb = new GridBagConstraints();
		
		gb.anchor = GridBagConstraints.NORTHWEST;
		gb.fill = GridBagConstraints.NONE;
		gb.weightx = 0;
		gb.weighty = 0;
		
		gb.gridy = 0;
		gb.gridx = 0;
		add(status,gb);
	}
}
