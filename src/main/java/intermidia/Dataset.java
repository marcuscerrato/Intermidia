package intermidia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.video.xuggle.XuggleVideo;


public class Dataset {

	private String diretorioVideos = null;
	protected ArrayList<String> nomesVideos = null;
	protected String siglaDataset = null;
	protected int quantiaVideos = 0;
	protected String diretorioScenes = "/home/marcus/eclipse_new/evaluation/";

	protected void setQuantiaVideos(int quantia) {
		this.quantiaVideos = quantia;
	}

	private String getSiglaDataset() {
		return this.siglaDataset;
	}

	protected void setSiglaDataset(String sigla) {
		this.siglaDataset = sigla;
	}

	protected void setDiretorioVideos(String diretorio) {
		this.diretorioVideos = diretorio;
	}

	private String getNomeVideo(int indice) {
		int quantiaVideos = this.getQuantiaVideos();
		if (indice > 0 || indice <= quantiaVideos) {
			return this.nomesVideos.get(indice);
		}
		return null;
	}

	protected String getExtensaoVideo(int indice) {
		return "mp4";
	}

	protected int getQuantiaVideos() {
		return this.quantiaVideos;
	}

	protected String getDiretorioVideos() {
		return diretorioVideos;
	}

	protected String getDiretorioScenes() {
		return diretorioScenes;
	}

	public String getVideoInfo(int indice) {
		String videoFileName = this.getPrefixFileName(indice) + "." + this.getExtensaoVideo(indice);
		File inputFile = new File(this.getDiretorioVideos() + videoFileName);
		XuggleVideo inputVideo = new XuggleVideo(inputFile);
		int videoFPS = (int) Math.round(inputVideo.getFPS());
		long quantiaFrames = inputVideo.countFrames();
		long duracaoVideo = inputVideo.getDuration();
		String duracaoFormatadaVideo = formatSeconds(((float) duracaoVideo));
		int alturaVideo = inputVideo.getHeight();
		int larguraVideo = inputVideo.getWidth();
		inputVideo.close();
		
		
    	XuggleAudio inputAudioMFCCRaw = new XuggleAudio(inputFile);    	 

   	   	long comprimento = inputAudioMFCCRaw.getLength();
   		int quantiaCanais = inputAudioMFCCRaw.getFormat().getNumChannels();
   		double rate = inputAudioMFCCRaw.getFormat().getSampleRateKHz();
   		int quantiaBits = inputAudioMFCCRaw.getFormat().getNBits();
		
		// System.out.printf("%s (%s) [%s] => \n FPS = %d \t #Frames = %d \t Duração = %d (%s) \t Medidas = %d x %d \n",
			//	getNomeVideo(indice), indice, videoFileName, videoFPS, quantiaFrames, duracaoVideo,
			//	duracaoFormatadaVideo, alturaVideo, larguraVideo);
		return (getNomeVideo(indice) + "(" + indice + ") [" + videoFileName + "] " + "\n" + 
				" \t FPS " + videoFPS +  
				" \t Frames " + quantiaFrames + " \t Duração " + duracaoVideo + " [ " + duracaoFormatadaVideo + "] " + 
				" \t Tamanho " + alturaVideo + " x " + larguraVideo + " \n " + 
				" \t Canais " + quantiaCanais +
				" \t Comprimento " + comprimento + " \t Taxa " + rate + " \t Bits " + quantiaBits);
	}

	public String getDatasetInfo() {
		StringBuilder info = new StringBuilder();
		int quantiaVideos = this.getQuantiaVideos();
		for (int conta = 1; conta <= quantiaVideos; conta++) {
			info.append(getVideoInfo(conta)+"\n");
		}
		return(info.toString());
	}

	private String formatSeconds(float seconds) {
		int segundos = (int) seconds;
		int segundo = segundos % 60;
		int minutos = segundos / 60;
		int minuto = minutos % 60;
		int hora = minutos / 60;
		return (String.format("%02d:%02d:%02d", hora, minuto, segundo));
	}

