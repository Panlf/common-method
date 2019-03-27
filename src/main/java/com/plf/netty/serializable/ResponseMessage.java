package com.plf.netty.serializable;

import java.io.Serializable;

public class ResponseMessage implements Serializable{


	private static final long serialVersionUID = 5226182879715311421L;
	
	
	private Long id;
	private String response;
	
	public ResponseMessage() {
		super();
	}
	
	
	public ResponseMessage(Long id, String response) {
		super();
		this.id = id;
		this.response = response;
	}
	
	@Override
	public String toString() {
		return "ResponseMessage [id=" + id + ", response=" + response + "]";
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
	
}
