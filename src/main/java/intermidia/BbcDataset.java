package intermidia;

import java.util.ArrayList;

public class BbcDataset extends Dataset{

	public BbcDataset() {
		this.nomesVideos = new ArrayList<String>(11);
		
		this.setSiglaDataset("bbc");
		this.setDiretorioVideos("/home/marcus/datasets/bbc/");
		this.setQuantiaVideos(11);
		
		for (int i = 1; i <= this.quantiaVideos; i++) {
		    this.nomesVideos.add(null);
		}

		this.nomesVideos.add(1, "From Pole to Pole");
		this.nomesVideos.add(2, "Mountains");
		this.nomesVideos.add(3, "Fresh Water");
		this.nomesVideos.add(4, "Caves");
		this.nomesVideos.add(5, "Deserts");
		this.nomesVideos.add(6, "Ice Worlds");
		this.nomesVideos.add(7, "Great Plains");
		this.nomesVideos.add(8, "Jungles");
		this.nomesVideos.add(9, "Shallow Seas");
		this.nomesVideos.add(10, "Seasonal Forests");
		this.nomesVideos.add(11, "Ocean Deep");

	}
}
