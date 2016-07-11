package server_v5.video;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/**
 * <code>Video will help you read in video formats and 
 * return it as a byte array as well as turn a byte array
 * back into a video.
 * 
 * @reference implemented with the help of given example by 
 * Michael Thiele-Maas based on https://gist.github.com/rostyslav/1070380
 * 
 * @author thiloilg
 *
 */
public class Video {
	
	private File video;
	private Queue<Frame> frames = new LinkedList<Frame>();
	
	private VideoCapture cap;
	private Mat mat;
	
	private int width;
	private int height;
	
	/**
	 * Constructor
	 * @param video
	 */
	public Video(File video, int width, int height){
		this.setVideo(video);
		cap = new VideoCapture(video.getAbsolutePath()); //video.getAbsolutePath()
		
		if(cap.isOpened()) System.out.println("Opened Media File.");
	    else System.out.println("Media File is not opened.");
		
		mat = new Mat();
		
		this.width = width;
		this.height = height;
	}
	
	/**
	 * get a given number of frames the video instance.
	 * @param num
	 * @return
	 */
	public int getFrames(int num){
		while(cap.read(mat) && num > 0){
		    Size sz = new Size(width, height);
		    
		    Imgproc.resize(mat, mat, sz);
		    
		    byte[] b = new byte[mat.channels()];
		    
		    int resolution = mat.cols()*mat.rows();
		    int[] pixels = new int[resolution];
		    
		    int cols = mat.cols();
		    int red, green, blue;
		    
		    for(int row = 0; row < mat.rows(); row++){
		    	for(int col = 0; col < mat.cols(); col++){
					mat.get(row, col, b);
					red = b[2] & 0xff;
					green = b[1] & 0xff;
					blue = b[0] & 0xff;
					pixels[row * cols + col] = 0xff000000 | red << 16 | green << 8 | blue;
				}
			}

		    frames.add(new Frame(pixels));
		    num--;
		}
		return frames.size();
	}

	/**
	 * Getter
	 * @return
	 */
	public File getVideo() {
		return video;
	}
	
	public Queue<Frame> getFrames(){
		return frames;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}

	/**
	 * Setter
	 * @param video
	 */
	public void setVideo(File video) {
		this.video = video;
	}
	
	public void setFrames(Queue<Frame> frames){
		this.frames = frames;
	}
	
	public int getFramesSize(){
		return width * height;
	}

	public int getByteSizeOfFrame() {
		return width * height * 8;
	}
}
