package de.mockup.ui.content.swing.manager;

import de.mockup.ui.content.ContentView;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class SnapshotableSwingNode extends SwingNode {

    private final ContentView<JPanel> view;

    public SnapshotableSwingNode(ContentView<JPanel> view) {
        super();
        this.view = view;
    }

    /**
     * This is an overridden implementation of the <code>Node.snapshot</code> method. It serves the purpose of
     * delivering a more robust way of getting an image out of the swing content, even if it is not displayed.
     *
     * @param parameters only used for viewport property
     * @param image
     * @return
     */
    @Override
    public WritableImage snapshot(SnapshotParameters parameters, WritableImage image) {
        JComponent jc = (JComponent) view.getContent().getParent();
        jc.setSize(view.getX() + view.getWidth(), view.getY() + view.getHeight());

        BufferedImage bufferedImage = null;

        Rectangle r;

        if (parameters == null) {
            r = jc.getBounds();
        } else {
            Rectangle2D r2d = parameters.getViewport();
            r = new Rectangle((int) r2d.getMinX(), ((int) r2d.getMinY()),
                    ((int) r2d.getWidth()), (int) r2d.getHeight());
        }

        try {
            bufferedImage = ScreenImage.createImage(jc, r);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WritableImage writableImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());

        if (image != null) {
            SwingFXUtils.toFXImage(bufferedImage, image);
        }
        SwingFXUtils.toFXImage(bufferedImage, writableImage);

        return writableImage;
    }
}
