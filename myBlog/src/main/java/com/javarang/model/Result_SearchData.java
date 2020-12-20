package com.javarang.model;

import java.io.Serializable;
import lombok.Data;
 
@Data
public class Result_SearchData implements Serializable {


	private static final long serialVersionUID = -8048505847533946922L;

	private String tid;

	private String ciNo;

	private String channelCode;

	private String wdAccount;

	private String wdName;

	private String dpAccount;

	private String dpName;

	private int transferPrice;

	private String transferName;

	private String loginType;

	private String accessToken;
	
	private String appKey;
	
}
