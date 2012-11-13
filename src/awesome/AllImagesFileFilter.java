package awesome;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class AllImagesFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) return true;
		for(String suff : javax.imageio.ImageIO.getReaderFileSuffixes()){
			if(f.getName().toLowerCase().endsWith(suff)) return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "Images supported by Java ImageIO";
	}

}
