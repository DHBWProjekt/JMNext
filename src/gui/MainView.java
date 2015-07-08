package gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainView extends JFrame {
	private ListenerKeyboard lkb = new ListenerKeyboard();
	private ListenerMouseKlick lmk = new ListenerMouseKlick();
	private ListenerMenuBar lmb = new ListenerMenuBar();
	private SoundBoard sb1;
	private int zeilen = 0;
	private int spalten = 0;

	private FensterListener fl = new FensterListener();
	private JMenuBar mb = new JMenuBar();
	private JMenu menuEinstellungen = new JMenu("Einstellungen");
	private JMenu menuSoundboard = new JMenu("Soundboard");
	private JMenuItem itemPbAusblenden = new JMenuItem(
			"Vortschrittsanzeige aller Button ausblenden");
	private JMenuItem itemAddSpalte = new JMenuItem("Spalte hinzufügen");
	private JMenuItem itemRemoveSpalte = new JMenuItem("Spalte entfernen");
	private JMenuItem itemAddZeile = new JMenuItem("Zeile hinzufügen");
	private JMenuItem itemRemoveZeile = new JMenuItem("Zeile entfernen");

	private FlowLayout fla = new FlowLayout();
	private JPanel pnlAnzeige = new JPanel(fla);
	private JLabel lblTitel = new JLabel("Aktueller Titel");
	private File anzeigePfad;

	private Cursor cursorHand = new Cursor(Cursor.HAND_CURSOR);
	private Cursor cursorMove = new Cursor(Cursor.MOVE_CURSOR);
	private SoundButton soundButton;

	private JFXPanel myJFXPanel = new JFXPanel();
	private MediaPlayer tapeA;
	private MediaPlayer tapeB;

	private SoundButton sbActive;
	private SoundButton sbNext;

	private File fileAutoSave;

	public MainView() {
		try {
			openAutoSave();
			addKeyListener(lkb);
			addWindowListener(fl);
			setLayout(new BorderLayout());
			createMenuEinstellung();
			createMenuSoundboard();
			setJMenuBar(mb);

			add(sb1, BorderLayout.CENTER);

			pnlAnzeige.add(lblTitel);
			pnlAnzeige.addMouseListener(lmk);
			pnlAnzeige.addMouseMotionListener(lmk);
			add(pnlAnzeige, BorderLayout.SOUTH);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(1000, 500);
			setVisible(true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		MainView hf = new MainView();
	}

	private void createMenuEinstellung() {
		itemPbAusblenden.addActionListener(lmb);
		menuEinstellungen.add(itemPbAusblenden);
		mb.add(menuEinstellungen);
	}

	private void createMenuSoundboard() {
		itemAddSpalte.addActionListener(lmb);
		itemRemoveSpalte.addActionListener(lmb);
		itemAddZeile.addActionListener(lmb);
		itemRemoveZeile.addActionListener(lmb);
		menuSoundboard.add(itemAddSpalte);
		menuSoundboard.add(itemRemoveSpalte);
		menuSoundboard.add(itemAddZeile);
		menuSoundboard.add(itemRemoveZeile);
		mb.add(menuSoundboard);
	}

	private void openAutoSave() {
		System.out.println(getClass().getClassLoader().getResource("resources")
				.toString().split(":")[0]);
		if (getClass().getClassLoader().getResource("resources").toString()
				.split(":")[0].compareTo("file") == 0) {
			fileAutoSave = new File(
					getClass().getClassLoader().getResource("resources")
							.toString().split(":")[1].concat("/autosave.ser"));
			System.out.println(fileAutoSave.getAbsolutePath());
		} else {
			fileAutoSave = new File("autosave.ser");
		}
		if (fileAutoSave.exists() == false) {
			try {
				fileAutoSave.createNewFile();
			} catch (Exception e) {
				System.out.println("Datei erstellen fehlgeschlagen");
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Daten werden aus AutoSave geladen");
			loadSoundboardSize();
			sb1 = new SoundBoard(this, zeilen, spalten);
			sb1.loadSoundBoard();
		}
	}

	public void loadSoundboardSize() {
		try {
			FileInputStream fileStream = new FileInputStream(fileAutoSave);
			ObjectInputStream os = new ObjectInputStream(fileStream);
			try {
				zeilen = os.readInt();
				spalten = os.readInt();
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

	private class FensterListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("closing");
			sb1.saveSoundboard();

		}

		@Override
		public void windowClosed(WindowEvent e) {
			System.out.println("closed");

		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class ListenerKeyboard implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("Keyevent");
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println(e.getKeyCode());
			if (e.getKeyCode() == 90) {
				sb1.undoChange();
			} else if (e.getKeyCode() == 32) {
				sb1.pausePlayer();
			}
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public class ListenerMenuBar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == itemPbAusblenden) {
				if (itemPbAusblenden.getLabel().compareTo(
						"Vortschrittsanzeige aller Button einblenden") != 0) {
					sb1.pbAusblenden();
					itemPbAusblenden
							.setText("Vortschrittsanzeige aller Button einblenden");
				} else {
					sb1.pbEinblenden();
					itemPbAusblenden
							.setText("Vortschrittsanzeige aller Button ausblenden");
				}
			} else if (e.getSource() == itemAddSpalte) {
				System.out.println("Spalte hinzufügen");
				sb1.addSpalte();
			} else if (e.getSource() == itemRemoveSpalte) {
				System.out.println("Spalte entfernen");
				sb1.removeSpalte();
			} else if (e.getSource() == itemAddZeile) {
				System.out.println("Zeile hinzufügen");
				sb1.addZeile();
			} else if (e.getSource() == itemRemoveZeile) {
				System.out.println("Spalte entfernen");
				sb1.removeZeile();
			}
		}
	}

	private class ListenerMouseKlick implements MouseListener,
			MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (e.getSource() == pnlAnzeige) {
					System.out.println(anzeigePfad);
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {

			if (sb1.getMousePosition() != null) {
				if (sb1.getComponentAt(sb1.getMousePosition()) instanceof SoundButton) {
					soundButton = (SoundButton) sb1.getComponentAt(sb1
							.getMousePosition());
					if (soundButton.getProperties().getButtonArt() == 99) {
						System.out.println("Buttonart = 99");
						sb1.setCursor(DragSource.DefaultCopyDrop);
					} else {
						sb1.setCursor(cursorHand);
					}
				}
			} else {
				setCursor(cursorHand);
			}

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			setCursor(cursorHand);

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (soundButton.getProperties().getButtonArt() == 99) {
				soundButton.getProperties().setMusicPath(anzeigePfad);
				soundButton.setName(anzeigePfad.getName());
				soundButton.getProperties().setButtonArt(0);
				soundButton = null;
			}
			sb1.setCursor(cursorMove);
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

	}

	public MediaPlayer getTapeA() {
		return tapeA;
	}

	public void setTapeA(MediaPlayer tapeA) {
		this.tapeA = tapeA;
	}

	public void setAnzeigePfad(File musicPath) {
		anzeigePfad = musicPath;
	}

	public void setTitelAnzeige(String titel) {
		lblTitel.setText(titel);
	}

	public SoundButton getSbActive() {
		return sbActive;
	}

	public void setSbActive(SoundButton sb) {
		sbActive = sb;
	}

	public SoundButton getSbNext() {
		return sbNext;
	}

	public void setSbNext(SoundButton sb) {
		sbNext = sb;
	}

	public File getFileAutoSave() {
		return fileAutoSave;
	}

	public void setFileAutoSave(File fileAutoSave) {
		this.fileAutoSave = fileAutoSave;
	}
}
