package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.components.SwingTree;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel for JTree
 */
class JTreePropertyPanel extends AbstractPropertyPanel{

	public JTreePropertyPanel(final SwingTree tree) {
		super("JTree "+ PROPERTY_BUNDLE.getString("properties"));

		JTree jtree = new JTree();
		
		//Editable
		Property<Boolean> propEditable = new Property<>(PROPERTY_BUNDLE.getString("editable"), tree,
				jtree::setEditable, jtree::isEditable);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propEditable));
		
		//RowHeight
		Property<Integer> propRowHeight = new Property<>(PROPERTY_BUNDLE.getString("rowheight"), tree,
				jtree::setRowHeight, jtree::getRowHeight);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propRowHeight));
		
		//ToggleClickCount
		Property<Integer> propToggleClickCount = new Property<>(PROPERTY_BUNDLE.getString("togglecount"), tree,
				jtree::setToggleClickCount, jtree::getToggleClickCount);
		this.addToContent(SwingSettingFieldFactory.createForInteger(propToggleClickCount));
		
		//RootVisible
		Property<Boolean> propRootVisible = new Property<>(PROPERTY_BUNDLE.getString("rootvisible"), tree,
				jtree::setRootVisible, jtree::isRootVisible);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propRootVisible));
		
		//ScrollsOnExpand
		Property<Boolean> propScrollsOnExpand = new Property<>(PROPERTY_BUNDLE.getString("scrollonexpand"), tree,
				jtree::setScrollsOnExpand, jtree::getScrollsOnExpand);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propScrollsOnExpand));

	}

}
