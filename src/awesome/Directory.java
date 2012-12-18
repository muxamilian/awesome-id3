package awesome;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Directory implements FilePathInfo {
	
	private File file;
	private ArrayList<FilePathInfo> subFiles;
	
	public Directory(File file){
		this.file = file;
		subFiles = new ArrayList<FilePathInfo>();
	}
	
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
