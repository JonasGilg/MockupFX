package de.mockup.ui.gui.windows.dialogs;

import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import de.mockup.system.model.ModelTypes;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import de.mockup.system.model.StylesheetModel;
import de.mockup.ui.gui.windows.helper.FontComboBox;

/**
 * Right side of the {@link de.mockup.ui.gui.windows.dialogs.ProjectStylesheetChooser Stylesheet-GUI}.
 * Has a tab for every GUI-element, that contains a field for each property.
 * @author Janos Vollmer, Julian Korten
 */
class ProjectStylesheet extends BorderPane {

	/**
	 * TabPane
	 */
	private final TabPane tabs;

	/**
	 * x and y Position for GridLayout organization.
	 */
	private int xPos, yPos;

	// All the controls
	private final HashMap<String, HashMap<String, Control>> controlMap;
    private final HashMap<String, HashMap<String, Object>> defaultMap;

    private static final ResourceBundle STYLE_BUNDLE = ResourceBundle.getBundle("properties.StyleSheetBundle");

	public ProjectStylesheet() {
		controlMap = new HashMap<>();
        defaultMap = new HashMap<>();

		setStyle("-fx-border-color: black");

		tabs = new TabPane();
		tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		Insets insets = new Insets(20, 20, 20, 20);

		//Container Tab
		addContainerTab(insets, ModelTypes.Container.CONTAINER);

		//Button Tab
		addButtonTab(insets, ModelTypes.Button.BUTTON);

		//Label Tab
		addLabelTab(insets, ModelTypes.LABEL);

		//TextField Tab
		addTextFieldTab(insets, ModelTypes.Fields.TEXT_FIELD);

		//Slider Tab
		addSliderTab(insets, ModelTypes.Fields.SLIDER);

		//Spinner Tab
		addSpinnerTab(insets, ModelTypes.Fields.SPINNER);

		//Tree tab
		addTreeTab(insets, ModelTypes.TREE);

		//Table tab
		addTableTab(insets, ModelTypes.TREE);

		this.setTop(tabs);
	}

	private void putInControlMap(String componentName, String propName, Control c) {
		HashMap<String, Control> scMap = controlMap.get(componentName);
		if(scMap == null) {
			scMap = new HashMap<String, Control>();
			controlMap.put(componentName, scMap);
		}
		scMap.put(propName, c);
	}

    private void putInDefaultMap(String componentName, String propName, Object o) {
        HashMap<String, Object> soMap = defaultMap.get(componentName);
        if(soMap == null) {
            soMap = new HashMap<String, Object>();
            defaultMap.put(componentName, soMap);
        }
        soMap.put(propName, o);
    }

	/**
	 * Adds a tab to the tabView for GUI-containers. Basically panels.
	 */
	private void addContainerTab(Insets insets, String componentName) {
		GridPane pane = createTab(STYLE_BUNDLE.getString("container"), insets);

		addBasicSections(pane, componentName);
		addSeparator(pane);

		addBinaryOptions(pane, componentName);
	}

	/**
	 * Adds a tab to the tabView for Swing-Buttons.
	 */
	private void addButtonTab(Insets insets, String componentName) {
		GridPane pane = createTab(STYLE_BUNDLE.getString("button"), insets);

		addBasicSections(pane, componentName);
		addSeparator(pane);

		addBinaryOptions(pane, componentName);
	}

	/**
	 * Adds a tab to the tabView for Swing-Labels.
	 */
	private void addLabelTab(Insets insets, String componentName) {
		GridPane pane = createTab(STYLE_BUNDLE.getString("label"), insets);

		addBasicSections(pane, componentName);
		addSeparator(pane);

		//Label Eigenschaften
		addTextField(pane, STYLE_BUNDLE.getString("horizontal alignment"), "horizontal alignment", componentName, "0");
		addTextField(pane, STYLE_BUNDLE.getString("vertical alignment"), "vertical alignment", componentName, "0");
		addTextField(pane, STYLE_BUNDLE.getString("horizontal text alignment"), "horizontal text alignment", componentName, "0");
		addTextField(pane, STYLE_BUNDLE.getString("vertical text alignment"), "vertical text alignment", componentName, "0");
		addSeparator(pane);

		addBinaryOptions(pane, componentName);
	}

	/**
	 * Adds a tab to the tabView for Swing-TextFields.
	 */
	private void addTextFieldTab(Insets insets, String componentName) {
		GridPane pane = createTab(STYLE_BUNDLE.getString("textfield"), insets);

		addBasicSections(pane, componentName);
		addSeparator(pane);

		addTextSection(pane, componentName);

		addBinaryOptions(pane, componentName);
	}

