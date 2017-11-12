package com.expercise.repository.challenge;

import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.user.User;
import com.expercise.repository.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeRepository extends BaseRepository<Challenge> {

    List<Challenge> findByApprovedIsTrue();

    List<Challenge> findByUserOrderByCreateDateDesc(User user);

    List<Challenge> findAllByOrderByCreateDateDesc();

    List<Challenge> findByApprovedIsTrueAndUser(User user);

    @Query("select c.id, c.tags from Challenge c where c.approved = true order by c.createDate desc")
    List<Object[]> getChallengeWithIdAndTags(Pageable pageable);

}
