package lib;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public abstract class Browse {
	private static JFileChooser fc = new JFileChooser();
	// private static JFileChooser fc = new JFileChooser(
	// "/Users/SCCplayer/Desktop/Musik");
	private static File musicFile;
	private static File musicFolder;
	private static File[] fileArray;
	private static File[] musicFileArray;
	private static FileFilter musicFileFilter = new FileNameExtensionFilter(
			"Musik", "mp3", "wav", "m4a");
	private static FileFilter speicherFileFilter = new FileNameExtensionFilter(
			"Java .ser", "ser");

	public static File getMusicFile() {
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(musicFileFilter);
		fc.setFileFilter(musicFileFilter);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getSaveFile() {
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(speicherFileFilter);
		fc.setFileFilter(speicherFileFilter);
		int auswahl = fc.showSaveDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getOpenFile() {
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.addChoosableFileFilter(speicherFileFilter);
		fc.setFileFilter(speicherFileFilter);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFile = fc.getSelectedFile();
		}
		return musicFile;
	}

	public static File getMusicFolder() {
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFolder = new File(fc.getSelectedFile().getPath());
		}
		return musicFolder;
	}

	public static File getMusicFileFolder() {
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.addChoosableFileFilter(musicFileFilter);
		fc.setFileFilter(musicFileFilter);
		int auswahl = fc.showOpenDialog(null);
		if (auswahl == JFileChooser.APPROVE_OPTION) {
			musicFolder = new File(fc.getSelectedFile().getPath());
			System.out.println(musicFolder.listFiles());
		}
		return musicFolder;
	}

	public static File[] getMusicFileArray(File musicFolder) {
		fileArray = musicFolder.listFiles();
		int musicFileCounter = 0;
		for (int i = 0; i < fileArray.length; i++) {
			if (fileArray[i].getName().endsWith(".mp3") == true
					|| fileArray[i].getName().endsWith(".m4a") == true
					|| fileArray[i].getName().endsWith(".mp3") == true
					|| fileArray[i].getName().endsWith(".wav") == true) {
				musicFileCounter++;
			}
		}
		musicFileArray = new File[musicFileCounter];
		musicFileCounter = 0;
		// Schleife um MusicData mit daten zu füllen
		for (int i = 0; i < fileArray.length; i++) {
			if (fileArray[i].getName().endsWith(".mp3") == true
					|| fileArray[i].getName().endsWith(".m4a") == true
					|| fileArray[i].getName().endsWith(".mp3") == true
					|| fileArray[i].getName().endsWith(".wav") == true) {
				musicFileArray[musicFileCounter] = fileArray[i];
				musicFileCounter++;
			}
		}
		return musicFileArray;
	}
}
