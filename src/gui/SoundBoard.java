package gui;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import lib.Info;
import data.SbpChange;
import data.SoundButtonProperties;

public class SoundBoard extends JPanel {
	private SoundButton sbActive;
	private SoundButton[][] sbArray;
	private ListenerMouseKlick lmk = new ListenerMouseKlick();
	private BlinkListener bl = new BlinkListener();
	private Timer blinkTimer = new Timer(500, bl);
	private ProgressbarListener pbl = new ProgressbarListener();
	private Timer pbUpdateTimer = new Timer(100, pbl);
	private Cursor cursorHand = new Cursor(Cursor.HAND_CURSOR);
	private Cursor cursorMove = new Cursor(Cursor.MOVE_CURSOR);
	private Stack<SbpChange> SbpChangeStack = new Stack<SbpChange>();
	private File fileAutoSave;
	private int zeilen;
	private int spalten;

	private SoundButtonProperties sbpSource;
	private boolean wasDragged = false;

	private JFXPanel myJFXPanel = new JFXPanel();
	private MediaPlayer sbPlayer;

	public SoundBoard(int zeilen, int spalten) {
		if (getClass().getClassLoader().getResource("resources").toString()
				.split(":")[0].compareTo("file") == 0) {
			fileAutoSave = new File(
					getClass().getClassLoader().getResource("resources")
							.toString().split(":")[1].concat("/autosave.ser"));
			System.out.println(fileAutoSave.getAbsolutePath());
		} else {
			fileAutoSave = new File("autosave.ser");
		}

		this.zeilen = zeilen;
		this.spalten = spalten;
		setLayout(new GridLayout(zeilen, spalten));
		sbArray = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp] = new SoundButton(String.valueOf(spalten * z
						+ sp));
				sbArray[z][sp].addMouseListener(lmk);
				sbArray[z][sp].addMouseMotionListener(lmk);
				add(sbArray[z][sp]);
			}
		}
		System.out.println(fileAutoSave.getAbsolutePath());
		if (fileAutoSave.exists() == false) {
			try {
				fileAutoSave.createNewFile();
			} catch (Exception e) {
				System.out.println("Datei erstellen fehlgeschlagen");
				System.out.println(e.getMessage());
			}
		} else {
			loadSoundBoard();
		}
	}

	public void saveSoundboard() {
		try {
			FileOutputStream fileStream = new FileOutputStream(fileAutoSave);
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
			for (int z = 0; z < zeilen; z++) {
				for (int sp = 0; sp < spalten; sp++) {
					os.writeObject(sbArray[z][sp].getProperties());
				}
			}
			os.close();
		} catch (Exception ex) {
			System.out
					.println("Objekte konnten nicht vollständig gespeichert werden");
			System.out.println(ex.getMessage());
		}
	}

	public void loadSoundBoard() {
		try {
			FileInputStream fileStream = new FileInputStream(fileAutoSave);
			ObjectInputStream os = new ObjectInputStream(fileStream);
			try {
				for (int z = 0; z < zeilen; z++) {
					for (int sp = 0; sp < spalten; sp++) {
						sbArray[z][sp].setProperties((SoundButtonProperties) os
								.readObject());
					}
				}
			} catch (Exception e) {
				System.out.println("Fehler beim Laden");
				System.out.println(e.getMessage());
			} finally {
				os.close();
			}
		} catch (Exception ex) {
			System.out.println("Fehler beim Öffnen der Datei.");
			System.out.println(ex.getMessage());
		}
	}

	public void undoChange() {
		if (SbpChangeStack.empty() == false) {
			SbpChange sbpChange = SbpChangeStack.pop();
			System.out.println(sbpChange.getSbpLastUpdate().getName()
					+ " wird wieder hergestellt");
			sbpChange.getSbLastUpdate().setProperties(
					sbpChange.getSbpLastUpdate());
			System.out.println("Undo changes");
		}
	}

	public void pbAusblenden() {
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp].pbAusblenden();
			}
		}
	}

	public void pbEinblenden() {
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp].pbEinblenden();
			}
		}
	}

	private class ListenerMouseKlick implements MouseListener,
			MouseMotionListener {

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
								if (sbArray[z][sp].getMusicPath() != null
										&& sbArray[z][sp].getButtonArt() != 99) {
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
			setCursor(cursorHand);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e) && wasDragged == true
					&& sbpSource != null) {
				for (int z = 0; z < sbArray.length; z++) {
					for (int sp = 0; sp < sbArray[z].length; sp++) {
						if (getComponentAt(getMousePosition()) == sbArray[z][sp]) {
							SbpChangeStack.push(new SbpChange(sbArray[z][sp],
									sbArray[z][sp].getProperties()));
							System.out.println(sbArray[z][sp].getProperties()
									.getName() + " wurde im Stack gespeichert");
							sbArray[z][sp].setProperties(sbpSource);
							System.out.println("Soundbuttonquelle: "
									+ sbpSource.getName()
									+ "Soundbuttontarget: "
									+ SbpChangeStack.peek().getSbpLastUpdate()
											.getName());
							wasDragged = false;
							sbpSource = null;
						}
					}
				}
			}
			setCursor(cursorMove);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (wasDragged == false) {
				for (int z = 0; z < sbArray.length; z++) {
					for (int sp = 0; sp < sbArray[z].length; sp++) {
						if (e.getSource() == sbArray[z][sp]) {
							sbpSource = sbArray[z][sp].getProperties();
							System.out
									.println("Buttoneigenschaften wurden gespeichert."
											+ sbArray[z][sp].getName());
							wasDragged = true;
						}
					}
				}
			}
			if (getComponentAt(getMousePosition()) != e.getSource()
					&& sbpSource != null) {
				setCursor(DragSource.DefaultCopyDrop);
			} else {
				setCursor(cursorHand);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
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

			if (Info.getRestzeitSekunde(sbPlayer) == 0
					&& sbActive.getButtonArt() == 1) {
				sbPlayer.stop();
				sbPlayer.dispose();
				sbPlayer = null;
				sbPlayer = new MediaPlayer(new Media(
						sbActive.getMusicPathASCII()));
				sbPlayer.play();
			}
			if (Info.getRestzeitSekunde(sbPlayer) == 0
					&& sbActive.getButtonArt() == 0) {
				blinkTimer.stop();
				pbUpdateTimer.stop();
				sbActive.setColorStandard();
				sbActive.setAnzeigeZuruecksetzen();
				sbActive = null;
			}
		}
	}
}
