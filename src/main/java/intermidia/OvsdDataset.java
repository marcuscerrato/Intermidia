package intermidia;

import java.util.ArrayList;

public class OvsdDataset extends Dataset{

	public OvsdDataset() {
		this.nomesVideos = new ArrayList<String>(21);
		
		this.setSiglaDataset("ovsd");
		this.setDiretorioVideos("/home/marcus/datasets/ovsd/");
		this.setQuantiaVideos(21);
		
		for (int i = 1; i <= this.quantiaVideos; i++) {
		    this.nomesVideos.add(null);
		}
		this.nomesVideos.add(1, "Big Buck Bunny");
		this.nomesVideos.add(2, "Cosmos Laundromat - First Cycle");
		this.nomesVideos.add(3, "Elephants Dream");
		this.nomesVideos.add(4, "Sintel");
		this.nomesVideos.add(5, "Tears of Steel");
		this.nomesVideos.add(6, "Valkaama");
		this.nomesVideos.add(7, "1000 Days");
		this.nomesVideos.add(8, "Boy Who Never Slept");
		this.nomesVideos.add(9, "CH7");
		this.nomesVideos.add(10, "Fires Beneath Water");
		this.nomesVideos.add(11, "Honey");
		this.nomesVideos.add(12, "Jathia's Wager");
		this.nomesVideos.add(13, "La Chute d’une Plume");
		this.nomesVideos.add(14, "Lord Meia");
		this.nomesVideos.add(15, "Meridian");
		this.nomesVideos.add(16, "Oceania");
		this.nomesVideos.add(17, "Pentagon");
		this.nomesVideos.add(18, "Route 66");
		this.nomesVideos.add(19, "Seven Dead Men");
		this.nomesVideos.add(20, "Sita Sings the Blues");
		this.nomesVideos.add(21, "Star Wreck");
	}
}
