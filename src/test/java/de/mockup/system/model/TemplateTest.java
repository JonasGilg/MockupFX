package de.mockup.system.model;

import de.mockup.system.Activator;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.components.buttons.ButtonModel;
import de.mockup.system.model.components.containers.ContainerModel;
import de.mockup.system.util.ModelManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the TemplateModel and Template creation.
 */
public class TemplateTest {

	private static final String PROPERTY = "property";

	private static final String VALUE1 = "value1";
	private static final String VALUE2 = "value2";

	@Before
	public void up() {
		new Activator().start();
	}

	@Test
	public void test() throws SystemException {

		ContainerModel container = new ContainerModel();
		container.setId(1);
		container.setProperty(PROPERTY, VALUE1);

		ButtonModel button = new ButtonModel();
		button.setProperty(PROPERTY, VALUE2);
		button.setId(2);

		container.addItem(button);

		ModelManager manager = ModelManager.get();

		String templateName = "TestTemplate";
		TemplateModel template = manager.createTemplate(templateName, container);

		assertEquals(templateName, template.getName());

		ContainerModel copyContainer = manager.createModel(template);

		assertNull(copyContainer.getId());
		assertEquals(container.getProperty(PROPERTY), copyContainer.getProperty(PROPERTY));
		assertEquals(container.getItems().size(), copyContainer.getItems().size());

		ButtonModel copyButton = (ButtonModel)copyContainer.getItems().get(0);

		assertEquals(button.getProperty(PROPERTY), copyButton.getProperty(PROPERTY));
		assertNull(copyButton.getId());

	}

}
