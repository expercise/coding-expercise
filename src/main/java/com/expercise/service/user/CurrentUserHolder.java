package com.expercise.service.user;

import com.expercise.domain.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CurrentUserHolder {

    private final ThreadLocal<User> CURRENT_USER_THREAD_LOCAL = new ThreadLocal<>();

    public boolean hasNotCurrentUser() {
        return CURRENT_USER_THREAD_LOCAL.get() == null;
    }

    public User getCurrentUser() {
        return CURRENT_USER_THREAD_LOCAL.get();
    }

    public void setCurrentUser(User user) {
        CURRENT_USER_THREAD_LOCAL.set(user);
    }

}
