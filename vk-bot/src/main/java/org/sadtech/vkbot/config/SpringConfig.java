package org.sadtech.vkbot.config;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.responses.GetLongPollServerResponse;
import org.sadtech.vkbot.ResponseData;
import org.sadtech.consultant.controller.Test;
import org.sadtech.vkbot.TestLogic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class SpringConfig {

    @Value("${vk.groupID}")
    private String groupId;

    @Value("${vk.appID}")
    private String appID;

    @Value("${vk.secretKey}")
    private String secretKey;

    @Value("${vk.redirectURL}")
    private String redirectURL;

    @Value("${vk.code}")
    private String code;

    @Value("${vk.token}")
    private String token;

    @Bean
    public TransportClient transportClient() {
        return HttpTransportClient.getInstance();
    }

    @Bean
    public VkApiClient vkApiClient() {
        VkApiClient vk = new VkApiClient(transportClient());
//        System.out.println("vk: " + vk);
        return vk;
    }

    @Bean
    public GroupActor groupActor() {
        GroupActor actor = new GroupActor(new Integer(groupId), token);
//        System.out.println("actor: " + actor);
        return actor;
    }

    @Bean
    public GetLongPollServerResponse GroupsGetLongPollServerQuery() {
        GetLongPollServerResponse getLongPollServerResponse = null;
        try {
            getLongPollServerResponse = vkApiClient().groups().getLongPollServer(groupActor()).execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
//        System.out.println("server: "+getLongPollServerResponse);
        return getLongPollServerResponse;
    }

    @Bean(initMethod = "start")
    public Test test() {
        return new Test();
    }

    @Bean
    public ResponseData responseData() {
        return new ResponseData();
    }

    @Bean
    public TestLogic testLogic() {
        return new TestLogic(responseData());
    }
}
