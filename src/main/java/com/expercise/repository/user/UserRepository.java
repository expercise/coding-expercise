package com.expercise.repository.user;

import com.expercise.domain.user.User;
import com.expercise.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends BaseRepository<User> {

    User findByEmail(String email);

    @Query("select u.id from User u")
    List<Long> findAllIds();

}
