package org.sadtech.vkbot.controller;

import com.vk.api.sdk.actions.LongPoll;
import com.vk.api.sdk.callback.longpoll.queries.GetLongPollEventsQuery;
import com.vk.api.sdk.callback.longpoll.responses.GetLongPollEventsResponse;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetLongPollServerResponse;
import lombok.extern.log4j.Log4j;
import org.sadtech.vkbot.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j
public class MessagesListener extends Thread{

    @Autowired
    private VkApiClient vk;

    @Autowired
    private GroupActor actor;

    @Autowired
    private ResponseData responseData;

    public void run() {
        GetLongPollServerResponse serverResponse = null;
        try {
            serverResponse = vk.groups().getLongPollServer(actor).execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        String key = serverResponse.getServer();
        String server = serverResponse.getKey();
        Integer ts = serverResponse.getTs();

        LongPoll longPoll = new LongPoll(vk);
        GetLongPollEventsQuery longPollEventsQuery = longPoll.getEvents(server, key, ts).waitTime(20);

        GetLongPollEventsResponse eventsResponse = null;
        do {
            try {
                eventsResponse = longPollEventsQuery.execute();
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
            if (eventsResponse.getUpdates().toArray().length != 0) {

                responseData.setJsonObjects(eventsResponse.getUpdates());

            }
            longPollEventsQuery = longPoll.getEvents(server, key, eventsResponse.getTs()).waitTime(20);
        } while (true);
    }

}
