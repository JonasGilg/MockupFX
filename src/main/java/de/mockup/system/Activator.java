package de.mockup.system;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.SliderModel;
import de.mockup.system.model.components.SpinnerModel;
import de.mockup.system.model.components.TableModel;
import de.mockup.system.model.components.TreeModel;
import de.mockup.system.model.components.buttons.ButtonModel;
import de.mockup.system.model.components.buttons.CheckboxModel;
import de.mockup.system.model.components.buttons.RadioButtonModel;
import de.mockup.system.model.components.containers.ContainerModel;
import de.mockup.system.model.components.containers.ViewModel;
import de.mockup.system.model.components.textfields.LabelModel;
import de.mockup.system.model.components.textfields.TextFieldModel;
import de.mockup.system.service.AppService;
import de.mockup.system.service.IconLibraryService;
import de.mockup.system.service.ProjectService;
import de.mockup.system.service.TemplateService;
import de.mockup.system.service.impl.AppServiceImpl;
import de.mockup.system.service.impl.IconLibraryServiceImpl;
import de.mockup.system.service.impl.ProjectServiceImpl;
import de.mockup.system.service.impl.TemplateServiceImpl;
import de.mockup.system.service.internal.FileSystemService;
import de.mockup.system.service.internal.impl.FileSystemServiceImpl;
import de.mockup.system.util.ModelManager;
import de.mockup.system.util.ModelManagerImpl;
import de.mockup.system.util.ModelUtil;
import de.mockup.ui.content.swing.swingObjects.components.SwingComboBox;

public class Activator {

	public void start() {

		ModelManager modelManager = new ModelManagerImpl();

		//Registering System Services
		SystemBundle.registerService(FileSystemService.class, new FileSystemServiceImpl());
		SystemBundle.registerService(ModelManager.class, modelManager);
		SystemBundle.registerService(ModelUtil.class, new ModelUtil());

		//Registering Services for external use
		Bundle.registerService(ModelManager.class, modelManager);
        Bundle.registerService(IconLibraryService.class, new IconLibraryServiceImpl());
		Bundle.registerService(ProjectService.class, new ProjectServiceImpl());
		Bundle.registerService(TemplateService.class, new TemplateServiceImpl());
		Bundle.registerService(AppService.class, new AppServiceImpl());

		//Registering Models
		modelManager.registerModel(ModelTypes.Container.CONTAINER, ContainerModel.class);
		modelManager.registerModel(ModelTypes.Container.SCROLL_PANE, ContainerModel.class);

		modelManager.registerModel(ModelTypes.Container.VIEW, ViewModel.class);

		modelManager.registerModel(ModelTypes.Button.BUTTON, ButtonModel.class);

		modelManager.registerModel(ModelTypes.TABLE, TableModel.class);
		modelManager.registerModel(ModelTypes.TREE, TreeModel.class);
		modelManager.registerModel(ModelTypes.LABEL, LabelModel.class);

		modelManager.registerModel(ModelTypes.Fields.SLIDER, SliderModel.class);
		modelManager.registerModel(ModelTypes.Fields.SPINNER, SpinnerModel.class);
		modelManager.registerModel(ModelTypes.Fields.CHECKBOX, CheckboxModel.class);
		modelManager.registerModel(ModelTypes.Fields.RADIO, RadioButtonModel.class);
		modelManager.registerModel(ModelTypes.Fields.COMBO_BOX, SwingComboBox.class);
		modelManager.registerModel(ModelTypes.Fields.TEXT_FIELD, TextFieldModel.class);

	}

}
