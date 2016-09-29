package de.mockup.ui;

import de.mockup.system.Bundle;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.AppStatus;
import de.mockup.system.service.AppService;
import de.mockup.ui.gui.globalListener.GlobalListenerManager;
import de.mockup.ui.gui.guiController.ProjectController;
import de.mockup.ui.gui.guiController.UIActivator;
import de.mockup.ui.gui.guiController.WindowManager;
import de.mockup.ui.gui.windows.MainFrame;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class Application extends javafx.application.Application {

	private static Window primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public synchronized void start(Stage primaryStage) throws Exception {

		//needs to happen early!
		GlobalListenerManager.initGlobalMouseListeners();

		javafx.scene.image.Image image1 = new javafx.scene.image.Image("icons/win_64x64.png");
		javafx.scene.image.Image image2 = new javafx.scene.image.Image("icons/win_32x32.png");
		javafx.scene.image.Image image3 = new javafx.scene.image.Image("icons/win_16x16.png");
		primaryStage.getIcons().addAll(image1, image2, image3);

		if(!(SwingUtilities.isEventDispatchThread() && Platform.isFxApplicationThread())) {
			System.err.println("Launched without JVM argument '-Djavafx.embed.singleThread=true'!");
			System.exit(-1);
		}


		final ResourceBundle closeBundle = ResourceBundle.getBundle("properties.CloseConfirmDialog");
		Application.primaryStage = primaryStage;
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);

		new UIActivator().start();

        MainFrame mainPanel = MainFrame.get();
		Scene mainScene = new Scene(mainPanel);
        mainPanel.addApplicationListeners();

		mainScene.getStylesheets().add("stylesheets/mainFrame.css");


		primaryStage.setScene(mainScene);

		primaryStage.setOnCloseRequest(t -> {
			t.consume();
			AppStatus status = new AppStatus();
			status.setWindows(WindowManager.get().getState());
			AppService appService = Bundle.getService(AppService.class);
			try {
				appService.saveAppStatus(status);
			} catch (SystemException e) {
				e.printStackTrace();
			}

			//TODO if manually saved before closing.

			if (ProjectController.hasProject()&&ProjectController.isProjectDirty()) {
				Alert closeAlert = new Alert(Alert.AlertType.CONFIRMATION);

				closeAlert.setTitle(closeBundle.getString("title"));
				closeAlert.setHeaderText(closeBundle.getString("header"));
				closeAlert.setContentText(closeBundle.getString("content"));
				ButtonType yesButton = new ButtonType(closeBundle.getString("yes"), ButtonBar.ButtonData.YES);
				ButtonType cancelButton =
						new ButtonType(closeBundle.getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
				ButtonType saveButton = new ButtonType(closeBundle.getString("save"), ButtonBar.ButtonData.YES);
				closeAlert.getButtonTypes().setAll(saveButton, yesButton, cancelButton);
				Optional<ButtonType> result = closeAlert.showAndWait();
				if (result.isPresent()) {
					if (result.get() == yesButton) {
						System.exit(0);
					}
					if (result.get() == saveButton) {

						try {
							GuiToControllerFacade.saveProject();
							System.exit(0);
						} catch (SystemException e) {
							e.printStackTrace();
						}
					}
					if (result.get() == cancelButton) {
						//canceled
					}
				}
			} else {
				System.exit(0);
			}
		});

		primaryStage.setTitle("Mockup++");
		primaryStage.setScene(mainScene);
		primaryStage.show();

		//Restore the states of the windows
		AppService appService = Bundle.getService(AppService.class);
		try {
			WindowManager.get().restoreState(appService.getAppStatus().getWindows());
		} catch (SystemException e) {
			e.printStackTrace();
			WindowManager.get().restoreState(null);
		}

	}

	public static Window getPrimaryStage() {
		return primaryStage;
	}
}
