package awesome;

import javax.swing.JFileChooser;

public class ID3Controller {
	
	//Singleton pattern variable
	private static ID3Controller controller;
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
		controller = new ID3Controller();
		controller.initViewAndShow();
	}
	
	/**
	 * Offers access to the singleton ID3Controller object which was
	 * created during startup.
	 * @return the ID3Controller
	 */
	
	public static ID3Controller getController() {
		return controller;
	}
	
	public ID3Controller() {
		// TODO: fileChooser in it's own window, 
		// then a window to edit something shows up.
		musicLib = chooseMusicLibrary();
	}
	
	private void initViewAndShow(){
		view = new ID3View();
		view.setVisible(true);
	}
	
	private MusicLibrary chooseMusicLibrary() {
		System.out.println("Choose Music Lib");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION){ //view may be null here, no problem
			return new MusicLibrary(new Directory(fileChooser.getSelectedFile()));
		} else if(view != null){
			return musicLib;
		} else {
			System.exit(0);
			return null; //never called
		}
	}

	// very wise! -- Max
	public void exitApplication(){
		System.exit(0); //TODO: should check for dirty data and save it before exiting.
	}
	
	public ID3View getView(){
		return view;
	}
}
