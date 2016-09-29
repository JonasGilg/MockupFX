package de.mockup.ui.gui.windows.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class LocaleComboBox extends ComboBox<Locale> {

	private static ObservableList<Locale> localelist;

	static {
		localelist = FXCollections
				.observableArrayList((Arrays.stream(Locale.getAvailableLocales()).sorted(Comparator
								.comparing(Locale::getLanguage).thenComparing(Locale::getCountry))
						.toArray(Locale[]::new)));
	}

	/**
	 * Initializes a LocaleComboBox with all available Locales on this machine
	 */
	public LocaleComboBox() {
		super(localelist);
		init();
	}

	/**
	 * Initializes a LocaleComoBox with a given ObservableList of Locale values
	 *
	 * @param list
	 */
	public LocaleComboBox(ObservableList<Locale> list) {
		super(list);
		init();
	}

	private void init() {
		this.setCellFactory(listview -> new ListCell<Locale>() {
			@Override
			protected void updateItem(Locale loc, boolean bln) {
				super.updateItem(loc, bln);
				setText(loc != null ? loc.getDisplayName() : null);
			}
		});

		this.setConverter(new StringConverter<Locale>() {
			@Override
			public String toString(Locale loc) {
				return loc != null ? loc.getDisplayName() : null;
			}

			@Override
			public Locale fromString(String locString) {
				return null; // No conversion fromString needed.
			}
		});
	}
}
