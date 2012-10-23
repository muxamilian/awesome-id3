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
		setTitle("Awesome ID3");
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
		
		JPanel detailPanel = new JPanel();
		detailPanel.setLayout(new BorderLayout());
		
		JPanel textDetailPanel = new JPanel();
		GridBagLayout textDetailLayout = new GridBagLayout();
		textDetailPanel.setLayout(textDetailLayout);
		GridBagConstraints textDetailConstraints = 
				new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 4, 2,
						0.0, 0.0, GridBagConstraints.BASELINE_LEADING, GridBagConstraints.HORIZONTAL, new Insets(10,20,0,5), 0, 0);
		GridBagConstraints textDetailConstraintsFill = (GridBagConstraints) textDetailConstraints.clone();
		textDetailConstraintsFill.weightx = 0.5;
		textDetailConstraintsFill.insets = new Insets(10,5,0,20);
		
		JLabel titleLabel = new JLabel("Title:");
		textDetailPanel.add(titleLabel, textDetailConstraints);
		titleField = new JTextField(25);
		textDetailPanel.add(titleField, textDetailConstraintsFill);
		
		JLabel albumLabel = new JLabel("Album:");
		textDetailPanel.add(albumLabel, textDetailConstraints);
		albumField = new JTextField(25);
		textDetailPanel.add(albumField, textDetailConstraintsFill);
		
		textDetailConstraints.gridy = 2;
		textDetailConstraintsFill.gridy = 2;
		
		JLabel yearLabel = new JLabel("Year:");
		textDetailPanel.add(yearLabel, textDetailConstraints);
		yearField = new JTextField(25);
		textDetailPanel.add(yearField, textDetailConstraintsFill);
		
		JLabel artistLabel = new JLabel("Artist:");
		textDetailPanel.add(artistLabel, textDetailConstraints);
		artistField = new JTextField(25);
		textDetailPanel.add(artistField, textDetailConstraintsFill);
		
		detailPanel.add(textDetailPanel, BorderLayout.NORTH);	
		
		getContentPane().add(detailPanel, BorderLayout.CENTER);
	}
}
