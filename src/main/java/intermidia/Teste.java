package intermidia;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.vfs2.FileSystemException;
import org.openimaj.audio.AudioPlayer;
import org.openimaj.audio.SampleChunk;
import org.openimaj.audio.analysis.FourierTransform;
import org.openimaj.audio.features.MFCC;
import org.openimaj.audio.filters.EQFilter;
import org.openimaj.audio.filters.EQFilter.EQType;
import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.data.dataset.VFSListDataset;
import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.BasicMatcher;
import org.openimaj.feature.local.matcher.BasicTwoWayMatcher;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.MatchingUtilities;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.connectedcomponent.GreyscaleConnectedComponentLabeler;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.pixel.statistics.HistogramModel;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.model.fit.RANSAC;
import org.openimaj.math.statistics.distribution.MultidimensionalHistogram;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.video.xuggle.XuggleVideo;
import org.openimaj.vis.audio.AudioWaveform;

import ch.akuhn.matrix.Vector.Entry;

/**
 * OpenIMAJ Hello world!
 *
 */
public class Teste {
	public static void main(String[] args) throws IOException {
		// Create an image
		MBFImage image = ImageUtilities.readMBF(new File("/home/marcus/eclipse_new/images/aviao01.jpg"));

		Teste application = new Teste();
		// application.processImage(image);
		// application.cluster(image);
		// application.histogram();
		// application.features();
		// application.dataset();
		// application.video();
		application.audio();

	}

	public void audio() throws MalformedURLException {
    	//XuggleAudio xa = new XuggleAudio( new File("/home/marcus/eclipse_new/results/audios/bbc01/s0003.wav"));

    	final XuggleAudio xa = new XuggleAudio(
    			new URL("http://www.audiocheck.net/download.php?filename=Audio/audiocheck.net_sweep20-20klin.wav"));
    	
    	/*
    	AudioPlayer.createAudioPlayer(xa).run();
    	final AudioWaveform vis = new AudioWaveform(400,400);
    	vis.showWindow("Waveform");
    	SampleChunk sc = null;
    	/*
    	while( (sc = xa.nextSampleChunk()) != null )
    		vis.setData( sc.getSampleBuffer() );
    	*/
    	/*
    	FourierTransform fft = new FourierTransform(xa);
    	sc = fft.nextSampleChunk();
    	while( sc != null ) {
    		float[][] fftData = fft.getMagnitudes();
    		double[] fftDataDouble = new double[fftData[0].length];
    		for (int d = 0; d < fftData[0].length; d++) {
    			fftDataDouble[d] = (double) fftData[0][d];
    		}
    		//vis.setData(fftData[0]);
    		vis.setData(fftDataDouble);
    		sc = fft.nextSampleChunk();
    	}
    	*/
    	/*
    	EQFilter eq = new EQFilter(xa, EQType.LPF, 5000);
    	FourierTransform fft = new FourierTransform(eq);
    	while ((sc = fft.nextSampleChunk()) != null) {
    		
    	}
    	*/
    	//MFCC mfcc = new MFCC(xa);
    }

