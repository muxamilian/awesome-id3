package awesome;
import java.io.File;
import java.util.List;

// holds the information visible in a node.
// you can retrieve the file from a node.
public interface FilePathInfo {	
	public File getFile();
	public boolean isDirectory();
	public List<FilePathInfo> listFiles(); //list the files of a directory, null if it isn't a dir.
}
