package awesome;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import org.w3c.dom.Document;

/**
 * This class is used to store the current working directory and (later)
 * to encapsulate the xml-cache functions. Only one MusicLibrary should exist
 * at the same time.
 * @author me
 *
 */

public class MusicLibrary {
	private Directory rootDir;
	private File rootAsFile;
	private File xmlLocation;
	
	
	public MusicLibrary(Directory rootDir, File directory){
		this.rootDir = rootDir;
		this.rootAsFile = directory;
		this.xmlLocation = new File(directory.getAbsolutePath()+"/cache.xml");
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
	
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document doc;
	public void saveXML() throws Exception {	
		// initializiation
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();
		
		// root elem
		doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("musicLibrary");
		doc.appendChild(rootElement);
	
		// document elems
		rootElement.appendChild(buildDirTree(rootDir));
		
		// write XML
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		
		StreamResult result =  new StreamResult(xmlLocation);
		transformer.transform(source, result);
	}
	
	Element buildDirTree(FilePathInfo file) {
		if(file instanceof MP3File) {
			file = (MP3File) file;
			Element mp3 = doc.createElement("mp3");
			Element title = doc.createElement("title");
			title.setTextContent(((MP3File) file).getTitle());
			Element artist = doc.createElement("artist");
			artist.setTextContent(((MP3File) file).getArtist());
			Element album = doc.createElement("album");
			album.setTextContent(((MP3File) file).getAlbum());
			Element year = doc.createElement("year");
			year.setTextContent(((MP3File) file).getYear());
			Element cover = doc.createElement("cover");
			// TODO: set cover base64 encoded.
			// use apache commons lib. it is already included
			
			mp3.appendChild(title);
			mp3.appendChild(artist);
			mp3.appendChild(album);
			mp3.appendChild(year);
			mp3.appendChild(cover);
			
			return mp3;
		} else {
			Element dir = doc.createElement("directory");
			for(FilePathInfo fpi:file.listFiles()) {
				dir.appendChild(buildDirTree(fpi));
			}
			return dir;
		}
	}
	
	public void saveAllDirtyFiles() throws IOException{
		saveDirtyMP3s(rootDir);
		try {
			saveXML();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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





