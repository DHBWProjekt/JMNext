package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import lib.Info;

public class SoundBoard extends JPanel {
	private SoundButton sbActive;
	private SoundButton[][] sbArray;
	private ListenerMouseKlick lmk = new ListenerMouseKlick();
	private BlinkListener bl = new BlinkListener();
	private Timer blinkTimer = new Timer(500, bl);
	private ProgressbarListener pbl = new ProgressbarListener();
	private Timer pbUpdateTimer = new Timer(100, pbl);

	private JFXPanel myJFXPanel = new JFXPanel();
	private MediaPlayer sbPlayer;

	public SoundBoard(int zeilen, int spalten) {
		setLayout(new GridLayout(zeilen, spalten));
		sbArray = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp] = new SoundButton(String.valueOf(spalten * z
						+ sp));
				sbArray[z][sp].addMouseListener(lmk);
				add(sbArray[z][sp]);
			}
		}
	}

	private class ListenerMouseKlick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (sbPlayer == null) {

				} else {
					sbPlayer.stop();
					sbPlayer.dispose();
					sbPlayer = null;
				}
				for (int z = 0; z < sbArray.length; z++) {
					for (int sp = 0; sp < sbArray[z].length; sp++) {
						if (e.getSource() == sbArray[z][sp]) {
							if (sbArray[z][sp] == sbActive) {
								blinkTimer.stop();
								pbUpdateTimer.stop();
								sbActive.setColorStandard();
								sbActive.setAnzeigeZuruecksetzen();
								sbActive = null;
							} else {
								blinkTimer.stop();
								pbUpdateTimer.stop();
								if (sbActive != null) {
									sbActive.setColorStandard();
									sbActive.setAnzeigeZuruecksetzen();
								}
								if (sbArray[z][sp].getMusicPathASCII() != null) {
									System.out.println(sbArray[z][sp]
											.getMusicPathASCII());
									sbPlayer = new MediaPlayer(new Media(
											sbArray[z][sp].getMusicPathASCII()));
									sbPlayer.play();
									sbPlayer.setVolume(sbArray[z][sp]
											.getVolume());
									sbArray[z][sp].lblCounterUp();
									sbArray[z][sp].changeColor();
									sbActive = sbArray[z][sp];
									blinkTimer.start();
									pbUpdateTimer.start();
								} else {
									sbActive = null;
								}

							}
						}
					}
				}

			} else if (SwingUtilities.isRightMouseButton(e)) {
				for (int z = 0; z < sbArray.length; z++) {
					for (int sp = 0; sp < sbArray[z].length; sp++) {
						if (e.getSource() == sbArray[z][sp]) {
							DialogSoundButton dsb = new DialogSoundButton(
									sbArray[z][sp]);
						}
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class BlinkListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			sbActive.changeColor();
		}
	}

	private class ProgressbarListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (sbActive.getTotalDuration().compareTo("0:00") == 0) {
				sbActive.setTotalDuration(Info.getTotalDuration(sbPlayer));
			}
			sbActive.setLblDuration(Info.getRestzeit(sbPlayer));
			sbActive.setPbDurationValue(Info.getPercent(sbPlayer));
		}
	}
}
