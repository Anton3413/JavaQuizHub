package com.example.javaquizhub.event.event;

import com.example.javaquizhub.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String contextPathAppUrl;

    private final User user;

    public OnRegistrationCompleteEvent(User user, String contextPathAppUrl){
        super(user);
        this.user = user;
        this.contextPathAppUrl = contextPathAppUrl;
    }
}
