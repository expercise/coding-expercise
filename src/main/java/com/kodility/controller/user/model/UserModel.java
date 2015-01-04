package com.kodility.controller.user.model;

import com.kodility.domain.user.User;
import com.kodility.enums.Lingo;
import com.kodility.enums.ProgrammingLanguage;
import com.kodility.utils.validation.UniqueEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class UserModel {

    @Size(min = 2, max = 15)
    private String firstName;

    @Size(min = 2, max = 15)
    private String lastName;

    @NotEmpty
    @Email
    @UniqueEmail
    private String email;

    @Valid
    private PasswordModel passwordModel;

    private Lingo lingo = Lingo.Turkish;

    private ProgrammingLanguage programmingLanguage;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PasswordModel getPasswordModel() {
        return passwordModel;
    }

    public void setPasswordModel(PasswordModel passwordModel) {
        this.passwordModel = passwordModel;
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

    public User createUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(getPasswordModel().getPassword());
        user.setProgrammingLanguage(programmingLanguage);
        user.setLingo(lingo);
        return user;
    }

}
