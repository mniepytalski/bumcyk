package pl.cybertech.bumcyk.ear;

import org.springframework.stereotype.Component;

@Component
public class SoundFFTData {

    double[] data;

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }
}
