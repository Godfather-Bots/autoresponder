package org.sadtech.vkbot.core.handlers.impl;

import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j;
import org.sadtech.vkbot.core.handlers.Handled;
import org.sadtech.vkbot.core.listener.Observable;
import org.sadtech.vkbot.core.data.ResponsibleData;
import org.sadtech.vkbot.core.listener.Observer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j
@Component("dispatcherHandler")
public class DispatcherHandlerVk implements Observable, Handled {

    private ResponsibleData date;
    private List<Observer> observers = new ArrayList<>();
    private JsonObject event;

    public DispatcherHandlerVk(ResponsibleData date) {
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
