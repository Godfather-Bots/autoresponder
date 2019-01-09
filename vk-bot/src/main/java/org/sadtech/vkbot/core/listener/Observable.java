package org.sadtech.vkbot.core.listener;

public interface Observable {

    //    void removeObserver(Observer o);
    void notifyObservers();

}
