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
	private String coverMimeType = "", coverDescription = "";
	private byte[] cover = null;
	
	private List<ID3Frame> unknownFrames;
	
	/**
	 * creates a new MP3 object, but does not parse its tag.
	 * @param file
	 */
	
	public MP3File(File file){
		this.file = file; 
		this.unknownFrames = new ArrayList<ID3Frame>();
	}
	
	/**
	 * adds a new unknown frame to the internal list.
	 * @param id3Frame
	 */

	protected void addUnknownFrame(ID3Frame id3Frame) {
		unknownFrames.add(id3Frame);
	}

	/**
	 * @param cover the cover to set
	 */
	public void deleteCover() {
		dirty |= (cover != null);
		this.cover = null;	
		this.coverMimeType = "";
	}
	
	/**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}
	
	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @return the cover
	 */
	public byte[] getCover() {
		return cover;
	}

	/**
	 * @return the coverDescription
	 */
	public String getCoverDescription() {
		return coverDescription;
	}
	
	/**
	 * returns the mime type of the cover.
	 * @return
	 */

	public String getCoverMimeType() {
		return coverMimeType;
	}
	
	@Override
	public File getFile() {
		return file;
	}
	
	/**
	 * returns the header flags of the id3 tag.
	 * @return
	 */

	protected int getHeaderFlags() {
		return headerFlags;
	}
	
	/**
	 * returns the size of the id3 tag, excluding the ten header bytes.
	 * @return
	 */
	
	protected int getTagSize() {
		return tagSize;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * returns a list of all the id3 frames which have unknown ids.
	 * @return
	 */

	protected List<ID3Frame> getUnknownFrames() {
		return unknownFrames;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * always returns false.
	 */

	@Override
	public boolean isDirectory() {
		return false;
	}
	
	/**
	 * returns the status of the internal dirty bit.
	 * @return
	 */

	public boolean isDirty()
	{
		return dirty;
	}
	
	/**
	 * always returns null as it does not make sense for a file.
	 */
	@Override
	public List<FilePathInfo> listFiles() {
		return null;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		dirty |= !(album.equals(this.album));
		this.album = album;
	}

	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		dirty |= !(artist.equals(this.artist));
		this.artist = artist;
	}
	
	/**
	 * sets the cover data (internal only)
	 * @param cover
	 */

	public void setCover(byte[] cover) {
		this.cover = cover;
	}

	/**
	 * @param coverDescription the coverDescription to set
	 */
	public void setCoverDescription(String coverDescription) {
		this.coverDescription = coverDescription;
	}
	
	/**
	 * sets the mime type of the cover, usually not needed because readCoverFromFile sets it automatically.
	 * @param string
	 */
	
	public void setCoverMimeType(String string) {
		coverMimeType = string;
	}
	
	/**
	 * sets the internal dirty bit to the given value (only for internal use)
	 * @param b
	 */

	protected void setDirty(boolean b) {
		dirty = b;		
	}
	
	/**
	 * sets the header flags. only for use with cache.
	 * @param flags
	 */

	protected void setHeaderFlags(int flags) {
		headerFlags = flags;
	}
	
	/**
	 * sets the size of the id3 tag, use for cache implementation only.
	 * @param size
	 */

	protected void setTagSize(int size) {
		tagSize = size;		
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		dirty |= !(title.equals(this.title));
		this.title = title;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		String padding = "";
		for(int i = 0; i < 4-year.length(); i++){
			padding += "0";
		}
		year = padding + year;
		dirty |= !(year.equals(this.year));
		this.year = year;
	}

	@Override
	public String toString() {
		return file.getName();
	}
	
}
