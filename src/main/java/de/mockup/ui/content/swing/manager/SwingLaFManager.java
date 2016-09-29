package de.mockup.ui.content.swing.manager;

import de.mockup.ui.content.ContentView;
import de.mockup.ui.content.swing.swingObjects.SwingView;
import de.mockup.ui.gui.windows.WorkingSurface;
import de.mockup.ui.gui.windows.helper.GlobalBundle;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import net.sourceforge.napkinlaf.NapkinTheme;

import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Initializes and controls the look and feels.
 */
public class SwingLaFManager {
	private final static HashMap<String, String[]> LAF_AND_THEMES = new HashMap<>();
	private static final String DEFAULT_THEME = "Default";

	private static String currentLaF;
	private static String currentTheme;

	public static void initialize() {

		initializeMetalLaF();
		initializeNimbusLaF();
		initializeNapkinLaF();
		initializeSubstanceLaF();
		initializePGSLaF();
		initializeSystemLaF();

		setCurrentLaF("Metal", METAL_OCEAN);
	}

	public static String getCurrentLaF() {
		return currentLaF;
	}

	public static String getCurrentTheme() {
		return currentTheme;
	}

	public static String[] getThemesToLaF(String laf) {
		return LAF_AND_THEMES.get(laf);
	}

	public static void setCurrentLaF(String key, String theme) {
		currentLaF = key;
		currentTheme = theme;
		UIManager.getDefaults().clear();
		try {
			switch (key) {
				case "Metal":
					setMetalLaF(theme);
					break;
				case "Nimbus":
					setNimbusLaF();
					break;
				case "Napkin":
					setNapkinLaF(theme);
					break;
				case "Substance":
					setSubstanceLaF(theme);
					break;
				case "System":
					setSystemLaF();
					break;
				case "PGS":
					setPGSLaF();
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getKeys() {
		String[] temp = new String[LAF_AND_THEMES.size()];
		int c = 0;
		for (String s : LAF_AND_THEMES.keySet()) {
			temp[c++] = s;
		}
		return temp;
	}

	private static final String METAL_OCEAN = "Ocean";

	private static void initializeMetalLaF() {
		String[] themes = {DEFAULT_THEME, METAL_OCEAN};
		putCurrentLaF("Metal", themes);
	}

	private static void setMetalLaF(String theme) {
		switch (theme) {
			case DEFAULT_THEME:
				MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
				break;
			case METAL_OCEAN:
				MetalLookAndFeel.setCurrentTheme(new OceanTheme());
				break;
		}
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private static void initializeNimbusLaF() {
		String[] theme = {"Default"};
		putCurrentLaF("Nimbus", theme);
	}

	private static void setNimbusLaF() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String NAPKIN_BLUEPRINT = "Blueprint";
	private static final String NAPKIN_WIRED = "Wired";

	private static void initializeNapkinLaF() {
		String[] themes = {DEFAULT_THEME, NAPKIN_BLUEPRINT, NAPKIN_WIRED};
		putCurrentLaF("Napkin", themes);
	}

	//"net.sourceforge.napkinlaf.NapkinLookAndFeel" (save the Path to the LookAndFeel (I lost it several times o.O ))
	private static void setNapkinLaF(String theme) {
		try {
			switch (theme) {
				case DEFAULT_THEME:
					NapkinTheme.Manager.setCurrentTheme("napkin");
					break;
				case NAPKIN_BLUEPRINT:
					NapkinTheme.Manager.setCurrentTheme("blueprint");
					break;
				case NAPKIN_WIRED:
					NapkinTheme.Manager.setCurrentTheme("debug");
					break;
			}
			UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String SUBSTANCE_AUTUMN = "Autumn";
	private static final String SUBSTANCE_BUSINESS_BLACK_STEEL = "Business Black Steel";
	private static final String SUBSTANCE_BUSINESS_BLUE_STEEL = "Business Blue Steel";
	private static final String SUBSTANCE_BUSINESS = "Business";
	private static final String SUBSTANCE_CERULEAN = "Cerulean";
	private static final String SUBSTANCE_CHALLENGER_DEEP = "Challenger Deep";
	private static final String SUBSTANCE_CREME_COFFEE = "Creme Coffee";
	private static final String SUBSTANCE_CREME = "Creme";
	private static final String SUBSTANCE_DUST_COFFEE = "Dust Coffee";
	private static final String SUBSTANCE_DUST = "Dust";
	private static final String SUBSTANCE_EMERALD_DUSK = "Emerald Dusk";
	private static final String SUBSTANCE_GEMINI = "Gemini";
	private static final String SUBSTANCE_GRAPHITE_AQUA = "Graphite Aqua";
	private static final String SUBSTANCE_GRAPHITE_GLASS = "Graphite Glass";
	private static final String SUBSTANCE_GRAPHITE = "Graphite";
	private static final String SUBSTANCE_MAGELLAN = "Magellan";
	private static final String SUBSTANCE_MARINER = "Mariner";
	private static final String SUBSTANCE_MIST_AQUA = "Mist Aqua";
	private static final String SUBSTANCE_MIST_SILVER = "Mist Silver";
	private static final String SUBSTANCE_MODERATE = "Moderate";
	private static final String SUBSTANCE_NEBULA_BRICK_WALL = "Nebula Brick Wall";
	private static final String SUBSTANCE_NEBULA = "Nebula";
	private static final String SUBSTANCE_OFFICE_BLACK_2007 = "Office Black 2007";
	private static final String SUBSTANCE_OFFICE_BLUE_2007 = "Office Blue 2007";
	private static final String SUBSTANCE_OFFICE_SILVER_2007 = "Office Silver 2007";
	private static final String SUBSTANCE_RAVEN = "Raven";
	private static final String SUBSTANCE_SAHARA = "Sahara";
	private static final String SUBSTANCE_TWILIGHT = "Twilight";

	private static void initializeSubstanceLaF() {
		String[] themes = {
                SUBSTANCE_AUTUMN,
                SUBSTANCE_BUSINESS_BLACK_STEEL,
                SUBSTANCE_BUSINESS_BLUE_STEEL,
                SUBSTANCE_BUSINESS,
                SUBSTANCE_CERULEAN,
                SUBSTANCE_CHALLENGER_DEEP,
                SUBSTANCE_CREME_COFFEE,
                SUBSTANCE_CREME,
                SUBSTANCE_DUST_COFFEE,
                SUBSTANCE_DUST,
                SUBSTANCE_EMERALD_DUSK,
                SUBSTANCE_GEMINI,
                SUBSTANCE_GRAPHITE_AQUA,
                SUBSTANCE_GRAPHITE_GLASS,
                SUBSTANCE_GRAPHITE,
                SUBSTANCE_MAGELLAN,
                SUBSTANCE_MARINER,
                SUBSTANCE_MIST_AQUA,
                SUBSTANCE_MIST_SILVER,
                SUBSTANCE_MODERATE,
                SUBSTANCE_NEBULA_BRICK_WALL,
                SUBSTANCE_NEBULA,
                SUBSTANCE_OFFICE_BLACK_2007,
                SUBSTANCE_OFFICE_BLUE_2007,
                SUBSTANCE_OFFICE_SILVER_2007,
                SUBSTANCE_RAVEN,
                SUBSTANCE_SAHARA,
                SUBSTANCE_TWILIGHT
        };
		putCurrentLaF("Substance", themes);
	}

	private static void setSubstanceLaF(String theme) {
		try {
			switch (theme) {
				case SUBSTANCE_AUTUMN:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel");
					break;
				case SUBSTANCE_BUSINESS_BLACK_STEEL:
					UIManager.setLookAndFeel(
							"org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel");
					break;
				case SUBSTANCE_BUSINESS_BLUE_STEEL:
					UIManager.setLookAndFeel(
							"org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel");
					break;
				case SUBSTANCE_BUSINESS:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel");
					break;
				case SUBSTANCE_CERULEAN:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceCeruleanLookAndFeel");
					break;
				case SUBSTANCE_CHALLENGER_DEEP:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceChallengerDeepLookAndFeel");
					break;
				case SUBSTANCE_CREME_COFFEE:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel");
					break;
				case SUBSTANCE_CREME:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel");
					break;
				case SUBSTANCE_DUST_COFFEE:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel");
					break;
				case SUBSTANCE_DUST:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel");
					break;
				case SUBSTANCE_EMERALD_DUSK:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceEmeraldDuskLookAndFeel");
					break;
				case SUBSTANCE_GEMINI:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel");
					break;
				case SUBSTANCE_GRAPHITE_AQUA:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel");
					break;
				case SUBSTANCE_GRAPHITE_GLASS:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel");
					break;
				case SUBSTANCE_GRAPHITE:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
					break;
				case SUBSTANCE_MAGELLAN:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel");
					break;
				case SUBSTANCE_MARINER:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel");
					break;
				case SUBSTANCE_MIST_AQUA:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel");
					break;
				case SUBSTANCE_MIST_SILVER:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");
					break;
				case SUBSTANCE_MODERATE:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel");
					break;
				case SUBSTANCE_NEBULA_BRICK_WALL:
					UIManager
							.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel");
					break;
				case SUBSTANCE_NEBULA:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel");
					break;
				case SUBSTANCE_OFFICE_BLACK_2007:
					UIManager
							.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel");
					break;
				case SUBSTANCE_OFFICE_BLUE_2007:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel");
					break;
				case SUBSTANCE_OFFICE_SILVER_2007:
					UIManager.setLookAndFeel(
							"org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel");
					break;
				case SUBSTANCE_RAVEN:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel");
					break;
				case SUBSTANCE_SAHARA:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel");
					break;
				case SUBSTANCE_TWILIGHT:
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel");
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initializePGSLaF() {
		String[] theme = {DEFAULT_THEME};
		putCurrentLaF("PGS", theme);
	}

	private static void setPGSLaF() {
		try {
			UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initializeSystemLaF() {
		String[] theme = {"Default"};
		putCurrentLaF("System", theme);
	}

	private static void setSystemLaF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void putCurrentLaF(String key, String[] themes) {
		LAF_AND_THEMES.put(key, themes);
	}

	public static Node getPreview() {

		ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.LaFBundle");

		SwingNode previewNode = new SwingNode();

		JPanel preview = new JPanel();
		preview.setLayout(new BorderLayout());

		JMenuBar menu = new JMenuBar();

		JMenu menu1 = new JMenu(resourceBundle.getString("file"));

		JMenuItem item1 = new JMenuItem(resourceBundle.getString("load"));

		JMenuItem item2 = new JMenuItem(GlobalBundle.getString("save"));

		JMenu menu2 = new JMenu(resourceBundle.getString("edit"));

		JMenuItem item3 = new JMenuItem(resourceBundle.getString("copy"));

		JMenuItem item4 = new JMenuItem(resourceBundle.getString("paste"));

		menu1.add(item1);
		menu1.add(item2);

		menu2.add(item3);
		menu2.add(item4);

		menu.add(menu1);
		menu.add(menu2);

		preview.add(menu, BorderLayout.NORTH);

		JPanel jpMain = new JPanel(new BorderLayout());

		preview.add(jpMain, BorderLayout.CENTER);

		JPanel toolBar = new JPanel();

		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton save = new JButton(UIManager.getIcon("FileView.floppyDriveIcon"));

		JButton load = new JButton(UIManager.getIcon("FileView.directoryIcon"));

		toolBar.add(save);
		toolBar.add(load);

		jpMain.add(toolBar, BorderLayout.NORTH);

		JTabbedPane tabbar = new JTabbedPane();

		JScrollPane textArea = new JScrollPane();

		tabbar.addTab("Text Editor", textArea);

		jpMain.add(tabbar, BorderLayout.CENTER);

		previewNode.setContent(preview);

		return previewNode;
	}

	/**
     * Updates the UI of all views.
     */
    public static void updateUI() {
		ArrayList<ContentView> v = WorkingSurface.get().getViews();
		for (ContentView vc : v) {
			SwingUtilities.updateComponentTreeUI(((SwingView) vc).getContent());
		}
	}
}
