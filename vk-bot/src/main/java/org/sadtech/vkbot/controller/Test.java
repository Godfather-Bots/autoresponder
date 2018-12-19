package org.sadtech.consultant.controller;

import com.vk.api.sdk.actions.LongPoll;
import com.vk.api.sdk.callback.longpoll.queries.GetLongPollEventsQuery;
import com.vk.api.sdk.callback.longpoll.responses.GetLongPollEventsResponse;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetLongPollServerResponse;
import org.json.JSONObject;
import org.sadtech.vkbot.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;

public class Test extends Thread {

    @Autowired
    private VkApiClient vk;

    @Autowired
    private GroupActor actor;

    @Autowired
    private ResponseData responseData;

    private GetLongPollEventsResponse getLongPollEventsResponse = null;

    public void run() {
        GetLongPollServerResponse getResponse = null;
        try {
            getResponse = vk.groups().getLongPollServer(actor).execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        JSONObject jObject = new JSONObject(getResponse); // json
//        System.out.println(getResponse);
        String key = jObject.getString("key"); // get the name from data.
        String server = jObject.getString("server");
        Integer ts = jObject.getInt("ts");
        LongPoll longPoll = new LongPoll(vk);
        GetLongPollEventsQuery getLongPollEventsQuery = longPoll.getEvents(server, key, ts).waitTime(5);
//        Gson gson = new Gson();

        while (true) {
            try {
                getLongPollEventsResponse = getLongPollEventsQuery.execute();
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
//            System.out.println(getLongPollEventsResponse);
            if (getLongPollEventsResponse.getUpdates().toArray().length != 0) {
                responseData.setJsonObjects(getLongPollEventsResponse.getUpdates());
//                JsonObject updates = getLongPollEventsResponse.getUpdates().get(0);
//                JsonObject object = updates.getAsJsonObject("object");
//                Message messages = gson.fromJson(object, Message.class);
            }
            getLongPollEventsQuery = longPoll.getEvents(server, key, getLongPollEventsResponse.getTs()).waitTime(5);
        }
    }

}
