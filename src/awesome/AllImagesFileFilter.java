package awesome;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class AllImagesFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		for(String suff : javax.imageio.ImageIO.getReaderFileSuffixes()){
			if(f.getName().toLowerCase().endsWith(suff)) return true;
		}
		return false;
	}
	
	public static boolean isPNGorJPEG(File f){
		return  f.getName().toLowerCase().endsWith(".jpg") ||
				f.getName().toLowerCase().endsWith(".jpeg") ||
				f.getName().toLowerCase().endsWith(".png");
	}

	@Override
	public String getDescription() {
		return "Images supported by Java ImageIO";
	}

}
