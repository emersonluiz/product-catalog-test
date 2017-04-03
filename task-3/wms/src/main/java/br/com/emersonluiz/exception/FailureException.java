package br.com.emersonluiz.exception;

import java.io.Serializable;

public class FailureException extends Exception implements Serializable {
	
	private static final long serialVersionUID = -5732323160892986147L;

	public FailureException(String message) {
		super(message);
	}

}

