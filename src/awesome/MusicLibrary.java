package awesome;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
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
	
	public boolean isCacheCurrent() {
		return xmlLocation.lastModified() >= rootAsFile.lastModified();
	}
	
	/**
	 * save recursively all files which were modfied.
	 * @throws IOException
	 */
	
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document doc;
	
	public void readXML() throws Exception {
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse(xmlLocation);
		
		Node rootNode = doc.getChildNodes().item(0); // should be only one because it is the root dir
	}
	
	FilePathInfo buildFromCache(Element elem) {
		NodeList children = elem.getChildNodes();
		if(elem.getNodeName() == "mp3") {
			MP3File mp3 = new MP3File(new File(elem.getAttribute("path")));
			mp3.setTitle(elem.getElementsByTagName("title").item(0).getNodeValue());
			mp3.setArtist(elem.getElementsByTagName("artist").item(0).getNodeValue());
			mp3.setAlbum(elem.getElementsByTagName("album").item(0).getNodeValue());
			mp3.setYear(elem.getElementsByTagName("year").item(0).getNodeValue());
			Node cover = elem.getElementsByTagName("cover").item(0);
			mp3.setCover(DatatypeConverter.parseBase64Binary(cover.getNodeValue()));
			mp3.setCoverMime(((Element) cover).getAttribute("mime"));
			return mp3;
		} else {
			Directory directory = new Directory(new File(elem.getAttribute("path")));
			for(int i=0; i<children.getLength(); i++) {
				buildFromCache((Element) children.item(i));
				// TODO: Must add method to add files to a Director
			}
			return directory;
		}
	}
	
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
			MP3File f = (MP3File) file;
			Element mp3 = doc.createElement("mp3");
			mp3.setAttribute("path", f.getFile().getAbsolutePath());
			Element title = doc.createElement("title");
			title.setTextContent(f.getTitle());
			Element artist = doc.createElement("artist");
			artist.setTextContent(f.getArtist());
			Element album = doc.createElement("album");
			album.setTextContent(f.getAlbum());
			Element year = doc.createElement("year");
			year.setTextContent(f.getYear());
			Element cover = null;
			if(f.getCover() != null){
				cover = doc.createElement("cover");
				cover.setAttribute("mime", f.getCoverMimeType());
				cover.setTextContent(DatatypeConverter.printBase64Binary(f.getCover()));
			}
			
			mp3.appendChild(title);
			mp3.appendChild(artist);
			mp3.appendChild(album);
			mp3.appendChild(year);
			if( cover != null)
				mp3.appendChild(cover);
			
			return mp3;
		} else {
			Element dir = doc.createElement("directory");
			for(FilePathInfo fpi:file.listFiles()) {
				dir.appendChild(buildDirTree(fpi));
			}
			dir.setAttribute("path", file.getFile().getAbsolutePath());
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
				if(mp3.isDirty())
					ID3Parser.save(mp3);
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
}





