package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.content.swing.properties.converter.*;
import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.gui.properties.Property;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class offers static methods to access lists of Properties for components in Swing/AWT.
 */
public class SwingProperties {

    static final ResourceBundle PROPERTY_BUNDLE = ResourceBundle.getBundle("properties.PropertyBundle");

    //don't instantiate this
    private SwingProperties() {
    }

    public static void addBasicProperties(ContentComponent<? extends JComponent> comp, ArrayList<Property<?>> result) {
        JComponent jcomp = comp.getContent();
        result.add(new Property<>("Name", comp, jcomp::setName, jcomp::getName));
        result.add(new Property<>("Tooltip Text", comp, jcomp::setToolTipText,
                jcomp::getToolTipText));
        result.add(new Property<>("Cursor", comp, jcomp::setCursor, jcomp::getCursor, new CursorPropertyConverter()));
        result.add(new Property<>("Locale", comp, jcomp::setLocale, jcomp::getLocale, new LocalePropertyConverter()));
        result.add(new Property<>("Component Orientation", comp,
                jcomp::setComponentOrientation, jcomp::getComponentOrientation, new ComponentOrientationPropertyConverter()));
        result.add(new Property<>("is Enabled", comp, jcomp::setEnabled, jcomp::isEnabled));
        result.add(new Property<>("is Focusable", comp, jcomp::setFocusable,
                jcomp::isFocusable));
        result.add(new Property<>("is Visible", comp, jcomp::setVisible, jcomp::isVisible));
        result.add(new Property<>("is FocusCycleRoot", comp, jcomp::setFocusCycleRoot,
                jcomp::isFocusCycleRoot));

        result.add(new Property<>("Location", comp, comp::setLocation, comp::getLocation, new PointPropertyConverter()));
        result.add(new Property<>("Size", comp, comp::setSize, comp::getSize, new DimensionPropertyConverter()));
        result.add(new Property<>("Preferred Size", comp, jcomp::setPreferredSize,
                jcomp::getPreferredSize, new DimensionPropertyConverter()));
        result.add(new Property<>("Minimum Size", comp, jcomp::setMinimumSize,
                jcomp::getMinimumSize, new DimensionPropertyConverter()));
        result.add(new Property<>("Maximum Size", comp, jcomp::setMaximumSize,
                jcomp::getMaximumSize, new DimensionPropertyConverter()));
        //TODO: floats are evil - result.add(new Property<>("Alignment X", jcomp, jcomp::setAlignmentX, jcomp::getAlignmentX));
        //TODO: floats are evil - result.add(new Property<>("Alignment Y", jcomp, jcomp::setAlignmentY, jcomp::getAlignmentY));

        result.add(new Property<>("Font", comp, jcomp::setFont, jcomp::getFont,
                new FontPropertyConverter()));

        result.add(new Property<>("Background Color", comp, jcomp::setBackground,
                jcomp::getBackground, new ColorPropertyConverter()));
        result.add(new Property<>("Foreground Color", comp, jcomp::setForeground,
                jcomp::getForeground, new ColorPropertyConverter()));
        //result.add(new Property<>("Border", jcomp, jcomp::setBorder, jcomp::getBorder)); TODO
        result.add(new Property<>("Doublebuffered", comp, jcomp::setDoubleBuffered,
                jcomp::isDoubleBuffered));
        result.add(new Property<>("Opaque", comp, jcomp::setOpaque, jcomp::isOpaque));
        result.add(new Property<>("Ignore Repaint", comp, jcomp::setIgnoreRepaint,
                jcomp::getIgnoreRepaint));
    }

