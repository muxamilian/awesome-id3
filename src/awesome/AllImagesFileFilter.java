package awesome;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class AllImagesFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return  f.isDirectory() ||
				f.getName().toLowerCase().endsWith(".jpg") ||
				f.getName().toLowerCase().endsWith(".jpeg") ||
				f.getName().toLowerCase().endsWith(".png") ||
				f.getName().toLowerCase().endsWith(".gif") ||
				f.getName().toLowerCase().endsWith(".bmp");
	}

	@Override
	public String getDescription() {
		return "Images";
	}

}
