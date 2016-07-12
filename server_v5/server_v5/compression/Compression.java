package server_v5.compression;

public class Compression {

	public static int[] encode(int[] pixels) {
		
		
		int[] temp = subsampling(pixels);
		
		return temp;
	}

	/**
	 * subsampling of pixels by given factor
	 * @param pixels
	 * @return
	 */
	private static int[] subsampling(int[] pixels) {
		int factor = 2;
		int length = pixels.length / factor;
		int[] result = new int[length];
		
		for(int i = 0; i < length; i++){
			result[i] = pixels[i * factor];
		}
		return result;
	}

	private static int[] chromaSubsampling(int[] pixels) {
		
		
		//TODO: Chroma subsampling
		
		return null;
	}

}
