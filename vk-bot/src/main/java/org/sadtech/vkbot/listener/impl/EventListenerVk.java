package org.sadtech.vkbot.listener.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.actions.LongPoll;
import com.vk.api.sdk.callback.longpoll.queries.GetLongPollEventsQuery;
import com.vk.api.sdk.callback.longpoll.responses.GetLongPollEventsResponse;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetLongPollServerResponse;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.extern.log4j.Log4j;
import org.json.JSONObject;
import org.sadtech.vkbot.listener.EventListenable;
import org.sadtech.vkbot.listener.data.ResponsibleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class EventListenerVk implements EventListenable {

    private VkApiClient vk;
    private GroupActor actor;
    private ResponsibleData responseData;

    private GetLongPollEventsQuery longPollEventsQuery;
    private LongPoll longPoll;
    private GetLongPollServerResponse server;

    public EventListenerVk(VkApiClient vk, GroupActor actor, ResponsibleData responseData) {
        this.vk = vk;
        this.actor = actor;
        this.responseData = responseData;
        longPoll = new LongPoll(vk);
    }

    private void initServer() throws ClientException, ApiException {
        server = vk.groups().getLongPollServer(actor).execute();
        String key = server.getKey();
        String serverUrl = server.getServer();
        Integer ts = server.getTs();
        longPoll = new LongPoll(vk);
        longPollEventsQuery = longPoll.getEvents(serverUrl, key, ts).waitTime(20);
    }

    @Async
    public void listen() throws Exception {
        initServer();
        do {
            GetLongPollEventsResponse eventsResponse;
            eventsResponse = longPollEventsQuery.execute();
            if (eventsResponse.getUpdates().toArray().length != 0) {
                responseData.add(eventsResponse.getUpdates().get(0));
                log.info(eventsResponse.getUpdates());
                String test = "{\"one_time\":false,\"buttons\":[[{\"action\":{\"type\":\"text\",\"payload\":\"{\\\"button\\\": \\\"1\\\"}\",\"label\":\"Red2\"},\"color\":\"negative\"},{\"action\":{\"type\":\"text\",\"payload\":\"{\\\"button\\\": \\\"2\\\"}\",\"label\":\"Green\"},\"color\":\"positive\"}],[{\"action\":{\"type\":\"text\",\"payload\":\"{\\\"button\\\": \\\"3\\\"}\",\"label\":\"White\"},\"color\":\"default\"},{\"action\":{\"type\":\"text\",\"payload\":\"{\\\"button\\\": \\\"4\\\"}\",\"label\":\"Blue\"},\"color\":\"primary\"}]]}";
                vk.messages().send(actor).peerId(244319573).keyboard(test).message("Сообщение получено").execute();

            }
            longPollEventsQuery = longPoll.getEvents(server.getServer(), server.getKey(), eventsResponse.getTs()).waitTime(20);
        } while (true);
    }



}
