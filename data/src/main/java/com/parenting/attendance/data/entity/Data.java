package com.parenting.attendance.data.entity;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("LTC")
	private LTC lTC;

	public void setLTC(LTC lTC){
		this.lTC = lTC;
	}

	public LTC getLTC(){
		return lTC;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"lTC = '" + lTC + '\'' + 
			"}";
		}
}