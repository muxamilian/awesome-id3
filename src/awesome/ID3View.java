package awesome;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

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
	}
}
