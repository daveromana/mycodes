package concurrentPro.huangxu.game;

import java.util.Random;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	int[] ss = GetRandomSequence2(80);
	}

	public static int[] GetRandomSequence2(int total) {
		int[] sequence = new int[total];
		int[] output = new int[total];
		for (int i = 0; i < total; i++) {
			sequence[i] = i;
		}
		Random random = new Random();
		int end = total - 1;
		for (int i = 0; i < total; i++) {
			int num = random.nextInt(end + 1);
			output[i] = sequence[num];
			sequence[num] = sequence[end];
			end--;
		}
		return output;
	}
}
