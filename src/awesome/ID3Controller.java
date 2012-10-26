package awesome;
public class ID3Controller {
	
	//Singleton pattern variable
	private static ID3Controller controller;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		controller = new ID3Controller();
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
		ID3View mainWindow = new ID3View();
		mainWindow.setVisible(true);
	}
	
	// very wise! -- Max
	public void exitApplication(){
		System.exit(0); //TODO: should check for dirty data and save it before exiting.
	}
}
