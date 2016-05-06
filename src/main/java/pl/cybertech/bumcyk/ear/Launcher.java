package pl.cybertech.bumcyk.ear;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {
   
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        EarService service = context.getBean(EarService.class);
        service.startHearing();                
        System.exit(0);
    }
}
