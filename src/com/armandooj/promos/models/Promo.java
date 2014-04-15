package com.armandooj.promos.models;

public class Promo {
	
	public String objectId;	
	public String title;
	public String description;
	public String price;
	public String imageUrl;	
	public int uses;
	// public boolean isFavorite;

	public Promo() {};

	public Promo(String objectId, String title, String desc, String price, String imageUrl, int uses) {
		this.objectId = objectId;
		this.title = title;
		this.description = desc;
		this.price = price;		
		this.imageUrl = imageUrl;
		this.uses = uses;
	};

}
