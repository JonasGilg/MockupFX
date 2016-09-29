package de.mockup.ui.gui.properties;

import de.mockup.system.binding.PropertyConverter;
import de.mockup.ui.content.ContentComponent;

import java.util.function.Consumer;
import java.util.function.Supplier;

//TODO properties are so broadly used in the project, they probably should not be part of the GUI layer!

/**
 * This class bundles attributes and functions to manipulate and store Properties of a Component
 *
 * @param <T> type of the Property
 */
public class Property<T> {

    //name of the modifiable variable of the component
    private String varName;

    /* the component which is associated with this Property*/
    private ContentComponent<?> component;

    /*instance that holds the set method for the variable of a component in the WorkingSurface.*/
    private Consumer<T> setter;

    /*instance that holds the get method for the variable of a component in the WorkingSurface.*/
    private Supplier<T> getter;

    /**
     * Converts Objects like Fonts to storage formats.
     */
    private final PropertyConverter<T> storageConverter;

    /**
     * standard constructor for the Property
     *
     * @param name   name of the Property
     * @param comp   the component whom the Property belongs to
     * @param setter a functional interface representing the set method for the given Property
     * @param getter a functional interface representing the get method for the given Property
     */
    public Property(String name, ContentComponent<?> comp, Consumer<T> setter, Supplier<T> getter) {
        this(name, comp, setter, getter, new DefaultPropertyConverter<>());
    }

    /**
     * special constructor for Properties whose generic type is not one of the primitive types or String
     *
     * @param name      name of the Property
     * @param comp      the component whom the Property belongs to
     * @param setter    a functional interface representing the set method for the given Property
     * @param getter    a functional interface representing the get method for the given Property
     * @param converter converter that is used to save and load the Property to JSON
     */
    public Property(String name, ContentComponent<?> comp, Consumer<T> setter, Supplier<T> getter,
                    PropertyConverter<T> converter) {
        setVarName(name);
        setComponent(comp);
        setSetter(setter);
        setGetter(getter);
        this.storageConverter = converter;
    }

    public String getVarName() {
        return varName;
    }

    private void setVarName(String varName) {
        this.varName = varName;
    }

    public ContentComponent getComponent() {
        return component;
    }

    private void setComponent(ContentComponent component) {
        this.component = component;
    }

    public Consumer<T> getSetter() {
        return setter;
    }

    private void setSetter(Consumer<T> setter) {
        this.setter = setter;
    }

    public Supplier<T> getGetter() {
        return getter;
    }

    private void setGetter(Supplier<T> getter) {
        this.getter = getter;
    }

    /**
     * Convenience method to directly use Supplier.get()
     *
     * @return result of getter method
     */
    public T get() {
        return getter.get();
    }

    /**
     * Convenience method to directly use Consumer.accept(T t)
     *
     * @param data parameter for setter method
     */
    public void set(T data) {
        set(data, false);
    }

    public void set(T data, boolean silent) {
        setter.accept(data);
        if (!silent) {
            component.notifyObservers();
        }
    }

    /**
     * Method to read command value. Default implementation uses the original storage value.
     *
     * @param value
     */
    public void fromConfig(Object value) {
        T object = storageConverter.fromConfig(value);
        this.set(object, true);
    }

    /**
     * Method to write the command value. Default implementation returns the value itself.
     *
     * @return
     */
    public Object toConfig() {
        return storageConverter.toConfig(this.get());
    }
}
