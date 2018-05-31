package com.parenting.attendance.data.entity;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CryptoListEntity{

	@SerializedName("Response")
	private String response;

	@SerializedName("BaseLinkUrl")
	private String baseLinkUrl;

	@SerializedName("Type")
	private int type;

	@SerializedName("Message")
	private String message;

	@SerializedName("BaseImageUrl")
	private String baseImageUrl;

	@SerializedName("Data")
	private Data data;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setBaseLinkUrl(String baseLinkUrl){
		this.baseLinkUrl = baseLinkUrl;
	}

	public String getBaseLinkUrl(){
		return baseLinkUrl;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setBaseImageUrl(String baseImageUrl){
		this.baseImageUrl = baseImageUrl;
	}

	public String getBaseImageUrl(){
		return baseImageUrl;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"CryptoListEntity{" + 
			"response = '" + response + '\'' + 
			",baseLinkUrl = '" + baseLinkUrl + '\'' + 
			",type = '" + type + '\'' + 
			",message = '" + message + '\'' + 
			",baseImageUrl = '" + baseImageUrl + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}