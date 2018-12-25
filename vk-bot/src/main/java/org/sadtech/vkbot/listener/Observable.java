package org.sadtech.vkbot.listener;

public interface Observable {
    void registerObserver(Observer o);

    //    void removeObserver(Observer o);
    void notifyObservers();

    void packaging() throws Exception;
}