	/**
	 * Adds a tab to the tabView for Swing-Sliders.
	 */
	private void addSliderTab(Insets insets, String componentName) {
		GridPane pane = createTab(STYLE_BUNDLE.getString("slider"), insets);

		addBasicSections(pane, componentName);
		addSeparator(pane);

		// TODO: Orientation with 2 RadioButton connecting with Model
		addText(pane, STYLE_BUNDLE.getString("orientation"));
		ToggleGroup orientationGroup = new ToggleGroup();
		BorderPane orientation = new BorderPane();
		RadioButton vert = new RadioButton(STYLE_BUNDLE.getString("vertical"));
		vert.setToggleGroup(orientationGroup);
		RadioButton hor = new RadioButton(STYLE_BUNDLE.getString("horizontal"));
		hor.setToggleGroup(orientationGroup);
		hor.setSelected(true);
		pane.add(orientation, xPos, yPos);
		orientation.setLeft(hor);
		orientation.setRight(vert);
		//TODO Orientation + invertiert

		addTextField(pane, STYLE_BUNDLE.getString("minimum"), "minimum", componentName, "0");
		addTextField(pane, STYLE_BUNDLE.getString("maximum"), "maximum", componentName, "100");
		addBinaryOption(pane, STYLE_BUNDLE.getString("show scale"), "show scale", componentName, false);
		addBinaryOption(pane, STYLE_BUNDLE.getString("engage"), "engage", componentName, false);
		addTextField(pane, STYLE_BUNDLE.getString("small scale"), "small scale", componentName, "5");
		addTextField(pane, STYLE_BUNDLE.getString("big scale"), "big scale", componentName, "10");
		addBinaryOption(pane, STYLE_BUNDLE.getString("show track"), "show track", componentName, true);
	}

	/**
	 * Adds a tab to the tabView for Swing-Spinners.
	 */
	private void addSpinnerTab(Insets insets, String componentName) {
		GridPane pane = createTab(STYLE_BUNDLE.getString("spinner"), insets);

		addBasicSections(pane, componentName);
	}

	/**
	 * Adds a tab to the tabView for Swing-TreeViews.
	 */
	private void addTreeTab(Insets insets, String componentName) {
		GridPane pane = createTab(STYLE_BUNDLE.getString("tree"), insets);

		addBasicSections(pane, componentName);
		addSeparator(pane);

		addBinaryOption(pane, STYLE_BUNDLE.getString("editable"), "editable", componentName, true);
		addTextField(pane, STYLE_BUNDLE.getString("row height"), "row height", componentName, "0");
		addTextField(pane, STYLE_BUNDLE.getString("toggle count"), "toggle count", componentName, "2");
		addBinaryOption(pane, STYLE_BUNDLE.getString("show root"), "show root", componentName, true);
		addBinaryOption(pane, STYLE_BUNDLE.getString("scroll on expand"), "scroll on expand", componentName, true);
	}

	/**
	 * Adds a tab to the tabView for Swing-Tables.
	 */
	private void addTableTab(Insets insets, String componentName) {
		GridPane pane = createTab(STYLE_BUNDLE.getString("table"), insets);

		addBasicSections(pane, componentName);
		addSeparator(pane);

		//Table Properties
		addColorPicker(pane, STYLE_BUNDLE.getString("selected bgcolor"), "selected bgcolor", componentName, Color.web("#b8cfe5"));
		addColorPicker(pane, STYLE_BUNDLE.getString("selected fgcolor"), "selected fgcolor", componentName, Color.web("#333333"));
		addColorPicker(pane, STYLE_BUNDLE.getString("grid color"), "grid color", componentName, Color.web("#7a8a99"));

		addTextField(pane, STYLE_BUNDLE.getString("row height"), "row height", componentName, "16");
		addTextField(pane, STYLE_BUNDLE.getString("row pitch"), "row pitch", componentName, "1");

		addBinaryOption(pane, STYLE_BUNDLE.getString("show horizontal lines"), "show horizontal lines", componentName, true);
		addBinaryOption(pane, STYLE_BUNDLE.getString("show vertical lines"), "show vertical lines", componentName, true);
		addBinaryOption(pane, STYLE_BUNDLE.getString("row chooser"), "row chooser", componentName, true);
		addBinaryOption(pane, STYLE_BUNDLE.getString("column chooser"), "column chooser", componentName, true);
		addBinaryOption(pane, STYLE_BUNDLE.getString("fill view window"), "fill view window", componentName, false);

		// TODO More binary Options (Tabellenüberschrift)
	}

	/**
	 * Adds the basic property sections every GUI-element has
	 */
	private void addBasicSections(GridPane pane, String componentName) {
		addSizeSection(pane, componentName);
		addColorSection(pane, componentName);
		addFontSection(pane, componentName);
	}

