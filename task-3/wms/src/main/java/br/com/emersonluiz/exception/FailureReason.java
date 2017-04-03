package br.com.emersonluiz.exception;

import java.io.Serializable;

public class FailureReason implements Serializable {
	
	private static final long serialVersionUID = 1046888159080053678L;
	
	private String message;
	
	public FailureReason(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
