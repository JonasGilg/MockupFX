package de.mockup.system.binding;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.ui.content.ContentComponent;

/**
 * Abstract class to create ContentComponents from Models
 */
public abstract class ContentFactory<T extends ContentComponent, S extends BaseComponentModel> {

	public abstract T createComponent(S model);

	public abstract void writeToModel(T component, S model) throws SystemException;

	public abstract void readFromModel(S model, T component) throws SystemException;

}
