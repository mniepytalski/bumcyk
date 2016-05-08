package pl.cybertech.bumcyk.ear;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {
   
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        EarService service = context.getBean(EarService.class);
        service.startHearing();
        System.exit(0);
    }
}
