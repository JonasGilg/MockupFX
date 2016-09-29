package de.mockup.ui.gui.guiController;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.components.ChildModel;
import de.mockup.system.model.components.containers.ContainerModel;
import de.mockup.system.util.ModelManager;
import de.mockup.ui.GuiToContentFacade;
import de.mockup.ui.GuiToControllerFacade;
import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.gui.windows.MainFrame;

import java.awt.*;
import java.util.ArrayList;

//TODO buggy
public class UserActionController {

	public static void cut() {
		ArrayList<ContentComponent> contentComponents = copy();
		if (contentComponents != null) {
			contentComponents.forEach(GuiToContentFacade::deleteComponent);
		}
	}

	public static ArrayList<ContentComponent> copy() {
		ArrayList<ContentComponent> contentComponents;

		if (SelectionManager.isSelection()) {
			ArrayList<ChildModel> childModels = new ArrayList<>();

			contentComponents = SelectionManager.getSelectedItems();

			for (ContentComponent tmp : contentComponents) {
				ChildModel cModel = (ChildModel) tmp.getComponentModel();
				childModels.add(cModel);
			}
			GuiToControllerFacade.copy(childModels);
			MainFrame.get().setPasteButtonState(true);
			return contentComponents;
		}

		return null;
	}

	public static void paste(ContentContainer parent, Point point) {
		ArrayList<ChildModel> childModels = GuiToControllerFacade.paste((ContainerModel) parent.getComponentModel());
		for (ChildModel tmp : childModels) {
			try {
				ContentComponent child = ModelManager.get().convert(tmp);
				GuiToContentFacade.addToParent(child, parent, point);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}



		/*Object[] components = GuiToControllerFacade.paste().toArray();
		BaseComponentModel[] models = Arrays.copyOf(components, components.length, BaseComponentModel[].class);
		for (BaseComponentModel m : models) {
			GuiToContentFacade.addFromModel(parent, m);
		}
		*/
	}
}
