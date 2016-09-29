package de.mockup.system.model.components;

import de.mockup.system.model.components.containers.ContainerModel;

/**
 * Model for all not view components.
 */
public abstract class ChildModel extends BaseComponentModel {

	private ContainerModel parent;

	public ContainerModel getParent() {
		return parent;
	}

	public void setParent(ContainerModel parent) {
		this.parent = parent;
	}
}
