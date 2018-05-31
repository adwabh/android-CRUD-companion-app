package com.parenting.attendance.domain.models;

public class LTC {

	private String proofType;

	private String fullName;

	private String imageUrl;

	private String sortOrder;

	private String id;

	private String algorithm;

	private String url;

	private String coinName;

	private String name;

	public void setProofType(String proofType){
		this.proofType = proofType;
	}

	public String getProofType(){
		return proofType;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setSortOrder(String sortOrder){
		this.sortOrder = sortOrder;
	}

	public String getSortOrder(){
		return sortOrder;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAlgorithm(String algorithm){
		this.algorithm = algorithm;
	}

	public String getAlgorithm(){
		return algorithm;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setCoinName(String coinName){
		this.coinName = coinName;
	}

	public String getCoinName(){
		return coinName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"LTC{" + 
			"proofType = '" + proofType + '\'' + 
			",fullName = '" + fullName + '\'' + 
			",imageUrl = '" + imageUrl + '\'' + 
			",sortOrder = '" + sortOrder + '\'' + 
			",id = '" + id + '\'' + 
			",algorithm = '" + algorithm + '\'' + 
			",url = '" + url + '\'' + 
			",coinName = '" + coinName + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}