package generator;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Sinus {
    //
    protected static final int SAMPLE_RATE = 16 * 1024;
    private SourceDataLine line;

    public static byte[] createSinWaveBuffer(double freq, int ms) {
        int samples = (int)(((double) ms * SAMPLE_RATE) / 1000.0);
        byte[] output = new byte[samples];
        //
        double period = (double)SAMPLE_RATE / freq;
        for (int i = 0; i < output.length; i++) {
            double angle = 2.0 * Math.PI * i / period;
            output[i] = (byte)(Math.sin(angle) * 127f);  }

        return output;
    }
    
    public void open() throws LineUnavailableException {
    }
    
    public void playTone(double freq, int ms) throws LineUnavailableException {
    	final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
        line = AudioSystem.getSourceDataLine(af);
        line.open(af, SAMPLE_RATE);
    	line.start();
    	byte [] toneBuffer = createSinWaveBuffer(freq, ms);
    	int written = line.write(toneBuffer, 0, toneBuffer.length);
        line.drain();
    	line.close();
    }
    
    public void close() {
        line.drain();
    	line.close();
    }




   

}

