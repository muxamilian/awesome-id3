package awesome;

import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;

public class AwesomeID3 {

	// Singleton pattern variable
	private static AwesomeID3 controller;
	private MusicLibrary musicLib;

	/**
	 * @return the musicLib
	 */
	public MusicLibrary getMusicLibrary() {
		return musicLib;
	}

	private ID3View view = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		controller = new AwesomeID3();
		//for mac os x menu bar
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		controller.chooseMusicLibrary(); //ask the user for music directory
		controller.initViewAndShow(); //init View
	}

	/**
	 * Offers access to the singleton ID3Controller object which was created
	 * during startup.
	 * 
	 * @return the ID3Controller
	 */

	public static AwesomeID3 getController() {
		return controller;
	}

	public AwesomeID3() {
	}

	private void initViewAndShow() {
		view = new ID3View();
		view.setVisible(true);
	}

	private void chooseMusicLibrary() {

		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) { // view may be null here, no problem
						Directory rootDir = new Directory(fileChooser.getSelectedFile());
						try {
							DirectoryWalker.buildFileTree(rootDir); //scan for mp3s
						} catch (IOException e) {
							if(view != null){
								view.presentException(e);
							}
						}
						AwesomeID3.getController().setMusicLibrary(new MusicLibrary(rootDir)); //for later access
					} else if (view != null) {
						// nothing
						return;
					} else {
						System.exit(0); //no mp3s => no tag editor
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setMusicLibrary(MusicLibrary musicLib) {
		this.musicLib = musicLib;
	}

	public void exitApplication() {
		if (musicLib.checkDirty()) { //unsaved mp3s?
			if (view.askUserForDirtyFiles()) { //does the user want to save?
				try {
					musicLib.saveAllDirtyFiles(); //save it
				} catch (IOException e) {
					view.presentException(e);
				}
			}
		}
		System.exit(0);
	}

	public ID3View getView() {
		return view;
	}
}
