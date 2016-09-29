package de.mockup.ui.gui.windows.dialogs.stringeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.StringTokenizer;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Service class which has all methods to manipulate and persist the model data.
 * designed to be reusable
 */
public class StringEditorService {

	
	/**
	 * map of filenames(String) to Properties
	 */
	private HashMap<Locale,Properties> propertiesMap;
	
	private ObservableList<TableDataModel> data;
	
	public StringEditorService(List<ContentView> views){
		propertiesMap = new HashMap<>();
		initiateData(views);
	}
	
	
	/**
	 * Initiates the data with the given Collection of views.
	 * If the {@code propertiesMap} is already filled, then the data will be supplied with their respective language Strings.
	 * @param views
	 */
	public void initiateData(Collection<ContentView> views){
		data = FXCollections.observableArrayList();
		System.out.println("Gr��e views:   " + views.size()); //zur zeit verbuggt: Viewgroesse nach Projektladen ist 0
		//iterate over every view to get its components
		for(ContentView<?> view : views){
			//iterate over every component to get its keys
			for(ContentComponent<?> comp: view.getChildren()){
				//iteration over available keys of the component
				for(String stringkey : comp.getStringKeys()){
					data.add(new TableDataModel(view, comp, stringkey, this.lookupDefaultValue(stringkey), this.lookupStrings(stringkey)));
				}
			}
		}
		System.out.println("datasize: " + data.size());
		data.forEach(System.out::println);
	}
	
	/**
	 * helper method to transform a String into a Locale
	 * @param s String to be transformed
	 * @return Locale based on the String or null if a Locale could not be created
	 */
	private static Locale toLocale(String s){
		StringTokenizer tempStringTokenizer = new StringTokenizer(s,"_");
		String l = null;
	    if(tempStringTokenizer.hasMoreTokens()) l = tempStringTokenizer.nextToken();
	    String c = null;
	    if(tempStringTokenizer.hasMoreTokens()) c = tempStringTokenizer.nextToken();
	    //returns null,a Locale specified by a language or a Locale specified by a language and country
	    return l == null ? null : (c == null ? new Locale(l) : new Locale(l,c));
	}
	
	/**
	 * transfers the information from {@code data} list to the internal Properties map 
	 */
	public void dataToProperties(){
		if(!data.isEmpty()){
			propertiesMap = new HashMap<Locale, Properties>();
			//create default and specific entries in PropertiesMap
			this.createProperties(null); // as default
			
			for(Locale loc : data.get(0).getLocales().keySet()){
				this.createProperties(loc);
			}
			
			//fill TableData into propertiesMap
			for (TableDataModel tData : data) {
				//for default Locale
				if(!this.addEntryToProperties(null, tData.getKey(), tData.getDefaultValue())){
					if(!this.editProperties(null, tData.getKey(), tData.getDefaultValue())){
						System.err.println(
							"Fehler beim eintragen in Properties: " + "default" + ", " + tData.getKey() + " = " +  tData.getDefaultValue());
					}
				}
				//for specific Locales
				for( Locale loc : tData.getLocales().keySet()){
					if(!this.addEntryToProperties(loc, tData.getKey(), tData.getFor(loc).get())){
						if(!this.editProperties(loc, tData.getKey(), tData.getFor(loc).get())){
							System.err.println(
								"Fehler beim eintragen in Properties: " + loc + ", " + tData.getKey() + " = " +  tData.getFor(loc).get());
						}
					}
				}
			}
			
		}
	}

	/**
	 * creates a new .properties file
	 * @param filename name of the new .properties file
	 * @return success of the operation
	 */
	private boolean createProperties(Locale lang){
		if(!propertiesMap.containsKey(lang)){
			propertiesMap.put(lang, new Properties());
			return true;
		}else
			return false;
	}

	/**
	 * Edits a key-value pair in a .properties file.
	 * @param filename name of the .properties file
	 * @param key the key, which will be associated with the new value
	 * @param newValue new value for the given key
	 * @return success of the operation
	 */
	private boolean editProperties(Locale lang,String key, String newValue){
		if(propertiesMap.containsKey(lang)){
			Properties prop = propertiesMap.get(lang);
			if(prop.containsKey(key)){
				prop.replace(key, newValue);
			}else{
				return false;
			}
		}else{
			return false;
		}

		return true;
	}

	/**
	 * Adds a new key-value pair to a .properties file.
	 * @param filename name of the .properties file
	 * @param newKey the key, which will be associated with a value
	 * @param newValue value belonging to the key
	 * @return success of the operation
	 */
	private boolean addEntryToProperties(Locale lang, String newKey, String newValue){
		if(propertiesMap.containsKey(lang)){
			Properties prop = propertiesMap.get(lang);
			if(!prop.containsKey(newKey)){
				prop.setProperty(newKey, newValue);
			}else{
				return false;
			}
		}else{
			return false;
		}

		return true;
	}
	
