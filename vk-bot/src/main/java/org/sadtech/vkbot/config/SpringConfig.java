package org.sadtech.vkbot.config;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.responses.GetLongPollServerResponse;
import org.sadtech.vkbot.ResponseData;
import org.sadtech.vkbot.TestLogic;
import org.sadtech.vkbot.VkOpenMethod;
import org.sadtech.vkbot.controller.MessagesListener;
import org.sadtech.vkbot.service.UserService;
import org.sadtech.vkbot.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan("org.sadtech.vkbot")
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

    @Value("${vk.service.token}")
    private String serviceToken;

    @Bean
    public TransportClient transportClient() {
        return HttpTransportClient.getInstance();
    }

    @Bean
    public VkApiClient vkApiClient() {
        VkApiClient vk = new VkApiClient(transportClient());
        return vk;
    }

    @Bean
    public GroupActor groupActor() {
        GroupActor actor = new GroupActor(new Integer(groupId), token);
        return actor;
    }

    @Bean
    public ServiceActor serviceActor() {
        ServiceActor actor = new ServiceActor(new Integer(appID), serviceToken);
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
    public MessagesListener messagesListener() {
        return new MessagesListener();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public ResponseData responseData() {
        return new ResponseData();
    }

    @Bean
    public TestLogic testLogic() {
        return new TestLogic(responseData());
    }

    @Bean
    public VkOpenMethod vkOpenMethod() {
        return new VkOpenMethod(vkApiClient(), groupActor());
    }
}
