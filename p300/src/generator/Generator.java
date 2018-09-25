package generator;

import java.util.Random;

import javax.sound.sampled.LineUnavailableException;

public class Generator {
	public static void main(String[] args) throws LineUnavailableException, InterruptedException {

		Sinus sinus = new Sinus();
		sinus.open();
		sinus.playTone(8000, 10);
		sinus.playTone(8000, 2);
		sinus.playTone(8000, 2);
		
		int interval = 2000;

		int i = 0;
		double nahoda = 0;
		long dalsi = System.currentTimeMillis() + interval;
		for (i = 0; i < 150;) {
			nahoda = Math.random() * 8;
			int ton = (int) nahoda == 7 ? 2 : 0;
			System.out.print(ton);
			if (ton == 0) {
				sinus.playTone(440, 100);
			} else if (ton == 1) {
				sinus.playTone(440, 100);
			} else if (ton == 2) {
				sinus.playTone(220, 100);
			}
			while (System.currentTimeMillis() < dalsi) {
				if (dalsi - System.currentTimeMillis() > 50) {
					Thread.sleep(40);
				}
			}
			dalsi = dalsi + interval;
		}

		sinus.close();
	}
}
