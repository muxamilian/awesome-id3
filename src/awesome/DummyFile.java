package awesome;

import java.io.File;
import java.util.List;

public class DummyFile implements FilePathInfo {
	
	public DummyFile(){
	}
	
	@Override
	public File getFile() {
		return null;
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public String toString() {
		return "<empty>";
	}

	@Override
	public List<FilePathInfo> listFiles() {
		return null;
	}

}
