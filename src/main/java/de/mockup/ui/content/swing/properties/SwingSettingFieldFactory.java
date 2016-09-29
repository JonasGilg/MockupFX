package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.SwingFXUtils;
import de.mockup.ui.gui.guiController.UndoRedo;
import de.mockup.ui.gui.properties.Property;
import de.mockup.ui.gui.windows.PropertyWindow;
import de.mockup.ui.gui.windows.helper.GlobalBundle;
import de.mockup.ui.gui.windows.helper.LocaleComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;


/**
 * Factory class that creates a JComponent that is used to adjust the value of the variable.
 * The SettingFields will make calls to the get and set methods of the given Property
 * and implements a very specific way to enable communication between the Component on the WorkingSurface
 * and the Property associated with it on the PropertyWindow.
 */
class SwingSettingFieldFactory {

	private static final ResourceBundle settingFieldBundle = ResourceBundle.getBundle("properties.SettingFieldBundle");

	//don't instantiate the factory
	private SwingSettingFieldFactory() {
	}


	static class IntSetter extends HBox implements SwingPropertySetter {
		private final Property<Integer> property;
		private final TextField field;

		IntSetter(Property<Integer> prop) {
			super();
			field = new TextField();
			field.setPrefColumnCount(3);
			this.property = prop;
			this.setOnKeyPressed(evt -> {
				if (evt.getCode() == KeyCode.ENTER) {
					new UndoRedo<>("Integer", prop.getComponent(), prop.getSetter(), prop.get(), getValue());
					this.sendNewValue(() -> property.set(getValue()));
				}
			});
			this.getChildren().add(new Label(property.getVarName() + ": "));
			this.getChildren().add(field);

			field.setText("" + property.get());
		}

		private Integer getValue() {
			return Integer.parseInt(field.getText());
		}
	}

	public static HBox createForInteger(Property<Integer> prop) {
		return new IntSetter(prop);
	}

	static class LongSetter extends HBox implements SwingPropertySetter {
		private final Property<Long> property;
		private final TextField field;

		LongSetter(Property<Long> prop) {
			super();
			field = new TextField();
			field.setPrefColumnCount(3);
			this.property = prop;
			this.setOnKeyPressed(evt -> {
				if (evt.getCode() == KeyCode.ENTER) {
					new UndoRedo<>("Long", prop.getComponent(), prop.getSetter(), prop.get(), getValue());
					this.sendNewValue(() -> property.set(getValue()));
				}
			});
			this.getChildren().add(new Label(property.getVarName() + ": "));
			this.getChildren().add(field);

			field.setText("" + property.get());
		}

		private Long getValue() {
			return Long.parseLong(field.getText());
		}

	}

	public static HBox createForLong(Property<Long> prop) {
		return new LongSetter(prop);
	}


	static class DoubleSetter extends HBox implements SwingPropertySetter {
		private final Property<Double> property;
		private final TextField field;

		DoubleSetter(Property<Double> prop) {
			super();
			field = new TextField();
			field.setPrefColumnCount(3);
			this.property = prop;
			this.setOnKeyPressed(evt -> {
				if (evt.getCode() == KeyCode.ENTER) {
					new UndoRedo<>("Double", prop.getComponent(), prop.getSetter(), prop.get(), getValue());
					this.sendNewValue(() -> property.set(getValue()));
				}
			});
			this.getChildren().add(new Label(property.getVarName() + ": "));
			this.getChildren().add(field);
            field.setText("" + property.get());
		}

		private Double getValue() {
			return Double.parseDouble(field.getText());
		}


	}

	public static HBox createForDouble(Property<Double> prop) {
		return new DoubleSetter(prop);
	}

	static class FloatSetter extends HBox implements SwingPropertySetter {
		private final Property<Float> property;
		private final TextField field;

		FloatSetter(Property<Float> prop) {
			super();
			this.property = prop;
			field = new TextField();
			field.setPrefColumnCount(3);

			this.setOnKeyPressed(evt -> {
				if (evt.getCode() == KeyCode.ENTER) {
					new UndoRedo<>("Float", prop.getComponent(), prop.getSetter(), prop.get(), getValue());
					this.sendNewValue(() -> property.set(getValue()));
				}

			});
			this.getChildren().add(new Label(property.getVarName() + ": "));
			this.getChildren().add(field);
            field.setText("" + property.get());
		}

