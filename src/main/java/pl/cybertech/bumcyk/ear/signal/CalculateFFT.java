package pl.cybertech.bumcyk.ear.signal;


import pl.cybertech.bumcyk.ear.signal.algorithms.Complex;
import pl.cybertech.bumcyk.ear.signal.algorithms.FFT;

public class CalculateFFT {
    
//    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_STEREO;
//    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    
    public double[] calculateFFT(byte[] signal)
    {           
        final int mNumberOfFFTPoints = 1024;
        double mMaxFFTSample;
        double temp;
        Complex[] y;
        Complex[] complexSignal = new Complex[mNumberOfFFTPoints];
        double[] absSignal = new double[mNumberOfFFTPoints/2];

        double scaleX = 32768.0F;
        for(int i = 0; i < mNumberOfFFTPoints; i++){
            temp = (double)((signal[2*i] & 0xFF) | (signal[2*i+1] << 8)) / scaleX;
            complexSignal[i] = new Complex(temp,0.0);
        }

        y = FFT.fft(complexSignal);

        mMaxFFTSample = 0.0;
        int mPeakPos = 0;
        for(int i = 0; i < (mNumberOfFFTPoints/2); i++)
        {
             absSignal[i] = Math.sqrt(Math.pow(y[i].re(), 2) + Math.pow(y[i].im(), 2));
             if(absSignal[i] > mMaxFFTSample)
             {
                 mMaxFFTSample = absSignal[i];
                 mPeakPos = i;
             } 
        }

        return absSignal;

    }
}
