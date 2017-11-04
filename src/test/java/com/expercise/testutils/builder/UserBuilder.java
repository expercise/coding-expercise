package com.expercise.testutils.builder;

import com.expercise.domain.user.User;
import com.expercise.enums.Lingo;
import com.expercise.enums.UserRole;
import com.expercise.utils.TextUtils;

public class UserBuilder extends BaseEntityBuilder<User, UserBuilder> {

    private String firstName = TextUtils.randomAlphabetic(5);

    private String lastName = TextUtils.randomAlphabetic(5);

    private String email = TextUtils.randomAlphabetic(5);

    private String password = TextUtils.randomAlphabetic(5);

    private UserRole userRole = UserRole.User;

    private Lingo lingo = Lingo.English;

    @Override
    protected User doBuild() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(password);
        user.setUserRole(userRole);
        user.setLingo(lingo);
        user.setEmail(email);
        return user;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder userRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public UserBuilder lingo(Lingo lingo) {
        this.lingo = lingo;
        return this;
    }

}
