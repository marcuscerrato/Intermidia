package intermidia;

import java.util.ArrayList;

public class BbcDataset extends Dataset{

	public BbcDataset() {
		int videosQuantity = 11;
		ArrayList<String> videosNames = new ArrayList<String>(videosQuantity);
		ArrayList<String> videosFilenames = new ArrayList<String>(videosQuantity);
		
		this.setVideosFolder("/home/marcus/Doutorado/intermidia/datasets/bbc/");
		this.setScenesFolder("/home/marcus/Doutorado/intermidia/datasets/bbc_groundtruth/");
		this.setAudiosFolder("/home/marcus/Doutorado/intermidia/datasets/bbc_audios_only/");

		this.setVideosQuantities(videosQuantity);
		this.setAudioNameSufix("ita");
		
		videosNames.add(0, "From Pole to Pole / da polo a polo");
		videosNames.add(1, "Mountains / montagne");
		videosNames.add(2, "Ice Worlds / mondi di ghiaccio");
		videosNames.add(3, "Great Plains / savane e praterie");
		videosNames.add(4, "Jungles / giungle");
		videosNames.add(5, "Seasonal Forests / foreste");
		videosNames.add(6, "Fresh Water / acque dolci");
		videosNames.add(7, "Ocean Deep / abissi");
		videosNames.add(8, "Shallow Seas / coste e barriere coralline");
		videosNames.add(9, "Caves / grotte e caverne");
		videosNames.add(10, "Deserts / deserti");
		this.setVideosNames(videosNames);

		videosFilenames.add(0, "bbc01");
		videosFilenames.add(1, "bbc02");
		videosFilenames.add(2, "bbc03");
		videosFilenames.add(3, "bbc04");
		videosFilenames.add(4, "bbc05");
		videosFilenames.add(5, "bbc06");
		videosFilenames.add(6, "bbc07");
		videosFilenames.add(7, "bbc08");
		videosFilenames.add(8, "bbc09");
		videosFilenames.add(9, "bbc10");
		videosFilenames.add(10, "bbc11");
		this.setVideosFilenames(videosFilenames);

		this.setAudioChannels("2");
		this.setChosenAudioChannel("1");
	}
}