    /**
     * Adds a section for sizing properties. Should only be called by {@link #addBasicSections(GridPane, String)}.
     */
    private void addSizeSection(GridPane pane, String componentName) {
        addTextField(pane, STYLE_BUNDLE.getString("width"), "width", componentName, "100");
        addTextField(pane, STYLE_BUNDLE.getString("height"), "height", componentName, "50");

        addSeparator(pane);
    }

	/**
	 * Adds a section for fore- and backgroundcolor. Should only be called by {@link #addBasicSections(GridPane, String)}.
	 */
	private void addColorSection(GridPane pane, String componentName) {
		addColorPicker(pane, STYLE_BUNDLE.getString("foreground color"), "foreground color", componentName, Color.web("#ffffff"));
		addColorPicker(pane, STYLE_BUNDLE.getString("background color"), "background color", componentName);

		addSeparator(pane);
	}

	/**
	 * Adds a section for font selection. Should only be called by {@link #addBasicSections(GridPane, String)}.
	 */
	private void addFontSection(GridPane pane, String componentName) {
		addFontComboBox(pane, STYLE_BUNDLE.getString("font"), "font", componentName);
		addTextField(pane, STYLE_BUNDLE.getString("fontsize"), "fontsize", componentName, "12");
		addBinaryOption(pane, STYLE_BUNDLE.getString("italic"), "italic", componentName, false);
		addBinaryOption(pane, STYLE_BUNDLE.getString("bold"), "bold", componentName, false);
	}

	/**
	 * Adds a section for text-based GUI-elements.
	 */
	private void addTextSection(GridPane pane, String componentName) {
		addTextField(pane, STYLE_BUNDLE.getString("text"), "text", componentName);

		addColorPicker(pane, STYLE_BUNDLE.getString("deactivated text color"), "deactivated text color", componentName, Color.web("#b8cfe5"));
		addColorPicker(pane, STYLE_BUNDLE.getString("selected color"), "selected color", componentName, Color.web("#333333"));
		addColorPicker(pane, STYLE_BUNDLE.getString("select color"), "select color", componentName, Color.web("#b8cfe5"));
		addColorPicker(pane, STYLE_BUNDLE.getString("text cursor color"), "text cursor color", componentName, Color.web("#333333"));

		addBinaryOption(pane, STYLE_BUNDLE.getString("editable"), "editable", componentName, true);
		addSeparator(pane);
	}

	/**
	 * Adds binary options every GUI-element has.
	 */
	private void addBinaryOptions(GridPane pane, String componentName) {
		addBinaryOption(pane, STYLE_BUNDLE.getString("is enabled"), "is enabled", componentName, true);
		addBinaryOption(pane, STYLE_BUNDLE.getString("is visible"), "is visible", componentName, true);
		addBinaryOption(pane, STYLE_BUNDLE.getString("is focusable"), "is focusable", componentName, true);
		addBinaryOption(pane, STYLE_BUNDLE.getString("paint border"), "paint border", componentName, true);
	}

	/**
	 * Adds a combobox that holds images of fonts names, written in their font. The combobox creation can be found {@link de.mockup.ui.gui.windows.helper.FontComboBox here}
	 */
	private void addFontComboBox(GridPane tab, String text, String propName, String componentName) {
		addText(tab, text);
		FontComboBox bFont = new FontComboBox();
		tab.add(bFont, xPos, yPos);

		putInControlMap(componentName, propName, bFont);
        putInDefaultMap(componentName, propName, "Arial");
	}

	/**
	 * Adds a textfield. Needed for many user inputs.
	 */
	private void addTextField(GridPane tab, String text, String propName, String componentName) {
		addTextField(tab, text, propName, componentName, "");
	}

	/**
	 * Adds a textfield. Needed for many user inputs.
	 */
	private void addTextField(GridPane tab, String text, String propName, String componentName, String def) {
		addText(tab, text);
		TextField textField = new TextField(def);
		tab.add(textField, xPos, yPos);

		putInControlMap(componentName, propName, textField);
        putInDefaultMap(componentName, propName, def);
	}

	/**
	 * Adds a colorpicker. Used in all sections, that need a userinput containing a color.
	 */
	private void addColorPicker(GridPane tab, String text, String propName, String componentName) {
		addColorPicker(tab, text, propName, componentName, Color.web("#000000"));
	}

	/**
	 * Adds a colorpicker. Used in all sections, that need a userinput containing a color.
	 */
	private void addColorPicker(GridPane tab, String text, String propName, String componentName, Color color) {
		addText(tab, text);
		ColorPicker colorPicker = new ColorPicker(color);
		tab.add(colorPicker, xPos, yPos);

		putInControlMap(componentName, propName, colorPicker);
        putInDefaultMap(componentName, propName, color);
	}

