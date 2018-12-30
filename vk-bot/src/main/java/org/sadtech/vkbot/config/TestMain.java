package org.sadtech.vkbot.config;

import lombok.extern.log4j.Log4j;
import org.sadtech.vkbot.listener.EventListenable;
import org.sadtech.vkbot.listener.Observable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Log4j
public class TestMain {
    public static void main(String[] args) throws Exception {
        log.info("\n\n\n\n=== Запуск прогарммы ===\n\n");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigVk.class);
        context.register(DataConfig.class);
        EventListenable eventListener = context.getBean(EventListenable.class);
        eventListener.listen();
        Observable dispetcherHandler = context.getBean(Observable.class);
        dispetcherHandler.packaging();

        log.info("\n\n=== Конец программы ===\n\n");
    }
}
