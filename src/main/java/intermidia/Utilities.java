package intermidia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.video.xuggle.XuggleVideo;

public class Utilities {
	
	public static String getVideoInfo(Dataset ds, int index) {
		File inputFile = new File(ds.getVideoFullName(index));
		XuggleVideo inputVideo = new XuggleVideo(inputFile);
		int videoFPS = (int) Math.round(inputVideo.getFPS());
		long framesQuantity = inputVideo.countFrames();
		long videoDuration = inputVideo.getDuration();
		String videoFormatedDuration = formatSeconds(((float) videoDuration));
		int frameHeight = inputVideo.getHeight();
		int frameWidth = inputVideo.getWidth();
		inputVideo.close();
		
    	XuggleAudio inputAudioMFCCRaw = new XuggleAudio(inputFile);    	 
   	   	long audioLength = inputAudioMFCCRaw.getLength();
   		int channelsQuantity = inputAudioMFCCRaw.getFormat().getNumChannels();
   		double rate = inputAudioMFCCRaw.getFormat().getSampleRateKHz();
   		int bitsQuantity = inputAudioMFCCRaw.getFormat().getNBits();
   		int indexToShow = index + 1;
		
		return (ds.getVideosNames().get(index) + "(" + indexToShow + ") [" + ds.getVideosFilenames().get(index) + "] " + "\n" + 
				" \t FPS " + videoFPS +  
				" \t Frames " + framesQuantity + " \t Duração " + videoDuration + " [ " + videoFormatedDuration + "] " + 
				" \t Tamanho " + frameHeight + " x " + frameWidth + " \n " + 
				" \t Canais " + channelsQuantity +
				" \t Comprimento " + audioLength + " \t Taxa " + rate + " \t Bits " + bitsQuantity);
	}

	public static String getDatasetInfo(Dataset ds) {
		StringBuilder info = new StringBuilder();
		int videosQuantity = ds.getVideosQuantities();
		for (int index = 0; index < videosQuantity; index++) {
			info.append(getVideoInfo(ds, index)+"\n");
		}
		return(info.toString());
	}

	private static String formatSeconds(float secondsToFormat) {
		int seconds = (int) secondsToFormat;
		int second = seconds % 60;
		int minutes = seconds / 60;
		int minute = minutes % 60;
		int hour = minutes / 60;
		return (String.format("%02d:%02d:%02d", hour, minute, second));
	}

