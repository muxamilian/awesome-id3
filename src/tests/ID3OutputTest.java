package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import awesome.ID3Output;

public class ID3OutputTest {

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
	public void testWriteAsSynchSafe() throws IOException {
		File tmp = File.createTempFile("id3outputtest", "bin");
		ID3Output out = new ID3Output(tmp, false);
		out.writeAsSynchSafe(1234567);
		byte[] data = Files.readAllBytes(tmp.toPath());
		assertArrayEquals(data, new byte[]{0,75,45,7});
		out.close();
		tmp.delete();
	}

	@Test
	public void testWriteTextFrame() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testWriteFrame() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testWritePadding() {
		fail("Not yet implemented"); // TODO
	}

}
