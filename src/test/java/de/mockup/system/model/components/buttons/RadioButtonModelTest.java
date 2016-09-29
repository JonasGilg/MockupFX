package de.mockup.system.model.components.buttons;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RadioButtonModelTest {

	private static final String NAME = "name";
	private static final String ICON = "icon";
	private static final String LABEL = "label";

	private static final String NAME_VALUE = "nameValue";
	private static final String ICON_VALUE = "iconValue";
	private static final String LABEL_VALUE = "labelValue";

	RadioButtonModel rb;

	@Before
	public void up() {
		rb = new RadioButtonModel();
	}

	@Test
	public void testProperties() {
		rb.setProperty(NAME, NAME_VALUE);
		rb.setProperty(ICON, ICON_VALUE);
		rb.setProperty(LABEL, LABEL_VALUE);
		try {
			JSONObject json = rb.toConfig();
			RadioButtonModel loadModel = new RadioButtonModel();
			loadModel.fromConfig(json);


			assertEquals(rb.getProperty(NAME), loadModel.getProperty(NAME));
			assertEquals(rb.getProperty(ICON), loadModel.getProperty(ICON));
			assertEquals(rb.getProperty(LABEL), loadModel.getProperty(LABEL));

			assertEquals(NAME_VALUE, loadModel.getProperty(NAME));
			assertEquals(ICON_VALUE, loadModel.getProperty(ICON));
			assertEquals(LABEL_VALUE, loadModel.getProperty(LABEL));

		} catch (SystemException e) {
			e.printStackTrace();
			fail("SystemException!");
		}

	}

/**	@Test(expected = SystemException.class)
	public void validateJSON() throws SystemException {
		rb.toConfig();
	}**/
}
