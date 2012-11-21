package awesome;

import java.io.File;
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
	
	/**
	 * save recursively all files which were modfied.
	 * @throws IOException
	 */
	
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
	
	public boolean checkDirty()
	{		
		return checkForDirtyMP3s(rootDir);
	}
		
	private boolean checkForDirtyMP3s(Directory dir) {		
		for(FilePathInfo fpi : dir.listFiles()){
			if(fpi.isDirectory()){
				if(checkForDirtyMP3s((Directory)fpi)) return true;				
			}
			else {
				MP3File mp3 = (MP3File) fpi;
				if(mp3.isDirty()) 
					return true;
			}			
		}	
		
		return false;
	}
	
	public static boolean containsMP3s(File rootFile) {
		if(rootFile.isFile()) {
			return MP3File.isMP3(rootFile);
		} else if(rootFile.isDirectory()){
			File[] subFiles = rootFile.listFiles();
			for(File f:subFiles) {
				if(containsMP3s(f)) {
					return true;
				}
			}
		}
		return false;
	}
}





