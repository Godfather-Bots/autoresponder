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
import org.json.JSONObject;
import org.sadtech.vkbot.ResponseData;
import org.sadtech.vkbot.dao.User;
import org.sadtech.vkbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Log4j
public class Test extends Thread {

    @Autowired
    private VkApiClient vk;

    @Autowired
    private GroupActor actor;

    @Autowired
    private ResponseData responseData;

    @Resource
    private UserService userService;

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
        log.info("Test");
        JSONObject jObject = new JSONObject(getResponse); // json
//        System.out.println(getResponse);
        String key = jObject.getString("key"); // get the name from data.
        String server = jObject.getString("server");
        Integer ts = jObject.getInt("ts");
        LongPoll longPoll = new LongPoll(vk);
        GetLongPollEventsQuery getLongPollEventsQuery = longPoll.getEvents(server, key, ts).waitTime(5);
//        Gson gson = new Gson();

        User user = new User();
        user.setId(1);
        user.setName("Test");
        userService.addUser(user);

        while (true) {
            try {
                getLongPollEventsResponse = getLongPollEventsQuery.execute();
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
            System.out.println(getLongPollEventsResponse);
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
