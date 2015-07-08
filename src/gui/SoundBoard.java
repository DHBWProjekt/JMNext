package gui;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

import javafx.scene.media.MediaPlayer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import data.SbpChange;
import data.SoundButtonProperties;

public class SoundBoard extends JPanel {
	private MainView hf;
	private SoundButton[][] sbArray;
	private SoundButton[][] sbArrayChange;
	private ListenerMouseKlick lmk = new ListenerMouseKlick();

	private Cursor cursorHand = new Cursor(Cursor.HAND_CURSOR);
	private Cursor cursorMove = new Cursor(Cursor.MOVE_CURSOR);
	private boolean wasDragged = false;

	private Stack<SbpChange> SbpChangeStack = new Stack<SbpChange>();
	private int zeilen;
	private int spalten;

	private SoundButtonProperties sbpSource;

	public SoundBoard(MainView parent, int zeilen, int spalten) {
		this.hf = parent;
		this.zeilen = zeilen;
		this.spalten = spalten;
		setLayout(new GridLayout(zeilen, spalten));
		sbArray = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArray[z][sp] = new SoundButton(this, String.valueOf(spalten
						* z + sp));
				sbArray[z][sp].addMouseListener(lmk);
				sbArray[z][sp].addMouseMotionListener(lmk);
				add(sbArray[z][sp]);
			}
		}
	}

	public void loadSoundBoard() {
		try {
			FileInputStream fileStream = new FileInputStream(
					hf.getFileAutoSave());
			ObjectInputStream os = new ObjectInputStream(fileStream);
			try {
				System.out.println("Zeilen: " + os.readInt());
				System.out.println("Spalten: " + os.readInt());

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

	public void saveSoundboard() {
		try {
			FileOutputStream fileStream = new FileOutputStream(
					hf.getFileAutoSave());
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
			os.writeInt(zeilen);
			os.writeInt(spalten);
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

	public void addSpalte() {
		int counter = zeilen * spalten;
		spalten++;
		removeAll();
		setLayout(new GridLayout(zeilen, spalten));
		sbArrayChange = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				if (sp + 1 != spalten) {
					sbArrayChange[z][sp] = sbArray[z][sp];
				} else {
					sbArrayChange[z][sp] = new SoundButton(this,
							String.valueOf(counter));
					sbArrayChange[z][sp].addMouseListener(lmk);
					sbArrayChange[z][sp].addMouseMotionListener(lmk);
					counter++;
				}
				add(sbArrayChange[z][sp]);
			}
		}
		sbArray = null;
		sbArray = sbArrayChange;
		sbArrayChange = null;
		validate();
		repaint();
	}

	public void removeSpalte() {
		spalten--;
		removeAll();
		setLayout(new GridLayout(zeilen, spalten));
		sbArrayChange = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArrayChange[z][sp] = sbArray[z][sp];
				add(sbArrayChange[z][sp]);
			}
		}
		sbArray = null;
		sbArray = sbArrayChange;
		sbArrayChange = null;
		validate();
		repaint();
	}

	public void addZeile() {
		int counter = zeilen * spalten;
		zeilen++;
		removeAll();
		setLayout(new GridLayout(zeilen, spalten));
		sbArrayChange = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				if (z + 1 != zeilen) {
					sbArrayChange[z][sp] = sbArray[z][sp];
				} else {
					sbArrayChange[z][sp] = new SoundButton(this,
							String.valueOf(counter));
					sbArrayChange[z][sp].addMouseListener(lmk);
					sbArrayChange[z][sp].addMouseMotionListener(lmk);
					counter++;
				}
				add(sbArrayChange[z][sp]);
			}
		}
		sbArray = null;
		sbArray = sbArrayChange;
		sbArrayChange = null;
		validate();
		repaint();
	}

	public void removeZeile() {
		zeilen--;
		removeAll();
		setLayout(new GridLayout(zeilen, spalten));
		sbArrayChange = new SoundButton[zeilen][spalten];
		for (int z = 0; z < zeilen; z++) {
			for (int sp = 0; sp < spalten; sp++) {
				sbArrayChange[z][sp] = sbArray[z][sp];
				add(sbArrayChange[z][sp]);
			}
		}
		sbArray = null;
		sbArray = sbArrayChange;
		sbArrayChange = null;
		validate();
		repaint();
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

	public void pausePlayer() {
		if (hf.getSbActive() != null) {
			if (hf.getSbActive().istPausiert == true
					|| hf.getSbActive().istFadeOutTimerAktiv() == true) {
				if (hf.getSbActive().istFadeOutTimerAktiv() == true) {
					hf.getSbActive().getFadeOutTimer().stop();
					hf.getSbActive().istPausiert = false;
					hf.getSbActive().sbFadeIn();
				}
				hf.getSbActive().istPausiert = false;
				hf.getSbActive().sbFadeIn();
			} else {
				if (hf.getSbActive().istFadeInTimerAktiv() == true) {
					hf.getSbActive().getFadeInTimer().stop();
					hf.getSbActive().istPausiert = true;
					hf.getSbActive().sbFadeOut();
				}
				hf.getSbActive().istPausiert = true;
				hf.getSbActive().sbFadeOut();
			}
		}
	}

	private class ListenerMouseKlick implements MouseListener,
			MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				for (int z = 0; z < sbArray.length; z++) {
					for (int sp = 0; sp < sbArray[z].length; sp++) {
						if (e.getSource() == sbArray[z][sp]) {
							if (hf.getTapeA() != null) {
								if (hf.getSbActive().istFadeOutTimerAktiv() == true) {
									hf.getSbActive().sbStop();
									sbArray[z][sp].sbPlay();
								} else {
									if ((hf.getSbActive() == sbArray[z][sp] && hf
											.getSbActive().istPausiert != true)
											|| (sbArray[z][sp].getButtonArt() == 99 && hf
													.getSbActive().istPausiert != true)) {
										hf.setSbNext(null);
										hf.getSbActive().sbFadeOut();
									} else {
										if (sbArray[z][sp].getButtonArt() != 99) {
											hf.setSbNext(sbArray[z][sp]);
											hf.getSbNext().changeColor();
											hf.getSbNext().sbStartBlink();
											hf.getSbActive().sbFadeOut();
										}
									}
								}
							} else {
								if (sbArray[z][sp].getButtonArt() != 99) {
									sbArray[z][sp].sbPlay();
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

	public void setAnzeigePfad(File musicPath) {
		hf.setAnzeigePfad(musicPath);
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

	public MediaPlayer getTapeA() {
		return hf.getTapeA();
	}

	public void setTapeA(MediaPlayer tapeA) {
		hf.setTapeA(tapeA);
	}

	public void setTitelAnzeige(String titel) {
		hf.setTitelAnzeige(titel);
	}

	public SoundButton getSbActive() {
		return hf.getSbActive();
	}

	public void setSbActive(SoundButton sb) {
		hf.setSbActive(sb);
		;
	}

	public SoundButton getSbNext() {
		return hf.getSbNext();
	}

	public void setSbNext(SoundButton sb) {
		hf.setSbNext(sb);
	}

}
