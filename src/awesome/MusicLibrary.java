package awesome;

import java.io.IOException;

/**
 * This class is used to store the current working directory and (later)
 * to encapsulate the xml-cache functions. Only one MusicLibrary should exist
 * at the same time.
 * @author me
 *
 */

public class MusicLibrary {
	private Directory rootDir;
	
	public MusicLibrary(Directory rootDir){
		this.rootDir = rootDir;
	}
	
	/**
	 * @return the rootDir
	 */
	public Directory getRootDirectory() {
		return rootDir;
	}
	
	public void saveAllDirtyFiles() throws IOException{
		saveDirtyMP3s(rootDir);
	}

	private void saveDirtyMP3s(Directory dir) throws IOException {
		for(FilePathInfo fpi : dir.listFiles()){
			if(fpi.isDirectory()){
				saveDirtyMP3s((Directory)fpi);
			} else {
				MP3File mp3 = (MP3File) fpi;
				mp3.save(); //MP3File does the dirty check 
			}
		}
	}
}
