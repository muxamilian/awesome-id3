package awesome;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MP3File implements FilePathInfo {
	
	private File file;
	private String title = null;
	private String artist = null;
	private String album = null;
	private String year = null;
	private boolean dirty = false; //indicates whether an attribute has changed
	private int tagSize = -1; //not including the ten header bytes
	private int headerFlags = 0;
	private String coverMimeType = "";
	
	private byte[] cover = null; //not null to indicate that cover hasn't been parsed yet
	
	private List<ID3Frame> unknownFrames;
	
	public void setCover(byte[] cover) {
		this.cover = cover;
	}

	public void setCoverMime(String coverMime) {
		this.coverMimeType = coverMime;
	}

	public MP3File(File file){
		this.file = file; 
		this.unknownFrames = new ArrayList<ID3Frame>();
	}
	
	public boolean isDirty()
	{
		return dirty;
	}
	
	@Override
	public File getFile() {
		return file;
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public String toString() {
		return file.getName();
	}

	@Override
	public List<FilePathInfo> listFiles() {
		return null;
	}
	
	public String getCoverMimeType() {
		return coverMimeType;
	}

	

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		dirty |= !(title.equals(this.title));
		this.title = title;
	}

	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		dirty |= !(artist.equals(this.artist));
		this.artist = artist;
	}

	/**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		dirty |= !(album.equals(this.album));
		this.album = album;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		dirty |= !(year.equals(this.year));
		this.year = year;
	}

	/**
	 * @return the cover
	 */
	public byte[] getCover() {
		return cover;
	}

	/**
	 * @param cover the cover to set
	 */
	public void deleteCover() {
		dirty |= (cover != null);
		this.cover = null;	
		this.coverMimeType = "";
	}

	public void setCoverMimeType(String string) {
		coverMimeType = string;
	}

	public void setDirty(boolean b) {
		dirty = b;		
	}

	protected void addUnknownFrame(ID3Frame id3Frame) {
		unknownFrames.add(id3Frame);
	}

	protected void setHeaderFlags(int flags) {
		headerFlags = flags;
	}

	protected void setTagSize(int size) {
		tagSize = size;		
	}

	protected int getTagSize() {
		return tagSize;
	}

	protected List<ID3Frame> getUnknownFrames() {
		return unknownFrames;
	}

	protected int getHeaderFlags() {
		return headerFlags;
	}
	
}
