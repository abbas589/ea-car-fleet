package edu.miu.carfleet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author bazz
 * Apr 25 2023
 * 16:50
 */
@Component
public class EventListener {


//    Logger logger = LoggerFactory.getLogger(EventListener.class);
    @org.springframework.context.event.EventListener
    public void sendNotification(NotificationEvent event){
        System.out.println("SENDING EMAIL TO FLEET MANAGER ======= {} ===============" +event.getMessage());
    }


}
