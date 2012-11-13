package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import awesome.AllImagesFileFilter;

public class ImageFileFilterTest {
	
	private AllImagesFileFilter filter;
	
	@Before
	public void setUp() throws Exception {
		filter = new AllImagesFileFilter();
	}

	@Test
	public void testIsPNGorJPEG() {
		assertFalse(filter.accept(new File("dings.txt")));
		assertFalse(filter.accept(new File("dingsjpg")));
		
		assertTrue(filter.accept(new File("/")));
		assertTrue(filter.accept(new File("dings.jpg")));
		assertTrue(filter.accept(new File("dings.png")));
		assertTrue(filter.accept(new File("dings.JPG")));
	}

}
