package org.sadtech.vkbot.core.handlers;

import org.sadtech.vkbot.core.listener.Observer;

public interface Handled {

    void sortAndSend() throws Exception;
    void registerObserver(Observer o);
}