	public static void showFileContent(String fileDirectory, String fileName, String fileType) {
		Path path = Paths.get(fileDirectory + fileName);
		List<String> lines;
		boolean secondsConvertion = (fileType.equals("seconds")) ? true : false;
		try {
			lines = Files.readAllLines(path);
			for (String line : lines) {
				if (secondsConvertion) {
					float secondsChange = Float.parseFloat(line);
					line = formatSeconds(secondsChange);
				}
				System.out.printf("%s | ", line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
	}

	protected void showVideoShots(String fileType, int index) {
		String videoFileName = getVideoFileName(fileType, index);
		System.out.printf("[[" + index + ") " + this.getVideoName(index) + "]] => ");
		this.showFileContent(this.getVideosFolder(), videoFileName, fileType);
	}

	protected void showVideoShots(String fileType) {
		int videosQuantity = this.getVideosQuantities();
		System.out.println("Dataset " + this.getDatabaseInitials() + " in Shots (" + fileType + ") ");
		for (int countVideo = 1; countVideo < videosQuantity; countVideo++) {
			this.showVideoShots(fileType, countVideo);
		}
	}

	private String getVideoFileName(String fileType, int index) {
		if (fileType.equals("seconds")) {
			return (this.getPrefixFileName(index) + "shots.txt");
		} else if (fileType.equals("frames")) {
			return (this.getPrefixFileName(index) + "shots.csv");
		} else if (fileType.equals("frameScenes")) {
			return (this.getPrefixFileName(index) + "scenes.txt");
		} else if (fileType.equals("shotScenes")) {
			return (this.getPrefixFileName(index) + "scenes.csv");
		}
		return null;
	}

	public String getPrefixFileName(int index) {
		String videoIndex = String.format("%02d", index);
		return this.getDatabaseInitials() + videoIndex;
	}

	public void fromSecondsToShots() {
		int videosQuantity = this.getVideosQuantities();
		for (int index = 1; index <= videosQuantity; index++) {
			String videoFileName = this.getPrefixFileName(index) + "." + this.getVideoInitials(index);
			File inputFile = new File(this.getVideosFolder() + videoFileName);
			XuggleVideo inputVideo = new XuggleVideo(inputFile);
			int videoFPS = (int) Math.round(inputVideo.getFPS());
			//long ultimoFrame = inputVideo.countFrames() - (long)1;
			
			try {
				FileWriter writer = new FileWriter(this.getVideosFolder() + this.getPrefixFileName(index) + 
						"shots.csv");

				// read shot changing in seconds
				String shotFile = this.getVideosFolder() + this.getPrefixFileName(index) + "shots.txt";
				Path path = Paths.get(shotFile);
				List<String> lines = Files.readAllLines(path);
				int oldFrame = 0;
				for (String line : lines) {
					float secondsChange = Float.parseFloat(line);
					float changingFrame = secondsChange * videoFPS;
					int changingFrameInt = Math.round(changingFrame);
					// write down shot changing frame
					writer.append("" + oldFrame + "\t" + changingFrameInt + "\n");
					oldFrame = changingFrameInt + 1;
				}
				//writer.append("" + oldFrame + "\t" + changingFrame + "\n");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputVideo.close();
		}
	}

	public void fromFrameScenesToShotsScenes() {
		int videosQuantity = this.getVideosQuantities();
		for (int index = 1; index <= videosQuantity; index++) {
			if (index != 6) {
			// arquivos de cenas
			Path pathFrameScenes = Paths.get(this.getScenesFolder() + this.getDatabaseInitials() +
					"_dataset/" + this.getVideoFileName("frameScenes", index));
			System.out.println(pathFrameScenes.toString());
			String shotScenesFilename = this.getScenesFolder() + this.getDatabaseInitials() +
					"_dataset/" + this.getVideoFileName("shotScenes", index);
			System.out.println(shotScenesFilename);
			List<String> frameScenesFile;
			//List<String> shotScenesFile;
			String[] frameScene;

			// shots file
			Path pathShots = Paths.get(this.getVideosFolder() + this.getVideoFileName("frames", index));
			System.out.println(pathShots.toString());
			List<String> shotsFile;
			String[] shots;
			int initialShotCounter = 0;
			
			try {
				frameScenesFile = Files.readAllLines(pathFrameScenes); // where I read
				shotsFile = Files.readAllLines(pathShots); // where I see where it is
				int totalShotsFile = shotsFile.size();
				FileWriter writer = new FileWriter(shotScenesFilename); // where I write
				for (String frameScenes : frameScenesFile) {
					frameScene = frameScenes.split("\t",2);
					int initialFrame = Integer.parseInt(frameScene[0]);
					int finalFrame = Integer.parseInt(frameScene[1]);
					
					int initialShot = 0;
					int finalShot = 0;
					for (int shotCounter = initialShotCounter; shotCounter < totalShotsFile; shotCounter++) {
						shots = shotsFile.get(shotCounter).split("\t",2);
						if ( (Integer.parseInt(shots[0]) <= initialFrame) && (Integer.parseInt(shots[1]) >= initialFrame) ) {
							initialShot = shotCounter;
						} 
						if ( (Integer.parseInt(shots[0]) <= finalFrame) && (Integer.parseInt(shots[1]) >= finalFrame) ) {
							finalShot = shotCounter;
						}
						if (initialShot != 0 && finalShot != 0) { break; }
					}
					//writer.append("" + initialShot + "\t" + finalShot + "\n");
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			System.out.println();
		}
	}

	public void fromSecondsToHMS() {
		int videosQuantity = this.getVideosQuantities();
		for (int index = 1; index < videosQuantity; index++) {
			String videoFileName = this.getPrefixFileName(index) + "." + this.getVideoInitials(index);
			File inputFile = new File(this.getVideosFolder() + videoFileName);
			XuggleVideo inputVideo = new XuggleVideo(inputFile);
			try {
				FileWriter writer = new FileWriter(this.getVideosFolder() + this.getPrefixFileName(index) + "shots_hms.txt");

				// read shot changing in seconds
				String shotOVSDFile = this.getVideosFolder() + this.getPrefixFileName(index) + "shots.txt";
				Path path = Paths.get(shotOVSDFile);
				List<String> lines = Files.readAllLines(path);
				for (String line : lines) {
					float seconds = Float.parseFloat(line);
					writer.append("" + formatSeconds(seconds) + "\n");
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputVideo.close();
		}
	}

	public void fromShotsToSeconds() {
		int videosQuantity = this.getVideosQuantities();
		float totalSeconds = (float) 0.00;
		for (int index = 1; index <= videosQuantity; index++) {
			String videoFileName = this.getPrefixFileName(index) + "." + this.getVideoInitials(index);
			File inputFile = new File(this.getVideosFolder() + videoFileName);
			XuggleVideo inputVideo = new XuggleVideo(inputFile);
			int videoFPS = (int) Math.round(inputVideo.getFPS());

			try {
				FileWriter writer = new FileWriter(
						this.getVideosFolder() + this.getPrefixFileName(index) + "shots.txt");

				// read shot changing in seconds
				String shotOVSDFile = this.getVideosFolder() + this.getPrefixFileName(index) + "shots.csv";
				Path path = Paths.get(shotOVSDFile);
				List<String> lines = Files.readAllLines(path);
				for (String line : lines) {
					String[] frames = line.split("\t");
					int deltaFrame = Integer.parseInt(frames[1]) - Integer.parseInt(frames[0]);
					float changingSeconds = deltaFrame / videoFPS;
					totalSeconds += changingSeconds;
					writer.append("" + totalSeconds + "\n");
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputVideo.close();
		}
	}
	
	public void showScenesInSecondsPython() {
		int videosQuantity = this.getVideosQuantities();
		for (int countVideo = 1; countVideo <= videosQuantity; countVideo++) {
			String videoIndex = String.format("%02d", countVideo);
			String videoFileName = this.getPrefixFileName(countVideo) + "." + this.getVideoInitials(countVideo);
			File inputFile = new File(this.getVideosFolder() + videoFileName);
			XuggleVideo inputVideo = new XuggleVideo(inputFile);
			int videoFPS = (int) Math.round(inputVideo.getFPS());
			inputVideo.close();
			System.out.printf("python scenes_in_seconds_0.py %s %s %d \n", this.getDatabaseInitials(), videoIndex, videoFPS);
		}
		
	}
	
	public void showScenesInSeconds(int index) {

		System.out.println("Shots in Seconds - Video "+this.getDatabaseInitials()+ " " +index);
		Path pathShots = Paths.get(this.getVideosFolder() + this.getVideoFileName("frames", index));
		Path pathScenes = Paths.get(this.getScenesFolder() + this.getDatabaseInitials() +
				"_dataset/" + this.getVideoFileName("shotScenes", index));

		List<String> shotsFile;
		List<String> scenesFile;
		String[] shots;
		try {
			shotsFile = Files.readAllLines(pathShots);
			scenesFile = Files.readAllLines(pathScenes);

			/*
			System.out.println("\nSCENES");
			for (String scene : scenesFile) { System.out.printf("%s | ",scene); }
			System.out.println("\nSHOTS");
			for (String shot : shotsFile) { System.out.printf("%s | ",shot); }
			*/
			System.out.println("\nVIDEO "+index);
			for (String scene : scenesFile) {
				shots = scene.split("\t",2);
				int initialShot = Integer.parseInt(shots[0]);
				int finalShot = Integer.parseInt(shots[1]);
				int initialFrame = Integer.parseInt(shotsFile.get(initialShot).split("\t",2)[0]);
				int finalFrame = Integer.parseInt(shotsFile.get(finalShot).split("\t",2)[1]);
				int totalFrames = finalFrame - initialFrame;
				String videoFileName = this.getPrefixFileName(index) + "." + this.getVideoInitials(index);
				File inputFile = new File(this.getVideosFolder() + videoFileName);
				XuggleVideo inputVideo = new XuggleVideo(inputFile);
				int videoFPS = (int) Math.round(inputVideo.getFPS());
				float totalSeconds = totalFrames / videoFPS;
				float remainSeconds = totalFrames % videoFPS;
				String seconds = String.format("%.0f", totalSeconds) + "." + String.format("%.0f", remainSeconds);
				
				int totalVideoFrames = finalFrame - Integer.parseInt(shotsFile.get(0).split("\t",2)[0]);
				float totalVideoSeconds = totalVideoFrames / videoFPS;
				float remainVideoSeconds = totalVideoFrames % videoFPS;
				float totalVideoMinutes = (float)Math.floor(totalVideoSeconds / 60);
				float remainVideoMinutes = totalVideoSeconds % 60;
				String videoSeconds = String.format("%.0f", totalVideoSeconds) + "." + String.format("%.0f", remainVideoSeconds);
				String videoMinutes = String.format("%.0f", totalVideoMinutes) + ":" + String.format("%.0f", remainVideoMinutes) +"."+ String.format("%.0f", remainVideoSeconds);
				System.out.printf("%s; %s; %s; %s; %s; %s; %s; \n", initialShot, finalShot, initialFrame, finalFrame, seconds, videoSeconds, videoMinutes);
				inputVideo.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
	}

/*	
	public static void BBC () {
		BbcDataset bbc = new BbcDataset();
		System.out.println("\nBBC Dataset");
		System.out.println(bbc.getDatasetInfo());
		//bbc.showScenesInSecondsPython();
		/* conferido, está certo
		for (int i = 1; i <= 11; i++) {
			bbc.showScenesInSeconds(i);
		}
		*/
		//bbc.fromShotsToSeconds();
		//bbc.fromSecondsToHMS();
		// bbc.showVideoShots("seconds");
		// bbc.showVideoShots("frames");
		// bbc.showVideoShots("frames", 9);
/*
	}
	public static void OVSD () {
		OvsdDataset ovsd = new OvsdDataset();
		System.out.println("\nOVSD Dataset"); 
		System.out.println(ovsd.getDatasetInfo());
		//ovsd.fromSecondsToShots();
		//ovsd.fromFrameScenesToShotsScenes();
		/*
		for (int i = 1; i <= 5; i++) {
			ovsd.showScenesInSeconds(i);
		}
		for (int i = 7; i <= 21; i++) {
			ovsd.showScenesInSeconds(i);
		}
		*/
		//ovsd.fromSecondsToHMS();
		// ovsd.showVideoShots("seconds");
		// ovsd.showVideoShots("frames");
/*
}

	public static void main(String[] args) throws Exception {
		BBC();
		OVSD();
	}
	*/

}
