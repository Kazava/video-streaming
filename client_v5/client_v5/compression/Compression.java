package client_v5.compression;

public class Compression {

	public static int[] decode(int[] pixels) {
		
		
		// TODO: decode chroma subsampling
		
		int[] temp = subsampling(pixels);
		
		return temp;
	}

	private static int[] subsampling(int[] pixels) {
		int factor = 2;
		int length = pixels.length * factor;
		int[] result = new int [length];
		for(int i = 0; i < length; i++){
			result[i] = pixels[i / factor];
		}
		return result;
	}
	
}