		private Float getValue() {
			return Float.parseFloat(field.getText());
		}
	}

	public static HBox createForFloat(Property<Float> prop) {
		return new FloatSetter(prop);
	}

	static class StringSetter extends HBox implements SwingPropertySetter {
		private final Property<String> property;
		final TextField field;
		private BiConsumer<String,String> function;

		StringSetter(Property<String> prop) {
			super();
			this.property = prop;
			field = new TextField();
			function = null;
			this.setOnKeyPressed(evt -> {
				if (evt.getCode() == KeyCode.ENTER) {
					this.sendNewValue(() -> {
						String oldString = prop.get();
						new UndoRedo<>("String", prop.getComponent(), prop.getSetter(), prop.get(), field.getText());
						property.set(field.getText());
						if(function != null) function.accept(oldString,field.getText());
					});
				}
			});
			this.getChildren().add(new Label(property.getVarName() + ": "));
			this.getChildren().add(field);
			field.setText(property.get());
		}
		
		public void setUpdateFunction(BiConsumer<String,String> func){
			function = func;
		}
	}

	public static StringSetter createForString(Property<String> prop) {
		return new StringSetter(prop);
	}

	static class BooleanSetter extends CheckBox implements SwingPropertySetter {
		final Property<Boolean> property;

		BooleanSetter(Property<Boolean> prop) {
			super(prop.getVarName());
			this.property = prop;
			this.setOnAction(evt -> this.sendNewValue(() -> property.set(this.isSelected())));
			this.setSelected(property.get());
		}
	}

	public static CheckBox createForBoolean(Property<Boolean> prop) {
		return new BooleanSetter(prop);
	}

	static class ColorSetter extends VBox implements SwingPropertySetter {
		private final Property<java.awt.Color> property;
		private Color oldColor;
		private Color newColor;

		private final ColorPicker colorPicker;

		ColorSetter(Property<java.awt.Color> prop) {
			super();
			this.property = prop;
			this.setSpacing(5);
			this.setPadding(new javafx.geometry.Insets(5, 5, 5, 5));

			try {
				oldColor = new Color(prop.get().getRGB());
			} catch (NullPointerException ignored) {}

			colorPicker = new ColorPicker();
			colorPicker.setMinWidth(PropertyWindow.get().getWidth() / 3);
			colorPicker.setMinHeight(colorPicker.getMinWidth() / 2);

			colorPicker.setOnAction(event -> {
				newColor = SwingFXUtils.colorJavafxToAwt(colorPicker.getValue());
				this.sendNewValue(() -> property.set(newColor));
				new UndoRedo<Color>("color", property.getComponent(), color -> {
					property.getSetter().accept(color);
					colorPicker.setValue(SwingFXUtils.colorAwtToJavafx(color));
				}, oldColor, newColor);
				oldColor = newColor;
			});

			this.getChildren().add(new Label(property.getVarName()));
			this.getChildren().add(colorPicker);

			Color c = property.get();
			if(c != null) {
				colorPicker.setValue(SwingFXUtils.colorAwtToJavafx(c));
			}
		}

	}

	public static VBox createForColor(Property<Color> prop) {
		return new ColorSetter(prop);
	}


	static class IconSetter extends VBox implements SwingPropertySetter {
		private final ImageView view;
		final int size = 80;
		private final Property<Icon> property;


		//TODO
		IconSetter(Property<Icon> prop) {
			super();
			this.property = prop;
			view = new ImageView();
			Label imglabel = new Label(property.getVarName() + ": ");
			Button button = new Button(settingFieldBundle.getString("changeIcon"));

			button.setOnMousePressed(evt -> {
				FileChooser chooser = new FileChooser();
				ExtensionFilter filter = new ExtensionFilter(GlobalBundle.getString("images"), ".jpeg", ".png", ".gif", ".bmp");
				chooser.setSelectedExtensionFilter(filter);
				File file = chooser.showOpenDialog(null);
				if (file != null) {
					String str = file.toURI().toString();
					this.sendNewValue(() -> {
						ImageIcon icon = null;
						try {
							icon = new ImageIcon(file.toURI().toURL());
						} catch (Exception e) {
							e.printStackTrace();
						}
						property.set(icon);
					});
					Image img = new Image(str, size, size, true, true);
					view.setImage(img);
				}
			});
			this.getChildren().addAll(imglabel, view, button);
		}
	}

