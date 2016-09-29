package de.mockup.ui.gui.windows;

import de.mockup.system.Bundle;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.TemplateModel;
import de.mockup.system.service.TemplateService;
import de.mockup.ui.gui.guiController.WindowManager;
import de.mockup.ui.gui.windows.helper.DragLabel;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.ResourceBundle;

public final class Toolbar extends TitlePane {

    private static final ResourceBundle TOOLBAR_BUNDLE = ResourceBundle.getBundle("properties.ToolbarBundle");

    public Toolbar() {
        super(TOOLBAR_BUNDLE.getString("toolbar"));
        initBar();
        addTools();
    }

    /**
     * Convenience method to get the Instance of this window
     *
     * @return instance of this window
     */
    public static synchronized Toolbar get() {
        return WindowManager.get().getWindow(Toolbar.class);
    }

    private VBox containerBox;
    private VBox controlBox;
    private VBox templateBox;

    private void initBar() {
        Accordion bar = new Accordion();

        containerBox = new VBox();
        System.out.println(TOOLBAR_BUNDLE);
        TitledPane container = new TitledPane(TOOLBAR_BUNDLE.getString("container"), containerBox);
        bar.getPanes().add(0, container);

        controlBox = new VBox();
        TitledPane control = new TitledPane(TOOLBAR_BUNDLE.getString("control"), controlBox);
        bar.getPanes().add(1, control);

        VBox menuBox = new VBox();
        TitledPane menu = new TitledPane(TOOLBAR_BUNDLE.getString("menu"), menuBox);
        bar.getPanes().add(2, menu);

        VBox miscBox = new VBox();
        TitledPane misc = new TitledPane(TOOLBAR_BUNDLE.getString("miscellaneous"), miscBox);
        bar.getPanes().add(3, misc);

        VBox viewBox = new VBox();
        TitledPane views = new TitledPane(TOOLBAR_BUNDLE.getString("views"), viewBox);
        bar.getPanes().add(4, views);

        templateBox = new VBox();
        bar.getPanes().add(5, new TitledPane(TOOLBAR_BUNDLE.getString("templates"), templateBox));

        //Scene scene = new Scene(bar);
        //setScene(scene);
        setContent(bar);
        bar.getStylesheets().add("stylesheets/toolbar.css");

        container.getStyleClass().add("TitlePane");
        control.getStyleClass().add("TitlePane");
        menu.getStyleClass().add("TitlePane");
        misc.getStyleClass().add("TitlePane");
        views.getStyleClass().add("TitlePane");

        refreshTemplates();
    }

    /**
     * List of tools to be added on the toolbar
     * type(2nd parameter) is for evaluating what kind of component the workspace needs to generate
     */
    private void addTools() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.ComponentBundle");
        containerBox.getChildren().add(toolsConfig(resourceBundle.getString("new.panel"), ModelTypes.Container.CONTAINER));
        containerBox.getChildren().add(toolsConfig(resourceBundle.getString("new.scrollpane"), ModelTypes.Container.SCROLL_PANE));

        controlBox.getChildren().add(toolsConfig(resourceBundle.getString("new.button"), ModelTypes.Button.BUTTON));
        controlBox.getChildren().add(toolsConfig(resourceBundle.getString("new.label"), ModelTypes.LABEL));
        controlBox.getChildren().add(toolsConfig(resourceBundle.getString("new.textfield"), ModelTypes.Fields.TEXT_FIELD));
        controlBox.getChildren().add(toolsConfig(resourceBundle.getString("new.slider"), ModelTypes.Fields.SLIDER));
        controlBox.getChildren().add(toolsConfig(resourceBundle.getString("new.spinner"), ModelTypes.Fields.SPINNER));
        controlBox.getChildren().add(toolsConfig(resourceBundle.getString("new.radio"), ModelTypes.Fields.RADIO));
        controlBox.getChildren().add(toolsConfig(resourceBundle.getString("new.checkbox"), ModelTypes.Fields.CHECKBOX));
        controlBox.getChildren().add(toolsConfig(resourceBundle.getString("new.tree"), ModelTypes.TREE));
        controlBox.getChildren().add(toolsConfig(resourceBundle.getString("new.table"), ModelTypes.TABLE));

    }

    public void refreshTemplates() {
        try {
            TemplateService templateService = Bundle.getService(TemplateService.class);
            List<TemplateModel> templates = templateService.getTemplates();

            if (templateBox.getChildren().size() > 0) {
                templateBox.getChildren().clear();
            }

            for (TemplateModel template : templates) {
                templateBox.getChildren().add(toolsConfig(template.getName(), ModelTypes.TEMPLATE_PREFIX + template.getId()));
            }

        } catch (SystemException e) {
            e.printStackTrace();
        }

    }

    private DragLabel toolsConfig(String title, String type) {
        // Configuration of new Tools
        return new DragLabel(title, type);
    }

}
