import java.io.File;


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
