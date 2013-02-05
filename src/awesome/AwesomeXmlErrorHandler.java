package awesome;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class AwesomeXmlErrorHandler implements ErrorHandler {

	public AwesomeXmlErrorHandler() {
		// does nothing
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		if(AwesomeID3.getController().getView() != null){
			AwesomeID3.getController().getView().presentException(exception);
		} else {
			exception.printStackTrace();
		}
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		if(AwesomeID3.getController().getView() != null){
			AwesomeID3.getController().getView().presentException(exception);
		} else {
			exception.printStackTrace();
		}
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		if(AwesomeID3.getController().getView() != null){
			AwesomeID3.getController().getView().presentException(exception);
		} else {
			exception.printStackTrace();
		}
	}
}
