package com.ufukuzun.kodility.testutils.builder;

import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.enums.ProgrammingLanguage;
import com.ufukuzun.kodility.enums.UserRole;

public class UserBuilder {

    private Long id;
    private String firstName;
    private String lastName;

    private String email;
    private String password;

    private UserRole userRole = UserRole.User;

    private Lingo lingo = Lingo.English;

    private ProgrammingLanguage programmingLanguage = null;

    public User build() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(password);
        user.setUserRole(userRole);
        user.setLingo(lingo);
        user.setProgrammingLanguage(programmingLanguage);
        return user;
    }

    public UserBuilder id(Long id) {
        this.id = id;
        return this;
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
