import java.io.File;

// holds the information visible in a node.
// you can retrieve the file from a node.
public class FilePathInfo {
	private File file;
	
	public File getFile() {
		return file;
	}

	public FilePathInfo(File file) {
		this.file = file;
	}
	
	@Override
	public String toString() {
		return file.getName();
	}
}