    private static void addAbstractButtonProperties(SwingComponent<? extends AbstractButton> button, ArrayList<Property<?>> result) {

        AbstractButton jAbstract = button.getContent();

        result.add(new Property<>("Text", button, jAbstract::setText, jAbstract::getText));
        result.add(new Property<>("is border painted", button,
                jAbstract::setBorderPainted, jAbstract::isBorderPainted));
        result.add(new Property<>("is focus painted", button,
                jAbstract::setFocusPainted, jAbstract::isFocusPainted));
        result.add(new Property<>("is content area filled", button,
                jAbstract::setContentAreaFilled, jAbstract::isContentAreaFilled));
        result.add(new Property<>("is selected", button,
                jAbstract::setSelected, jAbstract::isSelected));
        result.add(new Property<>("Horizontal Alignment", button,
                jAbstract::setHorizontalAlignment, jAbstract::getHorizontalAlignment));
        result.add(new Property<>("Vertical Alignment", button,
                jAbstract::setVerticalAlignment, jAbstract::getVerticalAlignment));
        result.add(new Property<>("Horizontal TextPosition", button,
                jAbstract::setHorizontalTextPosition, jAbstract::getHorizontalTextPosition));
        result.add(new Property<>("Vertical TextPosition", button,
                jAbstract::setVerticalTextPosition, jAbstract::getVerticalTextPosition));
        result.add(new Property<>("Icon Text Gap", button,
                jAbstract::setIconTextGap, jAbstract::getIconTextGap));
        result.add(new Property<>("Icon", button, jAbstract::setIcon, jAbstract::getIcon, new ImageIconPropertyConverter()));
        result.add(new Property<>("Selected Icon", button,
                jAbstract::setSelectedIcon, jAbstract::getSelectedIcon, new ImageIconPropertyConverter()));
        result.add(new Property<>("Pressed Icon", button,
                jAbstract::setPressedIcon, jAbstract::getPressedIcon, new ImageIconPropertyConverter()));
        result.add(new Property<>("Disabled Icon", button,
                jAbstract::setDisabledIcon, jAbstract::getDisabledIcon, new ImageIconPropertyConverter()));
        result.add(new Property<>("Disabled Selected Icon", button,
                jAbstract::setDisabledSelectedIcon, jAbstract::getDisabledSelectedIcon, new ImageIconPropertyConverter()));
        result.add(new Property<>("Rollover Icon", button,
                jAbstract::setRolloverIcon, jAbstract::getRolloverIcon, new ImageIconPropertyConverter()));
        result.add(new Property<>("Rollover Selected Icon", button,
                jAbstract::setRolloverSelectedIcon, jAbstract::getRolloverSelectedIcon, new ImageIconPropertyConverter()));
        /*TODO: Longs are evil - result.add(new Property<>("MultiClickThreshhold", mButton,
				mButton::setMultiClickThreshhold, mButton::getMultiClickThreshhold));*/
        result.add(new Property<>("is rollover enabled", button,
                jAbstract::setRolloverEnabled, jAbstract::isRolloverEnabled));
        //result.add(new Property<>("Margin", mButton, mButton::setMargin, mButton::getMargin/*,new InsetsPropertyConverter()*/));

    }

    public static void addJPanelProperties(ContentContainer<JPanel> jpanel, ArrayList<Property<?>> result) {
        addBasicProperties(jpanel, result);
    }

    public static void addJButtonProperties(SwingComponent<JButton> button, ArrayList<Property<?>> result) {
        addBasicProperties(button, result);
        addAbstractButtonProperties(button, result);

        JButton jb = button.getContent();

        result.add(new Property<>("is default capable", button, jb::setDefaultCapable, jb::isDefaultCapable));
    }

    public static void addJLabelProperties(SwingComponent<JLabel> label, ArrayList<Property<?>> result) {
        addBasicProperties(label, result);

        JLabel jLabel = label.getContent();

        result.add(new Property<>("Text", label, jLabel::setText, jLabel::getText));
        result.add(new Property<>("Horizontal Alignment", label, jLabel::setHorizontalAlignment,
                jLabel::getHorizontalAlignment));
        result.add(new Property<>("Vertical Alignment", label, jLabel::setVerticalAlignment,
                jLabel::getVerticalAlignment));
        result.add(new Property<>("Horizontal TextPosition", label, jLabel::setHorizontalTextPosition,
                jLabel::getHorizontalTextPosition));
        result.add(new Property<>("Vertical TextPosition", label, jLabel::setVerticalTextPosition,
                jLabel::getVerticalTextPosition));
        result.add(new Property<>("Icon Text Gap", label, jLabel::setIconTextGap,
                jLabel::getIconTextGap));
        result.add(new Property<>("Icon", label, jLabel::setIcon, jLabel::getIcon, new ImageIconPropertyConverter()));
        result.add(new Property<>("Disabled Icon", label, jLabel::setDisabledIcon,
                jLabel::getDisabledIcon, new ImageIconPropertyConverter()));
        result.add(new Property<>("Displayed Mnemonic", label,
                jLabel::setDisplayedMnemonic, jLabel::getDisplayedMnemonic));
    }

