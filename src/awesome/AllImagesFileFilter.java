package awesome;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * FileFilter for Images supported by ImageIO.
 *
 */

public class AllImagesFileFilter extends FileFilter {
	
	/**
	 * see FileFilter.accept()
	 */
	
	@Override
	public boolean accept(File f) {
		for(String suff : javax.imageio.ImageIO.getReaderFileSuffixes()){
			if(f.getName().toLowerCase().endsWith("." + suff)) return true;
		}
		return f.isDirectory();
	}
	
	/**
	 * checks whether a file is a PNG or JPEG file as they are handled different. 
	 * @param f
	 * @return
	 */
	
	public static boolean isPNGorJPEG(File f){
		return  f.getName().toLowerCase().endsWith(".jpg") ||
				f.getName().toLowerCase().endsWith(".jpeg") ||
				f.getName().toLowerCase().endsWith(".png");
	}
	
	/**
	 * for the file chooser ui.
	 */

	@Override
	public String getDescription() {
		return "Images supported by Java ImageIO";
	}

}
