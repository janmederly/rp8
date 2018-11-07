
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class SummarizerNew {
	
	public static final int VELKOST_CYKLU = 2000;
	public static final int REZERVA = 1000;
	public static final double odchylka = 0;
	public static final int RIADOK_OD = 73649;
	public static final String PORADIE_TONOV = "000000002200000000000200002000000000000002000020220000000000000000000000000000000200000000000000200000000020020020000000000000000020002002000000200000020000000002000220000020000000220000000000000200002000200000000000000000000220002000000000000000000020000000000000002000000000200000000000000200000000000000020000200020000000000020002000000200000000000000000000000020000000000000000000020000000000000000000000000000000000020000200000000000000000000000020000000000000000002000000000000000020000000000000020022000";
	public static boolean zle = false;
	public static int pocetVyhodenych = 0;
	public static double h = 0;
	
	public static void main(String[] args) throws IOException {
		int pocetDobrych = 0;
		int pocetDobrychSkip = 0;
		int pocetZlych = 0;
		int pocetZlychSkip = 0;
		int poziciaVCykle = 0;
		double[] positive;
		positive = new double[VELKOST_CYKLU + REZERVA];
		double[] positive2;
		positive2 = new double[VELKOST_CYKLU + REZERVA];
		double[] negative;
		negative = new double[VELKOST_CYKLU + REZERVA];
		int a = 0;
		double[] j;
		j = new double[VELKOST_CYKLU + REZERVA];

		BufferedReader reader;

		reader = new BufferedReader(new FileReader("/Users/janme/Documents/a25-n50-BpBe,o6,1-20Hz.txt"));
		
		for (int d = 0; d < RIADOK_OD; d++) {
			reader.readLine();
		}
		double[] cyklus;
		cyklus = new double[VELKOST_CYKLU + REZERVA]; 
		double[][] cyklus3 = new double[VELKOST_CYKLU + REZERVA][VELKOST_CYKLU + REZERVA]; 
		double[] cyklus4 = new double[VELKOST_CYKLU + REZERVA];
		PrintWriter pw2 = new PrintWriter(new FileWriter("cyklus.txt"));
		
		int cisloRiadku = 0;
		main: for (int cisloCyklu = 0; cisloCyklu < PORADIE_TONOV.length(); cisloCyklu++) {
			double z = odchylka * (cisloCyklu+1);
			z = Math.round(z);
			System.out.println("cyklus: " + cisloCyklu + ", z: " + z + ", cisloRiadku: " + cisloRiadku);
			for (int poradieVCykle = 0; cisloRiadku < VELKOST_CYKLU * (cisloCyklu+1) + z; poradieVCykle++) {
				String line = reader.readLine();
				cisloRiadku++;
				if (line == null) {
					System.out.println("dosli riadky!!!");
					break main;
				}
				line = line.replace(',', '.');
				String stringEeg = line.split("\t")[1];
				double eeg = Double.valueOf(stringEeg);
				cyklus[poradieVCykle] = eeg;
				cyklus4[poradieVCykle] = eeg;
				
				
			}
			
			char x = ton(cisloCyklu);
			kontroluj(cyklus);
			pw2.println(h);
			h = 0;
			if (x == '2'){
				pocetDobrych++;
				for (int t = 0; t < VELKOST_CYKLU; t++){
				cyklus3[pocetDobrych - 1][t] = cyklus4[t];
				}
			} else if (x == '0'){
				pocetZlych++;
			}
			if (x == '2' && zle == false) {
				pocetDobrychSkip++;
				for (int f = 0; f < VELKOST_CYKLU; f++){
				positive[f] = positive[f] + cyklus[f];
				}
			} else if (x == '0' && zle == false) {
				pocetZlychSkip++;
				for (int f = 0; f < VELKOST_CYKLU; f++){
					negative[f] = negative[f] + cyklus[f];
				}

			}
		}
		pw2.close();


	

		PrintWriter pw = new PrintWriter(new FileWriter("subor.txt"));
		pw.println("cas\tpos\tneg");
		for (int b = 0; b < VELKOST_CYKLU; b++) {
			double priemerDobrych = positive[b] / pocetDobrych;
			double priemerZlych = negative[b] / pocetZlych;
			
			pw.println(b/2.0 + "\t" + priemerDobrych + "\t" + priemerZlych);	
		}
		pw.close();
		PrintWriter pw3 = new PrintWriter(new FileWriter("subor2.txt"));
		pw3.print("cas");
		for (int x = 0; x < pocetDobrych; x++){
			pw3.print("\t" + "pos" + x);
		}
		
		pw3.println();
		for (int b = 0; b <= VELKOST_CYKLU; b++) {
			pw3.print(b/2.0);	
			for (int x = 0; x < pocetDobrych; x++){
				pw3.print("\t" + cyklus3[x][b]);
			}
			pw3.println();
		}
		System.out.println("pocet dobrych = " + pocetDobrych + ", pocet zlych = " + pocetZlych);
		System.out.println("pocet vyhodenych- " + pocetVyhodenych);
		System.out.println("pocet dobrych so skipovanym-" + pocetDobrychSkip);
		System.out.println("pocet zlych so skipovanym-" + pocetZlychSkip);
	}

	public static char ton(int i) {
		char cislo = PORADIE_TONOV.charAt(i);
		return cislo;
	}
	public static void kontroluj(double cyklus[]) {
		zle = false;
		for (int e = 0; e < 1500; e++){
			double riadok = Math.abs(cyklus[e]);
			if (riadok > h) {
				h = riadok;
			}
			if (riadok < -200.0 || riadok > 200.0){
				zle = true;
				pocetVyhodenych++;
				break;
			}
		}
	}
}