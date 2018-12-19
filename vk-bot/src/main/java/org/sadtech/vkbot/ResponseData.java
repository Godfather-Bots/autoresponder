package org.sadtech.vkbot;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class ResponseData implements Observable {

    private List<Observer> observers = new ArrayList<Observer>();
    private List<JsonObject> jsonObjects = new ArrayList<JsonObject>();

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer:observers) {
            observer.update(jsonObjects);
        }
    }

    public void setJsonObjects(List<JsonObject> jsonObjects) {
        this.jsonObjects = jsonObjects;
        notifyObservers();
    }
}
