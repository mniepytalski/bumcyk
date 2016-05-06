package pl.cybertech.bumcyk.ear;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EarService {

    private static final Logger LOG = LoggerFactory.getLogger(EarService.class);
    
    @Autowired
    private SoundCapture soundCapture;    

    @Autowired
    private Display display;    
    
    public void startHearing() {
        
        LOG.info("Ear start hearing ...");

        display.show();
        
        soundCapture.capture();
    }
}