	/**
	 * Adds a unique binary Option to the current section. (Normally all binary-options are grouped in one section at the end)
	 */
	private void addBinaryOption(GridPane tab, String text, String propName, String componentName, Boolean selected) {
		addText(tab, text);
		CheckBox checkbox = new CheckBox();
		checkbox.setSelected(selected);
		tab.add(checkbox, xPos, yPos);

		putInControlMap(componentName, propName, checkbox);
        putInDefaultMap(componentName, propName, selected);
	}

	/**
	 * Adds a horizontal line to separate distinct sections. Also automatically organizes the GridLayout positions.
	 */
	private void addSeparator(GridPane parent) {
		yPos++;
		parent.add(new Separator(), xPos - 1, yPos, 2, 1);
		yPos++;
	}

	/**
	 * Creates a new tab, which is needed for every distinct GUI-element.
	 * @param title Shown in the tab-title
	 * @return Returns a new Tab
	 */
	private GridPane createTab(String title, Insets insets) {
		xPos = 1;
		yPos = 0;

		Tab tab = new Tab(title);
		tabs.getTabs().add(tab);

		ScrollPane tabScrollPane = new ScrollPane();
		tabScrollPane.setMaxHeight((Toolkit.getDefaultToolkit().getScreenSize().getHeight()) - 225);
		GridPane pane = new GridPane();
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setPadding(insets);
		tab.setContent(tabScrollPane);
		tabScrollPane.setContent(pane);

		return pane;
	}
	/*
	private void applyAction() {
		//TODO
		close();
	}

	private void cancelAction() {
		//TODO
		close();
	}*/

	/**
	 * Adds a title for a property. Also helps organizing GridLayout positions.
	 */
	private void addText(GridPane container, String text) {
		xPos--; yPos++;
		container.add(new Text(text), xPos, yPos);
		xPos++;
	}

	/**
	 * Loads Stylesheets properties from the Model.
	 * @param sm Current Stylesheet
	 */
	public void loadFromModel(StylesheetModel sm) {
        for (Map.Entry<String, HashMap<String, Control>> controlMapEntry : this.controlMap.entrySet()) {
            String componentName = controlMapEntry.getKey();
            HashMap<String, Control> scMap = controlMapEntry.getValue();
            for (Map.Entry<String, Control> scMapEntry : scMap.entrySet()) {
                String propName = scMapEntry.getKey();
                Control c = scMapEntry.getValue();

                Object o = null;
                if(sm != null) {
                    o = sm.getValue(componentName, propName);
                }
                // Not a value in the StylesheetModel yet, load a default...
                if(o == null) {
                    HashMap<String, Object> soMap = defaultMap.get(componentName);
                    if(soMap != null) {
                        o = soMap.get(propName);
                    }
                }

                if (o != null) {
                    // The awesome FontComboBox from this project
                    if (c instanceof FontComboBox) {
                        ((FontComboBox) c).setFontValue((String) o);
                    } else if (c instanceof TextField) {
                        ((TextField) c).setText((String) o);
                    } else if (c instanceof ColorPicker) {
                        ((ColorPicker) c).setValue((javafx.scene.paint.Color) o);
                    } else if (c instanceof CheckBox) {
                        ((CheckBox) c).setSelected((Boolean) o);
                    }
                }
            }
        }
	}

	/**
	 * Saves the changes made to the current Stylesheet into the Model.
	 * @param sm Current Stylesheet
	 */
	public void saveIntoModel(StylesheetModel sm) {
		if(sm != null) {
			for (Map.Entry<String, HashMap<String, Control>> controlMapEntry : this.controlMap.entrySet()) {
				String componentName = controlMapEntry.getKey();
				HashMap<String, Control> scMap = controlMapEntry.getValue();
				for (Map.Entry<String, Control> scMapEntry : scMap.entrySet()) {
					String propName = scMapEntry.getKey();
					Control c = scMapEntry.getValue();

					Object o = null;
					if (c instanceof FontComboBox) {
						o = ((FontComboBox)c).getSelectionModel().getSelectedItem().getText();
					} else if (c instanceof TextField) {
						o = ((TextField) c).getText();
					} else if (c instanceof ColorPicker) {
						o = ((ColorPicker) c).getValue();
					} else if (c instanceof CheckBox) {
						o = ((CheckBox) c).isSelected();
					}

					if (!propName.equals("") && o != null) {
                        sm.putInto(componentName, propName, o);
                    }
				}
			}
		}
	}

}
