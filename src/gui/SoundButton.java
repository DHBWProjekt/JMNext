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

import lib.Browse;
import data.SoundButtonProperties;

public class SoundButton extends JPanel {

	private GridBagConstraints c = new GridBagConstraints();

	public ImageIcon iconShuffle = new ImageIcon(getClass().getClassLoader()
			.getResource("resources/shuffle.png"));
	public ImageIcon iconLoop = new ImageIcon(getClass().getClassLoader()
			.getResource("resources/loop.png"));
	public ImageIcon iconRepeat = new ImageIcon(getClass().getClassLoader()
			.getResource("resources/repeat.png"));

	private File[] musicFileArray;

	public static final int oneSong = 0;
	public static final int shuffle = 1;
	public static final int shuffleRepeat = 2;
	public static final int loop = 3;
	public static final int oneSongOwnPlayer = 4;

	private SoundButtonProperties properties = new SoundButtonProperties();

	private Font fontLblCounterLblDuration = new Font("Monospaced", Font.BOLD,
			14);
	private boolean istBtnColorStandard = true;
	private int counterCicle = 0;

	private JLabel lblName = new JLabel();
	private JLabel lblShuffle = new JLabel(iconShuffle);
	private JLabel lblLoop = new JLabel(iconLoop);
	private JLabel lblRepeat = new JLabel(iconRepeat);
	private JLabel lblCounterCicle = new JLabel("0");
	private JLabel lblDuration = new JLabel("0:00");

	private JProgressBar pbDuration = new JProgressBar(JProgressBar.VERTICAL,
			0, 1000);

	public SoundButton(String name) {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		properties.setButtonArt(99);
		properties.setForeground(lblName.getForeground());
		setBackground(Color.WHITE);
		properties.setBackground(getBackground());
		properties.setName(name);
		lblName.setText(name);
		lblName.setPreferredSize(new Dimension(20, 14));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblShuffle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoop.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepeat.setHorizontalAlignment(SwingConstants.RIGHT);

		lblDuration.setFont(fontLblCounterLblDuration);

		lblCounterCicle.setFont(fontLblCounterLblDuration);
		createBtnPanel();
		lblShuffle.setVisible(false);
		lblRepeat.setVisible(false);
		lblLoop.setVisible(false);
	}

	public void setProperties(int btnArt, String name, File musicPath,
			double volume, String totalDuration, Color foreground,
			Color background) {
		properties.setButtonArt(btnArt);
		setIcon();
		properties.setName(name);
		lblName.setText(properties.getName());
		properties.setMusicPath(musicPath);
		if (musicPath != null) {
			if (properties.getMusicPath().listFiles() != null) {
				setMusicFileArray(Browse.getMusicFileArray(properties
						.getMusicPath()));
				setLblDuration(String.valueOf(Browse
						.getMusicFileArray(properties.getMusicPath()).length));
			}
		}
		properties.setVolume(volume);
		properties.setTotalDuration(totalDuration);
		lblDuration.setText(properties.getTotalDuration());
		properties.setForeground(foreground);
		setLabelsTextColor(properties.getForeground());
		properties.setBackground(background);
		setBackground(properties.getBackground());
	}

	public void pbAusblenden() {
		pbDuration.setVisible(false);
	}

	public void pbEinblenden() {
		pbDuration.setVisible(true);
	}

	public void setProperties(SoundButtonProperties properties) {
		this.properties.copyProperties(properties);
		if (properties.getButtonArt() == 99
				|| properties.getMusicPath() == null) {

		} else {
			if (properties.getMusicPath().listFiles() != null) {
				setMusicFileArray(Browse.getMusicFileArray(properties
						.getMusicPath()));
				setLblDuration(String.valueOf(Browse
						.getMusicFileArray(properties.getMusicPath()).length));
			}
		}
		setIcon();
		lblName.setText(properties.getName());
		lblDuration.setText(properties.getTotalDuration());
		setLabelsTextColor(properties.getForeground());
		setBackground(properties.getBackground());
	}

