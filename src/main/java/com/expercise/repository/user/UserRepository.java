package com.expercise.repository.user;

import com.expercise.repository.BaseRepository;
import com.expercise.domain.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<User> {

    protected UserRepository() {
        super(User.class);
    }

    public User findByEmail(String email) {
        return findOneBy("email", email);
    }

}
