package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Test extends JFrame {
	private ListenerMouseKlick lmk = new ListenerMouseKlick();
	private SoundButton[][] sbArray = new SoundButton[10][8];
	private String name = "";

	private GridLayout gl = new GridLayout(10, 8);

	// private GridBagLayout gbl = new GridBagLayout();
	// private GridBagConstraints c = new GridBagConstraints();

	public Test() {
		setLayout(new BorderLayout());
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		// setLayout(new GridLayout(10, 8));

		// for (int i = 0; i < 25; i++) {
		// name = "Button ".concat(String.valueOf(i + 1));
		// sbArray[i] = new SoundButton(name);
		// sbArray[i].addMouseListener(lmk);
		// add(sbArray[i]);
		// }

		// getContentPane().setLayout(new GridBagLayout());
		// c.fill = GridBagConstraints.BOTH;
		// c.weightx = 0.1;
		// c.weighty = 0.1;

		// for (int z = 0; z < sbArray.length; z++) {
		// for (int sp = 0; sp < sbArray[z].length; sp++) {
		// // c.gridx = sp;
		// // c.gridy = z;
		// sbArray[z][sp] = new SoundButton(
		// String.valueOf(sbArray[z].length * z + sp));
		// sbArray[z][sp].addMouseListener(lmk);
		// add(sbArray[z][sp]);
		//
		// }
		// }
		SoundBoard sb = new SoundBoard(8, 6);
		add(sb, BorderLayout.CENTER);
		setSize(1000, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		Test myTest = new Test();
	}

	private class ListenerMouseKlick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				System.out.println("Rechtsklick: " + e.getSource());
				for (int sp = 0; sp < sbArray.length; sp++) {
					for (int z = 0; z < sbArray[sp].length; z++) {
						if (e.getSource() == sbArray[sp][z]) {
							sbArray[sp][z].setShuffle();
						}
					}
				}

			} else if (SwingUtilities.isLeftMouseButton(e)) {
				System.out.println("Linksklick: " + e.getSource());
				for (int sp = 0; sp < sbArray.length; sp++) {
					for (int z = 0; z < sbArray[sp].length; z++) {
						if (e.getSource() == sbArray[sp][z]) {
							sbArray[sp][z].setOneSong();
							// System.out.println(sbArray[sp][z].getLocation());
							// remove(sbArray[sp][z]);
							// validate();
							// repaint();

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
}
