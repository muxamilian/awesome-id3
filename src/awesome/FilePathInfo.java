package awesome;
import java.io.File;
import java.util.List;

// holds the information visible in a node.
// you can retrieve the file from a node.
/**
 * represents a element in the file system tree, implemented by MP3File and Directory who
 * share common methods like getFile().
 *
 */
public interface FilePathInfo {	
	public File getFile(); //get the represented file
	public boolean isDirectory(); //determine file type
	public List<FilePathInfo> listFiles(); //list the files of a directory, null if it isn't a dir.
}
