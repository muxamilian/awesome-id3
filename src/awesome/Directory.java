package awesome;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

/**
 * represents a diretory on the file system.
 *
 */

public class Directory implements FilePathInfo {
	
	private File file;
	private ArrayList<FilePathInfo> subFiles;
	
	/**
	 * inits the directory with the given file object and empty child list.
	 * @param file
	 */
	
	public Directory(File file){
		this.file = file;
		subFiles = new ArrayList<FilePathInfo>();
	}
	
	/**
	 * adds a contained file to the directory.
	 * @param fpi
	 */
	
	public void addChild(FilePathInfo fpi){
		subFiles.add(fpi);
	}
	
	@Override
	public File getFile() {
		return file;
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	@Override
	public String toString() {
		return file.getName() + "/";
	}

	@Override
	public List<FilePathInfo> listFiles() {
		return subFiles;
	}

}
