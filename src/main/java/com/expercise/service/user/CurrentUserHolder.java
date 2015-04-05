package com.expercise.service.user;

import com.expercise.domain.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CurrentUserHolder {

    private User currentUser;

    public boolean hasNotCurrentUser() {
        return currentUser == null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
