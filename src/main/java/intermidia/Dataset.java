package intermidia;

import java.util.ArrayList;


public class Dataset {

	private String videosFolder = null;
	private String scenesFolder = null;
	private String audiosFolder = null;
	private ArrayList<String> videosNames = null;
	private ArrayList<String> videosFilenames = null;
	private int videosQuantities = 0;
	private String audioChannels = null;
	private String chosenAudioChannel = null;
	private String audioNameSufix = "";

	protected String getVideoFullName(int index) {
		return this.getVideosFolder()+this.getVideosFilenames().get(index)+".mp4";
	}
	
	protected String getShotFullName(int index) {
		if (this.getClass() == TesteDataset.class) {
			return this.getScenesFolder()+this.getVideosFilenames().get(index)+"_shots.txt";
		} else {
			return this.getScenesFolder()+this.getVideosFilenames().get(index)+"_shots.csv";
		}
		
	}
	
	protected String getAudioFullName(int index) {
		String sufix = (this.getAudioNameSufix().equals("")) ? "-audio" : "-audio-"+this.getAudioNameSufix(); 
		return this.getAudiosFolder()+this.getVideosFilenames().get(index)+sufix+".mp4";
	}

	protected String getVideosFolder() {
		return videosFolder;
	}

	protected void setVideosFolder(String videosFolder) {
		this.videosFolder = videosFolder;
	}

	protected String getScenesFolder() {
		return scenesFolder;
	}

	protected void setScenesFolder(String scenesFolder) {
		this.scenesFolder = scenesFolder;
	}

	protected ArrayList<String> getVideosNames() {
		return videosNames;
	}

	protected void setVideosNames(ArrayList<String> videosNames) {
		this.videosNames = videosNames;
	}

	protected int getVideosQuantities() {
		return videosQuantities;
	}

	protected void setVideosQuantities(int videosQuantities) {
		this.videosQuantities = videosQuantities;
	}

	public ArrayList<String> getVideosFilenames() {
		return videosFilenames;
	}

	public void setVideosFilenames(ArrayList<String> videosFilenames) {
		this.videosFilenames = videosFilenames;
	}

	public String getAudioChannels() {
		return audioChannels;
	}

	public void setAudioChannels(String audioChannels) {
		this.audioChannels = audioChannels;
	}

	public String getChosenAudioChannel() {
		return chosenAudioChannel;
	}

	public void setChosenAudioChannel(String chosenAudioChannel) {
		this.chosenAudioChannel = chosenAudioChannel;
	}

	public String getAudiosFolder() {
		return audiosFolder;
	}

	public void setAudiosFolder(String audiosFolder) {
		this.audiosFolder = audiosFolder;
	}

	public String getAudioNameSufix() {
		return audioNameSufix;
	}

	public void setAudioNameSufix(String audioNameSufix) {
		this.audioNameSufix = audioNameSufix;
	}


}
