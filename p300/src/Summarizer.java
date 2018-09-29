

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Summarizer {
	public static final int VELKOST_CYKLU = 2000;
	public static final int RIADOK_OD = 18035;
	public static final String PORADIE = "0000002000000200000020000000200000020000000000200000000000000000000000000020000000002000000000000200000000000202020002000000002000000000000000002000000000222000002002020020000000000000000000020000000000000000000000000000000000000020002002000020002002002200000000202000000000000202000000020020002000002000000000000000002";

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

			reader = new BufferedReader(new FileReader("/Users/janme/Documents/ocko5.txt"));
			
			
			int cisloRiadku = 1;
			int pocetCyklov = PORADIE.length();
			int cisloCyklu = 0;
			
			while (cisloCyklu < pocetCyklov) {
				String line = reader.readLine();
				//System.out.println(cisloCyklu + ", " + poziciaVCykle + ", " + cisloRiadku + ", " + line);
				line = line.replaceAll(",", ".");
				if (cisloRiadku >= RIADOK_OD) {
					char x = cisla(cisloCyklu);
					String eeg = line.split("\t")[1];
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

		PrintWriter pw = new PrintWriter(new FileWriter("subor.txt"));
		for (int b = 0; b < VELKOST_CYKLU; b++) {
			pw.println(String.valueOf(dobre[b] / pocetDobrych).replace('.', ','));
		}
		pw.close();

		PrintWriter pw2 = new PrintWriter(new FileWriter("subor2.txt"));
		for (int g = 0; g < VELKOST_CYKLU; g++) {
			pw2.println(String.valueOf(zle[g] / pocetZlych).replace('.', ','));
		}
		pw2.close();
	}

	public static char cisla(int i) {
		char cislo = PORADIE.charAt(i);
		return cislo;
	}
}