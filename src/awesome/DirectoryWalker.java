/**
 * 
 */
package awesome;

import java.io.File;
import java.io.IOException;

/**
 * @author me
 *
 */
public class DirectoryWalker {
	
	/**
	 * scans the file system recursively for directories and mp3 files and add
	 * references to them to the parent object. MP3s get automatically parsed.
	 * @param rootDir
	 * @throws IOException
	 */
	
	public static void buildFileTree(Directory rootDir) throws IOException{
		File[] contents = rootDir.getFile().listFiles();
		for(File f : contents){
			if(f.isDirectory()){
				Directory subDir = new Directory(f);
				buildFileTree(subDir);
				if(containsMP3(subDir))
					rootDir.addChild(subDir);
			} else if(ID3Parser.isMP3(f)){
				MP3File mp3 = new MP3File(f);
				ID3Parser.parseID3(mp3);
				rootDir.addChild(mp3);
			}
		}
	}
	
	/**
	 * determines whether a directory contains at least one mp3 file in its fs-subtree
	 * @param subDir
	 * @return
	 */
	
	private static boolean containsMP3(Directory subDir) {
		return !subDir.listFiles().isEmpty();
	}
}
