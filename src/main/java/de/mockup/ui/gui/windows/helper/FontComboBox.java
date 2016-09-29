package de.mockup.ui.gui.windows.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.util.List;

public class FontComboBox extends ComboBox<Label> {
	private final ObservableList<Label> fontLabels;

	public FontComboBox() {
		super();
		List<String> names = javafx.scene.text.Font.getFamilies();
		fontLabels = FXCollections.observableArrayList();
		names.forEach(elem -> {
			Label tmp = new Label(elem);
			tmp.setFont(new Font(elem, 14));
			tmp.setTextFill(Color.BLACK);
			fontLabels.add(tmp);
		});
		setCellFactory(new Callback<ListView<Label>, ListCell<Label>>() {
			
			@Override
			public ListCell<Label> call(ListView<Label> p) {
				return new ListCell<Label>() {
					@Override
					protected void updateItem(Label t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getFont().getFamily());
							setFont(t.getFont());
							setTextFill(javafx.scene.paint.Color.BLACK);
						} else {
							setText(null);
						}
					}
				};
			}
		});
		setItems(fontLabels);
		setValue(fontLabels.get(0));
	}

	public void setFontValue(String s) {
		for(Label l : fontLabels) {
			if(l.getText().equals(s)) {
				setValue(l);
				return;
			}
		}
	}
}