	public static VBox createForIcon(Property<Icon> prop) {
		return new IconSetter(prop);
	}

	static class FontSetter extends GridPane implements SwingPropertySetter {

		private final Property<Font> property;
		private Spinner<Integer> sizespinner;
		private ComboBox<Label> familybox;
		private CheckBox italicBox;
		private CheckBox boldBox;
		private final ObservableList<Label> fontlabels;

		FontSetter(Property<Font> prop) {
			super();
			this.property = prop;
			Runnable r = () -> {
				int italic = italicBox.isSelected() ? Font.ITALIC : 0;
				int bold = boldBox.isSelected() ? Font.BOLD : 0;
				String family = familybox.getValue() != null ?
						familybox.getValue().getFont().getFamily()
						: property.get().getFamily();
				Font newFont = new Font(family, italic | bold, sizespinner.getValue());
				//TODO: Font Undo/Redo
				property.set(newFont);
			};
			this.setVgap(10);
			List<String> names = javafx.scene.text.Font.getFamilies();
			//die AWT Variante
			/*List<String> names = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());*/
			fontlabels = FXCollections.observableArrayList();
			names.forEach(elem -> {
				Label tmp = new Label(elem);
				tmp.setFont(new javafx.scene.text.Font(elem, 14));
				tmp.setTextFill(javafx.scene.paint.Color.BLACK);
				fontlabels.add(tmp);
			});
			Label fontlabel = new Label(GlobalBundle.getString("fontFamily"));
			familybox = new ComboBox<>(fontlabels);
			familybox.setCellFactory(new Callback<ListView<Label>, ListCell<Label>>() {
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
			familybox.valueProperty().addListener((spin, old, fresh) -> sendNewValue(r));

			sizespinner = new Spinner<>();
			sizespinner.setEditable(true);
			sizespinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, prop.get().getSize()));

			sizespinner.valueProperty().addListener((spin, old, fresh) -> sendNewValue(r));

			italicBox = new CheckBox(GlobalBundle.getString("italic"));
			italicBox.setSelected(property.get().isItalic());
			italicBox.setOnAction(evt -> sendNewValue(r));

			boldBox = new CheckBox(GlobalBundle.getString("bold"));
			boldBox.setSelected(property.get().isBold());
			boldBox.setOnAction(evt -> sendNewValue(r));

			this.add(fontlabel, 0, 0);
			this.add(familybox, 0, 1);
			this.add(new FlowPane(new Label(GlobalBundle.getString("size")), sizespinner), 0, 2);
			this.add(italicBox, 0, 3);
			this.add(boldBox, 0, 4);

			Font f = property.get();
			sizespinner.getValueFactory().setValue(f.getSize());
			italicBox.setSelected(f.isItalic());
			boldBox.setSelected(f.isBold());

			ObservableList<Label> list = familybox.getItems();
			Label toTest = new Label(f.getFamily());
			toTest.setFont(new javafx.scene.text.Font(f.getFamily(), 14));
			for (Label l : list) {
				if (l.getFont().getFamily().equals(toTest.getFont().getFamily())) {
					familybox.setValue(l);
				}
			}
		}
	}

	public static GridPane createForFont(Property<Font> prop) {
		return new FontSetter(prop);
	}

	public static class DimensionSetter extends GridPane implements SwingPropertySetter, Observer {
		final private TextField wField;
		final private TextField hField;
		private final Property<Dimension> property;

