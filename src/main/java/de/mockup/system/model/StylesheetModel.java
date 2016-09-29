package de.mockup.system.model;

import de.mockup.system.model.components.textfields.LabelModel;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.swing.swingObjects.components.SwingLabel;
import de.mockup.ui.gui.properties.Property;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;

import javax.swing.*;

/**
 * Model for ProjectStylesheets.
 * @autor Julian Korten, Janos Vollmer
 */
public class StylesheetModel extends BaseModel{

	// String to identify Stylesheets
	private static final String KEY_ID = "id";
	private static final String KEY_PROPERTIES = "styleProperties";
	private static final String KEY_NAME = "name";

    // Unique id for a stylesheet
    private String pssId;
    // Name that is displayed in the stylesheet selection window
    private String name;
    private String author;
    private String infoText;

    private final HashMap<String, HashMap<String, Object>> map;

    public StylesheetModel() {
    	this.pssId = System.currentTimeMillis() + "" + System.nanoTime();
        map = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> buttonMap = new HashMap<String, Object>();
        buttonMap.put("Background Color", Color.GREEN);
        map.put(ModelTypes.Button.BUTTON, buttonMap);
        HashMap<String, Object> labelMap = new HashMap<String, Object>();
        labelMap.put("Foreground Color", Color.RED);
        map.put(ModelTypes.LABEL, labelMap);
    }

    public void putInto(String componentName, String key, Object value) {
        HashMap<String, Object> subMap = map.get(componentName);
        if (subMap == null) {
            subMap = new HashMap<String, Object>();
            map.put(componentName, subMap);
        }
        subMap.put(key, value);
        //System.out.println("For " + componentName + ": key=" + key + ", value=" + value);
    }

    public Object getValue(String componentName, String key) {
        Map<String, Object> innerMap = map.get(componentName);
        if(innerMap == null)
            return null;
        return innerMap.get(key);
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPssId() {
        return pssId;
    }

    public void setPssId(String pssId) {
        this.pssId = pssId;
    }

    @Override
    public JSONObject toConfig() throws SystemException {
    	JSONObject config = super.toConfig();
    	config.put(KEY_ID, pssId);
    	config.put(KEY_NAME, this.name);

    	JSONObject jsonComponents = new JSONObject();
    	for(Map.Entry<String, HashMap<String, Object>> p : map.entrySet()) {
    		JSONObject jsonProperties = new JSONObject();
    		for(Map.Entry<String, Object> q : p.getValue().entrySet()) {
    			jsonProperties.put(q.getKey(), q.getValue());
    		}
    		jsonComponents.put(p.getKey(), jsonProperties);
    	}
    	config.put(KEY_PROPERTIES, jsonComponents);

    	return config;
    }

	@Override
	public void fromConfig(JSONObject data) throws SystemException {
		JSONObject components = data.getJSONObject(KEY_PROPERTIES);

		this.pssId = data.optString(KEY_ID);
		this.name = data.getString(KEY_NAME);
		if(components != null && components.length() > 0) {
			Iterator<String> keys = components.keys();
			while(keys.hasNext()) {
				String key = keys.next();
				HashMap<String, Object> subMap = new HashMap<String, Object>();
				JSONObject properties = components.getJSONObject(key);
				Iterator<String> innerKeys = properties.keys();
				while(innerKeys.hasNext()) {
					String innerKey = innerKeys.next();
					if(innerKey.contains("olor")) {
						subMap.put(innerKey, Color.web((String)properties.get(innerKey)));
					} else {
						subMap.put(innerKey, properties.get(innerKey));
					}
				}
				map.put(key, subMap);
			}
		}
	}

    public Map<String, Object> getPropertiesForType(ContentComponent component) {
        String name = component.getType();
        System.out.println(name);
        return map.get(name);
    }

    public void setColorForProperty(Property prop, Object o) {
        if (o != null) {
            // TODO: Convert colors in the setter of the content component
            if (o instanceof javafx.scene.paint.Color) {
                javafx.scene.paint.Color oj = (javafx.scene.paint.Color) o;
                o = new java.awt.Color((float) oj.getRed(), (float) oj.getGreen(), (float) oj.getBlue());
            }
            prop.getSetter().accept(o);
        }
    }

    /**
     * Updates the component with the values from the stylesheet
     * @param component The content component to update
     */
    public void applyToComponent(ContentComponent component) {
        Map<String, Object> stylesheetProps = this.getPropertiesForType(component);
        if (stylesheetProps != null) {
            List<Property> pList = component.getProperties();
            // All the properties the component has
            for (Property prop : pList) {
                String varName = prop.getVarName();
                System.out.println(varName);
                /* SIZE SECTION */
                if(varName == "Size" || varName == "Preferred Size" || varName == "Minimum Size" ||
                        varName == "Maximum Size") {
                    Integer i1 = Integer.parseInt((String)stylesheetProps.get("width"));
                    Integer i2 = Integer.parseInt((String)stylesheetProps.get("height"));
                    if (i1 > 0 && i2 > 0) {
                        prop.getSetter().accept(new Dimension(i1, i2));
                    }
                }
                /* COLOR SECTION */
                else if(varName == "Foreground Color") {
                    setColorForProperty(prop, stylesheetProps.get("foreground color"));
                } else if(varName == "Background Color") {
                    setColorForProperty(prop, stylesheetProps.get("background color"));
                }
                /* FONT SECTION */
                else if(varName == "Font") {
                    String name = (String)stylesheetProps.get("font");
                    // Style = is it bold and/or italic?
                    int style = Font.PLAIN;
                    Boolean bold = (Boolean)stylesheetProps.get("bold");
                    if(bold) {
                        style |= Font.BOLD;
                    }
                    Boolean italic = (Boolean)stylesheetProps.get("italic");
                    if(italic) {
                        style |= Font.ITALIC;
                    }
                    int size = Integer.parseInt((String)stylesheetProps.get("fontsize"));
                    if(name != "" && size > 0) {
                        prop.getSetter().accept(new Font(name, style, size));
                    }
                }
                /* BINARY OPTION SECTION */
                else if(varName == "is Enabled") {
                    prop.getSetter().accept(stylesheetProps.get("is enabled"));
                } else if(varName == "is Focusable") {
                    prop.getSetter().accept(stylesheetProps.get("is focusable"));
                } else if(varName == "is Visible") {
                    prop.getSetter().accept(stylesheetProps.get("is visible"));
                } else if(varName == "is border painted") {
                    prop.getSetter().accept(stylesheetProps.get("paint border"));
                }
            }
        }

        /* LABEL SECTION */
        if(component instanceof SwingLabel) {
            JLabel label =((SwingLabel)component).getContent();
            label.setHorizontalAlignment(Integer.parseInt((String)stylesheetProps.get("horizontal alignment")));
            label.setVerticalAlignment(Integer.parseInt((String)stylesheetProps.get("vertical alignment")));
            label.setHorizontalTextPosition(Integer.parseInt((String)stylesheetProps.get("horizontal text alignment")));
            label.setVerticalTextPosition(Integer.parseInt((String)stylesheetProps.get("vertical text alignment")));
        }
    }

}
