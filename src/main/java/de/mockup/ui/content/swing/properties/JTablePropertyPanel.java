package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.components.SwingTable;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel for JTables
 */
class JTablePropertyPanel extends AbstractPropertyPanel{

	public JTablePropertyPanel(final SwingTable table) {
		super("JTable "+PROPERTY_BUNDLE.getString("properties"));

		JTable jtable = table.getContent();
		
		// Selection Background & Foreground
		Property<Color> propSBGColor = new Property<>(PROPERTY_BUNDLE.getString("selbgcolor"), table, jtable::setSelectionBackground,
				jtable::getSelectionBackground);
		this.addToContent(SwingSettingFieldFactory.createForColor(propSBGColor));

		Property<Color> propSFGColor = new Property<>(PROPERTY_BUNDLE.getString("selfgcolor"), table, jtable::setSelectionForeground,
				jtable::getSelectionForeground);
		this.addToContent(SwingSettingFieldFactory.createForColor(propSFGColor));
		
		//Grid Color
		Property<Color> propGridColor = new Property<>(PROPERTY_BUNDLE.getString("gridcolor"), table, jtable::setGridColor,
				jtable::getGridColor);
		this.addToContent(SwingSettingFieldFactory.createForColor(propGridColor));
		
		//Row Height
		Property<Integer> propRowHeight = new Property<>(PROPERTY_BUNDLE.getString("rowheight"), table,
				jtable::setRowHeight, jtable::getRowHeight);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propRowHeight));
		
		//Row Margin
		Property<Integer> propRowMargin = new Property<>(PROPERTY_BUNDLE.getString("rowmargin"), table,
				jtable::setRowMargin, jtable::getRowMargin);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propRowMargin));
		
		//Intercell Spacing
		Property<Dimension> propIntercellSpacing = new Property<>(PROPERTY_BUNDLE.getString("cellspacing"), table,
				jtable::setIntercellSpacing, jtable::getIntercellSpacing);
		this.addToContent(SwingSettingFieldFactory.createForDimension(propIntercellSpacing));
		
		//Show Horizontal Lines
		Property<Boolean> propShowHorizontalLines = new Property<>(PROPERTY_BUNDLE.getString("showHLines"), table,
				jtable::setShowHorizontalLines, jtable::getShowHorizontalLines);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propShowHorizontalLines));
		
		//Show Vertical Lines
		Property<Boolean> propShowVerticalLines = new Property<>(PROPERTY_BUNDLE.getString("showVLines"), table,
				jtable::setShowVerticalLines, jtable::getShowVerticalLines);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propShowVerticalLines));
		
		//Row Selection Allowed
		Property<Boolean> propRowSelectionAllowed = new Property<>(PROPERTY_BUNDLE.getString("rowsel"), table,
				jtable::setRowSelectionAllowed, jtable::getRowSelectionAllowed);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propRowSelectionAllowed));
		
		//Column Selection Allowed
		Property<Boolean> propColumnSelectionAllowed = new Property<>(PROPERTY_BUNDLE.getString("colsel"), table,
				jtable::setColumnSelectionAllowed, jtable::getColumnSelectionAllowed);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propColumnSelectionAllowed));
		
		//Fills Viewport Height
		Property<Boolean> propFillsViewportHeight = new Property<>(PROPERTY_BUNDLE.getString("fillviewport"), table,
				jtable::setFillsViewportHeight, jtable::getFillsViewportHeight);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propFillsViewportHeight));
		
		//TableHeader
		Property<JTableHeader> propTableHeader = new Property<>(PROPERTY_BUNDLE.getString("tablehead"), table,
				jtable::setTableHeader, jtable::getTableHeader);
		this.addToContent(SwingSettingFieldFactory.createForJTableHeader(propTableHeader));
		
	}
}