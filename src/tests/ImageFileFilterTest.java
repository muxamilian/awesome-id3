package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import awesome.ImageFileFilter;

public class ImageFileFilterTest {
	
	private ImageFileFilter filter;
	
	@Before
	public void setUp() throws Exception {
		filter = new ImageFileFilter();
	}

	@Test
	public void testAcceptFile() {
		assertFalse(filter.accept(new File("dings.txt")));
		assertFalse(filter.accept(new File("dingsjpg")));
		
		assertTrue(filter.accept(new File("/")));
		assertTrue(filter.accept(new File("dings.jpg")));
		assertTrue(filter.accept(new File("dings.png")));
		assertTrue(filter.accept(new File("dings.JPG")));
	}

}
