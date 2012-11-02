package awesome;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class ID3OutputStream extends DataOutputStream {

	public ID3OutputStream(OutputStream out) {
		super(out);
	}
	
	public void writeAsSynchSafe(int newTagSize) throws IOException {
		byte[] ret = new byte[4];
		for(int i = 3; i >= 0; i--){
			ret[3-i] = (byte) ((newTagSize & (0x7F << i*7)) >> (i*7));
		}
		write(ret);
	}
	
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

	public void writeTextFrame(String id, byte[] titleData) throws UnsupportedEncodingException, IOException {
		write(id.getBytes("ASCII")); //write frame identifier
		writeInt(titleData.length+1); //write size of frame
		writeShort(0); //flags
		writeByte(0); //charset = ISO-8859-1
		write(titleData); //write text data
	}
	
	public void writeFrame(ID3Frame frame) throws UnsupportedEncodingException, IOException {
		write(frame.getId().getBytes("ASCII"));
		writeInt(frame.getSize());
		writeShort(frame.getFlags());
		write(frame.getData());
	}

}
