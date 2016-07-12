package server_v5.compression;

public class Compression {

	public static int[] encode(int[] pixels, int width, int height) {
		
		
//		int[] temp = grayScaling(downscaling(pixels));
		int[] temp = downscaling(pixels);
		
		return temp;
	}

	/**
	 * subsampling of pixels by given factor
	 * @param pixels
	 * @return
	 */
	private static int[] downscaling(int[] pixels) {
		int factor = 2;
		int length = pixels.length / factor;
		int[] result = new int[length];
		
		for(int i = 0; i < length; i++){
			result[i] = pixels[i * factor];
		}
		return result;
	}
	
	/**
	 * Performs gray scale on pixels
	 */
	public static int[] grayScaling(int[] pixels) {
		int[] result = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			int temp = (((pixels[i] >> 16) & 0xff)
					+ ((pixels[i] >> 8) & 0xff) + (pixels[i] & 0xff)) / 3;
			result[i] = ((0xFF << 24) | ((temp) << 16) | ((temp) << 8) | (temp));
		}
		return result;
	}

	private static int[] chromaSubsampling(int[] pixels) {
		
		
		//TODO: Chroma subsampling
		
		return null;
	}

}
