import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class ImageContainer extends JPanel {
	private BufferedImage img;
	private int width;
	private int height;
	
	public ImageContainer(BufferedImage img) {
		this.img = img;
		this.width = img.getWidth();
		this.height = img.getHeight();
		setSize(width, height);
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null); // see javadoc for more info on the parameters            
    }
}
