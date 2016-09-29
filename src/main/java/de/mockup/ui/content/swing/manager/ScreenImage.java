package de.mockup.ui.content.swing.manager;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Convenience class to create and optionally save to a file a
 * BufferedImage of an area on the screen. Generally there are
 * four different scenarios. Create an image of:
 * <p>
 * a) an entire component
 * b) a region of the component
 * c) the entire desktop
 * d) a region of the desktop
 * <p>
 * The first two use the Swing paint() method to draw the
 * component image to the BufferedImage. The latter two use the
 * AWT Robot to create the BufferedImage.
 * <p>
 * The created image can then be saved to a file by usig the
 * writeImage(...) method. The type of file must be supported by the
 * ImageIO write method.
 * <p>
 * Although this class was originally designed to create an image of a
 * component on the screen it can be used to create an image of components
 * not displayed on a GUI. Behind the scenes the component will be given a
 * size and the component will be layed out. The default size will be the
 * preferred size of the component although you can invoke the setSize()
 * method on the component before invoking a createImage(...) method. The
 * default functionality should work in most cases. However the only
 * foolproof way to get a image to is make sure the component has been
 * added to a realized window with code something like the following:
 * <p>
 * JFrame frame = new JFrame();
 * frame.setContentPane( someComponent );
 * frame.pack();
 * ScreenImage.createImage( someComponent );
 */
class ScreenImage {

    /**
     * Create a BufferedImage for Swing components.
     * All or part of the component can be captured to an image.
     *
     * @param component Swing component to create image from
     * @param region    The region of the component to be captured to an image
     * @return image the image for the given region
     */
    public static BufferedImage createImage(JComponent component, Rectangle region) {
        //  Make sure the component has a size and has been layed out.
        //  (necessary check for components not added to a realized frame)

        if (!component.isDisplayable()) {
            Dimension d = component.getSize();

            if (d.width == 0 || d.height == 0) {
                d = component.getPreferredSize();
                component.setSize(d);
            }
            layoutComponent(component);
        }

        BufferedImage image = new BufferedImage(region.width, region.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        //  Paint a background for non-opaque components,
        //  otherwise the background will be black

        if (!component.isOpaque()) {
            g2d.setColor(component.getBackground());
            g2d.fillRect(region.x, region.y, region.width, region.height);
        }

        g2d.translate(-region.x, -region.y);
        component.paint(g2d);
        g2d.dispose();
        return image;
    }

    private static void layoutComponent(Component component) {
        synchronized (component.getTreeLock()) {
            component.doLayout();

            if (component instanceof Container) {
                for (Component child : ((Container) component).getComponents()) {
                    layoutComponent(child);
                }
            }
        }
    }
}
