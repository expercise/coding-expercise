package com.expercise.domain.user;

import com.expercise.domain.AbstractEntity;
import com.expercise.enums.Lingo;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.enums.UserRole;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class User extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.User;

    @Enumerated(EnumType.STRING)
    private Lingo lingo = Lingo.English;

    @Enumerated(EnumType.STRING)
    private ProgrammingLanguage programmingLanguage;

    private String avatar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Lingo getLingo() {
        return lingo;
    }

    public void setLingo(Lingo lingo) {
        this.lingo = lingo;
    }

    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean hasAvatar() {
        return StringUtils.isNotBlank(avatar);
    }

    public boolean isAdmin() {
        return UserRole.Admin == userRole;
    }

    public boolean isNotAdmin() {
        return !isAdmin();
    }

}
