package de.mockup.ui.content.swing.properties;

import de.mockup.ui.content.swing.swingObjects.container.SwingContainer;
import de.mockup.ui.gui.properties.AbstractPropertyPanel;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import javax.swing.plaf.synth.SynthScrollBarUI;
import java.awt.*;
import java.util.ArrayList;

/**
 * PropertyPanel displaying layout properties of a component. This Panel can change the layout, insets and
 * all other parameters associated with layouts for a Component
 */
class LayoutPropertyPanel extends AbstractPropertyPanel {

	/**
	 * Constructor that builds the LayoutPropertyPanel with "Layout" in the TitlePane
	 *
	 * @param comp passed Component that will be used as a reference to manipulate it with the help of
	 *              the GUI elements on this PropertyPanel
	 */
	//TODO bitte nicht bearbeiten!!!
	public LayoutPropertyPanel(final SwingContainer<JPanel> comp) {
		super("Layout");

		JPanel jcomp = comp.getContent();

		//TODO only Container should be able to change their layout.
		LayoutManager layout = jcomp.getLayout();
		//TODO
		if (layout instanceof BorderLayout) {
			/*void 	setHgap(int hgap)
			void 	setVgap(int vgap)*/
		}
		if (layout instanceof BoxLayout) {
			/*BoxLayout(Container target, int axis)
			keine setMethode fuer die Achse*/
		}
		if (layout instanceof CardLayout) {
			/*void 	setHgap(int hgap)
			void 	setVgap(int vgap)*/
		}
		if (layout instanceof FlowLayout) {
			/*void 	setAlignment(int align)
			void 	setAlignOnBaseline(boolean alignOnBaseline)
			void 	setHgap(int hgap)
			void 	setVgap(int vgap)*/
		}
		if (layout instanceof GridBagLayout) {
			/*void 	setConstraints(Component comp, GridBagConstraints constraints)
			GridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight, double weightx,
					double weighty, int anchor, int fill, Insets insets, int ipadx, int ipady)
			keine set oder get Methoden vorhanden!*/
		}
		if (layout instanceof GridLayout) {
			/*void 	setColumns(int cols)
			void 	setHgap(int hgap)
			void 	setRows(int rows)
			void 	setVgap(int vgap)*/
		}
		if (layout instanceof GroupLayout) {
			/*void 	setAutoCreateContainerGaps(boolean autoCreateContainerPadding)
			void 	setAutoCreateGaps(boolean autoCreatePadding)
			void 	setHonorsVisibility(boolean honorsVisibility)
			void 	setHonorsVisibility(Component component, Boolean honorsVisibility)
			void 	setHorizontalGroup(GroupLayout.Group group)
			void 	setLayoutStyle(LayoutStyle layoutStyle)
			void 	setVerticalGroup(GroupLayout.Group group)*/
		}
		if (layout instanceof OverlayLayout) {
			/*nichts*/
		}
		if (layout instanceof SpringLayout) {
			/* komplex*/
		}
	}

	/* all known LayoutManager classes as a ArrayList*/
	private static ArrayList<Class<?>> getLayoutList() {
		ArrayList<Class<?>> layoutlist = new ArrayList<>();
		layoutlist.add(BasicComboBoxUI.ComboBoxLayoutManager.class);
		layoutlist.add(BasicInternalFrameTitlePane.TitlePaneLayout.class);
		layoutlist.add(BasicInternalFrameUI.InternalFrameLayout.class);
		layoutlist.add(BasicOptionPaneUI.ButtonAreaLayout.class);
		layoutlist.add(BasicScrollBarUI.class);
		layoutlist.add(BasicSplitPaneUI.BasicHorizontalLayoutManager.class);
		layoutlist.add(BasicSplitPaneUI.BasicVerticalLayoutManager.class);
		layoutlist.add(BasicTabbedPaneUI.TabbedPaneLayout.class);
		layoutlist.add(BorderLayout.class);
		layoutlist.add(BoxLayout.class);
		layoutlist.add(DefaultMenuLayout.class);
		layoutlist.add(CardLayout.class);
		layoutlist.add(DefaultMenuLayout.class);
		layoutlist.add(FlowLayout.class);
		layoutlist.add(GridBagLayout.class);
		layoutlist.add(GridLayout.class);
		layoutlist.add(GroupLayout.class);
		layoutlist.add(JSpinner.DateEditor.class);
		layoutlist.add(JSpinner.DefaultEditor.class);
		layoutlist.add(JSpinner.ListEditor.class);
		layoutlist.add(JSpinner.NumberEditor.class);
		layoutlist.add(MetalComboBoxUI.MetalComboBoxLayoutManager.class);
		layoutlist.add(MetalScrollBarUI.class);
		layoutlist.add(MetalTabbedPaneUI.TabbedPaneLayout.class);
		layoutlist.add(OverlayLayout.class);
		layoutlist.add(ScrollPaneLayout.class);
		layoutlist.add(ScrollPaneLayout.UIResource.class);
		layoutlist.add(SpringLayout.class);
		layoutlist.add(SynthScrollBarUI.class);
		layoutlist.add(ViewportLayout.class);
		return layoutlist;
	}

}