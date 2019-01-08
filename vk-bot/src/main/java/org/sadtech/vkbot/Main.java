package org.sadtech.vkbot;

import lombok.extern.log4j.Log4j;
import org.sadtech.consultant.processing.MessageHandler;
import org.sadtech.vkbot.config.SpringConfigVk;
import org.sadtech.vkbot.listener.EventListenable;
import org.sadtech.vkbot.handlers.Handled;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Log4j
public class Main {

    public static void main(String[] args) throws Exception {
       Main main = new Main();
       main.run();
    }

    public void run() throws Exception {
        log.info("\n\n\n\n=== Запуск прогарммы ===\n\n");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigVk.class);
        EventListenable eventListener = context.getBean(EventListenable.class);
        eventListener.listen();
        Handled dispetcherHandler = context.getBean(Handled.class);
        dispetcherHandler.sortAndSend();
//        MessageHandler messageHandler = context.getBean(MessageHandler.class);
//        messageHandler.processing();

        log.info("\n\n=== Конец программы ===\n\n");
    }
}
