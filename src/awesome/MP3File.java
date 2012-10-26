package awesome;

import java.io.File;
import java.util.List;

public class MP3File implements FilePathInfo {
	
	File file;
	
	public MP3File(File file){
		this.file = file; //TODO: Parse Info?
	}
	
	@Override
	public File getFile() {
		return file;
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public String toString() {
		return file.getName();
	}

	@Override
	public List<FilePathInfo> listFiles() {
		return null;
	}

	public static boolean isMP3(File f) {
		return f.getName().endsWith(".mp3"); //TODO: Add better MP3 recognition here
	}

}
