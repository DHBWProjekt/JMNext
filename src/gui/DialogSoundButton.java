package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import lib.Browse;

public class DialogSoundButton extends JDialog {
	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints c = new GridBagConstraints();
	private JLabel lbName = new JLabel("Button Name:");
	private JTextField tfName;
	private JTextField tfPath;
	private JPanel pnlDialog = new JPanel();
	private JButton btnAbbrechen = new JButton("Abbrechen");
	private JPanel pnlSouth = new JPanel();
	private JPanel pnlPathAuswahl = new JPanel();
	private JButton btnAnwenden = new JButton("Anwenden");
	private SoundButton soundButton;
	private JButton btnBrowse = new JButton("Durchsuchen");
	private JButton btnButtonFarbe = new JButton("Buttonfarbe auswählen");
	private JButton btnTextFarbe = new JButton("Textfarbe auswählen");
	private JLabel lbFarbe = new JLabel("Farbe: ");
	private btnListener bl = new btnListener();
	private File musicPath;
	private File[] musicFileArray;
	private JSlider volumeRegler;
	private JCheckBox cbLoop;
	private JCheckBox cbRepeat;
	private JCheckBox dedicatePlayer;

	public DialogSoundButton(SoundButton sb) {
		this.soundButton = sb;
		musicPath = sb.getMusicPath();
		if (sb.getButtonArt() == 0) {
			System.out.println("Soundbutton");
		} else if (sb.getButtonArt() == 1) {
			System.out.println("Shufflebutton");
		}
		pnlDialog.setLayout(gbl);
		pnlDialog.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10,
				10, 10));

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.ipadx = 20;
		c.ipady = 20;
		c.gridy = 0;
		c.gridx = 0;
		pnlDialog.add(lbName, c);

		c.weightx = 0.1;
		c.weighty = 0;
		c.ipadx = 10;
		c.ipady = 12;
		c.gridy = 0;
		c.gridx = 1;
		tfName = new JTextField(sb.getName());
		tfName.addActionListener(bl);
		pnlDialog.add(tfName, c);

		c.weightx = 0;
		c.weighty = 0;
		c.ipadx = 20;
		c.ipady = 20;
		c.gridy = 1;
		c.gridx = 0;
		pnlDialog.add(lbFarbe, c);

		c.weightx = 0.1;
		c.weighty = 0;
		c.ipadx = 5;
		c.ipady = 5;
		c.gridy = 1;
		c.gridx = 1;
		btnButtonFarbe.addActionListener(bl);
		btnButtonFarbe.setBackground(sb.getBackground());
		btnButtonFarbe.setForeground(sb.getForegroundColorStandard());
		pnlDialog.add(btnButtonFarbe, c);

		c.weightx = 0;
		c.weighty = 0;
		c.ipadx = 20;
		c.ipady = 20;
		c.gridy = 2;
		c.gridx = 0;
		pnlDialog.add(new JLabel("Textfarbe: "), c);

		c.weightx = 0.1;
		c.weighty = 0;
		c.ipadx = 5;
		c.ipady = 5;
		c.gridy = 2;
		c.gridx = 1;
		btnTextFarbe.addActionListener(bl);
		btnTextFarbe.setBackground(sb.getBackground());
		btnTextFarbe.setForeground(sb.getForegroundColorStandard());
		pnlDialog.add(btnTextFarbe, c);

		c.weightx = 0;
		c.weighty = 0;
		c.ipadx = 20;
		c.ipady = 20;
		c.gridy = 3;
		c.gridx = 0;
		pnlDialog.add(new JLabel("Pfad: "), c);

		if (sb.getMusicPath() == null) {
			tfPath = new JTextField();
		} else {
			tfPath = new JTextField(sb.getMusicPathString());
		}
		pnlPathAuswahl.setLayout(new GridBagLayout());

		c.weightx = 0.1;
		c.weighty = 0;
		c.ipadx = 10;
		c.ipady = 12;
		c.gridy = 0;
		c.gridx = 0;
		pnlPathAuswahl.add(tfPath, c);

		c.weightx = 0;
		c.weighty = 0;
		c.ipadx = 5;
		c.ipady = 5;
		c.gridy = 0;
		c.gridx = 1;
		btnBrowse.addActionListener(bl);
		pnlPathAuswahl.add(btnBrowse, c);

		c.weightx = 0.1;
		c.weighty = 0;
		c.ipadx = 5;
		c.ipady = 5;
		c.gridy = 3;
		c.gridx = 1;
		pnlDialog.add(pnlPathAuswahl, c);

		c.weightx = 0;
		c.weighty = 0;
		c.ipadx = 20;
		c.ipady = 20;
		c.gridy = 4;
		c.gridx = 0;
		pnlDialog.add(new JLabel("Lautstärke:"), c);

		c.weightx = 0.1;
		c.weighty = 0;
		c.ipadx = 5;
		c.ipady = 5;
		c.gridy = 4;
		c.gridx = 1;
		volumeRegler = new JSlider(0, 100);
		volumeRegler.setMajorTickSpacing(20);
		volumeRegler.setMinorTickSpacing(1);
		volumeRegler.setPaintLabels(true);
		volumeRegler.setPaintTicks(true);
		volumeRegler.setValue((int) (soundButton.getVolume() * 100));
		pnlDialog.add(volumeRegler, c);

		pnlSouth.setLayout(new FlowLayout());
		btnAbbrechen.addActionListener(bl);
		btnAnwenden.addActionListener(bl);
		pnlSouth.add(btnAbbrechen);
		pnlSouth.add(btnAnwenden);
		pnlDialog.setPreferredSize(new Dimension(1, 1));
		setSize(600, 260);
		setLocation(100, 100);
		setLayout(new BorderLayout());
		add(pnlDialog, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		setVisible(true);
	}

	public class btnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAbbrechen) {
				dispose();
			} else if (e.getSource() == btnAnwenden) {
				dialogAnwenden();
			} else if (e.getSource() == btnButtonFarbe) {
				Color choosedButtonFarbe = JColorChooser.showDialog(null,
						"Farbauswahl", btnButtonFarbe.getBackground());
				btnButtonFarbe.setBackground(choosedButtonFarbe);
				btnTextFarbe.setBackground(choosedButtonFarbe);

			} else if (e.getSource() == btnTextFarbe) {
				Color choosedTextFarbe = JColorChooser.showDialog(null,
						"Farbauswahl", btnButtonFarbe.getBackground());
				btnTextFarbe.setForeground(choosedTextFarbe);
				btnButtonFarbe.setForeground(choosedTextFarbe);

			} else if (e.getSource() == tfName) {
				dialogAnwenden();
			} else if (e.getSource() == btnBrowse) {
				musicPath = Browse.getMusicFileFolder();
				tfPath.setText(musicPath.getPath());
				// if (musicFile == null) {
				// tfPath.setText(musicFile.getPath());
				// soundButton.setOneSong();
				if (soundButton.getName().compareTo(tfName.getText()) == 0) {
					tfName.setText(musicPath.getName());
				}
			}

		}
	}

	private void dialogAnwenden() {
		if (musicPath != null) {
			if (musicPath.listFiles() == null) {
				soundButton.setProperties(0, tfName.getText(), musicPath,
						volumeRegler.getValue(), "0:00",
						btnButtonFarbe.getForeground(),
						btnButtonFarbe.getBackground());
			} else if (musicPath.listFiles() != null) {
				soundButton.setProperties(1, tfName.getText(), musicPath,
						volumeRegler.getValue(), String.valueOf(Browse
								.getMusicFileArray(musicPath).length),
						btnButtonFarbe.getForeground(), btnButtonFarbe
								.getBackground());
			}
		} else {
			soundButton.setProperties(0, tfName.getText(), null,
					volumeRegler.getValue(), "0:00",
					btnButtonFarbe.getForeground(),
					btnButtonFarbe.getBackground());
		}
		dispose();
	}
}