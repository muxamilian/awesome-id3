package awesome;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class AwesomeID3 {

	// Singleton pattern variable
	private static AwesomeID3 controller;
	private MusicLibrary musicLib = null;

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
		if(args.length > 0 && new File(args[0]).exists())
			controller.loadMusicLibrary(new File(args[0]));
		else
			controller.chooseMusicLibrary(); //ask the user for music directory
		
		if(controller.getMusicLibrary() != null)
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
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) { // view may be null here, no problem
			loadMusicLibrary(fileChooser.getSelectedFile());
		} else /*if (view != null)*/ {
			// nothing
			return;
		}
	}
	
	/**
	 * inits a reload of the music library.
	 * @param deleteCache shall the cache file deleted?
	 */
	
	public void reloadMusicLibrary(boolean deleteCache){
		if(deleteCache)
			musicLib.deleteCache();
		loadMusicLibrary(musicLib.getRootDirectory().getFile());
	}
	
	private void loadMusicLibrary(File file) {
		Directory rootDir = new Directory(file);
		AwesomeID3.getController().setMusicLibrary(new MusicLibrary(rootDir)); //for later access
	}

	private void setMusicLibrary(MusicLibrary musicLib) {
		this.musicLib = musicLib;
	}
	
	private void handleDirty(){
		if (musicLib.checkDirty()) { //unsaved mp3s?
			if (view.askUserForDirtyFiles()) { //does the user want to save?
				try {
					musicLib.saveAllDirtyFiles(); //save it
				} catch (IOException e) {
					view.presentException(e);
				}
			}
		}
	}

	public void exitApplication() {
		handleDirty();
		System.exit(0);
	}

	public ID3View getView() {
		return view;
	}

	public void changeMusicLibrary() {
		handleDirty();
		chooseMusicLibrary();
		view.changeMusicLibrary(musicLib);
	}
}
