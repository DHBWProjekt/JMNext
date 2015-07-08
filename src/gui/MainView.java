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
	private KeyboardListener kbl = new KeyboardListener();
	private ListenerMouseKlick lmk = new ListenerMouseKlick();
	private SoundBoard sb1;

	private FensterListener fl = new FensterListener();
	private JMenuBar mb = new JMenuBar();
	private JMenu menuReiter = new JMenu("Einstellungen");
	private JMenuItem pbAusblenden = new JMenuItem(
			"Vortschrittsanzeige aller Button ausblenden");

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
			addKeyListener(kbl);
			addWindowListener(fl);
			setLayout(new BorderLayout());
			pbAusblenden.addActionListener(new mbListener());
			menuReiter.add(pbAusblenden);
			mb.add(menuReiter);
			setJMenuBar(mb);

			sb1 = new SoundBoard(this, 8, 6);

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

	private class KeyboardListener implements KeyListener {

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

	public class mbListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == pbAusblenden) {
				if (pbAusblenden.getLabel().compareTo(
						"Vortschrittsanzeige aller Button einblenden") != 0) {
					sb1.pbAusblenden();
					pbAusblenden
							.setText("Vortschrittsanzeige aller Button einblenden");
				} else {
					sb1.pbEinblenden();
					pbAusblenden
							.setText("Vortschrittsanzeige aller Button ausblenden");
				}

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
