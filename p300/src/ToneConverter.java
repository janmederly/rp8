import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class ToneConverter {
	public static void main(String[] args) throws IOException {
		BufferedReader reader;
		reader = new BufferedReader(new FileReader("/Users/janme/Documents/a21-tones.txt"));
		String tones = "";
		for (;;) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			int i = line.indexOf(": ");
			if (i >= 0) {
				tones = tones + line.substring(i+2);
			}
		}
		reader.close();
		System.out.println(tones);
	}
}
