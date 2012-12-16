package awesome;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ID3Parser {
	
	public static final byte[] ID3_HEADER_START = {0x49,0x44,0x33,0x03,0x00};
	
	/**
	 * reads the tag segment of a mp3 file and saves the read information to the given MP3File object.
	 * dirty is reset to false after parsing.
	 * @param mp3
	 * @throws IOException
	 */
	
	public static void parseID3(MP3File mp3) throws IOException{
		ID3InputStream input = new ID3InputStream(new FileInputStream(mp3.getFile()));
		
		byte[] hstart = new byte[5];
		input.read(hstart);
		if(!Arrays.equals(hstart, ID3_HEADER_START)){
			System.err.println("Unable to find ID3v2.3 Header!");
			return; //TODO: maybe we should throw an exception here as MP3File already checked for header.
		}
		
		mp3.setHeaderFlags(input.readUnsignedByte());
		//TODO: Don't touch files which have flags set
		mp3.setTageSize(input.readSyncSafeSize());
		//Extended Header
		byte[] tag = new byte[mp3.getTagSize()];
		input.read(tag);
		input.close();
		input = new ID3InputStream(new ByteArrayInputStream(tag));
		while(input.available() > 10){
			parseFrame(mp3, input);
			input.readPadding();
		}
		input.close();
		mp3.setDirty(false);
	}
	
	/**
	 * checks whether a given File is a MP3 with ID3v2 tags or not.
	 * @param f
	 * @return
	 */
	
	public static boolean isMP3(File f) {
		if(!f.getName().endsWith(".mp3") || !f.exists()){
			return false; //we only respect files with mp3 suffix as we don't want read every file.
		}
		FileInputStream fis = null;
		byte[] isRightID3 = new byte[5];
		try {
			fis = new FileInputStream(f);			
			fis.read(isRightID3);
			fis.close();
		} catch ( IOException e) {
			AwesomeID3.getController().getView().presentException(e);
		}
		byte[] rightBytes = ID3_HEADER_START;
		return Arrays.equals(isRightID3,rightBytes);
	}
	
	private static void parseFrame(MP3File mp3, ID3InputStream input) throws IOException {
		String frameType = input.readStringOfLength(4);
		//System.out.println("Frame Type: " + frameType);
		int fsize = input.readInt();
		//System.out.println("Frame Size: " + fsize);
		int flags = input.readUnsignedShort();
		switch (frameType) { //the frame type indicates which info we read
			case "TALB" : mp3.setAlbum(input.readStringOfLengthWithCharsetByte(fsize)); break;
			case "TIT2" : mp3.setTitle(input.readStringOfLengthWithCharsetByte(fsize)); break;
			case "TPE1" : mp3.setArtist(input.readStringOfLengthWithCharsetByte(fsize)); break; //check whether TPE2 is correct
			case "TYER" : mp3.setYear(input.readStringOfLengthWithCharsetByte(fsize)); break;
			case "APIC" : parsePic(mp3, input, fsize); break;
			default: byte[] buff2  = new byte[fsize]; input.read(buff2); 
			mp3.addUnknownFrame(new ID3Frame(frameType, fsize, flags, buff2)); //we need to restore the frame later
			break;
		}
	}
	
	private static void parsePic(MP3File mp3, ID3InputStream input, int fsize) throws FileNotFoundException, IOException {
		Charset cs = input.readCharset(); //1. charset byte
		mp3.setCoverMimeType(input.readStringUntilZero(cs)); //2. mime string (zero-terminated)
		int picType = input.readUnsignedByte(); // 3. byte indicating kind of picture, e.g. front cover
		if(picType != 0x03) //we want only front covers
			return;
		String desc = input.readStringUntilZero(cs); //read image description
		byte buff[] = new byte[fsize-1-mp3.getCoverMimeType().length()-1-1-desc.length()-1]; //image is rest of the data
		input.read(buff);
		input.readPadding(); //image can be followed by zero bytes used to make modifications easier
		mp3.setCover(buff);
	}
	
	/**
	 * read an image file and sets the cover of the mp3 file to its contents.
	 * images in other formats as jpeg or png will be converted.
	 * @param mp3
	 * @param file
	 */
	
	public static void readCoverFromFile(MP3File mp3, File file) {
		//TODO: Add conversion for other image formats like BMP, TIFF, etc
		try {
			byte[] buff;
			if(!AllImagesFileFilter.isPNGorJPEG(file)) {
				BufferedImage in = ImageIO.read(file);
				ByteArrayOutputStream baos = new ByteArrayOutputStream(); //this mucher is easier and faster than a temp file
				ImageIO.write(in, "png", baos);
				buff = baos.toByteArray();
				mp3.setCoverMimeType("image/png");
			} else {
				InputStream in = new BufferedInputStream(new FileInputStream(file));
				buff = new byte[(int) file.length()];
				in.read(buff);
				in.close(); 
				mp3.setCoverMimeType(file.getName().endsWith(".png") ? "image/png" : "image/jpeg");
			}
			mp3.setCover(buff);
			mp3.setDirty(true);
		} catch (IOException e) {
			AwesomeID3.getController().getView().presentException(e);
		}
	}
	
	/**
	 * saves the id3 tag segment with data set in the mp3 file.
	 * @param mp3
	 * @throws IOException
	 */
	
	public static void save(MP3File mp3) throws IOException{
		if(!mp3.isDirty()) return; //if nothing changed, we don't need to save anything
		
		//we always use ISO-8859-1 as it is the ID3 standard encoding
		Charset cs = Charset.forName("ISO-8859-1");
		
		//convert strings to byte arrays so that the length can be determined
		byte[] titleData = mp3.getTitle().getBytes(cs);
		byte[] albumData = mp3.getAlbum().getBytes(cs);
		byte[] artistData = mp3.getArtist().getBytes(cs);
		byte[] yearData = mp3.getYear().getBytes(cs);
		
		// compute size of ID3-Tag excluding the 10 header bytes
		int newTagSize = 0;
		for(ID3Frame frame : mp3.getUnknownFrames()){
			newTagSize += frame.getSize() + 10;
		}
		newTagSize += 40 + titleData.length + albumData.length + artistData.length + yearData.length;
		
		boolean writeCover = mp3.getCover() != null;
		
		if(writeCover)
			newTagSize += 10 + 3 + mp3.getCover().length + mp3.getCoverMimeType().getBytes(cs).length;
		
		boolean rewrite = newTagSize > mp3.getTagSize(); //did the tag segment grow?
		
		// read music data
		byte musicData[] = null;
		if(rewrite) {
			musicData = readMusicData(mp3, mp3.getTagSize()); //read music data for rewrite
		}
		
		// create outputstream
		ID3Output dos = new ID3Output(mp3.getFile(), rewrite);
		//write magic bytes and size of tag
		dos.write(ID3_HEADER_START); //write Header start
		dos.writeByte(mp3.getHeaderFlags());
		dos.writeAsSynchSafe(Math.max(mp3.getTagSize(), newTagSize));
		
		// write the four elemental text frames and the cover
		dos.writeTextFrame("TPE1", artistData);
		dos.writeTextFrame("TALB", albumData);
		dos.writeTextFrame("TIT2", titleData);
		dos.writeTextFrame("TYER", yearData);
		if(writeCover)
			dos.writeCover(mp3.getCover(), mp3.getCoverMimeType());
		
		// write all the unkown frames not modified by our software
		for(ID3Frame frame : mp3.getUnknownFrames()){
			dos.writeFrame(frame);
		}
		
		if(rewrite){
			//tag ist finished, we can write the music data
			dos.write(musicData);
		} else { 
			dos.writePadding(mp3.getTagSize()-newTagSize); //fill up the now shorter segment with zero bytes
		}
		// and close
		dos.close();
		mp3.setTageSize(Math.max(newTagSize, mp3.getTagSize())); //if the tag segment grew, update here for further writes
		mp3.setDirty(false);
	}

	private static byte[] readMusicData(MP3File mp3, int tagSize) throws FileNotFoundException,
			IOException {
		byte musicData[] = new byte[(int)mp3.getFile().length()-(tagSize+10)];
		FileInputStream fis = new FileInputStream(mp3.getFile());
		fis.skip(tagSize+10);
		fis.read(musicData);
		fis.close();
		return musicData;
	}
	
}
