package awesome;

/**
 * stores the data of an unknown id3 frame.
 * @author me
 *
 */

public class ID3Frame {
	
	private String id;
	private int size;
	private int flags;
	private byte[] data;
	
	/**
	 * init with all necessary data.
	 * @param id
	 * @param size
	 * @param flags
	 * @param data
	 */
	
	public ID3Frame(String id, int size, int flags, byte[] data) {
		this.id = id;
		this.size = size;
		this.flags = flags;
		this.data = data;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the flags
	 */
	public int getFlags() {
		return flags;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

}
