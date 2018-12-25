//package org.sadtech.vkbot.config;
//
//import com.vk.api.sdk.exceptions.ApiException;
//import com.vk.api.sdk.exceptions.ClientException;
//import org.sadtech.vkbot.listener.EventListenable;
//import org.sadtech.vkbot.listener.Observable;
//import org.sadtech.vkbot.listener.impl.EventListenerVk;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//
//@Configuration
//public class WebConfig implements WebApplicationInitializer {
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.register(SpringConfigVk.class, DataConfig.class, WebConfig.class);
//
////        context.setServletContext(servletContext);
////
////
////        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
////        dispatcher.setLoadOnStartup(1);
////        dispatcher.addMapping("/");
//    }
//}
