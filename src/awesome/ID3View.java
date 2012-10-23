package awesome;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.awt.event.*;

public class ID3View extends JFrame {
	
	private JTextField titleField;
	private JTextField albumField;
	private JTextField yearField;
	private JTextField artistField;
	
	public ID3View() {
		
		File homeDir = FileSystemView.getFileSystemView().getHomeDirectory();
		
		setSize(500, 500);
		setTitle("awesome-id3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //TODO: Change to ID3Controller.exitApplication()
		
		//TODO: Maybe we should create a split pane here so that the user can resize the tree.
		createTree();
		createMenu();
		createDetailForm();
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
	
	/**
	 * creates and initializes the tree used to present the directory structure.
	 */
	
	private void createTree(){
		JTree tree = new JTree();
		tree.setPreferredSize(new Dimension(150, 1000));
		tree.setMinimumSize(new Dimension(150, 1000));
		
		JScrollPane scrollPane = new JScrollPane();		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(150, 1));
		
		scrollPane.add(tree);
		
		getContentPane().add(scrollPane, BorderLayout.WEST);
	}
	
	
	/**
	 * creates and initializes the fields used to display and modify the
	 * detailed information of a mp3 file.
	 */
	
	private void createDetailForm(){
		
		//TODO: Change layout manager
		
		JPanel detailPanel = new JPanel();
		JPanel textDetailPanel = new JPanel();
		
		textDetailPanel.setLayout(new GridLayout(4,1));
		
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Title:");
		titlePanel.add(titleLabel, BorderLayout.WEST);
		titleField = new JTextField(25);
		titlePanel.add(titleField, BorderLayout.CENTER);
		textDetailPanel.add(titlePanel);
		
		JPanel albumPanel = new JPanel();
		JLabel albumLabel = new JLabel("Album:");
		albumPanel.add(albumLabel, BorderLayout.WEST);
		albumField = new JTextField(25);
		albumPanel.add(albumField, BorderLayout.CENTER);
		textDetailPanel.add(albumPanel);
		
		JPanel yearPanel = new JPanel();
		JLabel yearLabel = new JLabel("Year:");
		yearPanel.add(yearLabel, BorderLayout.WEST);
		yearField = new JTextField(25);
		yearPanel.add(yearField, BorderLayout.CENTER);
		textDetailPanel.add(yearPanel);
		
		JPanel artistPanel = new JPanel();
		JLabel artistLabel = new JLabel("Artist:");
		artistPanel.add(artistLabel, BorderLayout.WEST);
		artistField = new JTextField(25);
		artistPanel.add(artistField, BorderLayout.CENTER);
		textDetailPanel.add(artistPanel);
		
		detailPanel.add(textDetailPanel, BorderLayout.CENTER);	
		
		getContentPane().add(detailPanel, BorderLayout.CENTER);
	}
}