	public SoundButtonProperties getProperties() {
		return properties;
	}

	public void setIcon() {
		if (properties.getButtonArt() == SoundButton.oneSong
				|| properties.getButtonArt() == 99) {
			lblShuffle.setVisible(false);
			lblLoop.setVisible(false);
			lblRepeat.setVisible(false);
		} else if (properties.getButtonArt() == SoundButton.shuffle) {
			lblLoop.setVisible(false);
			lblRepeat.setVisible(false);
			lblShuffle.setVisible(true);
		} else if (properties.getButtonArt() == SoundButton.shuffleRepeat) {
			lblLoop.setVisible(false);
			lblRepeat.setVisible(true);
			lblShuffle.setVisible(true);
		} else if (properties.getButtonArt() == SoundButton.loop) {
			lblShuffle.setVisible(false);
			lblRepeat.setVisible(false);
			lblLoop.setVisible(true);
		}
	}

	public void setLblDuration(String duration) {
		lblDuration.setText(duration);
	}

	public void setTotalDuration(String duration) {
		if (properties.getButtonArt() == 0) {
			properties.setTotalDuration(duration);
			lblDuration.setText(properties.getTotalDuration());
		} else if (properties.getButtonArt() == 1) {
			properties.setTotalDuration(String.valueOf(musicFileArray.length));
			lblDuration.setText(properties.getTotalDuration());
		}
	}

	public String getTotalDuration() {
		return properties.getTotalDuration();
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
		return properties.getVolume();
	}

	public void setVolume(int volume) {
		properties.setVolume((double) volume / 100);
	}

	public void setAnzeigeZuruecksetzen() {
		pbDuration.setValue(1000);
		lblDuration.setText(properties.getTotalDuration());
	}

	public int getButtonArt() {
		return properties.getButtonArt();
	}

	public void setButtonArt(int buttonArt) {
		properties.setButtonArt(buttonArt);
	}

	public File getMusicPath() {
		return properties.getMusicPath();
	}

	public void setForegroundColorStandard(Color foreground) {
		properties.setForeground(foreground);
	}

	public Color getForegroundColorStandard() {
		return properties.getForeground();
	}

	public String getMusicPathASCII() {
		if (properties.getMusicPath() != null) {
			if (properties.getButtonArt() == 0) {
				return properties.getMusicPath().toURI().toASCIIString();
			} else if (properties.getButtonArt() == 1) {
				Random shuffle = new Random();
				int zufallsZahl = shuffle.nextInt(musicFileArray.length);
				System.out.println(zufallsZahl + ": "
						+ musicFileArray[zufallsZahl].getName());
				return musicFileArray[zufallsZahl].toURI().toASCIIString();
			}
		}
		return null;
	}

	public void setBackgroundColorStandard(Color background) {
		properties.setBackground(background);
	}

	public void setPbDurationValue(int value) {
		pbDuration.setValue(value);
	}

	public void setMusicPath(File musicPath) {
		properties.setMusicPath(musicPath);
	}

	public String getName() {
		return properties.getName();
	}

	public void setName(String name) {
		lblName.setText(name);
		properties.setName(name);
	}

	public void setMusicFileArray(File[] musicFileArray) {
		this.musicFileArray = musicFileArray;
	}

	public String getMusicPathString() {
		return properties.getMusicPath().getPath();
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
		add(lblLoop, c);

		// lblDuration (Unten links)
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		lblDuration.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3,
				0, 0));
		add(lblDuration, c);

		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		lblDuration.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3,
				0, 0));
		add(lblRepeat, c);

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
		setBackground(properties.getBackground());
		setLabelsTextColor(properties.getForeground());
		istBtnColorStandard = true;
	}

	public boolean getIstButtonColorStandard() {
		return istBtnColorStandard;
	}

	public void changeColor() {
		if (istBtnColorStandard == true) {
			setBackground(properties.getForeground());
			setLabelsTextColor(properties.getBackground());
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
