package org.sadtech.vkbot.config;

import org.sadtech.vkbot.listener.EventListenable;
import org.sadtech.vkbot.listener.Observable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestMain {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigVk.class);
        context.register(DataConfig.class);
        EventListenable eventListener = context.getBean(EventListenable.class);
        eventListener.listen();
        Observable dispetcherHandler = context.getBean(Observable.class);
        dispetcherHandler.packaging();
    }
}
