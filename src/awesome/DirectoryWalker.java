/**
 * 
 */
package awesome;

import java.io.File;
import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

/**
 * @author me
 *
 */
public class DirectoryWalker {
	
	/**
	 * scans the file system recursively for directories and mp3 files and add
	 * references to them to the parent object. MP3s get automatically parsed.
	 * @param musicLibrary 
	 * @param rootDir
	 * @throws IOException
	 * @throws XPathExpressionException 
	 */
	
	public static void buildFileTree(MusicLibrary library, Directory rootDir) throws Exception{
		File[] contents = rootDir.getFile().listFiles();
		for(File f : contents){
			if(f.isDirectory()){
				Directory subDir = new Directory(f);
				buildFileTree(library, subDir);
				if(containsMP3(subDir))
					rootDir.addChild(subDir);
			} else if(ID3Parser.isMP3(f)){
				MP3File mp3 = library.parseMP3FromCache(f);
				if(mp3 == null) {
					mp3 = new MP3File(f);
					ID3Parser.parseID3(mp3);
				} else
					System.out.println("Parsed from Cache: " + f);
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
