package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import awesome.Directory;
import awesome.MusicLibrary;

public class MusicLibraryTest {
	
	private MusicLibrary ml;
	private Directory dir;
	
	@Before
	public void setup(){
		dir = new Directory(new File(TestSuite.EXAMPLE_MUSIC_DIR));
		ml = new MusicLibrary(dir);
	}

	@Test
	public void testGetRootDirectory() {
		assertEquals(ml.getRootDirectory(), dir);
	}

	@Test
	public void testSaveXML() {
		File f = new File(dir.getFile(), "cache.xml");
		f.delete();
		ml.saveXML();
		assertTrue(f.exists());
	}

}
