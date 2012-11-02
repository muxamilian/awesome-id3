package awesome;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return  f.isDirectory() ||
				f.getName().endsWith(".jpg") ||
				f.getName().endsWith(".jpeg") ||
				f.getName().endsWith(".png");
	}

	@Override
	public String getDescription() {
		return "Images";
	}

}