	/**
	 * finds the default value for a given key
	 * @param key key to be searched for a default value
	 * @return default value associated with the key
	 */
	private String lookupDefaultValue(String key){
		if(propertiesMap.containsKey(null)){
			return propertiesMap.get(null).getProperty(key);
		}else return "";
	}

	/**
	 * creates a Map with values for every language associated with a given key
	 * @param key key associated with {@link String}'s in different languages
	 * @return map with {@link Locale}'s mapped to the String belonging to the given key
	 */
	private Map<Locale,String> lookupStrings(String key){
		Map<Locale,String> map = new HashMap<Locale,String>();
		for(Entry<Locale,Properties> entry : propertiesMap.entrySet()){
			if(entry.getValue().containsKey(key)){
				map.put(entry.getKey(), entry.getValue().getProperty(key));
			}
		}

		return map;
	}
	
	/**
	 * loads the {@link Properties} map from the files in the given folder
	 * @param folderpath path of the .properties files
	 * @return success of the operation
	 */
	public boolean loadPropertiesFromFiles(String folderpath){
		//System.out.println("Strings laden");
		propertiesMap = new HashMap<>();
		File folder = new File(folderpath + File.separator + "languages");
		if(!folder.exists()){
			folder.mkdir();
		}
		File[] files = folder.listFiles();
		//System.out.println(Arrays.toString(files));
		if(files.length > 0){
			
			//filter for default .properties file
			Optional<File> f = Arrays.stream(files)
					.filter(filename -> filename.getAbsolutePath().endsWith(".properties"))
					.min((file1,file2) -> Integer.compare(file1.getAbsolutePath().length(), file2.getAbsolutePath().length()));
			Properties properties = new Properties();
			f.ifPresent( defFile -> {
				//System.out.println("DefaultStringfile: " + f.get().getPath());
				try {
					properties.load(new FileReader(f.get().getAbsolutePath()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				propertiesMap.put(null, properties);
			});
			
			//filter for desired files
			File[] propfiles = Arrays.stream(files)
					.filter( file -> file.getAbsolutePath().endsWith(".properties"))
					.sorted((file1,file2) -> Integer.compare(file1.getAbsolutePath().length(), file2.getAbsolutePath().length()))
					.skip(1)
					.toArray(File[]::new);

			//add Properties to the map
			for(File propfile : propfiles){
				String propfilepath = propfile.getAbsolutePath();
				String filename = propfilepath.substring(propfilepath.lastIndexOf(File.separator));
				System.out.println("Pfad: " + propfilepath);
				Locale loc = toLocale(filename.substring(filename.indexOf("_")+1, filename.indexOf(".properties")));
				Properties locprop = new Properties();
				try {
					locprop.load(new FileReader(propfilepath));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//System.out.println("Propertiesinhalt: " + locprop);
				propertiesMap.put(loc, locprop);
			}
			System.out.println("Gesamte Propertiesmap: " + propertiesMap);
			return true;
		}else return false;
	}
	
	/**
	 * writes the cached {@link Properties} to its respective files
	 * @param path to save the .properties files in
	 * @return success of the saving operation
	 */
	public boolean savePropertiesToFiles(String projPath){
		//System.out.println("Strings speichern");
		File folder = new File(projPath + File.separator + "languages");
		if(!folder.exists()){
			folder.mkdir();
		}
		
		for(Entry<Locale,Properties> entry : propertiesMap.entrySet()){
			//create files if necessary
			String filename = folder.getPath() + File.separator + "lang" + (entry.getKey() == null ? "" : "_" + entry.getKey()) + ".properties";
			if(!Files.exists(Paths.get(filename))){
				File file = new File(filename);
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Fehler beim Erstellen der Datei " + filename);
					return false;
				}
			}
			
			//store Properties objects in files
			try {
				entry.getValue().store(new FileWriter(filename), null);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Fehler beim Speichern von " + filename);
				return false;
			}
				
		}
		
		return true;
	}
	
	/**
	 * replaces an existing keyString of a component with a new one
	 * @param oldKey
	 * @param newKey
	 * @return success of the operation
	 */
	public boolean replaceStringKey(String oldKey, String newKey){
		for(Properties p : propertiesMap.values()){
			String val = p.getProperty(oldKey);
			p.setProperty(newKey, val);
		}
		return true;
	}
	
	/**
	 * Returns a String belonging to a language and the associated key
	 * @param loc language of the String
	 * @param keyString 
	 * @return
	 */
	public String getLanguageString(Locale loc, String keyString){
		if(propertiesMap.containsKey(loc)){
			return propertiesMap.get(loc).getProperty(keyString);
		}else return "---";
	}
	
	public Collection<Locale> getLanguages(){
		return propertiesMap.keySet();
	}
	
	public ObservableList<TableDataModel> getModelData(){
		return data;
	}
	
	
}
