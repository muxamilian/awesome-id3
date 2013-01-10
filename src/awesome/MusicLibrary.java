package awesome;

import java.io.File;
import java.io.IOException;

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
	private File xmlLocation;
	
	
	public MusicLibrary(Directory rootDir){
		this.rootDir = rootDir;
		this.xmlLocation = new File(rootDir.getFile(), "cache.xml");
	}
	
	/**
	 * @return the rootDir
	 */
	public Directory getRootDirectory() {
		return rootDir;
	}
	
	public boolean isCacheCurrent() {
		return xmlLocation.lastModified() >= rootDir.getFile().lastModified();
	}
	
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document doc;
	
	public void readXML() throws Exception {
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse(xmlLocation);
		
		// Todo call buildFromCache on the root diretory tag and replace the music lib with the value
	}
	
	FilePathInfo buildFromCache(Element elem) {
		NodeList children = elem.getChildNodes();
		if(elem.getNodeName() == "file") {
			MP3File mp3 = new MP3File(new File(elem.getAttribute("path")));
			mp3.setTitle(elem.getElementsByTagName("title").item(0).getNodeValue());
			mp3.setArtist(elem.getElementsByTagName("artist").item(0).getNodeValue());
			mp3.setAlbum(elem.getElementsByTagName("album").item(0).getNodeValue());
			mp3.setYear(elem.getElementsByTagName("year").item(0).getNodeValue());
			Element cover = (Element) elem.getElementsByTagName("cover").item(0);
			mp3.setCover(DatatypeConverter.parseBase64Binary(cover.getElementsByTagName("data").item(0).getNodeValue()));
			mp3.setCoverMime(cover.getAttribute("mimetype"));
			mp3.setCoverDescription(cover.getElementsByTagName("description").item(0).getNodeValue());
			return mp3;
		} else {
			Directory directory = new Directory(new File(elem.getAttribute("path")));
			for(int i=0; i<children.getLength(); i++) {
				directory.addChild(buildFromCache((Element) children.item(i)));
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
		Element rootElement = doc.createElement("cache");
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
			Element mp3 = doc.createElement("file");
			mp3.setAttribute("name", f.getFile().getName());
			Element title = doc.createElement("title");
			title.setTextContent(f.getTitle());
			Element artist = doc.createElement("artist");
			artist.setTextContent(f.getArtist());
			Element album = doc.createElement("album");
			album.setTextContent(f.getAlbum());
			Element year = doc.createElement("year");
			year.setTextContent(f.getYear());
			
			mp3.appendChild(title);
			mp3.appendChild(artist);
			mp3.appendChild(album);
			mp3.appendChild(year);
			
			if(f.getCover() != null){
				Element cover = doc.createElement("cover");
				cover.setAttribute("size", ""+f.getCover().length);
				Element mimetype = doc.createElement("mimetype");
				mimetype.setTextContent(f.getCoverMimeType());
				cover.appendChild(mimetype);
				Element description = doc.createElement("description");
				description.setTextContent(f.getCoverDescription());
				cover.appendChild(description);
				Element data = doc.createElement("data");
				data.setTextContent(DatatypeConverter.printBase64Binary(f.getCover()));
				cover.appendChild(data);
				mp3.appendChild(cover);
			}
			
			return mp3;
		} else {
			Element dir = doc.createElement("folder");
			for(FilePathInfo fpi:file.listFiles()) {
				dir.appendChild(buildDirTree(fpi));
			}
			dir.setAttribute("name", file.getFile().getName());
			return dir;
		}
	}
	
	public void saveAllDirtyFiles() throws IOException{
		saveDirtyMP3s(rootDir);
		try {
			saveXML();
		} catch (Exception e) {
			AwesomeID3.getController().getView().presentException(e);
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





