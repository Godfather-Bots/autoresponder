package org.sadtech.vkbot.listener.handlers.impl;

import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j;
import org.sadtech.vkbot.listener.Observable;
import org.sadtech.vkbot.listener.Observer;
import org.sadtech.vkbot.listener.data.ResponsibleData;
import org.sadtech.vkbot.listener.handlers.Handled;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j
@Component
public class DispetcherHandlerVk implements Observable, Handled {

    private ResponsibleData date;
    private List<Observer> observers = new ArrayList<Observer>();
    private JsonObject event;

    public DispetcherHandlerVk(ResponsibleData date) {
        this.date = date;
    }

    @Async
    public void sortAndSend() {
        while (true) {
            if (date.getJsonObjects().peek() != null) {
                event = date.getJsonObjects().poll();
                notifyObservers();
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
            observer.update(event);
        }
    }
}
