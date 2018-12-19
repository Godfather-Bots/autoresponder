package org.sadtech.vkbot;

public interface Observable {
    void registerObserver(org.sadtech.vkbot.Observer o);
//    void removeObserver(Observer o);
    void notifyObservers();
}
