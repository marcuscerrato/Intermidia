package intermidia;

import java.util.ArrayList;

public class TesteDataset extends Dataset{

	public TesteDataset() {
		int videosQuantity = 1;
		ArrayList<String> videosNames = new ArrayList<String>(videosQuantity);
		ArrayList<String> videosFilenames = new ArrayList<String>(videosQuantity);
		
		this.setVideosFolder("/home/marcus/Doutorado/intermidia/results/bbc_teste/testes/");
		this.setScenesFolder("/home/marcus/Doutorado/intermidia/results/bbc_teste/testes/");
		this.setVideosQuantities(videosQuantity);
		
		videosNames.add(0, "Teste");
		this.setVideosNames(videosNames);

		videosFilenames.add(0, "teste");
		this.setVideosFilenames(videosFilenames);
		
		this.setAudioChannels("1");
		this.setChosenAudioChannel("1");
	}

}
