package awesome;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Directory implements FilePathInfo {
	
	private File file;
	private ArrayList<FilePathInfo> subFiles;
	
	public Directory(File file){
		this.file = file;
		
		File[] contents = file.listFiles();
		subFiles = new ArrayList<FilePathInfo>(contents.length);
		for(File f : contents){
			if(f.isDirectory()){
				subFiles.add(new Directory(f));
			} else if(MP3File.isMP3(f)){
				subFiles.add(new MP3File(f));
			}
		}
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
