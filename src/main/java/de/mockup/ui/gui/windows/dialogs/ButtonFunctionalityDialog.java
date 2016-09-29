package de.mockup.ui.gui.windows.dialogs;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.gui.windows.WorkingSurface;
import de.mockup.ui.gui.windows.helper.GlobalBundle;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;

public class ButtonFunctionalityDialog extends Stage {

	private final BorderPane contentRootPane;
	private ListView<ContentView> viewList;

	private final ContentComponent callingComponent;

	private ArrayList<String> viewNames;
	private ContentView selectedView;

	public ButtonFunctionalityDialog(Window owner, ContentComponent callingComponent) {
		this.callingComponent = callingComponent;
		initOwner(owner);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(800);
		setHeight(500);
		contentRootPane = new BorderPane();
		//------- initate content-----------------------------------
		initiateViewListPane();


		//----------------------------------------------------------
		ButtonBar buttonBar = new ButtonBar();
		Button okButton = new Button(GlobalBundle.getString("ok"));
		okButton.setOnAction(e -> {
			if(selectedView!=null) writeConnectionToButtonModel(selectedView);
            close();
		});
		Button cancelButton = new Button(GlobalBundle.getString("cancel"));
		cancelButton.setOnAction(e -> close());
		cancelButton.setDefaultButton(true);
		buttonBar.getButtons().addAll(okButton, cancelButton);

		BorderPane rootPane = new BorderPane();
		rootPane.setBottom(buttonBar);
		rootPane.setCenter(contentRootPane);
		Scene scene = new Scene(rootPane);
		setScene(scene);
		show();
	}

	private void initiateViewListPane() {
		viewList = new ListView();
        viewList.getItems().addAll(WorkingSurface.get().getViews());
		viewList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        viewList.setCellFactory(lv -> new ListCell<ContentView>() {
            @Override
            public void updateItem(ContentView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getTitle() ;
                    setText(text);
                }
            }
        });



		HBox viewListBar = new HBox();
		Text selectionText = new Text(GlobalBundle.getString("connectTo"));
		Text selectionMessage = new Text("");

		Button connectViewButton = new Button(GlobalBundle.getString("connectToView"));
		connectViewButton.setOnAction(e -> {
			selectedView = viewList.getSelectionModel().getSelectedItem();
            if (selectedView != null) {
                selectionMessage.setText(selectedView.getTitle());
                writeConnectionToButtonModel(selectedView);
            }
		});

		viewListBar.getChildren().addAll(selectionText, selectionMessage, connectViewButton);


		contentRootPane.setCenter(viewList);
		contentRootPane.setBottom(viewListBar);
	}



	private void writeConnectionToButtonModel(ContentView view) {

        ((de.mockup.ui.content.functionallity.interfaces.Button) callingComponent).connectToView(view);
	}
}
