package de.mockup.system;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.util.ModelManager;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the ModelManager
 *
 * TODO More tests
 *
 */
public class ModelManagerTest {

	@Before
	public void up(){
		new Activator().start();
	}


	@Test(expected=SystemException.class)
	public void createComponentTest() throws SystemException {
		ModelManager.get().createModel("thisshouldfail");
	}
}
