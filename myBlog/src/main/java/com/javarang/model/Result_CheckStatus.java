package com.javarang.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Result_CheckStatus implements Serializable{
	
	private static final long serialVersionUID = -8188569744179485273L;
	
	private String tid;
	
	private String checkStatus;

}
