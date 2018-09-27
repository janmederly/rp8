

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Summarizer {
	public static final int VELKOST_CYKLU = 4000;
	public static final int RIADOK_OD = 17580;
	public static final String PORADIE = "00000200000000020000020000020000200002002002000000000000000200000200220202002200000000000200000000000000000002002000000000000000000000000000000002000000000000000000000000000000000200200002000000000000020000200000000000000020002002002000000000002000000000000000000200020000000002220002000000000000000000000002020200000000000000200000000000000000000200000000002002000000000000002220000000000000000002202000000000200020000000202000002000000002000000000200020022000220000000000020";

	public static void main(String[] args) throws IOException {
		int pocetDobrych = 0;
		int pocetZlych = 0;
		int poziciaVCykle = 0;
		double[] dobre;
		dobre = new double[VELKOST_CYKLU];
		double[] zle;
		zle = new double[VELKOST_CYKLU];
		int a = 0;
		BufferedReader reader;

			reader = new BufferedReader(new FileReader("/Users/janme/Documents/ocko2.txt"));
			
			
			int cisloRiadku = 1;
			int pocetCyklov = PORADIE.length();
			int cisloCyklu = 0;
			
			while (cisloCyklu < pocetCyklov) {
				String line = reader.readLine();
				//System.out.println(cisloCyklu + ", " + poziciaVCykle + ", " + cisloRiadku + ", " + line);
				line = line.replaceAll(",", ".");
				if (cisloRiadku >= RIADOK_OD) {
					char x = cisla(cisloCyklu);
					String eeg = line.split("\t")[2];
					double povodne = Double.valueOf(eeg);
					if (x == '2') {
						dobre[poziciaVCykle] = dobre[poziciaVCykle] + povodne;
						pocetDobrych++;
					} else{
						zle[poziciaVCykle] = zle[poziciaVCykle] + povodne;
						pocetZlych++;
					}
					poziciaVCykle++;
					if (poziciaVCykle == VELKOST_CYKLU) {
						poziciaVCykle = 0;
						cisloCyklu++;
					}
				}
				cisloRiadku++;
			}

			reader.close();

		PrintWriter pw = new PrintWriter(new FileWriter("subor.csv"));
		for (int b = 0; b < 4000; b++) {
			pw.println(String.valueOf(dobre[b] / pocetDobrych).replace('.', ','));
		}
		pw.close();

		PrintWriter pw2 = new PrintWriter(new FileWriter("subor2.csv"));
		for (int g = 0; g < 4000; g++) {
			pw2.println(String.valueOf(zle[g] / pocetZlych).replace('.', ','));
		}
		pw2.close();
	}

	public static char cisla(int i) {
		char cislo = PORADIE.charAt(i);
		return cislo;
	}
}