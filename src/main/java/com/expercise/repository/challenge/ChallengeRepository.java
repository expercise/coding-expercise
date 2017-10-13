package com.expercise.repository.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.theme.Theme;
import com.expercise.domain.user.User;
import com.expercise.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeRepository extends BaseRepository<Challenge> {

    List<Challenge> findByApprovedIsTrue();

    List<Challenge> findByUserOrderByCreateDateDesc(User user);

    List<Challenge> findAllByOrderByCreateDateDesc();

    List<Challenge> findByApprovedIsTrueAndUser(User user);

    Long countByApprovedIsTrueAndLevelTheme(Theme theme);

    @Query("select c from Challenge c left join c.level l where c.approved = true and (l is null or l.theme is null)")
    List<Challenge> findNotThemedApprovedChallenges();

    @Query("select count(c) from Challenge c left join c.level l where c.approved = true and (l is null or l.theme is null)")
    Long countNotThemedApprovedChallenges();

}
