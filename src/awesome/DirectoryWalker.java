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
	
	private static boolean containsMP3(Directory subDir) {
		return !subDir.listFiles().isEmpty();
	}
}
