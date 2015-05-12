package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class SoundButton extends JPanel {
	private GridBagConstraints c = new GridBagConstraints();

	private ImageIcon iconShuffle = new ImageIcon(getClass().getClassLoader()
			.getResource("resources/shuffle.png"));

	private File musicPath;
	private File[] musicFileArray;

	private int buttonArt = 0; // 0->oneSong, 1->shuffleSong

	private boolean istBtnColorStandard = true;
	private int counterCicle = 0;
	private double volume = 0.5;
	private String totalDuration = "0:00";

	private JLabel lblName = new JLabel();
	private JLabel lblShuffle = new JLabel(iconShuffle);
	private JLabel lblCounterCicle = new JLabel("0");
	private JLabel lblDuration = new JLabel("0:00");

	private JProgressBar pbDuration = new JProgressBar(JProgressBar.VERTICAL,
			0, 1000);

	private Font fontLblCounterLblDuration = new Font("Monospaced", Font.BOLD,
			14);
	private Color foreground;
	private Color background;

	public SoundButton(String name) {

		setBorder(BorderFactory.createLineBorder(Color.darkGray));
		foreground = lblName.getForeground();
		background = getBackground();

		lblName.setText(name);
		lblName.setPreferredSize(new Dimension(20, 14));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblShuffle.setHorizontalAlignment(SwingConstants.RIGHT);

		lblDuration.setFont(fontLblCounterLblDuration);

		lblCounterCicle.setFont(fontLblCounterLblDuration);
		createBtnPanel();
		lblShuffle.setVisible(false);
	}

	public void setOneSong() {
		lblShuffle.setVisible(false);
		buttonArt = 0;
	}

	public void setShuffle() {
		lblShuffle.setVisible(true);
		buttonArt = 1;
	}

	public void setLblDuration(String duration) {
		lblDuration.setText(duration);
	}

	public void setTotalDuration(String duration) {
		if (buttonArt == 0) {
			totalDuration = duration;
			lblDuration.setText(totalDuration);
		} else if (buttonArt == 1) {
			totalDuration = String.valueOf(musicFileArray.length);
			lblDuration.setText(totalDuration);
		}
	}

	public String getTotalDuration() {
		return totalDuration;
	}

	public void lblCounterUp() {
		counterCicle++;
		lblCounterCicle.setText(String.valueOf(counterCicle));
	}

	public void setCounterCicle(int anzahl) {
		counterCicle = anzahl;
		lblCounterCicle.setText(String.valueOf(counterCicle));
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = (double) volume / 100;
	}

	public void setAnzeigeZuruecksetzen() {
		pbDuration.setValue(1000);
		lblDuration.setText(totalDuration);
	}

	public int getButtonArt() {
		return buttonArt;
	}

	public void setButtonArt(int buttonArt) {
		this.buttonArt = buttonArt;
	}

	public File getMusicPath() {
		return musicPath;
	}

	public void setForegroundColorStandard(Color foreground) {
		this.foreground = foreground;
	}

	public Color getForegroundColorStandard() {
		return foreground;
	}

	public String getMusicPathASCII() {
		if (musicPath != null) {
			if (musicPath.listFiles() == null) {
				return musicPath.toURI().toASCIIString();
			} else {
				Random shuffle = new Random();
				int zufallsZahl = shuffle.nextInt(musicFileArray.length);
				System.out.println(zufallsZahl + ": "
						+ musicFileArray[zufallsZahl].toURI().toASCIIString());
				return musicFileArray[zufallsZahl].toURI().toASCIIString();
			}
		}
		return null;
	}

	public void setBackgroundColorStandard(Color background) {
		this.background = background;
	}

	public void setPbDurationValue(int value) {
		pbDuration.setValue(value);
	}

	public void setMusicPath(File musicPath) {
		this.musicPath = musicPath;
	}

	public String getName() {
		return lblName.getText();
	}

	public void setName(String name) {
		lblName.setText(name);
	}

	public void setMusicFileArray(File[] musicFileArray) {
		this.musicFileArray = musicFileArray;
	}

	public String getMusicPathString() {
		return musicPath.getPath();
	}

	public void createBtnPanel() {
		setLayout(new GridBagLayout());
		// lblCounterCicle (Oben links)
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 3;
		c.weightx = 0;
		c.weighty = 0;
		lblCounterCicle.setBorder(javax.swing.BorderFactory.createEmptyBorder(
				2, 3, 0, 0));
		add(lblCounterCicle, c);

		c.ipady = 0;
		c.ipadx = 2;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		lblShuffle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
				0, 0));
		add(lblShuffle, c);

		// lblDuration (Unten links)
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		lblDuration.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3,
				0, 0));
		add(lblDuration, c);

		// lblName (Mitte)
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.1;
		c.weighty = 0.1;
		c.gridwidth = 2;
		add(lblName, c);

		// pbDuration Progressbar (Rechts)
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.0;
		c.weighty = 0.1;
		c.gridheight = 3;
		pbDuration.setValue(1000);
		pbDuration.setForeground(new Color(0, 176, 103));
		add(pbDuration, c);
	}

	public void setColorStandard() {
		setBackground(background);
		setLabelsTextColor(foreground);
		istBtnColorStandard = true;
	}

	public boolean getIstButtonColorStandard() {
		return istBtnColorStandard;
	}

	public void changeColor() {
		if (istBtnColorStandard == true) {
			setBackground(foreground);
			setLabelsTextColor(background);
			istBtnColorStandard = false;
		} else {
			setColorStandard();
			istBtnColorStandard = true;
		}
	}

	public void setLabelsTextColor(Color foreground) {
		lblCounterCicle.setForeground(foreground);
		lblDuration.setForeground(foreground);
		lblName.setForeground(foreground);
	}
}
