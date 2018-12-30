package org.sadtech.vkbot.listener.handlers;

import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j;
import org.sadtech.vkbot.listener.Observable;
import org.sadtech.vkbot.listener.Observer;
import org.sadtech.vkbot.listener.data.ResponsibleData;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j
@Component
public class DispetcherHandler implements Observable {

    private ResponsibleData date;
    private List<Observer> observers = new ArrayList<Observer>();

    public DispetcherHandler(ResponsibleData date) {
        this.date = date;
    }

    @Async
    public void packaging() {
        while (true) {
            if (date.getJsonObjects().peek() != null) {
                log.info(date.getJsonObjects().poll());
            }
        }
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            //observer.update();
        }
    }
}
