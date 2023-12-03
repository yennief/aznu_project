package org.bp.types;

public class Fault extends Throwable {

	protected String text;

	public Fault(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
