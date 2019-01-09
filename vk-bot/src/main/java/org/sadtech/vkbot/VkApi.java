package org.sadtech.vkbot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j
@Component
public class VkApi {

    private static VkApiClient vk;
    private static GroupActor actor;

    public VkApi(VkApiClient vk, GroupActor actor) {
        this.vk = vk;
        this.actor = actor;
    }

    public static void sendMessage(Integer id, String text) {
        try {
            vk.messages().send(actor).userId(id).message(text).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public static UserXtrCounters getUserVk(Integer id) {
        List<UserXtrCounters> temp = null;
        try {
            temp = vk.users().get(actor).userIds(String.valueOf(id)).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return temp.get(0);
    }

}
