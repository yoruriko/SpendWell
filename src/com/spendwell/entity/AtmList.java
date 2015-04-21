package com.spendwell.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Store all ATM items in to a list
 * @author Yifei Gao
 *
 */
public class AtmList {

	private List<AtmItem> list;

	public AtmList() {
		list = new ArrayList<AtmItem>();
		AtmItem GreyStreet = new AtmItem("Grey Street ATM", "Mon-Sun: 24 Hours",
				54.96772, -1.61579);
		AtmItem ShieldsStreet = new AtmItem("Shields Street ATM",
				"Mon-Sun: 24 Hours", 54.97707, -1.58112);
		AtmItem AkensideHouse = new AtmItem("Akenside House Hill ATM",
				"Mon-Sun: 24 Hours", 54.97063, -1.59959);
		AtmItem BentonRoad = new AtmItem("Benton Road ATM", "Mon-Sun: 24 Hours",
				55.00757, -1.57847);
		AtmItem AcornRoad = new AtmItem("Acorn Road ATM", "Mon-Sun: 24 Hours",
				54.99276, -1.60362);
		AtmItem KentonLane = new AtmItem("Asda, Kenton Lane,ATM",
				"Mon - Sun: Subject to location", 54.99276, -1.60362);
		AtmItem EldonSquare = new AtmItem("Eldon Square ATM",
				"Mon - Sun: Subject to location", 54.976156, -1.74192);
		AtmItem FenWicks = new AtmItem("Fenwick Newcastle ATM",
				"Mon - Sun: Subject to location", 54.975333, -1.612949);
		AtmItem HayMarket = new AtmItem("Haymarket Branch ATM",
				"Mon-Sun: 24 Hours", 54.97752, -1.613890);
		list.add(GreyStreet);
		list.add(ShieldsStreet);
		list.add(AkensideHouse);
		list.add(BentonRoad);
		list.add(AcornRoad);
		list.add(KentonLane);
		list.add(EldonSquare);
		list.add(FenWicks);
		list.add(HayMarket);
	}

	public List<AtmItem> getList() {
		return list;
	}
}
