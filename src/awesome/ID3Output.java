package awesome;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class ID3Output extends RandomAccessFile {
	
	public ID3Output(File out, boolean rewrite) throws IOException {
		super(out, "rw");
		if(rewrite){
			setLength(0); //delete the file
		}
	}
	
	/**
	 * write the tag header size in 28b format.
	 * @param newTagSize
	 * @throws IOException
	 */
	
	public void writeAsSynchSafe(int newTagSize) throws IOException {
		byte[] ret = new byte[4];
		for(int i = 3; i >= 0; i--){
			ret[3-i] = (byte) ((newTagSize & (0x7F << i*7)) >> (i*7));
		}
		write(ret);
	}
	
	/**
	 * write the cover to the stream.
	 * @param cover
	 * @param coverMime
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	
	public void writeCover(byte[] cover, String coverMime) throws UnsupportedEncodingException, IOException {
		write("APIC".getBytes("ASCII"));
		writeInt(cover.length+4+coverMime.getBytes("ISO-8859-1").length); // write size of frame
		writeByte(0); //flag byte 1
		writeByte(0); //flag byte 2
		writeByte(0); //charset
		write(coverMime.getBytes("ISO-8859-1")); //mime type, e.g. image/jpeg
		write(new byte[]{0,3,0}); // null-terminated string, 3 = cover, 0 = description (null-terminated)
		write(cover); // write image data
	}
	
	/**
	 * write the given text frame to the stream.
	 * @param id
	 * @param titleData
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	
	public void writeTextFrame(String id, byte[] titleData) throws UnsupportedEncodingException, IOException {
		write(id.getBytes("ASCII")); //write frame identifier
		writeInt(titleData.length+1); //write size of frame
		writeShort(0); //flags
		writeByte(0); //charset = ISO-8859-1
		write(titleData); //write text data
	}
	
	/**
	 * write a binary frame to the stream.
	 * @param frame
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	
	public void writeFrame(ID3Frame frame) throws UnsupportedEncodingException, IOException {
		write(frame.getId().getBytes("ASCII"));
		writeInt(frame.getSize());
		writeShort(frame.getFlags());
		write(frame.getData());
	}
	
	/**
	 * writes a sequence of padding bytes ( equals zero-bytes )
	 * @param size the amount of padding bytes
	 * @throws IOException
	 */

	public void writePadding(int size) throws IOException {
		for(int i = 0; i < size; i++)
			write((byte) 0);
	}

}
