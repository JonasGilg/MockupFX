package de.mockup.ui.controller.logic;

import de.mockup.system.exceptions.SystemErrorCodes;
import de.mockup.system.exceptions.SystemException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * This class contains some methods for converting components to JPG/PDF/PNG and JPG to PDF
 */
public class Utility {

	/* don't instantiate this class */
	private Utility() {
	}

    /**
     * Saves a component to a png file
     * @param panel the component to be exported
     * @param filename the filename on the filesystem
     * @throws SystemException
     */
    public static void componentToPNG(Node panel, String filename) throws SystemException {
		WritableImage image = panel.snapshot(new SnapshotParameters(), null);

		File file = new File(filename);

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			throw new SystemException(SystemErrorCodes.ERROR_CREATING_SCREENSHOT);
        }
    }
}
