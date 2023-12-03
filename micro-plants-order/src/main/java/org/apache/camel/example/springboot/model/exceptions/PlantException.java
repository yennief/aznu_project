package org.apache.camel.example.springboot.model.exceptions;


public class PlantException extends Exception {

	public PlantException() {
	}

	public PlantException(String message) {
		super(message);
	}

	public PlantException(Throwable cause) {
		super(cause);
	}

	public PlantException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
