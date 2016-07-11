package client_v5.video;

/**
 * <code>Frame</code> represents single pictures of a video
 * in a byte array.
 * @author thiloilg
 *
 */
public class Frame {
	
	private int[] pixels;
	
	/**
	 * Constructor
	 * @param pixels
	 */
	public Frame(int[] pixels){
		this.setPixels(pixels);
	}

	/**
	 * Getter
	 * @return
	 */
	public int[] getPixels() {
		return pixels;
	}
	
	public int getPixel(int pos){
		return pixels[pos];
	}

	/**
	 * Setter
	 * @param pixels
	 */
	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public String print() {
		String str = "";
		for(int i = 0; i < pixels.length; i++){
			str += String.format("#%02x%02x%02x%02x", (pixels[i] >> 24) & 0xff,(pixels[i] >> 16) & 0xff, (pixels[i] >> 8) & 0xff , pixels[i] & 0xff) + " ";
			if(i % 10 == 9) str += "\n";
		}
		str += "\n length: " + pixels.length;
		return str;
	}

	public int size() {
		// TODO Auto-generated method stub
		return pixels.length;
	}
	
	
}
