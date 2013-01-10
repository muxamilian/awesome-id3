package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import awesome.ID3InputStream;

public class ID3InputStreamTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testReadStringUntilZero() throws IOException {
		ID3InputStream in = new ID3InputStream(new ByteArrayInputStream(new byte[]{0x41, 0x42, 0x43, 0x00, 0x44, 0x00}));
		assertEquals(in.readStringUntilZero(Charset.forName("ASCII")), "ABC");
		assertEquals(in.readStringUntilZero(Charset.forName("ASCII")), "D");
		in.close();
	}

	@Test
	public void testReadStringOfLengthInt() throws IOException {
		ID3InputStream in = new ID3InputStream(new ByteArrayInputStream(new byte[]{0x41, 0x42, 0x43, 0x00, 0x44, 0x00}));
		assertEquals(in.readStringOfLength(2), "AB");
		assertEquals(in.readStringOfLength(1, Charset.forName("ASCII")), "C");
		in.close();
	}

	@Test
	public void testReadSyncSafeSize() throws IOException {
		ID3InputStream in = new ID3InputStream(new ByteArrayInputStream(new byte[]{0,75,45,7}));
		assertEquals(1234567, in.readSyncSafeSize());
		in.close();
	}

	@Test
	public void testReadCharset() throws IOException {
		ID3InputStream in = new ID3InputStream(new ByteArrayInputStream(new byte[]{0,1,2,3,127,4}));
		assertEquals(in.readCharset(), Charset.forName("ISO-8859-1"));
		assertEquals(in.readCharset(), Charset.forName("UTF-16"));
		assertEquals(in.readCharset(), Charset.forName("UTF-16BE"));
		assertEquals(in.readCharset(), Charset.forName("UTF-8"));
		assertEquals(in.readCharset(), Charset.forName("ISO-8859-1")); //127 invalid, should get default
		assertEquals(in.readCharset(), Charset.forName("ISO-8859-1")); //same again
		in.close();
	}

	@Test
	public void testReadPadding() throws IOException {
		ID3InputStream in = new ID3InputStream(new ByteArrayInputStream(new byte[]{0,0,0,0,4,0,0,0,1,0,0}));
		assertEquals(4, in.readPadding());
		in.readByte();
		assertEquals(3, in.readPadding());
		assertEquals(0, in.readPadding());
		in.readByte();
		assertEquals(2, in.readPadding());
		in.close();
	}

}
