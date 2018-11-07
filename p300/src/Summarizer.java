
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Summarizer {
	
	public static final int VELKOST_CYKLU = 2000;
	public static final int RIADOK_OD = 18035;
	public static final String PORADIE_TONOV = "0000002000000200000020000000200000020000000000200000000000000000000000000020000000002000000000000200000000000202020002000000002000000000000000002000000000222000002002020020000000000000000000020000000000000000000000000000000000000020002002000020002002002200000000202000000000000202000000020020002000002000000000000000002";

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

		reader = new BufferedReader(new FileReader("/Users/janme/Documents/ocko5-n50-BpBe,o6,1-20Hz.txt"));
		

		int cisloRiadku = 1;
		int pocetCyklov = PORADIE_TONOV.length();
		int cisloCyklu = 0;
		int za = 0;

		while (cisloCyklu < pocetCyklov) {
			String line = reader.readLine();
			// System.out.println("spracovavam cyklus cislo " + cisloCyklu + ",
			// cislo riadku: " + cisloRiadku + ", pozicia v cykle = " +
			// poziciaVCykle);
			// System.out.println(cisloCyklu + ", " + poziciaVCykle + ", " +
			// cisloRiadku + ", " + line);
			line = line.replaceAll(",", ".");
			if (cisloRiadku >= RIADOK_OD) {
				char x = cisla(cisloCyklu);
				String eeg = line.split("\t")[1];
				double povodne = Double.valueOf(eeg);
				int yz = cisloCyklu;
				if (yz != za) {
					if (x == '2') {

						// System.out.println("pocet dobrych = " +
						// pocetDobrych);
						pocetDobrych++;
					} else {

						// System.out.println("pocet zlych = " + pocetZlych);
						pocetZlych++;
					}
				}

				if (x == '2') {
					dobre[poziciaVCykle] = dobre[poziciaVCykle] + povodne;
					// System.out.println("pocet dobrych = " + pocetDobrych);

				} else {
					zle[poziciaVCykle] = zle[poziciaVCykle] + povodne;
					// System.out.println("pocet zlych = " + pocetZlych);

				}

				za = cisloCyklu;
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
		pw.println("cas\tpos\tneg");
		for (int b = 0; b < VELKOST_CYKLU; b++) {
			double priemerDobrych = dobre[b] / pocetDobrych;
			double priemerZlych = zle[b] / pocetZlych;
			
			pw.println(b/2.0 + "\t" + priemerDobrych + "\t" + priemerZlych);	
		}
		pw.close();
		System.out.println("pocet dobrych = " + pocetDobrych + ", pocet zlych = " + pocetZlych);
		
	}

	public static char cisla(int i) {
		char cislo = PORADIE_TONOV.charAt(i);
		return cislo;
	}
}