package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.ContentComponent;
import de.mockup.ui.content.ContentContainer;
import de.mockup.ui.content.swing.swingObjects.SwingComponent;
import de.mockup.ui.content.swing.swingObjects.components.*;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;
import de.mockup.ui.gui.windows.PropertyWindow;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.ArrayList;

/**
 * creates different sets of PropertyPanels for specific Swing/AWT components
 */
public class SwingPropertyPanelFactory {

	private SwingPropertyPanelFactory() {
	}


	/**
	 * creates with the help of the passed Component fitting PropertyPanels which will be then displayed
	 * on the {@link PropertyWindow}.
	 *
	 * @param jcomp Component which will passed to the underlying methods as a reference, so it can be
	 *              manipulated via PropertyPanels
	 * @return a list of PropertyPanels that fits specifically to the Component
	 */
	public static ArrayList<AbstractPropertyPanel> createJComponentPropertyPanel(final ContentComponent<? extends JComponent> comp) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.add(new BasicPropertyPanel(comp));
		result.add(new DimensionalSpacePropertyPanel(comp));
		result.add(new FontPropertyPanel(comp));
		result.add(new RenderingPropertyPanel(comp));

		
		/* von Component
		void	setBackground(Color c)
		void 	setBounds(Rectangle r)
		void 	setComponentOrientation(ComponentOrientation o)
		void 	setCursor(Cursor cursor)
		void 	setEnabled(boolean b)
		void 	setFocusable(boolean focusable)
		void 	setFont(Font f)
		void 	setForeground(Color c)
		void 	setIgnoreRepaint(boolean ignoreRepaint)
		void 	setLocale(Locale l)
		void 	setLocation(int x, int y)
		void 	setMaximumSize(Dimension maximumSize)
		void 	setMinimumSize(Dimension minimumSize)
		void 	setName(String name)
		void 	setPreferredSize(Dimension preferredSize)
		void 	setSize(Dimension d)
		void 	setVisible(boolean b)*/
		
	 	/* von Container
	 	void 	setFocusCycleRoot(boolean focusCycleRoot)
	 	void 	setLayout(LayoutManager mgr)= wird noch entschieden
	 	*/
		
