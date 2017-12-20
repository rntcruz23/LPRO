package windows.drawprompt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;

public class Draw {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Draw window = new Draw();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Draw() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 122, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel drawOffer = new JLabel("Your opponent is offering a draw, do you accept?");
		GridBagConstraints gbc_drawOffer = new GridBagConstraints();
		gbc_drawOffer.gridwidth = 2;
		gbc_drawOffer.insets = new Insets(0, 0, 5, 0);
		gbc_drawOffer.gridx = 2;
		gbc_drawOffer.gridy = 1;
		panel.add(drawOffer, gbc_drawOffer);
		
		JButton btnYes = new JButton("Yes");
		GridBagConstraints gbc_btnYes = new GridBagConstraints();
		gbc_btnYes.insets = new Insets(0, 0, 0, 5);
		gbc_btnYes.gridx = 2;
		gbc_btnYes.gridy = 3;
		panel.add(btnYes, gbc_btnYes);
		
		JButton btnNo = new JButton("No");
		GridBagConstraints gbc_btnNo = new GridBagConstraints();
		gbc_btnNo.gridx = 3;
		gbc_btnNo.gridy = 3;
		panel.add(btnNo, gbc_btnNo);
	}

}
