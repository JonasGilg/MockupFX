package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.swing.properties.SwingSettingFieldFactory.StringSetter;
import de.mockup.ui.content.swing.properties.converter.ComponentOrientationPropertyConverter;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.properties.Property;
import de.mockup.ui.gui.windows.dialogs.stringeditor.StringEditorController;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import static de.mockup.ui.content.swing.properties.SwingProperties.PROPERTY_BUNDLE;

/**
 * PropertyPanel displaying basic properties of a component such as its name,
 * parent etc.
 */
public class BasicPropertyPanel extends AbstractPropertyPanel {

	@Override protected void optionalConstructor(){
		if(!EXPAND_MAP.containsKey(this.getClass())){
			EXPAND_MAP.put(this.getClass(), false);
		}
	}

	/**
	 * Constructor that builds the BasicPropertyPanel with "Properties" in the
	 * TitlePane
	 *
	 * @param comp passed Component that will be used as a reference to
	 *              manipulate it with the help of the GUI elements on this
	 *              PropertyPanel
	 */
	public BasicPropertyPanel(final ContentComponent<? extends JComponent> comp) {
		super(PROPERTY_BUNDLE.getString("basics"));


		JComponent jcomp = comp.getContent();

		// name and parent name
		Property<String> propName = new Property<>(PROPERTY_BUNDLE.getString("name"), comp, jcomp::setName, jcomp::getName);
		GridPane infoPanel = new GridPane();
		infoPanel.hgapProperty().set(5);
		infoPanel.vgapProperty().set(5);

		StringSetter nameField = SwingSettingFieldFactory.createForString(propName);
		//TODO After changing the name the StringEditor must be informed so it can update its Properties. Status: Buggy 
		//nameField.setUpdateFunction((oldVal,newVal) -> StringEditorController.getInstance().changeComponentName(comp,oldVal,newVal));

		Label parentLabel = new Label(PROPERTY_BUNDLE.getString("parentcont") + ": ");
		class ParentObserverLabel extends Label implements Observer {
			public ParentObserverLabel() {
				super();
				comp.addObserver(this);
			}

			@Override
			public void update(Observable o, Object arg) {
                if("property.parent".equals(arg)) {
                    this.setText(jcomp.getParent() == null || jcomp.getParent().getName() == null ?
                            PROPERTY_BUNDLE.getString("noParent") : jcomp.getParent().getName());
                }
			}
		}
		ParentObserverLabel parentInfoLabel = new ParentObserverLabel();

		infoPanel.add(nameField, 0, 0);
		infoPanel.add(new FlowPane(parentLabel,parentInfoLabel), 0, 1);
		//infoPanel.add(parentInfoLabel, 1, 1);
		this.addToContent(infoPanel);

		// tooltiptext
		Property<String> propTooltipText = new Property<>(PROPERTY_BUNDLE.getString("tooltiptext"), comp, jcomp::setToolTipText,
				jcomp::getToolTipText);
		this.addToContent(SwingSettingFieldFactory.createForString(propTooltipText));

		// cursor
		Property<Cursor> propCursor = new Property<>(PROPERTY_BUNDLE.getString("cursor"), comp, jcomp::setCursor, jcomp::getCursor);
		this.addToContent(SwingSettingFieldFactory.createForCursor(propCursor));

		// Locale
		Property<Locale> propLocale = new Property<>(PROPERTY_BUNDLE.getString("locale"), comp, jcomp::setLocale, jcomp::getLocale);
		this.addToContent(SwingSettingFieldFactory.createForLocale(propLocale));
		

		// ComponentOrientation TODO: needs Factory?
		Property<ComponentOrientation> propOrientation = new Property<>(PROPERTY_BUNDLE.getString("compOrientation"), comp,
				jcomp::setComponentOrientation, jcomp::getComponentOrientation, new ComponentOrientationPropertyConverter());
		this.addToContent(SwingSettingFieldFactory.createForComponentOrientation(propOrientation));

		// setEnabled
		Property<Boolean> propEnabled = new Property<>(PROPERTY_BUNDLE.getString("isEnabled"), comp, jcomp::setEnabled, jcomp::isEnabled);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propEnabled));

		// setFocusable
		Property<Boolean> propFocusable = new Property<>(PROPERTY_BUNDLE.getString("isFocusable"), comp, jcomp::setFocusable,
				jcomp::isFocusable);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propFocusable));

		// setVisible
		Property<Boolean> propVisible = new Property<>(PROPERTY_BUNDLE.getString("isVisible"), comp, jcomp::setVisible, jcomp::isVisible);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propVisible));

		// setFocusCycleRoot
		Property<Boolean> propCycleRoot = new Property<>(PROPERTY_BUNDLE.getString("focuscycle"), comp, jcomp::setFocusCycleRoot,
				jcomp::isFocusCycleRoot);
		this.addToContent(SwingSettingFieldFactory.createForBoolean(propCycleRoot));

	}
}
