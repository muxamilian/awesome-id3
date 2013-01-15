/**
 * 
 */
package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({GUITest.class, ID3OutputTest.class, ID3InputStreamTest.class, ImageFileFilterTest.class})

public class TestSuite {
	/**
	 * the directory with example files, should be reconfigured for every system.
	 */
	public final static String EXAMPLE_MUSIC_DIR = "/Users/me/Music/mp3s";
}
