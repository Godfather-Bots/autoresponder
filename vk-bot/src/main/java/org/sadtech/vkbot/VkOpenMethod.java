package org.sadtech.vkbot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Log4j
@Component
public class VkOpenMethod {

    private VkApiClient vk;
    private GroupActor actor;

    public VkOpenMethod(VkApiClient vk, GroupActor actor) {
        this.vk = vk;
        this.actor = actor;
    }


}
