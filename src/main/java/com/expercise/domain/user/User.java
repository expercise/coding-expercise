package com.expercise.domain.user;

import com.expercise.domain.BaseEntity;
import com.expercise.enums.Lingo;
import com.expercise.enums.ProgrammingLanguage;
import com.expercise.enums.SocialSignInProvider;
import com.expercise.enums.UserRole;
import com.expercise.utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "SEQ_USERS")
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.User;

    @Enumerated(EnumType.STRING)
    private Lingo lingo = Lingo.English;

    @Enumerated(EnumType.STRING)
    private ProgrammingLanguage programmingLanguage;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserConnection> userConnections = new ArrayList<>();

    private String avatar;

    private String socialImageUrl;

    public String getBookmarkableUrl() {
        return String.format("/user/%s/%s", getId().toString(), UrlUtils.makeBookmarkable(getFullName()));
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
        String delimiter = " ";
        if (StringUtils.isAnyBlank(firstName, lastName)) {
            delimiter = "";
        }
        return String.join(delimiter, StringUtils.defaultString(firstName), StringUtils.defaultString(lastName));
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

    public List<UserConnection> getUserConnections() {
        return userConnections;
    }

    public void setUserConnections(List<UserConnection> userConnections) {
        this.userConnections = userConnections;
    }

    public void addUserConnection(UserConnection userConnection) {
        this.userConnections.add(userConnection);
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

    public String getSocialImageUrl() {
        return socialImageUrl;
    }

    public void setSocialImageUrl(String socialImageUrl) {
        this.socialImageUrl = socialImageUrl;
    }

    public boolean hasAvatar() {
        return StringUtils.isNotBlank(avatar);
    }

    public boolean hasSocialImageUrl() {
        return StringUtils.isNotBlank(socialImageUrl);
    }

    public boolean hasTwitterConnection() {
        return getTwitterConnection() != null;
    }

    public UserConnection getTwitterConnection() {
        return getSocialConnection(SocialSignInProvider.Twitter);
    }

    private UserConnection getSocialConnection(SocialSignInProvider provider) {
        return userConnections.stream()
                .filter(uc -> provider == SocialSignInProvider.getForProviderId(uc.getProviderId()).orElse(null))
                .findFirst().orElse(null);
    }

    public boolean isAdmin() {
        return UserRole.Admin == userRole;
    }

    public boolean isNotAdmin() {
        return !isAdmin();
    }

}
