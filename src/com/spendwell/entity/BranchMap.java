package com.spendwell.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Store and Branches details in a map
 * 
 * @author Yifei Gao
 * 
 */
public class BranchMap {
	private Map<String, BranchItem> map;

	public BranchMap() {
		map = new HashMap<String, BranchItem>();
		BranchItem Benton = new BranchItem(55.00757, -1.57847, "Benton",
				"331 Benton RoadBenton,Newcastle Upon Tyne,Tyne And Wear",
				"NE7 7EE", new String[] { "09:30 - 16:30", "09:30 - 16:30",
						"10:00 - 16:30", "09:30 - 16:30", "09:30 - 16:30",
						"09:30 - 13:30", "Closed" }, "77-20-19",
				"0845 3000 000", "01245 704705");
		BranchItem Chopwell = new BranchItem(54.91933, -1.81523, "Chopwell",
				"21 Derwent St,Newcastle Upon Tyne,Tyne And Wear", "NE17 7HU",
				new String[] { "09:30 - 15:30", "Closed", "Closed",
						"09:30 - 15:30", "09:30 - 15:30", "Closed", "Closed" },
				"30-19-54", "0845 3000 000", "0845 0701190");
		BranchItem Gosforth = new BranchItem(
				55.00538,
				-1.61941,
				"Gosforth",
				"32 The Gosforth Centre,Gosforth,Gosforth,Newcastle Upon Tyne,Tyne And Wear",
				"NE3 1JZ", new String[] { "09:00 - 17:00", "09:00 - 17:00",
						"09:30 - 17:00", "09:00 - 19:00", "09:00 - 17:00",
						"09:00 - 13:00", "Closed" }, "30-93-55, 77-20-25",
				"0845 3000 000", "0845 0701190");
		BranchItem GreyStreet = new BranchItem(54.96772, -1.61579,
				"Grey Street",
				"102 Grey Street,Newcastle Upon Tyne,Tyne And Wear",
				"NE99 1SL", new String[] { "09:00 - 17:00", "09:00 - 17:00",
						"09:30 - 17:00", "09:00 - 19:00", "09:00 - 17:00",
						"09:00 - 16:00", "Closed" },
				"30-93-71, 77-20-01, 77-20-30", "0845 3000 000", "0845 0701190");
		BranchItem Haymarket = new BranchItem(54.97279, -1.60247, "Haymarket",
				"Haymarket Branch,Haymarket,Newcastle Upon Tyne,Tyne And Wear",
				"NE1 7QD", new String[] { "09:00 - 17:00", "09:00 - 17:00",
						"09:30 - 17:00", "09:00 - 19:00", "09:00 - 17:00",
						"09:00 - 16:00", "Closed" }, "77-20-26",
				"0845 3000 000", "01245 704705");
		BranchItem Heaton = new BranchItem(54.97707, -1.58112, "Heaton",
				"171 Shields Road,Heaton,Newcastle Upon Tyne,Tyne And Wear",
				"NE6 1HN", new String[] { "09:00 - 17:00", "09:00 - 17:00",
						"09:30 - 17:00", "09:00 - 19:00", "09:00 - 17:00",
						"09:00 - 13:00", "Closed" }, "30-93-71",
				"0845 3000 000", "01245 704705");
		BranchItem Newburn = new BranchItem(54.98242, -1.74332, "Newburn",
				"Station Road,Newburn,Newcastle Upon Tyne,Tyne And Wear",
				"NE15 8LS", new String[] { "09:30 - 16:30", "09:30 - 16:30",
						"10:00 - 16:30", "09:30 - 16:30", "09:30 - 16:30",
						"Closed", "Closed" }, "30-19-54", "0845 3000 000",
				"0845 0701190");
		BranchItem OsborneRoad = new BranchItem(54.99276, -1.60362,
				"Osborne Road", "35 Acorn Road,Jesmond,Newcastle-upon-tyne",
				"NE2 2DY", new String[] { "09:30 - 16:30", "09:30 - 16:30",
						"10:00 - 16:30", "09:30 - 16:30", "09:30 - 16:30",
						"Closed", "Closed" }, "30-93-55", "0845 3000 000",
				"0845 0701190");
		BranchItem Ponteland = new BranchItem(55.04973, -1.74192, "Ponteland",
				"5 Main Street,Ponteland,Newcastle Upon Tyne,Tyne And Wear",
				"NE20 9NJ", new String[] { "09:30 - 16:30", "09:30 - 16:30",
						"10:00 - 16:30", "09:30 - 16:30", "09:30 - 16:30",
						"Closed", "Closed" }, "30-93-55", "0845 3000 000",
				"0845 0701190");
		BranchItem Whickham = new BranchItem(54.94611, -1.67647, "Whickham",
				"44 Front Street,Whickham,Newcastle Upon Tyne,Tyne And Wear",
				"NE16 4DS", new String[] { "09:00 - 17:00", "09:00 - 17:00",
						"09:30 - 17:00", "09:00 - 19:00", "09:00 - 17:00",
						"09:00 - 13:00", "Closed" }, "30-19-54, 30-84-82",
				"0845 3000 000", "0845 0701190");
		map.put("Benton", Benton);
		map.put("Chopwell", Chopwell);
		map.put("Gosforth", Gosforth);
		map.put("Grey Street", GreyStreet);
		map.put("Haymarket", Haymarket);
		map.put("Heaton", Heaton);
		map.put("Newburn", Newburn);
		map.put("Osborne Road", OsborneRoad);
		map.put("Ponteland", Ponteland);
		map.put("Whickham", Whickham);

	}

	public Map<String, BranchItem> getBranchMap() {
		return map;
	}
}
