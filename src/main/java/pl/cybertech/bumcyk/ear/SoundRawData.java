package pl.cybertech.bumcyk.ear;

import org.springframework.stereotype.Component;

@Component
public class SoundRawData extends SoundData {

    byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
