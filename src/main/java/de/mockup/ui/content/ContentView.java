package de.mockup.ui.content;

import de.mockup.system.model.components.BaseComponentModel;
import de.mockup.system.model.components.containers.ViewModel;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * A top level container, that cannot have a parent and is representing a view on the <code>WorkingSurface</code>.
 *
 * @param <T> The content that is being represented by this component.
 *           (For example: <code>JPanel</code>, <code>BorderPane</code>)
 */
public abstract class ContentView<T> extends ContentContainer<T> implements Observer {

	private String title;
    private String description;

	protected ContentView(T content, String title, BaseComponentModel model) {
		super(content, model);
		this.title = title;
        this.description = "your description here";
    }

	/**
     * Views cannot have parents.
     * @param newParent ignored
     */
    @Override
    public final void setParent(ContentContainer newParent) { /*Views cannot have parents!*/ }

	/**
	 * @return the name of the View
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the new name of the View
	 */
	public void setTitle(String title) {
		this.title = title;
	}

    public String getDescriptionText(){
        return this.description;
    }

    public void setDescriptionText(String text){
        this.description = text;
    }

    public Date getLastModifyDate(){
        return this.getComponentModel().getModifiedOn();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof ContentComponent) {
            ((ViewModel) getComponentModel()).setDirty(true);
        }
    }
}
