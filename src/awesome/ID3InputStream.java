package awesome;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * This class adds some id3 utility methods to the underlying inputstream.
 * @author me
 *
 */

public class ID3InputStream extends DataInputStream {
	
	/**
	 * simply calls super(in); as no initialization is needed.
	 * @param in
	 */
	public ID3InputStream(InputStream in) {
		super(in);
	}

	/**
	 * read a zero-terminated string with given charset.
	 * @param cs the charset to use for decode
	 * @return
	 * @throws IOException
	 */
	
	public String readStringUntilZero(Charset cs) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int in;
		while((in = read()) != 0){
			baos.write(in);
		}
		return new String(baos.toByteArray(), cs);
	}
	
	/**
	 * read string of given length, including a leading charset byte.
	 * @param length the length of the string
	 * @return
	 * @throws IOException
	 */
	
	public String readStringOfLengthWithCharsetByte(int length) throws IOException {
		Charset cs = readCharset();
		return readStringOfLength(length-1, cs);
	}
	
	/**
	 * calls readStringOfLength(length, charset) with default charset.
	 * @param length
	 * @return
	 * @throws IOException
	 */
	
	public String readStringOfLength(int length) throws IOException {
		return readStringOfLength(length, Charset.forName("ISO-8859-1"));
	}
	
	/**
	 * reads a string of given length respecting the given charset
	 * @param length
	 * @param cs
	 * @return
	 * @throws IOException
	 */
	
	public String readStringOfLength(int length, Charset cs) throws IOException {
		byte[] buffer = new byte[length];
		read(buffer);
		return new String(buffer, cs);
	}
	
	/**
	 * read a 28b-int as specified in ID3 specification for tag size.
	 * @return
	 * @throws IOException
	 */
	
	public int readSyncSafeSize() throws IOException{
		int size = 0;
		for(int i = 3; i >= 0; i--){
			int by = read() & 0xFF;
			size |= by << i*7;
		}
		return size;
	}
	
	/**
	 * interprete the next byte as charset
	 * @return
	 * @throws IOException
	 */
	
	public Charset readCharset() throws IOException {
		String csName;		
		switch(read()) {
			case 0: csName = "ISO-8859-1"; break;
			case 1: csName = "UTF-16"; break;
			case 2: csName = "UTF-16BE"; break;
			case 3: csName = "UTF-8"; break;
			default: csName = "ISO-8859-1"; break;
		}
		return Charset.forName(csName);
	}
	
	/**
	 * if possible, read appended zero-bytes.
	 * @return
	 * @throws IOException
	 */
	
	public int readPadding() throws IOException {
		int paddy = 0;
		while(true){
			this.mark(in.available());
			int in = read();
			if(in != 0){
				reset();
				return paddy;
			} else {
				paddy++;
			}
		}
	}
}
