package com.expercise.testutils.builder;

import com.expercise.domain.user.User;
import com.expercise.enums.Lingo;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.enums.UserRole;
import org.apache.commons.lang3.RandomStringUtils;

public class UserBuilder extends BaseEntityBuilder<User, UserBuilder> {

    private String firstName = RandomStringUtils.random(5);

    private String lastName = RandomStringUtils.random(5);

    private String email = RandomStringUtils.random(5);

    private String password = RandomStringUtils.random(5);

    private UserRole userRole = UserRole.User;

    private Lingo lingo = Lingo.English;

    private ProgrammingLanguage programmingLanguage;

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
        user.setProgrammingLanguage(programmingLanguage);
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

    public UserBuilder programmingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
        return this;
    }

}
