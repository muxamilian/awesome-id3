import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ID3View extends JFrame implements TreeSelectionListener {
	JTree fileTree;
	
	public ID3View() {
		// retrieves the user's home directory
		File homeDir = FileSystemView.getFileSystemView().getHomeDirectory();
		int textFieldSize = 20;
		
		// initializes layouts
		GridLayout mainGrid = new GridLayout(1,2);
		setLayout(mainGrid);
		JPanel leftPanel = new JPanel(new GridLayout(1,1));
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		// initializes the tree
		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode(new FilePathInfo(homeDir));
		fileTree = new JTree(topNode);
		fileTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		fileTree.addTreeSelectionListener(this);
		// packs the tree into a scroll pane.
		JScrollPane treePane = new JScrollPane(fileTree);
		
		// creates labels and textfields visible on the right
		JLabel title = new JLabel("<html><b>Title</b></html>");
		JTextField titleField = new JTextField(textFieldSize);
		JLabel artist = new JLabel("<html><b>Artist</b></html>");
		JTextField artistField = new JTextField(textFieldSize);
		JLabel album = new JLabel("<html><b>Album</b></html>");
		JTextField albumField = new JTextField(textFieldSize);
		JLabel year = new JLabel("<html><b>Year</b></html>");
		JTextField yearField = new JTextField(textFieldSize);
		JPanel cover = new ImageContainer(getDemoCoverImage());
		rightPanel.add(title, BorderLayout.LINE_START);
		
		// adds the widgets into the layouts on the right.
		// rather boring code...
		JPanel titlePanel = new JPanel(new GridLayout(2,1));
		titlePanel.add(title); titlePanel.add(titleField);
		JPanel artistPanel = new JPanel(new GridLayout(2,1));
		artistPanel.add(artist); artistPanel.add(artistField);
		JPanel albumPanel = new JPanel(new GridLayout(2,1));
		albumPanel.add(album); albumPanel.add(albumField);
		JPanel yearPanel = new JPanel(new GridLayout(2,1));
		yearPanel.add(year); yearPanel.add(yearField);
		JPanel fieldContainer = new JPanel(new GridLayout(4,1));
		fieldContainer.add(titlePanel);
		fieldContainer.add(artistPanel);
		fieldContainer.add(albumPanel);
		fieldContainer.add(yearPanel);
		
		// The layout containers are now arranged in a
		// border layout
		rightPanel.add(fieldContainer, BorderLayout.PAGE_START);
		rightPanel.add(cover, BorderLayout.CENTER);
		leftPanel.add(treePane);
		
		// Finishes ui creation and sets size and name of the frame
		add(leftPanel);
		add(rightPanel);
		setTitle("awesome-id3");
		setSize(485, 408);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Retrieves the demo image.
	// Java is horribly complicated. I want to add an image and
	// it takes 30 lines of code :o
	public static BufferedImage getDemoCoverImage()	{
		BufferedImage bufferedImage = null;
		byte[] byteStream = resources.TestImages.png;
		ByteArrayInputStream myByteBuffer = new ByteArrayInputStream(byteStream);
		MemoryCacheImageInputStream imgInputStream = new MemoryCacheImageInputStream(myByteBuffer);
		try {
			bufferedImage = ImageIO.read(imgInputStream);
		} catch (IOException e) {
			// Blablabla
			e.printStackTrace();
		}
		return bufferedImage;
	}

	@Override
	// The event handler for the tree
	public void valueChanged(TreeSelectionEvent event) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
		if(selectedNode != null) {
			Object info = selectedNode.getUserObject();
			FilePathInfo fpi = (FilePathInfo) info;
			File file = fpi.getFile();
			if(file.isDirectory()) {
				// checks if the node already has children
				if(selectedNode.getChildCount() == 0) {
					File[] childNodes = file.listFiles();
					DefaultMutableTreeNode[] newNodes = nodify(childNodes);
					for(DefaultMutableTreeNode n:newNodes) {
						selectedNode.add(n);
					}
				} else {
					// if the node has children and is clicked, remove all.
					selectedNode.removeAllChildren();
				}
			} else {
				// TODO get the file's information and add it to the right.
			}
		}
	}
	
	// makes tree nodes out of an array of files
	public static DefaultMutableTreeNode[] nodify(File[] files) {
		DefaultMutableTreeNode[] ret = new DefaultMutableTreeNode[files.length];
		for(int i=0;i<files.length;i++) {
			FilePathInfo info = new FilePathInfo(files[i]);
			ret[i] = new DefaultMutableTreeNode(info);
		}
		return ret;
	}
}