	public void showFileContent(String fileDirectory, String fileName, String fileType) {
		Path path = Paths.get(fileDirectory + fileName);
		List<String> linhasArquivo;
		boolean converteSegundos = (fileType.equals("seconds")) ? true : false;
		try {
			linhasArquivo = Files.readAllLines(path);
			for (String linha : linhasArquivo) {
				if (converteSegundos) {
					float segundosMudanca = Float.parseFloat(linha);
					linha = formatSeconds(segundosMudanca);
				}
				System.out.printf("%s | ", linha);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
	}

	protected void showVideoShots(String fileType, int indice) {
		String videoFileName = getVideoFileName(fileType, indice);
		System.out.printf("[[" + indice + ") " + this.getNomeVideo(indice) + "]] => ");
		this.showFileContent(this.getDiretorioVideos(), videoFileName, fileType);
	}

	protected void showVideoShots(String fileType) {
		int quantiaVideos = this.getQuantiaVideos();
		System.out.println("Dataset " + this.getSiglaDataset() + " in Shots (" + fileType + ") ");
		for (int conta = 1; conta < quantiaVideos; conta++) {
			this.showVideoShots(fileType, conta);
		}
	}

	private String getVideoFileName(String fileType, int indice) {
		if (fileType.equals("seconds")) {
			return (this.getPrefixFileName(indice) + "shots.txt");
		} else if (fileType.equals("frames")) {
			return (this.getPrefixFileName(indice) + "shots.csv");
		} else if (fileType.equals("frameScenes")) {
			return (this.getPrefixFileName(indice) + "scenes.txt");
		} else if (fileType.equals("shotScenes")) {
			return (this.getPrefixFileName(indice) + "scenes.csv");
		}
		return null;
	}

	public String getPrefixFileName(int indice) {
		String videoIndex = String.format("%02d", indice);
		return this.getSiglaDataset() + videoIndex;
	}

	public void fromSecondsToShots() {
		int quantiaVideos = this.getQuantiaVideos();
		for (int indice = 1; indice <= quantiaVideos; indice++) {
			String videoFileName = this.getPrefixFileName(indice) + "." + this.getExtensaoVideo(indice);
			File inputFile = new File(this.getDiretorioVideos() + videoFileName);
			XuggleVideo inputVideo = new XuggleVideo(inputFile);
			int videoFPS = (int) Math.round(inputVideo.getFPS());
			//long ultimoFrame = inputVideo.countFrames() - (long)1;
			
			try {
				FileWriter writer = new FileWriter(this.getDiretorioVideos() + this.getPrefixFileName(indice) + 
						"shots.csv");

				// read shot changing in seconds
				String shotFile = this.getDiretorioVideos() + this.getPrefixFileName(indice) + "shots.txt";
				Path path = Paths.get(shotFile);
				List<String> linhasArquivo = Files.readAllLines(path);
				int frameAntigo = 0;
				for (String linha : linhasArquivo) {
					float segundosMudanca = Float.parseFloat(linha);
					float frameMudanca = segundosMudanca * videoFPS;
					int frameMudancaInt = Math.round(frameMudanca);
					// write down shot changing frame
					writer.append("" + frameAntigo + "\t" + frameMudancaInt + "\n");
					frameAntigo = frameMudancaInt + 1;
				}
				//writer.append("" + frameAntigo + "\t" + ultimoFrame + "\n");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputVideo.close();
		}
	}

	public void fromFrameScenesToShotsScenes() {
		int quantiaVideos = this.getQuantiaVideos();
		for (int indice = 1; indice <= quantiaVideos; indice++) {
			if (indice != 6) {
			// arquivos de cenas
			Path pathFrameScenes = Paths.get(this.getDiretorioScenes() + this.getSiglaDataset() +
					"_dataset/" + this.getVideoFileName("frameScenes", indice));
			System.out.println(pathFrameScenes.toString());
			String shotScenesFilename = this.getDiretorioScenes() + this.getSiglaDataset() +
					"_dataset/" + this.getVideoFileName("shotScenes", indice);
			System.out.println(shotScenesFilename);
			List<String> frameScenesFile;
			//List<String> shotScenesFile;
			String[] frameScene;

			// arquivo de tomadas
			Path pathShots = Paths.get(this.getDiretorioVideos() + this.getVideoFileName("frames", indice));
			System.out.println(pathShots.toString());
			List<String> shotsFile;
			String[] shots;
			int initialShotCounter = 0;
			
			try {
				frameScenesFile = Files.readAllLines(pathFrameScenes); // leio desse
				shotsFile = Files.readAllLines(pathShots); // vejo onde está nesse
				int totalShotsFile = shotsFile.size();
				FileWriter writer = new FileWriter(shotScenesFilename); // escrevo nesse
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
		int quantiaVideos = this.getQuantiaVideos();
		for (int indice = 1; indice < quantiaVideos; indice++) {
			String videoFileName = this.getPrefixFileName(indice) + "." + this.getExtensaoVideo(indice);
			File inputFile = new File(this.getDiretorioVideos() + videoFileName);
			XuggleVideo inputVideo = new XuggleVideo(inputFile);
			try {
				FileWriter writer = new FileWriter(this.getDiretorioVideos() + this.getPrefixFileName(indice) + "shots_hms.txt");

				// read shot changing in seconds
				String shotOVSDFile = this.getDiretorioVideos() + this.getPrefixFileName(indice) + "shots.txt";
				Path path = Paths.get(shotOVSDFile);
				List<String> linhasArquivo = Files.readAllLines(path);
				for (String linha : linhasArquivo) {
					float segundos = Float.parseFloat(linha);
					writer.append("" + formatSeconds(segundos) + "\n");
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
		int quantiaVideos = this.getQuantiaVideos();
		float segundosTotal = (float) 0.00;
		for (int indice = 1; indice <= quantiaVideos; indice++) {
			String videoFileName = this.getPrefixFileName(indice) + "." + this.getExtensaoVideo(indice);
			File inputFile = new File(this.getDiretorioVideos() + videoFileName);
			XuggleVideo inputVideo = new XuggleVideo(inputFile);
			int videoFPS = (int) Math.round(inputVideo.getFPS());

			try {
				FileWriter writer = new FileWriter(
						this.getDiretorioVideos() + this.getPrefixFileName(indice) + "shots.txt");

				// read shot changing in seconds
				String shotOVSDFile = this.getDiretorioVideos() + this.getPrefixFileName(indice) + "shots.csv";
				Path path = Paths.get(shotOVSDFile);
				List<String> linhasArquivo = Files.readAllLines(path);
				for (String linha : linhasArquivo) {
					String[] frames = linha.split("\t");
					int deltaFrame = Integer.parseInt(frames[1]) - Integer.parseInt(frames[0]);
					float segundosMudanca = deltaFrame / videoFPS;
					segundosTotal += segundosMudanca;
					writer.append("" + segundosTotal + "\n");
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
		int quantiaVideos = this.getQuantiaVideos();
		for (int conta = 1; conta <= quantiaVideos; conta++) {
			String videoIndex = String.format("%02d", conta);
			String videoFileName = this.getPrefixFileName(conta) + "." + this.getExtensaoVideo(conta);
			File inputFile = new File(this.getDiretorioVideos() + videoFileName);
			XuggleVideo inputVideo = new XuggleVideo(inputFile);
			int videoFPS = (int) Math.round(inputVideo.getFPS());
			inputVideo.close();
			System.out.printf("python scenes_in_seconds_0.py %s %s %d \n", this.getSiglaDataset(), videoIndex, videoFPS);
		}
		
	}
	
	public void showScenesInSeconds(int indice) {

		System.out.println("Shots in Seconds - Video "+this.getSiglaDataset()+ " " +indice);
		Path pathShots = Paths.get(this.getDiretorioVideos() + this.getVideoFileName("frames", indice));
		Path pathScenes = Paths.get(this.getDiretorioScenes() + this.getSiglaDataset() +
				"_dataset/" + this.getVideoFileName("shotScenes", indice));

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
			System.out.println("\nVIDEO "+indice);
			for (String scene : scenesFile) {
				shots = scene.split("\t",2);
				int initialShot = Integer.parseInt(shots[0]);
				int finalShot = Integer.parseInt(shots[1]);
				int initialFrame = Integer.parseInt(shotsFile.get(initialShot).split("\t",2)[0]);
				int finalFrame = Integer.parseInt(shotsFile.get(finalShot).split("\t",2)[1]);
				int totalFrames = finalFrame - initialFrame;
				String videoFileName = this.getPrefixFileName(indice) + "." + this.getExtensaoVideo(indice);
				File inputFile = new File(this.getDiretorioVideos() + videoFileName);
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
	}

	public static void main(String[] args) throws Exception {
		BBC();
		OVSD();
	}

}
