package de.mockup.ui.gui.windows.dialogs;

import de.mockup.system.Bundle;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.IconModel;
import de.mockup.system.service.IconLibraryService;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

/**
 * initializes the Icongrid in the Iconlibframe
 */
public class IconLibIcongrid extends ScrollPane {

    private GridPane gridPane;

    public IconLibIcongrid() throws SystemException {
        initIcongrid();
    }

    private void initIcongrid() throws SystemException {

        gridPane = new GridPane();
        int row = 0;
        int col = 0;
        //gridPane.setPrefSize(770, 800);

        IconLibraryService iconService = Bundle.getService(IconLibraryService.class);
        List<IconModel> iconModels = iconService.getIcons();
        for (int i = 0; i < iconModels.size(); i++) {
            IconModel model = iconModels.get(i);
            System.out.println(model.getPath());
            ImageView image = new ImageView(model.getPath());
            //Text t = new Text("Test " + i);

            if (i < 4) {
                gridPane.add(image, i, 0);
            } else {
                if (i % 4 == 0) {
                    col = 0;
                    row++;
                    gridPane.add(image, col, row);
                } else {
                    col++;
                    gridPane.add(image, col, row);
                }
            }

        }

        gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(5));
        for (int i = 0; i < 4; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(25);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        System.out.println("Rows = " + row);
        row++;
        for (int i = 0; i < row; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(25);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        setContent(gridPane);
        setPrefSize(800, 800);
        setVbarPolicy(ScrollBarPolicy.ALWAYS);
        setHbarPolicy(ScrollBarPolicy.ALWAYS);
    }
}
