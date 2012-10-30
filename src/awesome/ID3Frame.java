package awesome;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ID3Frame {
	
	private String id;
	private int size;
	private int flags;
	private byte[] data;
	
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

	public void writeToStream(DataOutputStream dos) throws UnsupportedEncodingException, IOException {
		dos.write(id.getBytes("ASCII"));
		dos.writeInt(size);
		dos.writeShort(flags);
		dos.write(data);
	}

}
