package awesome;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.awt.event.*;

public class ID3View extends JFrame {
	public ID3View() {
		GridLayout mainGrid = new GridLayout(1,2);
		setLayout(mainGrid);
		
		File homeDir = FileSystemView.getFileSystemView().getHomeDirectory();
		
		JPanel leftPanel = new JPanel();
		leftPanel.add(new JLabel("Spam and eggs"));
		JPanel rightPanel = new JPanel();
		rightPanel.add(new Button("Just for fun"));
		
		add(leftPanel);
		add(rightPanel);
		setSize(500, 500);
		setTitle("awesome-id3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createMenu();
	}
	
	
	/**
	 * creates and initializes the menu bar of the frame and its subcomponents.
	 */
	
	private void createMenu(){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuMain = new JMenu("Awesome ID3");
		
		JMenuItem itemReload = new JMenuItem("Reload MP3 Files");
		JMenuItem itemChangeDir = new JMenuItem("Choose Music Directory...");
		JMenuItem itemExit = new JMenuItem("Exit Awesome ID3");
		
		itemExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ID3Controller.getController().exitApplication();
			}
			
		});
		
		menuMain.add(itemReload);
		//menuMain.addSeparator(); //maybe it's prettier
		menuMain.add(itemChangeDir);
		menuMain.addSeparator();
		menuMain.add(itemExit);
		
		menuBar.add(menuMain);
		
		this.setJMenuBar(menuBar);
	}
}
