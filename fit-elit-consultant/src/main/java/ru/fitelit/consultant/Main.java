package ru.fitelit.consultant;

import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.processing.MessageHandler;
import org.sadtech.vkbot.core.handlers.Handled;
import org.sadtech.vkbot.core.listener.EventListenable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.fitelit.consultant.config.SpringConfigVk;

@Log4j
public class Main {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.run();
    }

    public void run() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigVk.class);
        EventListenable eventListener = context.getBean(EventListenable.class);
        eventListener.listen();
        Handled dispatcherHandler = context.getBean(Handled.class);
        dispatcherHandler.sortAndSend();
        MessageHandler messageHandler = context.getBean(MessageHandler.class);
        messageHandler.processing();
    }
}
