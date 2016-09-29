package de.mockup.ui.gui.windows.dialogs.stringeditor;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentView;
import de.mockup.ui.gui.windows.WorkingSurface;
import de.mockup.ui.gui.windows.helper.GlobalBundle;
import de.mockup.ui.gui.windows.helper.LocaleComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StringEditorView extends Stage{
	
	StringEditorController controller;
	
	/**
	 * the ResourceBundle for StringEditor
	 */
	public static final ResourceBundle resBundle = ResourceBundle.getBundle("properties.StringEditorBundle");

	private BorderPane content;
	private HBox subPane;

	private TableView<TableDataModel> table;
	private ObservableList<TableDataModel> data;
	
	private ObservableList<Locale> currentLangs;
	private ScrollPane scroll;

	private VBox buttonPane;

	private StackPane previewPane;
	private ImageView previewImageView;
	private final Color selectionColor = Color.VIOLET;

	private ButtonBar buttonBar;
	private Button okButton;
	private Button applyButton;
	private Button closeButton;

	private HBox addBox;
	private LocaleComboBox localeBox;
	private Button addButton;

	private HBox removeBox;
	private LocaleComboBox removeLocaleBox;
	
	private Button removeButton;
	
	private Runnable applyFunc;
	private Runnable cancelFunc;

	/**
	 * Constructs a View with methods from the given controller
	 * @param cont Controller for this view
	 */
	public StringEditorView(StringEditorController cont){
		super();
		controller = cont;
		setModelData(controller.getModelData());
		
		initModality(Modality.APPLICATION_MODAL);
		setTitle(resBundle.getString("editStrings"));
		//setResizable(true);
		setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.5);
		setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.75);

		content = new BorderPane();
		content.prefWidthProperty().bind(this.widthProperty());

		buildButtonPane();

		buildPreviewPane();

		buildButtonBar();

		buildTable();
		
		setApplyAction(controller::applyAction);
		setCancelAction(controller::cancelAction );
		
		
		//add languages
		addLanguages(controller.getLanguages());

		subPane = new HBox(previewPane,buttonPane);
		subPane.prefWidthProperty().bind(content.widthProperty());
		//HBox.setHgrow(subPane, Priority.ALWAYS);
		HBox.setHgrow(previewPane, Priority.ALWAYS);
		//HBox.setHgrow(buttonPane, Priority.ALWAYS);
		this.widthProperty().addListener( (observable,oldValue,newValue) ->{
			previewPane.setMaxWidth((Double)newValue*0.60);
			previewPane.setMinWidth((Double)newValue*0.60);
		});
		this.heightProperty().addListener( (observable,oldValue,newValue) ->{
			subPane.setMaxHeight((Double)newValue*0.5);
			subPane.setMinHeight((Double)newValue*0.5);
			scroll.setMaxHeight((Double)newValue*0.4);
			buttonBar.setMinHeight((Double)newValue*0.05);
		});

		content.setTop(scroll);
		content.setCenter(subPane);
		content.setBottom(buttonBar);

		Scene sc = new Scene(content);
		setScene(sc);
	}
	
	/**
	 * builds a pane with {@link ComboBox}'s and buttons to interact with.
	 */
	private void buildButtonPane() {
		//UI for adding languages
		localeBox = new LocaleComboBox();
		addButton = new Button(resBundle.getString("add"));
		addButton.setOnAction( evt -> {
			try {
				this.addLanguageToColumn();
			} catch (ExistingColumnException e) {
				new Alert(AlertType.ERROR,e.getMessage()).showAndWait();
			}
		});
		addBox = new HBox(localeBox,addButton);
		addBox.setSpacing(5);

		//UI for removing languages
		currentLangs = FXCollections.observableArrayList();
		removeLocaleBox = new LocaleComboBox(currentLangs);
		removeButton = new Button(resBundle.getString("remove"));
		removeButton.setOnAction( evt -> this.removeLanguage());
		removeBox = new HBox(removeLocaleBox,removeButton);
		removeBox.setSpacing(5);

		buttonPane = new VBox();
		buttonPane.setSpacing(10);
		VBox.setVgrow(addBox, Priority.ALWAYS);
		VBox.setVgrow(removeBox, Priority.ALWAYS);
		buttonPane.setPadding(new Insets(10,10,10,10));
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.getChildren().addAll(addBox,removeBox);

	}

	/**
	 * builds a {@link ButtonBar} with an OK, an Apply and a Cancel button
	 */
	private void buildButtonBar(){
		buttonBar = new ButtonBar();
		buttonBar.setPadding(new Insets(10, 5, 10, 0));
		buttonBar.setMinHeight(50);

		okButton = new Button(GlobalBundle.getString("ok"));
		okButton.setOnAction(event -> {
			this.applyFunc.run();
			close();
		});
		
		applyButton = new Button(GlobalBundle.getString("apply"));
		applyButton.setOnAction( evt -> applyFunc.run());
		applyButton.setDefaultButton(true);

		closeButton = new Button(GlobalBundle.getString("cancel"));
		closeButton.setCancelButton(true);
		closeButton.setOnAction( evt -> cancelFunc.run());

		buttonBar.getButtons().addAll(okButton, applyButton, closeButton);
	}

	/**
	 * builds a preview pane which will be half as big and half as wide as the view
	 * @default: first view
	 */
	private void buildPreviewPane(){
		this.previewPane = new StackPane();
		WritableImage img = new WritableImage(50, 50);
		previewImageView = new ImageView(img);
		previewImageView.fitHeightProperty().bind(previewPane.heightProperty());
		previewImageView.fitWidthProperty().bind(previewPane.widthProperty());
		previewPane.getChildren().add(previewImageView);

	}


	/**
	 * builds the {@link TableView} and fills it with value from .properties files
	 */
	@SuppressWarnings("unchecked")
	private void buildTable(){
		table = new TableView<>();
		table.setEditable(true);
		table.setItems(data);
		scroll = new ScrollPane(table);
		scroll.setFitToHeight(true);
		scroll.setFitToWidth(true);
		
		TableColumn<TableDataModel,ContentView<?>> viewColumn = new TableColumn<>(resBundle.getString("view"));
		viewColumn.setCellValueFactory( celldata -> celldata.getValue().viewProperty());
		viewColumn.setCellFactory( table -> {
			TableCell<TableDataModel,ContentView<?>> cell = new TableCell<TableDataModel, ContentView<?>>() {
		        @Override protected void updateItem(ContentView<?> item, boolean empty) {
		            super.updateItem(item, empty);
		            setText(item == null || empty ? null : item.getTitle());
		        }
			};
			//if a cell in the view column is doubleclicked the respective view will be shown and progress in the StringEditor will be saved
			cell.setOnMouseClicked( evt -> {
                if (evt.getClickCount() == 2) {
                    applyFunc.run();
					close();
					WorkingSurface.get().selectView((ContentView<?>) ((Cell<ContentView<?>>) evt.getSource()).getItem());
                }            
            });
            return cell;
	    });

		TableColumn<TableDataModel,ContentComponent<?>> componentColumn = new TableColumn<>(resBundle.getString("component"));
		componentColumn.setCellValueFactory(celldata -> celldata.getValue().componentProperty());
		componentColumn.setCellFactory( table -> new TableCell<TableDataModel, ContentComponent<?>>() {
	        @Override protected void updateItem(ContentComponent<?> item, boolean empty) {
	            super.updateItem(item, empty);
	            setText(item == null || empty ? null : item.getIdentifier());
	        }
	    });

		TableColumn<TableDataModel,String> keyColumn = new TableColumn<>(resBundle.getString("key"));
		keyColumn.setCellValueFactory(celldata -> celldata.getValue().keyProperty());
		keyColumn.setOnEditCommit(t -> ((TableDataModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setKey(t.getNewValue()));

		TableColumn<TableDataModel,String> defaultColumn = new TableColumn<>(resBundle.getString("defVal"));
		defaultColumn.setCellValueFactory(celldata -> celldata.getValue().defaultValueProperty());
		defaultColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		defaultColumn.setOnEditCommit(t -> ((TableDataModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDefaultValue(t.getNewValue()));
		
		table.getColumns().add(viewColumn);
		table.getColumns().add(componentColumn);
		table.getColumns().add(keyColumn);
		table.getColumns().add(defaultColumn);
		
		table.getColumns().forEach( col -> col.setStyle( "-fx-alignment: CENTER;"));
		table.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> this.updatePreviewPane(newValue));
		
	}
	
	/**
	 * adds a {@link TableColumn} representing a Locale to the {@link TableView}
	 * @throws ExistingColumnException
	 */
	private void addLanguageToColumn() throws ExistingColumnException{
		addLanguageToColumn(localeBox.getValue());

	}

	/**
	 * adds a {@link TableColumn} representing a Locale to the {@link TableView}
	 * @param loc Locale to be added to the {@link TableView}
	 * @throws ExistingColumnException if Column already exists
	 */
	private void addLanguageToColumn(Locale loc) throws ExistingColumnException{
		//loc == null means default language
		if(loc != null){
			TableColumn<TableDataModel,String> column = new TableColumn<TableDataModel,String>(loc.toString());
			for(TableColumn<TableDataModel,?> col : table.getColumns()){
				if(col.getText().equals(column.getText())){
					//Column with this Locale already exists, therefore we do nothing and notify the user
					throw new ExistingColumnException(loc);
				}
			}
			column.setCellValueFactory( celldata -> celldata.getValue().getFor(loc));
			column.setCellFactory(TextFieldTableCell.forTableColumn());
			column.setOnEditCommit(t -> ((TableDataModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).putKV(loc,t.getNewValue()));
			Label columnLabel = new Label(resBundle.getString("language") + ": ");
			columnLabel.setTooltip(new Tooltip(loc.getDisplayName()));
			column.setGraphic(columnLabel);
			column.setPrefWidth(150);
			column.setStyle( "-fx-alignment: CENTER;");
			table.getColumns().add(column);
			currentLangs.add(loc);
		}
	}
	
	/**
	 * removes a {@link TableColumn} in the {@link TableView}
	 */
	private void removeLanguage(){
		Locale toRemove = removeLocaleBox.getValue();
		for(TableColumn<TableDataModel,?> t : table.getColumns()){
			if(t.getText().equals(toRemove.toString())){
				table.getColumns().remove(t);
				currentLangs.remove(toRemove);
				table.getItems().forEach(tabledata -> tabledata.removeLocale(toRemove));
				return;
			}
		}
	}
	
	/**
	 * @param data {@link TableDataModel} which the preview pane uses to update its contents
	 */
	private void updatePreviewPane(TableDataModel data){
		SnapshotParameters snap = new SnapshotParameters();
        snap.setViewport(new Rectangle2D(data.getView().getX(), data.getView().getY(), data.getView().getWidth(), data.getView().getHeight()));
		WritableImage img = WorkingSurface.get().getViewNode(data.getView()).snapshot(snap, null);
		PixelWriter pixelwriter = img.getPixelWriter();
		Rectangle r = data.getComponent().getBoundsOnView();
		int margin = 7; //how thick is the border of the drawn rectangle
		for(int x = r.x; x < (r.x +r.width); ++x){
			for(int y = r.y; y < (r.y +r.height); ++y){
				//Workaround: PixelWriter hat Probleme mit transparenten Farben, daher wird nur Rand der Komponente markiert
				if(x< r.x+margin || x > (r.x+r.width-margin) || y <r.y +margin || y > (r.y+r.height-margin)){
					pixelwriter.setColor(x, y, selectionColor);
				}
			}
		}
		previewImageView.setImage(img);
	}
	
	/**
	 * adds languages from a collection, which most likely comes from the controller, to the table
	 * @param langs Languages to add to the table
	 */
	public void addLanguages(Collection<Locale> langs){
		for(Locale loc : langs){
			try {
				this.addLanguageToColumn(loc);
			} catch (ExistingColumnException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * sets the data to use in the table
	 * @param list
	 */
	public void setModelData(ObservableList<TableDataModel> list) {
		data = list;
	}


	/**
	 * function to map to the OK and apply button
	 * @param func
	 */
	public void setApplyAction(Runnable func) {
		this.applyFunc = func;
	}


	/**
	 * function to map to the Cancel button
	 * @param func
	 */
	public void setCancelAction(Runnable func) {
		this.cancelFunc = func;
	}

	public void setAddButton(Button addButton) {
		this.addButton = addButton;
	}

	public void setRemoveButton(Button removeButton) {
		this.removeButton = removeButton;
	}

	public void setPreviewImageView(ImageView previewImageView) {
		this.previewImageView = previewImageView;
	}

	public void setLocaleBox(LocaleComboBox localeBox) {
		this.localeBox = localeBox;
	}

	/**
	 * sets the languages, that will appear in the Combobox, which removes languages from the table.
	 * To avoid confusion only languages that are already in the table should be added here.
	 * @param list
	 */
	public void setRemovableLocales(List<Locale> list) {
		for(Locale l : list){
			currentLangs.add(l);
		}
	}

}
