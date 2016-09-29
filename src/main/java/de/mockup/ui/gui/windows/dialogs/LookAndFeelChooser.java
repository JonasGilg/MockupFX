package de.mockup.ui.gui.windows.dialogs;

import de.mockup.ui.gui.windows.helper.GlobalBundle;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ResourceBundle;

/**
 * Lets the user choose the Look and Feel of the Application.
 */
public class LookAndFeelChooser extends Stage {

	private final LookAndFeelChooserPane mainPane = new LookAndFeelChooserPane();

	public LookAndFeelChooser(Window owner) {
		super();
		initOwner(owner);
		initModality(Modality.APPLICATION_MODAL);
		setTitle(ResourceBundle.getBundle("properties.LaFBundle").getString("choose.a.look.and.feel"));
		setResizable(true);
		setX(500);
		setY(500);
		setWidth(800);
		setHeight(500);

		Scene s = new Scene(mainPane);

		setScene(s);

		mainPane.setPadding(new Insets(5, 5, 0, 5));

		ButtonBar buttonBar = new ButtonBar();

		buttonBar.setPadding(new Insets(10, 5, 10, 0));

		Button applyButton = new Button(GlobalBundle.getString("ok"));
		applyButton.setDefaultButton(true);
		applyButton.setOnAction(event -> {
			mainPane.apply();
			close();
		});

		Button closeButton = new Button(GlobalBundle.getString("cancel"));
		closeButton.setCancelButton(true);
		closeButton.setOnAction(event -> {
			mainPane.cancel();
			close();
		});

		buttonBar.getButtons().addAll(applyButton, closeButton);

		mainPane.setBottom(buttonBar);

		show();
	}
}
