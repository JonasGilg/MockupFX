package de.mockup.ui.gui.globalListener;

import com.pagosoft.OS;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalListenerManager {
	private static boolean initSuccessful;

	/**
	 * Initializes the GlobalMouseListeners for use.
	 */
	public static void initGlobalMouseListeners() {
		synchronized (System.out) {
			PrintStream out = System.out;
			try {
				System.setOut(new PrintStream(new OutputStream() {
					@Override
					public void write(int b) {
					}
				}));
				Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
				logger.setLevel(Level.OFF);
				logger.setUseParentHandlers(false);

				GlobalScreen.registerNativeHook();
				initSuccessful = true;
			} catch (NativeHookException e) {
				if (OS.isMacOsX()) {
					System.err.println("Support for global MouseListeners is currently disabled on MacOSX! " +
							"You can try enabling it by enabling 'Access for Assistive Devices' in your OS settings.");
				}
				initSuccessful = false;
			} finally {
				//Reenable System.out
				System.setOut(out);
			}
		}
	}

	/**
	 * @return if GlobalMouseListeners can be used
	 */
	public static boolean isGlobalMouseListenerActive() {
		return initSuccessful;
	}
}
