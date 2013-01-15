/**
 * 
 */
package awesome;

/**
 * Thrown to signal an unparsable mp3 file. the essage string should contain info about the reason, e.g.
 * header flags set, frame header set, etc.
 */
public class IncompatibleID3Exception extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7753491226510103583L;
	
	/**
	 * creates new Exception with the given message string.
	 * @param message the reason message
	 */
	
	public IncompatibleID3Exception(String message){
		super(message);
	}
}
