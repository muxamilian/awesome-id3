package awesome;

import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;

public class AwesomeID3 {
	
	//Singleton pattern variable
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
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		controller.chooseMusicLibrary();
		controller.initViewAndShow();
	}
	
	/**
	 * Offers access to the singleton ID3Controller object which was
	 * created during startup.
	 * @return the ID3Controller
	 */
	
	public static AwesomeID3 getController() {
		return controller;
	}
	
	public AwesomeID3() {
	}
	
	private void initViewAndShow(){
		view = new ID3View();
		view.setVisible(true);
	}
	
	private void chooseMusicLibrary() {
		
		try {
			EventQueue.invokeAndWait( new Runnable()
			{
			  public void run() {
				  JFileChooser fileChooser = new JFileChooser();
				  fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				  if(fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION){ //view may be null here, no problem
						AwesomeID3.getController().setMusicLibrary(new MusicLibrary(new Directory(fileChooser.getSelectedFile()), fileChooser.getSelectedFile()));
					} else if(view != null){
						//nothing
						return;
					} else {
						System.exit(0);
					}
			  }
			} );
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void setMusicLibrary(MusicLibrary musicLib) {
		this.musicLib = musicLib;
	}

	public void exitApplication(){		
		if(musicLib.checkDirty()){
			if(view.askUserForDirtyFiles()){
				try {
					musicLib.saveAllDirtyFiles();
				} catch (IOException e) {
					view.presentException(e);
				}
			}
		}
		System.exit(0);
	}
	
	public ID3View getView(){
		return view;
	}
}