    private static void addJTextComponentProperties(
            SwingComponent<? extends JTextComponent> textcomp, ArrayList<Property<?>> result) {

        JTextComponent jtextcomp = textcomp.getContent();

        result.add(new Property<>("Text", textcomp, jtextcomp::setText, jtextcomp::getText));
        result.add(new Property<>("Disabled Text Color", textcomp,
                jtextcomp::setDisabledTextColor, jtextcomp::getDisabledTextColor));
        result.add(new Property<>("Selected Text Color", textcomp,
                jtextcomp::setSelectedTextColor, jtextcomp::getSelectedTextColor));
        result.add(new Property<>("Selection Color", textcomp,
                jtextcomp::setSelectionColor, jtextcomp::getSelectionColor));
        result.add(new Property<>("Caret Color", textcomp,
                jtextcomp::setCaretColor, jtextcomp::getCaretColor));
        result.add(new Property<>("Margin", textcomp,
                jtextcomp::setMargin, jtextcomp::getMargin));
        result.add(new Property<>("Editable", textcomp,
                jtextcomp::setEditable, jtextcomp::isEditable));
    }

    public static void addJEditorPaneProperties(SwingComponent<JEditorPane> pane, ArrayList<Property<?>> result) {
        addBasicProperties(pane, result);
        addJTextComponentProperties(pane, result);

        JEditorPane jpane = pane.getContent();

        result.add(new Property<>(PROPERTY_BUNDLE.getString("text"), pane,
                jpane::setText, jpane::getText));
        result.add(new Property<>(PROPERTY_BUNDLE.getString("page"), pane, input -> {
            try {
                jpane.setPage(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, jpane::getPage));
    }

    private static void addJTextFieldProperties(SwingComponent<? extends JTextField> textfield, ArrayList<Property<?>> result) {
        addBasicProperties(textfield, result);
        addJTextComponentProperties(textfield, result);

        JTextField jtextfield = textfield.getContent();

        result.add(new Property<>("Columns", textfield, jtextfield::setColumns, jtextfield::getColumns));
        result.add(new Property<>("Horizontal Alignment", textfield,
                jtextfield::setHorizontalAlignment, jtextfield::getHorizontalAlignment));
        result.add(new Property<>("Scroll Offset", textfield,
                jtextfield::setScrollOffset, jtextfield::getScrollOffset));

    }

    public static void addJFormattedTextFieldProperties(SwingComponent<JFormattedTextField> txtfield, ArrayList<Property<?>> result) {
        addBasicProperties(txtfield, result);
        addJTextComponentProperties(txtfield, result);
        addJTextFieldProperties(txtfield, result);
    }

    public static void addJToggleButtonProperties(SwingComponent<? extends JToggleButton> jtoggle, ArrayList<Property<?>> result) {
        addBasicProperties(jtoggle, result);
        addAbstractButtonProperties(jtoggle, result);
    }

    public static void addJCheckBoxProperties(SwingComponent<JCheckBox> jcheck, ArrayList<Property<?>> result) {
        addJToggleButtonProperties(jcheck, result);
    }

    public static void addJRadioButtonProperties(SwingComponent<JRadioButton> jradio, ArrayList<Property<?>> result) {
        addJToggleButtonProperties(jradio, result);
    }

    public static void addJSliderProperties(SwingComponent<JSlider> slider, ArrayList<Property<?>> result) {
        addBasicProperties(slider, result);

        JSlider jslider = slider.getContent();

        result.add(new Property<>("Orientation", slider, jslider::setOrientation, jslider::getOrientation));
        result.add(new Property<>("Inverted", slider, jslider::setInverted, jslider::getInverted));
        result.add(new Property<>("Minimum", slider, jslider::setMinimum, jslider::getMinimum));
        result.add(new Property<>("Maximum", slider, jslider::setMaximum, jslider::getMaximum));
        result.add(new Property<>("Paint Ticks", slider, jslider::setPaintTicks, jslider::getPaintTicks));
        result.add(new Property<>("MinorTickSpacing", slider, jslider::setMinorTickSpacing, jslider::getMinorTickSpacing));
        result.add(new Property<>("Major Tick Spacing", slider, jslider::setMajorTickSpacing, jslider::getMajorTickSpacing));
        result.add(new Property<>("Snap to Ticks", slider, jslider::setSnapToTicks, jslider::getSnapToTicks));
        result.add(new Property<>("Extent", slider, jslider::setExtent, jslider::getExtent));
        result.add(new Property<>("Paint Track", slider, jslider::setPaintTrack, jslider::getPaintTrack));
        result.add(new Property<>("Paint Labels", slider, jslider::setPaintLabels, jslider::getPaintLabels));

    }

    public static void addJSpinnerProperties(SwingComponent<JSpinner> jspinner, ArrayList<Property<?>> result) {
        addBasicProperties(jspinner, result);
        //probably empty
    }

    public static <E> void addJComboBoxProperties(SwingComponent<JComboBox<E>> combo, ArrayList<Property<?>> result) {
        addBasicProperties(combo, result);

        JComboBox<E> jcombo = combo.getContent();

        result.add(new Property<>(PROPERTY_BUNDLE.getString("editable"), combo,
                jcombo::setEditable, jcombo::isEditable));
        result.add(new Property<>(PROPERTY_BUNDLE.getString("isEnabled"), combo,
                jcombo::setEnabled, jcombo::isEnabled));
        result.add(new Property<>(PROPERTY_BUNDLE.getString("maxrowcount"), combo,
                jcombo::setMaximumRowCount, jcombo::getMaximumRowCount));
        result.add(new Property<>(PROPERTY_BUNDLE.getString("popupvisible"), combo,
                jcombo::setPopupVisible, jcombo::isPopupVisible));
    }

    public static void addJTreeProperties(SwingComponent<JTree> tree, ArrayList<Property<?>> result) {
        addBasicProperties(tree, result);

        JTree jtree = tree.getContent();

        result.add(new Property<>("Editable", tree, jtree::setEditable, jtree::isEditable));
        result.add(new Property<>("Row Height", tree, jtree::setRowHeight, jtree::getRowHeight));
        result.add(new Property<>("Toggle Clickcount", tree, jtree::setToggleClickCount, jtree::getToggleClickCount));
        result.add(new Property<>("Root visible", tree, jtree::setRootVisible, jtree::isRootVisible));
        result.add(new Property<>("Scrolls on expand", tree, jtree::setScrollsOnExpand, jtree::getScrollsOnExpand));

    }

    public static void addJTableProperties(SwingComponent<JTable> table, ArrayList<Property<?>> result) {
        addBasicProperties(table, result);

        JTable jtable = table.getContent();

        result.add(new Property<>("Selection Background Color", table, jtable::setSelectionBackground,
                jtable::getSelectionBackground));
        result.add(new Property<>("Selection Foreground Color", table, jtable::setSelectionForeground,
                jtable::getSelectionForeground));
        result.add(new Property<>("Grid Color", table, jtable::setGridColor,
                jtable::getGridColor));
        result.add(new Property<>("Row Height", table,
                jtable::setRowHeight, jtable::getRowHeight));
        result.add(new Property<>("Row Margin", table,
                jtable::setRowMargin, jtable::getRowMargin));
        result.add(new Property<>("Intercell Spacing", table,
                jtable::setIntercellSpacing, jtable::getIntercellSpacing));
        result.add(new Property<>("Show Horizontal Lines", table,
                jtable::setShowHorizontalLines, jtable::getShowHorizontalLines));
        result.add(new Property<>("Show Vertical Lines", table,
                jtable::setShowVerticalLines, jtable::getShowVerticalLines));
        result.add(new Property<>("Row Selection Allowed", table,
                jtable::setRowSelectionAllowed, jtable::getRowSelectionAllowed));
        result.add(new Property<>("Column Selection Allowed", table,
                jtable::setColumnSelectionAllowed, jtable::getColumnSelectionAllowed));
        result.add(new Property<>("Fills Viewport Height", table,
                jtable::setFillsViewportHeight, jtable::getFillsViewportHeight));
        result.add(new Property<>("Table Header", table,
                jtable::setTableHeader, jtable::getTableHeader));

    }

}
