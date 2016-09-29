package de.mockup.system.service;

import de.mockup.system.Activator;
import de.mockup.system.Bundle;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.AppConfig;
import de.mockup.system.model.AppStatus;
import de.mockup.system.model.Project;
import de.mockup.system.model.WindowStatus;
import de.mockup.system.model.components.buttons.ButtonModel;
import de.mockup.system.model.components.containers.ViewModel;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static junit.framework.TestCase.assertEquals;

public class PersistenceServiceTest {

	private static final String TMP_STORE = "test_storage";
	private static final String BACKUP = "_back";
	private File tmpStorage;

	@Before
	public void up() throws SystemException {
		Activator ac = new Activator();
		ac.start();
		tmpStorage = new File(TMP_STORE);
		if (!tmpStorage.exists()) {
			tmpStorage.mkdir();
		}

		String userHome = System.getProperty("user.home");
		File systemDirectory = new File(userHome, ".mockup");
		if (systemDirectory.exists()) {
			systemDirectory.renameTo(new File(systemDirectory.getParent(), systemDirectory.getName() + BACKUP));
		}
	}

	/**
	 * Tests the AppConfig
	 *
	 * @throws SystemException
	 */
	@Test
	public void testConfig() throws SystemException {
		AppService appService = Bundle.getService(AppService.class);
		AppConfig config = appService.getAppConfig();
		config.setAppFolder(tmpStorage.getPath());
		appService.saveAppConfig(config);

		AppConfig loaded = appService.getAppConfig();
		assertEquals(config.getAppFolder(), loaded.getAppFolder());
	}

	/**
	 * Tests the AppStatus
	 */
	@Test
	public void testStatus() throws SystemException {
		AppService appService = Bundle.getService(AppService.class);
		AppStatus status = appService.getAppStatus();
		List<WindowStatus> windows = new ArrayList<>(2);
		WindowStatus window1 = new WindowStatus();
		window1.setClazz("SomeWindow");
		window1.setWidth(1);
		window1.setHeight(2);
		window1.setX(3);
		window1.setY(4);
		windows.add(window1);
		WindowStatus window2 = new WindowStatus();
		window2.setClazz("AnOtherWindow");
		window2.setWidth(5);
		window2.setHeight(6);
		window2.setX(7);
		window2.setY(8);
		windows.add(window2);

		Stack<String> projects = new Stack<>();
		projects.push("Project 1");
		projects.push("Project 2");
		projects.push("Project 3");


		status.setLatestProjects(projects);
		status.setWindows(windows);

		appService.saveAppStatus(status);

		AppStatus loaded = appService.getAppStatus();

		assertEquals(windows.size(), loaded.getWindows().size());

		int compared = 0;
		for (WindowStatus win : loaded.getWindows()) {
			for (WindowStatus savedWin : windows) {
				if (savedWin.getClazz().equals(win.getClazz())) {
					assertEquals(savedWin.getWidth(), win.getWidth());
					assertEquals(savedWin.getHeight(), win.getHeight());
					assertEquals(savedWin.getX(), win.getX());
					assertEquals(savedWin.getY(), win.getY());
					compared++;
				}
			}
		}
		assertEquals(windows.size(), compared);

		Stack<String> loadedProjects = loaded.getLatestProjects();
		assertEquals(projects.size(), loadedProjects.size());
		while (projects.isEmpty()) {
			assertEquals(projects.pop(), loadedProjects.pop());
		}
	}

	/**
	 * Combined test stores a project with its components.
	 *
	 * @throws SystemException
	 */
	@Test
	public void testStoreProject() throws SystemException {

		ProjectService projectService = Bundle.getService(ProjectService.class);

		Project project = new Project();
		project.setName("Test");
		project.setProjectType("Test");
		project.setLookAndFeel("LaF");
        project.setStoragePath("");

		ViewModel container = new ViewModel();
		container.setTitle("Test View");
		container.setId(project.generateComponentId());

		ButtonModel button = new ButtonModel();
		button.setLabel("Button");
		button.setId(project.generateComponentId());
		container.addItem(button);

		project.addView(container);
		project.setRootView(container);

		//Create Custom AppConfig for test data
		AppConfig appConfig = new AppConfig();
		appConfig.setAppFolder(tmpStorage.getPath());

		projectService.saveProject(appConfig, project);

		Project read = projectService.getProject(appConfig, project);

		assertEquals(project.getName(), read.getName());
		assertEquals(project.getStoragePath(), read.getStoragePath());
		assertEquals(project.getLookAndFeel(), read.getLookAndFeel());
		assertEquals(project.getProjectType(), read.getProjectType());

		Map<Integer, ViewModel> pMap = project.getViewStorage();
		Map<Integer, ViewModel> readMap = read.getViewStorage();

		assertEquals(pMap.size(), readMap.size());
		for (Integer id : pMap.keySet()) {
			ViewModel a = pMap.get(id);
			ViewModel b = readMap.get(id);
			Assert.assertEquals(a.getId(), b.getId());
			Assert.assertEquals(a.getType(), b.getType());
			Assert.assertEquals(a.getStorageName(), b.getStorageName());
			Assert.assertEquals(a.getWidth(), b.getWidth());
			Assert.assertEquals(a.getHeight(), b.getHeight());
		}

		assertEquals(project.getRootView().getStorageName(), read.getRootView().getStorageName());
	}

	@After
	public void down() throws IOException, SystemException {
		//Clear temp application storage
		FileUtils.deleteDirectory(tmpStorage);

		//restore original config files
		String userHome = System.getProperty("user.home");
		File systemDirectory = new File(userHome, ".mockup");
		File original = new File(systemDirectory.getParent(), systemDirectory.getName() + BACKUP);
		if (systemDirectory.exists()) {
			FileUtils.deleteDirectory(systemDirectory);
		}
		if (original.exists()) {
			original.renameTo(new File(userHome, ".mockup"));
		}
	}

}
