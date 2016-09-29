package de.mockup.ui.gui.windows.dialogs.stringeditor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Model class for the table in StringBuilder
 */
public class TableDataModel{

	private SimpleObjectProperty<ContentView<?>> view;
	private SimpleObjectProperty<ContentComponent<?>> component;
	private SimpleStringProperty key;
	private SimpleStringProperty defaultValue;
	private Map<Locale,SimpleStringProperty> locales;

	/**
	 * initiates TableData with null values, empty Strings and an empty map.
	 */
	public TableDataModel(){
		this(null,null,"","",new HashMap<Locale,String>());
	}

	/**
	 * Initiates a TableData with the given parameters.
	 * @param view
	 * @param component
	 * @param keyName
	 * @param defaultName
	 * @param localeNames
	 */
	public TableDataModel(ContentView<?> view, ContentComponent<?> component, String keyName,String defaultName, Map<Locale,String> localeNames) {
		super();
		this.view = new SimpleObjectProperty<>(view);
		this.component = new SimpleObjectProperty<>(component);
		this.key = new SimpleStringProperty(keyName);
		this.defaultValue = new SimpleStringProperty(defaultName);
		this.setLocales(localeNames);
	}


	public SimpleObjectProperty<ContentView<?>> viewProperty(){
		return view;
	}

	public ContentView<?> getView() {
		return view.get();
	}

	public void setView(ContentView<?> viewName) {
		this.view.set(viewName);
	}

	public SimpleObjectProperty<ContentComponent<?>> componentProperty(){
		return component;
	}

	public ContentComponent<?> getComponent() {
		return component.get();
	}

	public void setComponent(ContentComponent<?> componentName) {
		this.component.set(componentName);
	}

	public SimpleStringProperty keyProperty(){
		return key;
	}

	public String getKey() {
		return key.get();
	}

	public void setKey(String keyName) {
		this.key.set(keyName);;
	}

	public SimpleStringProperty defaultValueProperty(){
		return defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue.get();
	}

	public void setDefaultValue(String defaultName) {
		this.defaultValue.set(defaultName);
	}

	public Map<Locale,SimpleStringProperty> getLocales() {
		return locales;
	}

	public void setLocales(Map<Locale,String> localeNames) {
		this.locales = new HashMap<>();
		localeNames.forEach( (loc, str) -> this.locales.put(loc,new SimpleStringProperty(str)));
	}

	/**
	 * puts a Locale-String pair into the map
	 * @param locName Locale for the new Entry
	 * @param val value for the Locale
	 */
	public void putKV(Locale locName, String val){
		locales.put(locName, new SimpleStringProperty(val));
	}
	
	public void removeLocale(Locale loc){
		locales.remove(loc);
	}

	public SimpleStringProperty getFor(Locale loc){
		return locales.get(loc);
	}
}
