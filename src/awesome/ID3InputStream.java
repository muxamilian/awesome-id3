package awesome;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ID3InputStream extends DataInputStream {

	public ID3InputStream(InputStream arg0) {
		super(arg0);
	}
	
	public String readStringUntilZero(Charset cs) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int in;
		while((in = read()) != 0){
			baos.write(in);
		}
		return new String(baos.toByteArray(), cs);
	}
	
	public String readStringOfLengthWithCharsetByte(int length) throws IOException {
		Charset cs = readCharset();
		return readStringOfLength(length-1, cs);
	}
	
	public String readStringOfLength(int length) throws IOException {
		return readStringOfLength(length, Charset.forName("ISO-8859-1"));
	}
	
	public String readStringOfLength(int length, Charset cs) throws IOException {
		byte[] buffer = new byte[length];
		read(buffer);
		return new String(buffer, cs);
	}
	
	public int readSyncSafeSize() throws IOException{
		int size = 0;
		for(int i = 3; i >= 0; i--){
			int by = read() & 0xFF;
			size |= by << i*7;
		}
		return size;
	}
	
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