		DimensionSetter(final Property<Dimension> prop) {
			super();
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);
			this.property = prop;
			this.setHgap(5);
			wField = new TextField();
			wField.setPrefColumnCount(3);
			hField = new TextField();
			hField.setPrefColumnCount(3);
			EventHandler<? super KeyEvent> listener = evt -> {
				if (evt.getCode() == KeyCode.ENTER) {
					sendNewValue(() -> {
						Dimension value = new Dimension(new Integer(wField.getText()), new Integer(hField.getText()));
						new UndoRedo<>("Dimension", prop.getComponent(), prop.getSetter(), prop.get(), value);
						property.set(value);
					});
				}
			};
			wField.setOnKeyPressed(listener);
			hField.setOnKeyPressed(listener);
			this.add(new Label(property.getVarName()), 0, 0, 2, 1);
			this.add(new Label(GlobalBundle.getString("width")), 0, 1);
			this.add(wField, 0, 2);
			this.add(new Label(GlobalBundle.getString("height")), 1, 1);
			this.add(hField, 1, 2);
			update(prop.getComponent(), "property.bounds");
		}

		@Override
		public void update(Observable observing, Object arg) {
            if("property.bounds".equals(arg)) {
                wField.setText("" + (property.get().width));
                hField.setText("" + (property.get().height));
            }
		}
	}

	public static GridPane createForDimension(Property<Dimension> prop) {
		return new DimensionSetter(prop);
	}

	public static class PointSetter extends GridPane implements SwingPropertySetter, Observer {
		final private TextField xField;
		final private TextField yField;
		private final Property<Point> property;

		PointSetter(final Property<Point> prop) {
			super();
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);
			this.property = prop;
			this.setHgap(5);
			xField = new TextField();
			xField.setPrefColumnCount(3);
			yField = new TextField();
			yField.setPrefColumnCount(3);
			EventHandler<? super KeyEvent> listener = evt -> {
				if (evt.getCode() == KeyCode.ENTER) {
					sendNewValue(() -> {
						Point value = new Point(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
						new UndoRedo<>("Point", prop.getComponent(), prop.getSetter(), prop.get(), value);
						property.set(value);
					});
				}
			};
			xField.setOnKeyPressed(listener);
			yField.setOnKeyPressed(listener);

			this.add(new Label(property.getVarName()), 0, 0);
			this.add(new Label("X:"), 0, 1);
			this.add(xField, 0, 2);
			this.add(new Label("Y:"), 1, 1);
			this.add(yField, 1, 2);
			update(prop.getComponent(), "property.bounds");
		}

		@Override
		public void update(Observable observing, Object arg) {
            if("property.bounds".equals(arg)) {
                xField.setText("" + (property.get().x));
                yField.setText("" + (property.get().y));
            }
		}
	}

	public static GridPane createForPoint(Property<Point> prop) {
		return new PointSetter(prop);
	}

	@Deprecated
	public static JComponent createForvalueList(Property prop, Collection<?> plist) {
		/*class ConstListSetter extends  JComboBox<String> implements PropertySetter {
			private ConstListSetter it = this;
			ConstListSetter(Collection<Class<?>> plist){
				//transform the Class<?> list into a String list
				Vector<String> list = new Vector<String>();
				list.add(null);
				for(Class<?> cls: plist){
					list.add(cls.getTitle());
				}

				this.setModel(new DefaultComboBoxModel<String>(list));
				this.setSelectedIndex(list.indexOf(itself.getCommand().get()));
				this.addActionListener(new ActionListener(){
					@Override public void actionPerformed(ActionEvent e) {
						itself.getCommand().set(it.getSelectedItem());
					}
				});
			}

			@Override
			public PropertyConverter getConverter() {
				return new DefaultPropertyConverter(command);
			}
		}*/
		return null;
	}

	static class RectangleSetter extends GridPane implements SwingPropertySetter, Observer {

		private final Property<Rectangle> property;
		TextField Xfield;
		TextField Yfield;
		TextField widthfield;
		TextField heightfield;

		RectangleSetter(Property<Rectangle> prop) {
			super();
			this.property = prop;
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);
			this.setHgap(10);

			EventHandler<KeyEvent> listener = evt -> {
				if (evt.getCode() == KeyCode.ENTER) {
					sendNewValue(() -> {
						new UndoRedo<>("Rectangle", prop.getComponent(),
								prop.getSetter(), prop.get(), new Rectangle(this.getValue(Xfield), this.getValue(Yfield),
								this.getValue(widthfield),
								this.getValue(heightfield)));
						property.set(new Rectangle(this.getValue(Xfield), this.getValue(Yfield),
								this.getValue(widthfield), this.getValue(heightfield)));
					});
				}
			};
			int columnsize = 3;
			Label Xlabel = new Label(settingFieldBundle.getString("xpos"));
			Xfield = new TextField();
			Xfield.setPrefColumnCount(columnsize);
			Xfield.setOnKeyPressed(listener);
			Label Ylabel = new Label(settingFieldBundle.getString("ypos"));
			Yfield = new TextField();
			Yfield.setPrefColumnCount(columnsize);
			Yfield.setOnKeyPressed(listener);
			Label widthlabel = new Label(GlobalBundle.getString("width"));
			widthfield = new TextField();
			widthfield.setPrefColumnCount(columnsize);
			widthfield.setOnKeyPressed(listener);
			Label heightlabel = new Label(GlobalBundle.getString("height"));
			heightfield = new TextField();
			heightfield.setPrefColumnCount(columnsize);
			heightfield.setOnKeyPressed(listener);

			this.add(new Label(property.getVarName()), 0, 0, 2, 1);
			this.add(Xlabel, 0, 1);
			this.add(Xfield, 0, 2);
			this.add(Ylabel, 1, 1);
			this.add(Yfield, 1, 2);
			this.add(widthlabel, 2, 1);
			this.add(widthfield, 2, 2);
			this.add(heightlabel, 3, 1);
			this.add(heightfield, 3, 2);

			update(prop.getComponent(), null);
		}

		@Override
		public void update(Observable observing, Object arg) {
			Xfield.setText("" + (int) property.get().getX());
			Yfield.setText("" + (int) property.get().getY());
			widthfield.setText("" + (int) property.get().getWidth());
			heightfield.setText("" + (int) property.get().getHeight());

		}

		private Integer getValue(TextField field) {
			return Integer.parseInt(field.getText());
		}

	}

	public static GridPane createForRectangle(Property<Rectangle> prop) {
		return new RectangleSetter(prop);
	}

	static class ComponentOrientationSetter extends VBox implements SwingPropertySetter, Observer {

		private final Property<ComponentOrientation> property;
		private final RadioButton[] radios = new RadioButton[3];
		private final ToggleGroup group;

		ComponentOrientationSetter(Property<ComponentOrientation> prop) {
			super();
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);
			this.property = prop;
			this.setSpacing(3);
			group = new ToggleGroup();
			EventHandler<ActionEvent> listener = evt -> {
				ComponentOrientation result;
				Toggle stmt = group.getSelectedToggle();
				if (stmt == radios[0]) {
					result = ComponentOrientation.LEFT_TO_RIGHT;
				} else if (stmt == radios[1]) {
					result = ComponentOrientation.RIGHT_TO_LEFT;
				} else {
					result = ComponentOrientation.UNKNOWN;
				}
				new UndoRedo<>("CompOrientation", prop.getComponent(), prop.getSetter(), prop.get(), result);
				sendNewValue(() -> prop.set(result));
			};
			radios[0] = new RadioButton(settingFieldBundle.getString("leftright"));
			radios[1] = new RadioButton(settingFieldBundle.getString("rightleft"));
			radios[2] = new RadioButton(settingFieldBundle.getString("unknown"));
			this.getChildren().add(new Label(property.getVarName() + ":"));
			for (RadioButton r : radios) {
				r.setToggleGroup(group);
				r.setOnAction(listener);
				this.getChildren().add(r);
			}
			update(prop.getComponent(), null);
		}

		@Override
		public void update(Observable observing, Object arg) {
			ComponentOrientation orient = property.get();
			if (orient.equals(ComponentOrientation.LEFT_TO_RIGHT)) {
				radios[0].setSelected(true);
			}
			if (orient.equals(ComponentOrientation.RIGHT_TO_LEFT)) {
				radios[1].setSelected(true);
			}
			if (orient.equals(ComponentOrientation.UNKNOWN)) {
				radios[2].setSelected(true);
			}

		}
	}

	public static VBox createForComponentOrientation(Property<ComponentOrientation> prop) {
		return new ComponentOrientationSetter(prop);
	}

	static class LocaleSetter extends HBox implements SwingPropertySetter, Observer {

		private final Property<Locale> property;
		final ComboBox<Locale> localebox;

		LocaleSetter(Property<Locale> prop) {
			super();
			this.property = prop;
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);

			localebox = new LocaleComboBox();
			localebox.valueProperty().addListener((spin, old, fresh) -> {
				new UndoRedo<>("Locale", prop.getComponent(), prop.getSetter(), prop.get(), fresh);
				sendNewValue(() -> property.set(fresh));
			});
			this.getChildren().add(new Label(property.getVarName() + ": "));
			this.getChildren().add(localebox);

			update(prop.getComponent(), null);
		}

		@Override
		public void update(Observable observing, Object arg) {
			Locale loc = property.get();
			localebox.setValue(loc);

		}

	}

	public static HBox createForLocale(Property<Locale> prop) {
		return new LocaleSetter(prop);
	}

	static class CursorSetter extends HBox implements SwingPropertySetter, Observer {

		private final Property<Cursor> property;
		private final ComboBox<Cursor> cursorbox;

		public CursorSetter(Property<Cursor> prop) {
			super();
			this.property = prop;
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);
			ArrayList<Cursor> cursors = new ArrayList<>();
			for (int i = 0; i < 14; ++i) {
				cursors.add(Cursor.getPredefinedCursor(i));
			}

			cursorbox = new ComboBox<>(FXCollections.observableList(cursors));
			cursorbox.setCellFactory(new Callback<ListView<Cursor>, ListCell<Cursor>>() {
				@Override
				public ListCell<Cursor> call(ListView<Cursor> p) {
					return new ListCell<Cursor>() {
						@Override
						protected void updateItem(Cursor t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								setText(t.getName());
							} else {
								setText(null);
							}
						}
					};
				}
			});
			cursorbox.valueProperty().addListener((spin, old, fresh) -> {
				new UndoRedo<>("Cursor", prop.getComponent(), prop.getSetter(), prop.get(), fresh);
				sendNewValue(() -> property.set(fresh));
			});
			this.getChildren().add(new Label(property.getVarName() + ": "));
			this.getChildren().add(cursorbox);

			update(prop.getComponent(), null);
		}

		@Override
		public void update(Observable observing, Object arg) {
			cursorbox.setValue(property.get());
		}

	}

	public static HBox createForCursor(Property<Cursor> prop) {
		return new CursorSetter(prop);
	}

	static class BorderSetter extends HBox implements Observer {

		private final Property<Border> property;

		public BorderSetter(Property<Border> prop) {
			super();
			this.property = prop;
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);

		}

		@Override
		public void update(Observable observing, Object arg) {
		}

	}

	public static HBox createForBorder(Property<Border> prop) {
		return new BorderSetter(prop);
	}


	static class InsetsSetter extends GridPane implements SwingPropertySetter, Observer {
		private final Property<Insets> property;

		private TextField topfield;
		private TextField leftfield;
		private TextField bottomfield;
		private TextField rightfield;


		InsetsSetter(Property<Insets> prop) {
			super();
			this.property = prop;
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);

			this.setHgap(10);
			EventHandler<KeyEvent> listener = evt -> {
				if (evt.getCode() == KeyCode.ENTER) {
					sendNewValue(() -> {
						new UndoRedo<>("Insets", prop.getComponent(), prop.getSetter(), prop.get(), new Insets(this.getValue(topfield), this.getValue(leftfield),
								this.getValue(bottomfield), this.getValue(rightfield)));
						property.set(new Insets(this.getValue(topfield), this.getValue(leftfield),
								this.getValue(bottomfield), this.getValue(rightfield)));
					});
				}
			};
			int columnsize = 3;
			Label toplabel = new Label(GlobalBundle.getString("top"));
			topfield = new TextField();
			topfield.setPrefColumnCount(columnsize);
			topfield.setOnKeyPressed(listener);
			Label leftlabel = new Label(GlobalBundle.getString("left"));
			leftfield = new TextField();
			leftfield.setPrefColumnCount(columnsize);
			leftfield.setOnKeyPressed(listener);
			Label bottomlabel = new Label(GlobalBundle.getString("bottom"));
			bottomfield = new TextField();
			bottomfield.setPrefColumnCount(columnsize);
			bottomfield.setOnKeyPressed(listener);
			Label rightlabel = new Label(GlobalBundle.getString("right"));
			rightfield = new TextField();
			rightfield.setPrefColumnCount(columnsize);
			rightfield.setOnKeyPressed(listener);

			this.add(new Label(property.getVarName()), 0, 0, 2, 1);
			this.add(toplabel, 0, 1);
			this.add(topfield, 0, 2);
			this.add(leftlabel, 1, 1);
			this.add(leftfield, 1, 2);
			this.add(bottomlabel, 2, 1);
			this.add(bottomfield, 2, 2);
			this.add(rightlabel, 3, 1);
			this.add(rightfield, 3, 2);

			update(prop.getComponent(), null);

		}

		@Override
		public void update(Observable observing, Object arg) {
			Insets value = property.get();
			topfield.setText("" + value.top);
			leftfield.setText("" + value.left);
			bottomfield.setText("" + value.bottom);
			rightfield.setText("" + value.right);
		}

		private Integer getValue(TextField field) {
			return Integer.parseInt(field.getText());
		}

	}

	public static GridPane createForInsets(Property<Insets> prop) {
		return new InsetsSetter(prop);
	}

	static class CaretSetter extends GridPane implements Observer {

		private final Property<Caret> property;

		public CaretSetter(Property<Caret> prop) {
			this.property = prop;


		}

		@Override
		public void update(Observable observing, Object arg) {
		}

	}

	static class JTableHeaderSetter extends VBox implements SwingPropertySetter, Observer {

		private final Property<JTableHeader> property;
		private final CheckBox reorder;
		private final CheckBox resize;

		public JTableHeaderSetter(Property<JTableHeader> prop) {
			super();
			this.property = prop;
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);
			reorder = new CheckBox(settingFieldBundle.getString("reorder"));
			resize = new CheckBox(settingFieldBundle.getString("resize"));
			EventHandler<ActionEvent> handler = evt -> {
				JTableHeader head = new JTableHeader();
				head.setReorderingAllowed(reorder.isSelected());
				head.setResizingAllowed(resize.isSelected());
				this.sendNewValue(() -> property.set(head));
			};
			reorder.setOnAction(handler);
			resize.setOnAction(handler);
			this.getChildren().addAll(new Label(settingFieldBundle.getString("tableheader")), reorder, resize);
			update(prop.getComponent(), null);
		}

		@Override
		public void update(Observable observing, Object arg) {
			JTableHeader header = property.get();
			reorder.setSelected(header.getReorderingAllowed());
			resize.setSelected(header.getResizingAllowed());
		}

	}

	public static VBox createForJTableHeader(Property<JTableHeader> prop) {
		return new JTableHeaderSetter(prop);
	}


	static class DocumentSetter extends GridPane implements SwingPropertySetter, Observer {

		private final Property<Document> property;

		public DocumentSetter(Property<Document> prop) {
			super();
			this.property = prop;
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);

		}

		@Override
		public void update(Observable observing, Object arg) {
		}

	}


	static class EditorKitSetter extends FlowPane implements Observer {

		private final Property<EditorKit> property;

		public EditorKitSetter(Property<EditorKit> prop) {
			this.property = prop;


		}

		@Override
		public void update(Observable observing, Object arg) {
		}

	}

	static class URLSetter extends HBox implements SwingPropertySetter, Observer {

		private final Property<URL> property;
		private final TextField field;

		URLSetter(Property<URL> prop) {
			super();
			property = prop;
			//this.registerOnContentComponent(prop.getComponent());
			prop.getComponent().addObserver(this);

			field = new TextField();
			field.setOnKeyPressed(evt -> {
				if (evt.getCode() == KeyCode.ENTER) {
					this.sendNewValue(() -> {
						try {
							property.set(new URL(field.getText()));
						} catch (MalformedURLException e) {
							System.err.println("Malformed URL Error");
						}
					});
				}
			});
			this.getChildren().addAll(new Label(property.getVarName() + ":"), field);

			update(prop.getComponent(), null);
		}

		@Override
		public void update(Observable observing, Object arg) {
			field.setText(property.get().toString());
		}

	}

	public static HBox createForURL(Property<URL> prop) {
		return new URLSetter(prop);
	}

}
