package com.ufukuzun.kodility.service.challenge;

import com.ufukuzun.kodility.dao.challenge.SolutionDao;
import com.ufukuzun.kodility.domain.challenge.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolutionService {

    @Autowired
    private SolutionDao solutionDao;

    @Transactional
    public void saveSolution(Solution solution) {
        solutionDao.save(solution);
    }

}