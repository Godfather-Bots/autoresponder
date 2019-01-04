package org.sadtech.vkbot;

import com.google.gson.Gson;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.User;
import com.vk.api.sdk.objects.users.UserFull;
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

    public static String getUserName(Integer id) {
        List<UserXtrCounters> user = null;
        UserFull userFull = null;
        try {
            user = vk.users().get(actor).userIds(String.valueOf(id)).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return user.get(0).getLastName() + " " + user.get(0).getFirstName();
    }

}
