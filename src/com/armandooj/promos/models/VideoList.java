package com.armandooj.promos.models;

import java.util.ArrayList;

public class VideoList {

	public static ArrayList<Promo> getVideoList() {
		ArrayList<Promo> resultList = new ArrayList<Promo>();

		Promo itm;

		itm = new Promo(
				1,
				89,
				"Brave",
				"brave.jpg",
				"Determined to make her own path in life, Princess Merida defies a custom that brings chaos to her kingdom. Granted one wish, Merida must rely on her bravery and her archery skills to undo a beastly curse.",
				true);
		resultList.add(itm);

		itm = new Promo(
				2,
				81,
				"Ice Age",
				"ice_age.jpg",
				"Manny, Diego, and Sid embark upon another adventure after their continent is set adrift. Using an iceberg as a ship, they encounter sea creatures and battle pirates as they explore a new world.",
				false);
		resultList.add(itm);

		itm = new Promo(
				3,
				86,
				"The Incredibles",
				"incredibles.jpg",
				"A family of undercover superheroes, while trying to live the quiet suburban life, are forced into action to save the world.",
				true);
		resultList.add(itm);

		itm = new Promo(
				4,
				85,
				"Finding Nemo",
				"nemo.jpg",
				"After his son is captured in the Great Barrier Reef and taken to Sydney, a timid clownfish sets out on a journey to bring him home.",
				false);
		resultList.add(itm);

		itm = new Promo(
				5,
				87,
				"UP",
				"up.jpg",
				"By tying thousands of balloons to his home, 78-year-old Carl sets out to fulfill his lifelong dream to see the wilds of South America. Russell, a wilderness explorer 70 years younger, inadvertently becomes a stowaway.",
				false);
		resultList.add(itm);

		itm = new Promo(
				6,
				89,
				"Brave",
				"brave.jpg",
				"Determined to make her own path in life, Princess Merida defies a custom that brings chaos to her kingdom. Granted one wish, Merida must rely on her bravery and her archery skills to undo a beastly curse.",
				false);
		resultList.add(itm);

		itm = new Promo(
				7,
				81,
				"Ice Age",
				"ice_age.jpg",
				"Manny, Diego, and Sid embark upon another adventure after their continent is set adrift. Using an iceberg as a ship, they encounter sea creatures and battle pirates as they explore a new world.",
				false);
		resultList.add(itm);

		itm = new Promo(
				8,
				86,
				"The Incredibles",
				"incredibles.jpg",
				"A family of undercover superheroes, while trying to live the quiet suburban life, are forced into action to save the world.",
				false);
		resultList.add(itm);

		itm = new Promo(
				9,
				85,
				"Finding Nemo",
				"nemo.jpg",
				"After his son is captured in the Great Barrier Reef and taken to Sydney, a timid clownfish sets out on a journey to bring him home.",
				false);
		resultList.add(itm);

		itm = new Promo(
				10,
				87,
				"UP",
				"up.jpg",
				"By tying thousands of balloons to his home, 78-year-old Carl sets out to fulfill his lifelong dream to see the wilds of South America. Russell, a wilderness explorer 70 years younger, inadvertently becomes a stowaway.",
				false);
		resultList.add(itm);

		return resultList;
	};

}
