package de.mockup.ui.content.swing;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.content.swing.swingObjects.SwingView;
import de.mockup.ui.gui.windows.WorkingSurface;

import java.awt.*;

/**
 * Utility class which offers methods to convert JavaFX classes to Swing classes respectively.
 */
public class SwingFXUtils {

	/**
	 * Converts a point from a component coordinate system to the screen coordinate system.
	 * @param p
	 * @param c
	 * @return a Point on the Screen
	 */
    public static Point convertPointToScreen(Point p, ContentComponent c) {
        convertPointToOverlay(p, c);
		Point offset = WorkingSurface.get().getTabCoordinates();
        return new Point(p.x + offset.x, p.y + offset.y);
    }

	/**
	 * Converts a point from a component coordinate system to the Overlay coordinate system.
	 * @param p
	 * @param c
	 */
    public static void convertPointToOverlay(Point p, ContentComponent c) {
        while (c != null && c.getParent() != null && !(c instanceof SwingView)) {
            p.x += c.getX();
            p.y += c.getY();
            c = c.getParent();
        }
    }

	/**
	 * Converts a rectangle from a component coordinate system to the overlay coordinate system.
	 * @param r
	 * @param c
	 * @return a rectangle in the overlay coordinate system.
	 */
	public static Rectangle convertRectangleToOverlay(Rectangle r, ContentComponent c) {
		ContentComponent cur = c;
		while(!(cur instanceof ContentView)) {
			r.x += cur.getX();
			r.y += cur.getY();
			cur = cur.getParent();
		}
		return r;
	}
    
    /**
     * Converts a {@link java.awt.Color} into a {@link javafx.scene.paint.Color}
     * @param input AWT Color to be converted to into a JavaFX Color.
     * @return resulting javaFX Color
     */
	public static javafx.scene.paint.Color colorAwtToJavafx(java.awt.Color input){
		int r = input.getRed();
		int g = input.getGreen();
		int b = input.getBlue();
		int a = input.getAlpha();
		double opacity = a / 255.0 ;
		return javafx.scene.paint.Color.rgb(r, g, b, opacity);
	}
	
    /**
     * Converts a {@link javafx.scene.paint.Color} into a {@link java.awt.Color}.
     * @param input javaFX Color to be converted to into a AWT Color.
     * @return resulting AWT Color
     */
	public static java.awt.Color colorJavafxToAwt(javafx.scene.paint.Color input){
		int r = (int) (input.getRed() * 255);
		int g = (int) (input.getGreen() * 255);
		int b = (int) (input.getBlue() * 255);
		int a = (int) (input.getOpacity() * 255);
		return new java.awt.Color(r,g,b,a);
	}
	
	/**
	 * Utility method that converts a {@link javafx.scene.paint.Color} into a combined sRGB value,
	 * which can be used to </br>
	 * e.g. style with css
	 * @param javafxColor color to convert from
	 * @return the resulting int value (in form of 0xXXYYZZ) 
	 */
	public static int calculateSRGB(javafx.scene.paint.Color javafxColor){
		int r = (int) (javafxColor.getRed() * 255);
		int g = (int) (javafxColor.getGreen() * 255);
		int b = (int) (javafxColor.getBlue() * 255);

		return (r << 16) + (g << 8) + b;
	}
	
	/**
	 * Utility method that converts a {@link java.awt.Color} into a combined sRGB value which can be used to </br>
	 * e.g. style with css
	 * @param awtColor color to convert from
	 * @return the resulting int value (in form of 0xXXYYZZ) 
	 */
	public static int calculateSRGB(java.awt.Color awtColor){
		return (awtColor.getRed() << 16) + (awtColor.getGreen() << 8) + awtColor.getBlue();
	}
}
