package com.mutant.api.util;


import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter 
@EqualsAndHashCode
public class Response {

	private int status;
	private String message;
	private String messageDeveloper;
	private Object data;
	
	public Response(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public Response(int status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Response(int status, String message, String messageDeveloper, Object data) {
		this.status = status;
		this.message = message;
		this.messageDeveloper = messageDeveloper;
		this.data = data;
	}
	
	

}
