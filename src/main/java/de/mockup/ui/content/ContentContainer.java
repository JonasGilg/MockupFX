package de.mockup.ui.content;

import de.mockup.system.model.ModelTypes;
import de.mockup.system.model.components.BaseComponentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Representing a <code>ContentComponent</code> that can contain other <code>ContentComponents</code>.
 * It manages relations between itself and its <code>children</code>.
 *
 * @param <T> The content that is being represented by this component.
 *           (For example: <code>JPanel</code>, <code>BorderPane</code>)
 */
public abstract class ContentContainer<T> extends ContentComponent<T> {

	private final List<ContentComponent<T>> children;

	protected ContentContainer(T content, BaseComponentModel model) {
		super(content, model);
		children = new ArrayList<>();
	}

	/**
     * @return direct children of this container
     */
    public ContentComponent[] getChildren() {
		return children.toArray(new ContentComponent[children.size()]);
	}

	/**
     * @return every <code>ContentComponent</code> that is in the subtree of this component
     */
    public ArrayList<ContentComponent<T>> getDescendants() {
        ArrayList<ContentComponent<T>> descendants = new ArrayList<>();

        descendants.addAll(children);
		children.stream().filter(c -> c instanceof ContentContainer).forEach(c ->
				descendants.addAll(((ContentContainer<T>) c).getDescendants())
		);

        return descendants;
    }

	public synchronized void addChild(ContentComponent<T> component) {
		if(component.getParent() != null) {
			component.getParent().removeChild(component);
		}
		component.setParent(this);
		children.add(component);
	}

	public synchronized void removeChild(ContentComponent<T> component) {
		children.remove(component);
		component.setParent(null);
	}

	@Override
	public String getType() {
		return ModelTypes.Container.CONTAINER;
	}
}
