package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.domain.challenge.Challenge;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChallengeDao extends PagingAndSortingRepository<Challenge, String> {

    Challenge findById(String id);

}
