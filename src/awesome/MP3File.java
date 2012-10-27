package awesome;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MP3File implements FilePathInfo {
	
	File file;
	private String title;
	private String artist;
	private String album;
	private String year;
	private BufferedImage cover;
	
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
		if(!f.getName().endsWith(".mp3")){
			return false; //we only respect files with mp3 suffix as we don't want read every file.
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		byte[] isRightID3 = new byte[5];
		try {
			fis.read(isRightID3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] firstThree = {isRightID3[0],isRightID3[1],isRightID3[2]};
		byte[] nextTwo = {isRightID3[3],isRightID3[4]};
		String identifier = new String(firstThree);
		byte[] rightVersionNumber = {3,0};
		return identifier.equals("ID3") && 
				Arrays.equals(nextTwo,rightVersionNumber);
	}

	public static boolean containsMP3s(File rootFile) {
		if(rootFile.isFile()) {
			return MP3File.isMP3(rootFile);
		} else {
			File[] subFiles = rootFile.listFiles();
			for(File f:subFiles) {
				if(MP3File.containsMP3s(f)) {
					return true;
				}
			}
		}
		return false;
	}
	private void parse() {
		
	}
	
}
