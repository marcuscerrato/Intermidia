package intermidia;

import java.util.ArrayList;

public class OvsdDataset extends Dataset{

	public OvsdDataset() {
		this.videosNames = new ArrayList<String>(21);
		
		this.setDatabaseInitials("ovsd");
		this.setVideosFolder("/home/marcus/datasets/ovsd/");
		this.setVideosQuantities(21);
		
		for (int i = 1; i <= this.videosQuantities; i++) {
		    this.videosNames.add(null);
		}
		this.videosNames.add(1, "Big Buck Bunny");
		this.videosNames.add(2, "Cosmos Laundromat - First Cycle");
		this.videosNames.add(3, "Elephants Dream");
		this.videosNames.add(4, "Sintel");
		this.videosNames.add(5, "Tears of Steel");
		this.videosNames.add(6, "Valkaama");
		this.videosNames.add(7, "1000 Days");
		this.videosNames.add(8, "Boy Who Never Slept");
		this.videosNames.add(9, "CH7");
		this.videosNames.add(10, "Fires Beneath Water");
		this.videosNames.add(11, "Honey");
		this.videosNames.add(12, "Jathia's Wager");
		this.videosNames.add(13, "La Chute d’une Plume");
		this.videosNames.add(14, "Lord Meia");
		this.videosNames.add(15, "Meridian");
		this.videosNames.add(16, "Oceania");
		this.videosNames.add(17, "Pentagon");
		this.videosNames.add(18, "Route 66");
		this.videosNames.add(19, "Seven Dead Men");
		this.videosNames.add(20, "Sita Sings the Blues");
		this.videosNames.add(21, "Star Wreck");
	}
}