		/*von Jcomponent 
		void 	setAlignmentX(float alignmentX)
		void 	setAlignmentY(float alignmentY)
		void 	setBorder(Border border) TODO
		void 	setComponentPopupMenu(JPopupMenu popup)//optional
		void 	setDebugGraphicsOptions(int debugOptions)
		void 	setDoubleBuffered(boolean aFlag)
		void 	setEnabled(boolean enabled)
		void 	setInheritsPopupMenu(boolean value)
		void 	setNextFocusableComponent(Component aComponent)//optional
		void 	setOpaque(boolean isOpaque)
		void 	setToolTipText(String text)*/
		return result;
	}

	private static ArrayList<AbstractPropertyPanel> createAbstractButtonPropertyPanel(final SwingComponent<? extends AbstractButton> button) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.add(new AbstractButtonPropertyPanel(button));
		/*
		void 	setBorderPainted(boolean b)
		void 	setContentAreaFilled(boolean b)
		void 	setDisabledIcon(Icon disabledIcon)
		void 	setDisabledSelectedIcon(Icon disabledSelectedIcon)
		void 	setFocusPainted(boolean b)
		void 	setHorizontalAlignment(int alignment)
		void 	setHorizontalTextPosition(int textPosition)
		void 	setIcon(Icon defaultIcon)
		void 	setIconTextGap(int iconTextGap)
		void 	setMargin(Insets m)
		void 	setMnemonic(int mnemonic)
		void 	setMultiClickThreshhold(long threshhold)
		void 	setPressedIcon(Icon pressedIcon)
		void 	setRolloverEnabled(boolean b)
		void 	setRolloverIcon(Icon rolloverIcon)
		void 	setRolloverSelectedIcon(Icon rolloverSelectedIcon)
		void 	setSelected(boolean b)
		void 	setSelectedIcon(Icon selectedIcon)
		void 	setText(String text)
		void 	setVerticalAlignment(int alignment)
		void 	setVerticalTextPosition(int textPosition)*/
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJToggleButtonPropertyPanel(SwingToggleButton toggle) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(toggle));
		result.addAll(createAbstractButtonPropertyPanel(toggle));
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJButtonPropertyPanel(SwingButton button) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(button));
		result.addAll(createAbstractButtonPropertyPanel(button));
		result.add(new JButtonPropertyPanel(button));
		
		/*void 	setDefaultCapable(boolean defaultCapable)*/
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJLabelPropertyPanel(SwingLabel label) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(label));
		result.add(new JLabelPropertyPanel(label));
		
		/*void 	setDisabledIcon(Icon disabledIcon)
		void 	setDisplayedMnemonic(int key)
		void 	setHorizontalAlignment(int alignment)
		void 	setHorizontalTextPosition(int textPosition)
		void 	setIcon(Icon icon)
		void 	setIconTextGap(int iconTextGap)
		void 	setText(String text)
		void 	setVerticalAlignment(int alignment)
		void 	setVerticalTextPosition(int textPosition)*/
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJPanelPropertyPanel(ContentContainer<? extends JPanel> panel) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(panel));
		/* Jpanel und der LayoutSetter werden noch genauer untersucht, bitte noch nicht bearbeiten*/
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJTreePropertyPanel(SwingTree tree) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(tree));
		result.add(new JTreePropertyPanel(tree));
		
		/*
		void 	setEditable(boolean flag)
		void 	setRootVisible(boolean rootVisible)
		void 	setRowHeight(int rowHeight)
		void 	setScrollsOnExpand(boolean newValue)
		void 	setToggleClickCount(int clickCount)
		*/
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJTablePropertyPanel(SwingTable table) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(table));
		result.add(new JTablePropertyPanel(table));

		
		
		/*
		void 	setColumnSelectionAllowed(boolean columnSelectionAllowed)
		void 	setFillsViewportHeight(boolean fillsViewportHeight)
		void 	setGridColor(Color gridColor)
		void 	setIntercellSpacing(Dimension intercellSpacing)
		void 	setPreferredScrollableViewportSize(Dimension size)
		void 	setRowHeight(int rowHeight)
		void 	setRowMargin(int rowMargin)
		void 	setRowSelectionAllowed(boolean rowSelectionAllowed)
		void 	setSelectionBackground(Color selectionBackground)
		void 	setSelectionForeground(Color selectionForeground)
		void 	setShowHorizontalLines(boolean showHorizontalLines)
		void 	setShowVerticalLines(boolean showVerticalLines)
		void 	setTableHeader(JTableHeader tableHeader)
		*/
		return result;
	}

	private static ArrayList<AbstractPropertyPanel> createJTextComponentPropertyPanel(SwingComponent<? extends JTextComponent> textcomp) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.add(new JTextComponentPropertyPanel(textcomp));
		
		/*void 	setCaret(Caret c)//optional
		void 	setCaretColor(Color c)//optional
		void 	setCaretPosition(int position)//optional
		void 	setDisabledTextColor(Color c)
		void 	setEditable(boolean b)
		void 	setHighlighter(Highlighter h)//optional
		void 	setMargin(Insets m)
		void 	setSelectedTextColor(Color c)
		void 	setSelectionColor(Color c)
		void 	setText(String t)
		*/
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJTextFieldPropertyPanel(SwingComponent<? extends JTextField> txtfield) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(txtfield));
		result.addAll(createJTextComponentPropertyPanel(txtfield));
		result.add(new JTextFieldPropertyPanel(txtfield));
		
		/*
		void 	setColumns(int columns)
		void 	setHorizontalAlignment(int alignment)
		void 	setScrollOffset(int scrollOffset)*/
		return result;
	}


	/*TODO*/
	public static ArrayList<AbstractPropertyPanel> createJPasswordFieldPropertyPanel(SwingPasswordField passfield) {
		return new ArrayList<>();
	}

	public static ArrayList<AbstractPropertyPanel> createJCheckBoxPropertyPanel(SwingCheckBox cbox) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(cbox));
		result.addAll(createAbstractButtonPropertyPanel(cbox));
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJRadioButtonPropertyPanel(SwingRadioButton radio) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(radio));
		result.addAll(createAbstractButtonPropertyPanel(radio));
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJSliderPropertyPanel(SwingSlider jslider) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(jslider));
		result.add(new JSliderPropertyPanel(jslider));
		
		/*
		void 	setExtent(int extent)
		void 	setInverted(boolean b)
		void 	setMajorTickSpacing(int n)
		void 	setMaximum(int maximum)
		void 	setMinimum(int minimum)
		void 	setMinorTickSpacing(int n)
		void 	setOrientation(int orientation)
		void 	setPaintLabels(boolean b)
		void 	setPaintTicks(boolean b)
		void 	setPaintTrack(boolean b)
		void 	setSnapToTicks(boolean b)
		*/
		return result;
	}


	/*TODO*/
	public static ArrayList<AbstractPropertyPanel> createJSpinnerPropertyPanel(SwingSpinner spinner) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(spinner));

		/*
		void 	setModel(SpinnerModel model) //vorerst nur SpinnerNumberModel
		*/
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJComboBoxPropertyPanel(SwingComboBox combo) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(combo));
		result.add(new JComboBoxPropertyPanel(combo));
		
		/*
		void 	setEditable(boolean aFlag)
		void 	setEnabled(boolean b)
		void 	setMaximumRowCount(int count)
		void 	setPopupVisible(boolean v)
		*/
		return result;
	}

	public static ArrayList<AbstractPropertyPanel> createJEditorPanePropertyPanel(SwingEditorPane pane) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(pane));
		result.addAll(createJTextComponentPropertyPanel(pane));
		result.add(new JEditorPanePropertyPanel(pane));
		
		/*
		void 	setContentType(String type)//optional
		void 	setEditorKit(EditorKit kit)//optional
		void 	setEditorKitForContentType(String type, EditorKit k)//optional
		void 	setPage(String url)
		void 	setText(String t)
		*/
		return result;
	}

	/*TODO*/
	public static ArrayList<AbstractPropertyPanel> createJFormattedTextFieldPropertyPanel(SwingFormattedTextField formatfield) {
		ArrayList<AbstractPropertyPanel> result = new ArrayList<>();
		result.addAll(createJComponentPropertyPanel(formatfield));
		result.addAll(createJTextComponentPropertyPanel(formatfield));
		result.addAll(createJTextFieldPropertyPanel(formatfield));
		result.add(new JFormattedTextFieldPropertyPanel(formatfield));
		
		/*
		void 	setDocument(Document doc)
		void 	setFocusLostBehavior(int behavior)
		void 	setFormatterFactory(JFormattedTextField.AbstractFormatterFactory tf)//optional
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJListPropertyPanel(JList<?> list) {
		/*
		void 	setDragEnabled(boolean b)
		void 	setFixedCellHeight(int height)
		void 	setFixedCellWidth(int width)
		void 	setLayoutOrientation(int layoutOrientation)
		void 	setListData(Vector<? extends E> listData)//beispiel mit primitiven datentypen
		void 	setModel(ListModel<E> model)//optional
		void 	setSelectionBackground(Color selectionBackground)
		void 	setSelectionForeground(Color selectionForeground)
		void 	setSelectionMode(int selectionMode)
		void 	setSelectionModel(ListSelectionModel selectionModel)
		void 	setVisibleRowCount(int visibleRowCount)*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJProgressbarPropertyPanel(JProgressBar progress) {
		/* 
		void	setBorderPainted(boolean b)
		void 	setIndeterminate(boolean newValue)
		void 	setMaximum(int n)
		void 	setMinimum(int n)
		void 	setModel(BoundedRangeModel newModel)//optional
		void 	setOrientation(int newOrientation)
		void 	setString(String s)
		void 	setStringPainted(boolean b)
		void 	setValue(int n)
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJScrollBarPropertyPanel(JScrollBar scroll) {
		/*
		void 	setBlockIncrement(int blockIncrement)
		void 	setMaximum(int maximum)
		void 	setMinimum(int minimum)
		void 	setModel(BoundedRangeModel newModel)//optional
		void 	setOrientation(int orientation)
		void 	setUnitIncrement(int unitIncrement)
		void 	setValueIsAdjusting(boolean b)
		void 	setValues(int newValue, int newExtent, int newMin, int newMax)//optional
		void 	setVisibleAmount(int extent)
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJSeparatorPropertyPanel(JSeparator sep) {
		/*
		void 	setOrientation(int orientation)
		 */
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJTextAreaPropertyPanel(JTextArea textarea) {
		/*
		void 	setColumns(int columns)
		void 	setLineWrap(boolean wrap)
		void 	setRows(int rows)
		void 	setTabSize(int size)
		void 	setWrapStyleWord(boolean word)
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJTextPanePropertyPanel(JTextPane pane) {
		/* 
		void 	setEditorKit(EditorKit kit)
		void 	setStyledDocument(StyledDocument doc)//optional
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createBoxPropertyPanel(Box pane) {
		/* Kein LayoutSetter!, erfordert genauere Nachforschung*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJScrollPanePropertyPanel(JScrollPane pane) {
		/* festes layout!
		void 	setComponentOrientation(ComponentOrientation co)
		void 	setHorizontalScrollBarPolicy(int policy)
		void 	setVerticalScrollBarPolicy(int policy)
		void 	setViewportBorder(Border viewportBorder)
		void 	setWheelScrollingEnabled(boolean handleWheel)
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJLayeredPanePropertyPanel(JLayeredPane pane) {
		/* Tree von Komponenten erstellen und mit moveToBack(Component c) und moveToFront(Component c) manipulieren*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJSplitPanePropertyPanel(JSplitPane pane) {
		/* layout ist noch debatierbar
		void 	setContinuousLayout(boolean newContinuousLayout)
		void 	setDividerLocation(double proportionalLocation)
		void 	setDividerSize(int newSize)
		void 	setOneTouchExpandable(boolean newValue)
		void 	setOrientation(int orientation)
		void 	setResizeWeight(double value)
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJTabbedPanePropertyPanel(JTabbedPane pane) {
		/*
		void 	setBackgroundAt(int index, Color background)
		void 	setComponentAt(int index, Component component)
		void 	setDisabledIconAt(int index, Icon disabledIcon)
		void 	setDisplayedMnemonicIndexAt(int tabIndex, int mnemonicIndex)
		void 	setEnabledAt(int index, boolean enabled)
		void 	setForegroundAt(int index, Color foreground)
		void 	setIconAt(int index, Icon icon)
		void 	setMnemonicAt(int tabIndex, int mnemonic)
		void 	setTabLayoutPolicy(int tabLayoutPolicy)
		void 	setTabPlacement(int tabPlacement)
		void 	setTitleAt(int index, String title)
		void 	setToolTipTextAt(int index, String toolTipText)
		Tabmanagement muss irgenwie implementiert werden
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJToolBarPropertyPanel(JToolBar bar) {
		/*
		void 	setBorderPainted(boolean b)
		void 	setFloatable(boolean b)//optional
		void 	setMargin(Insets m)
		void 	setOrientation(int o)
		void 	setRollover(boolean rollover)
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJToolTipPropertyPanel(JToolTip tool) {
		/*
		void 	setContent(JComponent c)//optional
		void 	setTipText(String tipText)*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJCheckBoxMenuItemPropertyPanel(JCheckBoxMenuItem item) {
		/* void 	setState(boolean b)*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJMenuPropertyPanel(JMenu menu) {
		/*
		void 	setComponentOrientation(ComponentOrientation o)
		void 	setDelay(int d)
		void 	setPopupMenuVisible(boolean b)
		void 	setSelected(boolean b)
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJRadioButtonMenuItemPropertyPanel(JRadioButtonMenuItem radio) {
		/* nichts*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJMenuBarPropertyPanel(JMenuBar bar) {
		/*
		void 	setBorderPainted(boolean b)
		void 	setMargin(Insets m)
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJMenuItemPropertyPanel(JMenuItem item) {
		/* 
		void 	setEnabled(boolean b)
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJPopupMenuPropertyPanel(JPopupMenu menu) {
		/*
		void 	setBorderPainted(boolean b)
		void 	setLabel(String label)
		void 	setVisible(boolean b)
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJColorChooserPropertyPanel(JColorChooser chooser) {
		/*
		void 	setChooserPanels(AbstractColorChooserPanel[] panels)//mittels liste von auswaehlbaren Panels
		*/
		return null;
	}

	/*TODO*/
	public static AbstractPropertyPanel createJFileChooserPropertyPanel(JFileChooser chooser) {
		/*
		void 	setAcceptAllFileFilterUsed(boolean b)
		void 	setApproveButtonMnemonic(int mnemonic)//optional
		void 	setApproveButtonText(String approveButtonText)
		void 	setApproveButtonToolTipText(String toolTipText)
		void 	setControlButtonsAreShown(boolean b)
		void 	setDialogTitle(String dialogTitle)
		void 	setDialogType(int dialogType)
		void 	setFileFilter(FileFilter filter)//optional
		void 	setFileHidingEnabled(boolean b)
		void 	setFileSelectionMode(int mode)
		void 	setFileView(FileView fileView)//optional
		void 	setMultiSelectionEnabled(boolean b)
		*/
		return null;
	}

	/*TODO komplett optional*/
	public static AbstractPropertyPanel createJOptionPanePropertyPanel(JOptionPane pane) {
		/*
		void 	setIcon(Icon newIcon)
		void 	setInitialSelectionValue(Object newValue)
		void 	setInitialValue(Object newInitialValue)
		void 	setInputValue(Object newValue)
		void 	setMessage(Object newMessage)
		void 	setMessageType(int newType)
		void 	setOptions(Object[] newOptions)
		void 	setOptionType(int newType)*/
		return null;
	}

}