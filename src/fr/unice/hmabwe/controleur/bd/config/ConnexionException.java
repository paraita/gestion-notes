package fr.unice.hmabwe.controleur.bd.config;

import javax.xml.parsers.ParserConfigurationException;

public class ConnexionException extends Exception {

	public ConnexionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnexionException(String message) {
		super(message);
	}

}
