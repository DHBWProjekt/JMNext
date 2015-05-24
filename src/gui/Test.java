package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public class Test extends JFrame {
	private KeyboardListener kbl = new KeyboardListener();
	private SoundBoard sb;
	private String name = "";
	private FensterListener fl = new FensterListener();
	private JMenuBar mb = new JMenuBar();
	private JMenu menuReiter = new JMenu("Einstellungen");
	private JMenuItem pbAusblenden = new JMenuItem(
			"Vortschrittsanzeige aller Button ausblenden");

	private GridLayout gl = new GridLayout(10, 8);

	// private GridBagLayout gbl = new GridBagLayout();
	// private GridBagConstraints c = new GridBagConstraints();

	public Test() {
		try {
			addKeyListener(kbl);
			addWindowListener(fl);
			setLayout(new BorderLayout());
			pbAusblenden.addActionListener(new mbListener());
			menuReiter.add(pbAusblenden);
			mb.add(menuReiter);
			setJMenuBar(mb);

			sb = new SoundBoard(8, 6);
			add(sb, BorderLayout.CENTER);
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
		Test myTest = new Test();
	}

	private class FensterListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("closing");
			sb.saveSoundboard();

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
				sb.undoChange();
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
					sb.pbAusblenden();
					pbAusblenden
							.setText("Vortschrittsanzeige aller Button einblenden");
				} else {
					sb.pbEinblenden();
					pbAusblenden
							.setText("Vortschrittsanzeige aller Button ausblenden");
				}

			}

		}
	}
}
