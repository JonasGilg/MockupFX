package de.mockup.ui.content;

public enum ContentType {
	SWING("swing"),
	JAVAFX("javafx"),
	BROWSER("browser");

	private final String type;

	ContentType(final String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
