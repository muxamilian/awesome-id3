package awesome;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class ImageContainer extends JPanel {
	private static final long serialVersionUID = 6174421452105025008L;
	private BufferedImage img;
	private int verticalPadding;
	private int horizontalPadding;
	
	public ImageContainer(BufferedImage img) {
		this.img = img;
	}
	
	public void setImage(BufferedImage img) {
		this.img = img;
	}

	/**
	 * @see javax.swing.JComponent#getMaximumSize()
	 */
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(img.getWidth(), img.getHeight());
	}

	/**
	 * @return the verticalPadding
	 */
	public int getVerticalPadding() {
		return verticalPadding;
	}

	/**
	 * @param verticalPadding the verticalPadding to set
	 */
	public void setVerticalPadding(int verticalPadding) {
		this.verticalPadding = verticalPadding;
	}

	/**
	 * @return the horizontalPadding
	 */
	public int getHorizontalPadding() {
		return horizontalPadding;
	}

	/**
	 * @param horizontalPadding the horizontalPadding to set
	 */
	public void setHorizontalPadding(int horizontalPadding) {
		this.horizontalPadding = horizontalPadding;
	}

	/**
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return getMaximumSize();
	}



	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        //scale image to current size and fill whole area
        double scaleFactor = Math.min((getWidth()-horizontalPadding*2) / (double)img.getWidth(), (getHeight()-verticalPadding*2) / (double)img.getHeight());
        int x = 0;
        if(scaleFactor > 1.0){
        	scaleFactor = 1.0;
        	x = ((getWidth()-horizontalPadding*2) - img.getWidth()) / 2;
        }
        g.drawImage(img.getScaledInstance((int)(img.getWidth()*scaleFactor), 
        									(int)(img.getHeight()*scaleFactor), Image.SCALE_DEFAULT), horizontalPadding + x, verticalPadding, null); 
        // see javadoc for more info on the parameters            
    }
}
