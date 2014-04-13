package com.ufukuzun.kodility.dao.user;

import com.ufukuzun.kodility.domain.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<User, String> {

    User findByEmail(String email);

}
