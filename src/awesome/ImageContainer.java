package awesome;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class ImageContainer extends JPanel {
	private static final long serialVersionUID = 6174421452105025008L;
	private BufferedImage img;
	private int width;
	private int height;
	
	public ImageContainer(BufferedImage img) {
		this.img = img;
		this.width = img.getWidth();
		this.height = img.getHeight();
		setSize(width, height);
	}
	
    public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null); // see javadoc for more info on the parameters            
    }
}
