package intermidia;

import java.util.ArrayList;

public class GeneralTests {

	public static void main( String[] args ) throws Exception
    {
		//TesteDataset dataset = new TesteDataset();
		BbcDataset dataset = new BbcDataset();
		System.out.println("\n"+Utilities.getDatasetInfo(dataset));
		
		String[] keyframesThresholds = {"0.5", "0.6"};
		String[] bofKmeansSizes = {"30", "50", "100", "150", "200"};
		String[] bofClusteringSteps = {"30", "50", "80", "100"};
		String[] poolingMethods = {"javg", "avg", "cobavg", "max", "cobmax", 
					"avgharm", "avggeom", 
					"mnk1", "mnk2", "mnk3", "mnk4", "mnk5", "mnk6", 
					"mnk7", "mnk8", "mnk9", "mnk10", "mnk11", "mnk12",
					"per40", "per65", "per85",
					"max5", "max10", "max15", "max20", "max40", "max60", "max80",
					"lst50", "lst65", "lst80", "lst90"};
		boolean normalisationFlag = true;
		String[] deltaThresholds = {"0.35", "0.45", "0.55"};
		String[] timeWindows = {"5", "6", "7", "8", "9"};
		boolean csiftFlag = true;
		String formatOut = "-alt_csv_out";
		
		String resultsFolder = "/home/marcus/Doutorado/intermidia/results/bbc_teste/testes_bbc/";
		ArrayList<String> videoFilenames = dataset.getVideosFilenames();

		int index = 0;
		for (String videoFilename : videoFilenames) {
			String videoFullname = dataset.getVideoFullName(index); 
			String audioFullname = dataset.getAudioFullName(index);
			String shotsFullname = dataset.getShotFullName(index);

			String audios = resultsFolder+videoFilename+"_audios/"; 
			String images = resultsFolder+videoFilename+"_images/";
			String filteredImages = resultsFolder+videoFilename+"_filtered_images/";
			
			System.out.println("\nPipeline information"+
					"\nVideo file name:"+videoFullname+
					"\nShots file name:"+shotsFullname+
					"\nAudio file name:"+audioFullname+
					"\nAudios folder name:"+audios+
					"\nImages folder name:"+images+
					"\nFiltered images folder name:"+filteredImages+
					"");

			for (String keyframesThreshold : keyframesThresholds) {
				for (String k : bofKmeansSizes) {
					for (String clusteringStep : bofClusteringSteps) {
						for (String poolingMethod : poolingMethods) {
							for (String deltaThreshold : deltaThresholds) {
								for (String timeWindow : timeWindows) {
									System.out.println("\nPipeline begin");
									System.out.format("\nParameters: information"+
											"\nvideoFilename:"+videoFilename+
											"\nkeyframesThreshold:"+keyframesThreshold+
											"\nbofKmeansSize:"+k+
											"\nbofClusteringStep:"+clusteringStep+
											"\npoolingMethod:"+poolingMethod+
											"\ndeltaThreshold:"+deltaThreshold+
											"\ntimeWindow:"+timeWindow+
											"\nnormalisationFlag:"+normalisationFlag+
											"\ncsiftFlag:"+csiftFlag+
											"\nformatOut:"+formatOut+
											"");
									
									System.out.println("\n" + Utilities.getVideoInfo(dataset, index));
/*
									String argsMfcc[] = {
											videoFullname, 
									    	shotsFullname,
									    	audios, 
									    	resultsFolder+videoFilename+"_mfcc.csv", 
									    	dataset.getAudioChannels(),
									    	dataset.getChosenAudioChannel()
									};
									String argsMfccAudio[] = {
											audioFullname, 
									    	shotsFullname,
									    	audios, 
									    	resultsFolder+videoFilename+"_mfcc.csv",
									    	"1",
									    	"1"
									};
									System.out.println("\nMFCC Extractor\n"+getFunctionCall(argsMfccAudio));
							    	//intermidia.MFCCExtractor.main(argsMfcc);
							    	intermidia.MFCCExtractor.main(argsMfccAudio);
							    	
									String argsBoaw[] = {
											resultsFolder+videoFilename+"_mfcc.csv",
											resultsFolder+videoFilename+"_auralhistogram.csv",
											k,
											clusteringSteps
									};
									System.out.println("\nBoAW Calculator"+getFunctionCall(argsBoaw));
							    	intermidia.BoAWCalculator.main(argsBoaw);
							    	
							    	System.gc();
							    	
									String argsKeyframe[] = {
											videoFullname, 
									    	shotsFullname,
									    	resultsFolder+videoFilename+"_keyframes.csv", 
									    	images,
									    	keyframesThreshold
									};
									System.out.println("\nKeyframe Detector"+getFunctionCall(argsKeyframe));
									intermidia.KeyframeDetector.main(argsKeyframe);
							    	
									String argsFilter[] = {
									    	images,
									    	filteredImages
									};
							    	System.out.println("\nKeyframe Filter"+getFunctionCall(argsFilter));
							    	intermidia.KeyframeFilter.main(argsFilter);
							    	
									String argsSift[] = {
											filteredImages,
											resultsFolder+videoFilename+"_keypoints.csv",
											(csiftFlag) ? "csift" : ""
									};
									System.out.println("\nSIFT Extractor"+getFunctionCall(argsSift));
							    	intermidia.SIFTExtractor.main(argsSift);

									String argsBovw[] = {
											resultsFolder+videoFilename+"_keypoints.csv",
											resultsFolder+videoFilename+"_visualhistogram.csv",
											k,
											clusteringSteps
									};
							    	System.out.println("\nBoVW Calculator"+getFunctionCall(argsBovw));
							    	intermidia.BoVWCalculator.main(argsBovw);
							    	
							    	System.gc();
							    	
									String argsFusion[] = {
											resultsFolder+videoFilename+"_auralhistogram.csv",
											resultsFolder+videoFilename+"_visualhistogram.csv",
											resultsFolder+videoFilename+"_fusion.csv",
											k,
											clusteringSteps,
											poolingMethod,
											(normalisationFlag) ? "normalise" : ""
									};
									System.out.println("\nFloat MLF Fuser"+getFunctionCall(argsFusion));
							    	intermidia.FloatMLFFuser.main(argsFusion);
							    	
									String argsSegmentation[] = {
											resultsFolder+videoFilename+"_fusion.csv",
											resultsFolder+videoFilename+"_segmentation.csv",
											deltaThreshold, 
											timeWindow, 
											formatOut
									};
									System.out.println("\nSTG Scene Seg"+getFunctionCall(argsSegmentation));
							    	intermidia.STGSceneSeg.main(argsSegmentation);
							    	
							    	System.out.println("\nPipeline end");
							    	System.gc();
							    	
*/
								}
							}
						}
					}
				}
			}
			index++;
		}
    }
	
	private static String getFunctionCall(String[] args) {
		StringBuffer functionCall = new StringBuffer();
		for(String arg : args) {
			functionCall.append(arg+"\n");
		}
		return(functionCall.toString());
	}
	
}
