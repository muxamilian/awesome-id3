package tests;

import static org.junit.Assert.*;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JTree;

import junit.extensions.jfcunit.*;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import awesome.AwesomeID3;
import awesome.ID3View;

public class GUITest extends JFCTestCase{
	
	private ID3View view;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		setHelper( new JFCTestHelper( ) );
		AwesomeID3.main(new String[]{"/Users/me/Music/mp3s"});
		view = AwesomeID3.getController().getView();
	}

	@Test
	public void testDisabledTextFieldsOnStartup() {
		assertNotNull(view);
		ComponentFinder finder = new ComponentFinder(JTextField.class);
		JTextField titleField = (JTextField) finder.find(view,0);
		assertFalse(titleField.isEnabled());
		assertNotNull("title field not found!", titleField);
	}
	
	@Test
	public void testYearFieldRestriction() {
		assertNotNull(view);
		ComponentFinder finder = new ComponentFinder(JTextField.class);
		JTextField yearField = (JTextField) finder.find(view,2);		
		assertNotNull("year field not found!", yearField);
		finder.setComponentClass(JTree.class);
		JTree tree = (JTree) finder.find(view, 0);
		tree.expandRow(1);
		tree.expandRow(2);
		tree.setSelectionRow(3);
		assertTrue(yearField.isEnabled());
		String year = yearField.getText();
		yearField.setText("");
		getHelper().sendString(new StringEventData( this, yearField, "Bla" )); //no other chars
		assertEquals(yearField.getText(), "");
		getHelper().sendString(new StringEventData( this, yearField, "2013" ));
		assertEquals(yearField.getText(), "2013");
		getHelper().sendString(new StringEventData( this, yearField, "1" )); //not more than four chars
		assertEquals(yearField.getText(), "2013");
		yearField.setText(year);
	}
	
	@Test
	public void testSelectionPersitentTitle() {
		assertNotNull(view);
		ComponentFinder finder = new ComponentFinder(JTextField.class);
		JTextField titleField = (JTextField) finder.find(view,0);		
		assertNotNull("title field not found!", titleField);
		finder.setComponentClass(JTree.class);
		JTree tree = (JTree) finder.find(view, 0);
		tree.expandRow(1);
		tree.expandRow(2);
		tree.setSelectionRow(3);
		String title = titleField.getText();
		titleField.setText("TEST");
		tree.setSelectionRow(4);
		tree.setSelectionRow(3);
		assertEquals(titleField.getText(), "TEST");
		titleField.setText(title);
	}
	
	@Test
	public void testCoverDeletion() {
		assertNotNull(view);
		ComponentFinder finder = new ComponentFinder(JLabel.class);
		JLabel cc = (JLabel) finder.find(view,4);		
		assertNotNull("cover container field not found!", cc);
		finder.setComponentClass(JTree.class);
		JTree tree = (JTree) finder.find(view, 0);
		tree.expandRow(1);
		tree.expandRow(2);
		tree.setSelectionRow(3);

		assertNotNull(cc.getIcon());
		//getHelper().enterClickAndLeave(new MouseEventData( this, cc, 1, 0, true ));
		cc.getComponentPopupMenu().show(cc, 100, 100);
		JMenuItem delitem = (JMenuItem) new JMenuItemFinder("Delete Cover").find(cc.getComponentPopupMenu(), 0);
		getHelper().enterClickAndLeave(new MouseEventData( this, delitem ));
		assertNull(cc.getIcon());
	}

}