	public void video() {
		Video<MBFImage> video = new XuggleVideo(new File("/home/marcus/eclipse_new/videos/bbc01.mp4"));
		// VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);
		/*
		 * for (MBFImage mbfImage : video) {
		 * DisplayUtilities.displayName(mbfImage.process(new CannyEdgeDetector()),
		 * "videoFrames"); }
		 */

		VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);
		display.addVideoListener(new VideoDisplayListener<MBFImage>() {
			public void beforeUpdate(MBFImage frame) {
				frame.processInplace(new CannyEdgeDetector());
			}

			public void afterUpdate(VideoDisplay<MBFImage> display) {

			}
		});
	}

	public void dataset() throws FileSystemException {

		/*
		 * VFSListDataset<FImage> images = new
		 * VFSListDataset<FImage>("/home/marcus/eclipse_new/images",
		 * ImageUtilities.FIMAGE_READER);
		 * DisplayUtilities.display(images.getRandomInstance(),
		 * "A random image from the dataset"); System.out.println(images.size());
		 */

		VFSListDataset<MBFImage> imagesColor = new VFSListDataset<MBFImage>("/home/marcus/eclipse_new/images",
				ImageUtilities.MBFIMAGE_READER);

		// DisplayUtilities.display(imagesColor.getRandomInstance(),
		// "A random image from the color dataset");
		// DisplayUtilities.display("My images", imagesColor);

		/*
		 * VFSListDataset<FImage> faces = new
		 * VFSListDataset<FImage>("zip:http://datasets.openimaj.org/att_faces.zip",
		 * ImageUtilities.FIMAGE_READER); DisplayUtilities.display("ATT faces", faces);
		 */

		VFSGroupDataset<FImage> groupedFaces = new VFSGroupDataset<FImage>("/home/marcus/eclipse_new/images/",
				ImageUtilities.FIMAGE_READER);
		for (final java.util.Map.Entry<String, VFSListDataset<FImage>> entry : groupedFaces.entrySet()) {
			DisplayUtilities.display(entry.getKey(), entry.getValue());
		}
	}

	public void features() throws MalformedURLException, IOException {
		// MBFImage query = ImageUtilities.readMBF(new
		// URL("http://static.openimaj.org/media/tutorial/query.jpg"));
		// MBFImage target = ImageUtilities.readMBF(new
		// URL("http://static.openimaj.org/media/tutorial/target.jpg"));
		MBFImage query = ImageUtilities.readMBF(new File("/home/marcus/eclipse_new/images/aviao01.jpg"));
		MBFImage target = ImageUtilities.readMBF(new File("/home/marcus/eclipse_new/images/aviao02.jpg"));

		DoGSIFTEngine engine = new DoGSIFTEngine();
		LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten());
		LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(target.flatten());
		LocalFeatureMatcher<Keypoint> matcher1 = new BasicMatcher<Keypoint>(80);
		LocalFeatureMatcher<Keypoint> matcher = new BasicTwoWayMatcher<Keypoint>();
		/*
		 * matcher.setModelFeatures(queryKeypoints);
		 * matcher.findMatches(targetKeypoints); MBFImage basicMatches =
		 * MatchingUtilities.drawMatches(query, target, matcher.getMatches(),
		 * RGBColour.RED); DisplayUtilities.display(basicMatches);
		 */
		RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(5.0, 1500,
				new RANSAC.PercentageInliersStoppingCondition(0.5));
		matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(new FastBasicKeypointMatcher<Keypoint>(8), modelFitter);
		matcher.setModelFeatures(queryKeypoints);
		matcher.findMatches(targetKeypoints);
		MBFImage consistentMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(), RGBColour.RED);
		DisplayUtilities.display(consistentMatches);
		target.drawShape(query.getBounds().transform(modelFitter.getModel().getTransform().inverse()), 3,
				RGBColour.BLUE);
		// DisplayUtilities.display(target);
	}

	public void histogram() throws IOException {
		MBFImage[] imagePlane = new MBFImage[] {
				ImageUtilities.readMBF(new File("/home/marcus/eclipse_new/images/aviao01.jpg")),
				ImageUtilities.readMBF(new File("/home/marcus/eclipse_new/images/aviao03.jpg")),
				ImageUtilities.readMBF(new File("/home/marcus/eclipse_new/images/carro01.jpg")) };
		List<MultidimensionalHistogram> histograms = new ArrayList<MultidimensionalHistogram>();
		HistogramModel model = new HistogramModel(4, 4, 4);
		for (MBFImage i : imagePlane) {
			model.estimateModel(i);
			histograms.add(model.histogram.clone());
		}
		int histogramsSize = histograms.size();
		for (int i = 0; i < histogramsSize; i++) {
			for (int j = 0; j < histogramsSize; j++) {
				double distance = histograms.get(i).compare(histograms.get(j), DoubleFVComparison.EUCLIDEAN);
				System.out.printf("%.4f ", distance);
			}
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < histogramsSize; i++) {
			for (int j = 0; j < histogramsSize; j++) {
				double distance = histograms.get(i).compare(histograms.get(j), DoubleFVComparison.INTERSECTION);
				System.out.printf("%.4f ", distance);
			}
			System.out.println();
		}

	}

	public void cluster(MBFImage image) {
		MBFImage input = ColourSpace.convert(image, ColourSpace.CIE_Lab);
		FloatKMeans cluster = FloatKMeans.createExact(2);
		float[][] imageData = input.getPixelVectorNative(new float[input.getWidth() * input.getHeight()][3]);
		FloatCentroidsResult result = cluster.cluster(imageData);
		float[][] centroids = result.getCentroids();
		for (float[] fs : centroids) {
			System.out.println(Arrays.toString(fs));
		}
		HardAssigner<float[], ?, ?> assigner = result.defaultHardAssigner();
		for (int y = 0; y < input.getHeight(); y++) {
			for (int x = 0; x < input.getWidth(); x++) {
				float[] pixel = input.getPixelNative(x, y);
				int centroid = assigner.assign(pixel);
				input.setPixelNative(x, y, centroids[centroid]);
			}
		}
		input = ColourSpace.convert(input, ColourSpace.RGB);
		// DisplayUtilities.display(image);
		// DisplayUtilities.display(input);

		GreyscaleConnectedComponentLabeler labeler = new GreyscaleConnectedComponentLabeler();
		List<ConnectedComponent> components = labeler.findComponents(input.flatten());
		int i = 0;
		for (ConnectedComponent comp : components) {
			if (comp.calculateArea() < 50)
				continue;
			input.drawText("Point:" + (i++), comp.calculateCentroidPixel(), HersheyFont.TIMES_MEDIUM, 20);
		}
		// DisplayUtilities.display(input);
	}

	public void processImage(MBFImage image) {
		MBFImage clone = image.clone();
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				clone.getBand(1).pixels[y][x] = 0;
				clone.getBand(2).pixels[y][x] = 0;
			}
		}
		DisplayUtilities.display(clone);
		MBFImage imageCanny = image.processInplace(new CannyEdgeDetector());
		DisplayUtilities.display(image);
		DisplayUtilities.display(clone);
		DisplayUtilities.display(imageCanny);
	}
}
