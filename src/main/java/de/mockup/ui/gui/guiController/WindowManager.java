package de.mockup.ui.gui.guiController;

import de.mockup.system.model.WindowStatus;
import de.mockup.ui.gui.windows.TitlePane;

import java.util.*;

/**
 * Manages the windows
 */
public class WindowManager {

	private static WindowManager instance;

	public static WindowManager get() {
		if (instance == null) {
			instance = new WindowManager();
		}
		return instance;
	}

	private final Map<String, TitlePane> windows = new HashMap<>();

	public <T extends TitlePane> void register(Class<T> clazz, T window) {
		windows.put(clazz.getName(), window);
	}

	public <T extends TitlePane> T getWindow(Class<T> clazz) {
		return (T) windows.get(clazz.getName());
	}

	public Collection<TitlePane> getWindows(){
		return windows.values();
	}

	public List<WindowStatus> getState() {
		List<WindowStatus> state = new ArrayList<>(windows.size());
		for (TitlePane dialog : windows.values()) {
			WindowStatus status = new WindowStatus();
			status.setClazz(dialog.getClass().getName());
			status.setWidth(dialog.getWidth());
			status.setHeight(dialog.getHeight());
			state.add(status);
		}
		return state;
	}

	public void restoreState(List<WindowStatus> state) {
		if (state != null && state.size() > 0) {
			//TODO Should not happen
			state.stream().filter(status -> windows.containsKey(status.getClazz())).forEach(status -> {
				TitlePane dialog = windows.get(status.getClazz());
			});
		}
	}
}
