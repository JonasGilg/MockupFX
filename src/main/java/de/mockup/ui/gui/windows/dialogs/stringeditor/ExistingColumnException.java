package de.mockup.ui.gui.windows.dialogs.stringeditor;

import java.util.Locale;
import java.util.ResourceBundle;



public class ExistingColumnException extends Exception{
	private static final long serialVersionUID = 1L;

	ExistingColumnException(Locale loc){
		super(ResourceBundle.getBundle("properties.StringEditorBundle").getString("exceptionMsg") + loc.getDisplayName());
	}
}
