package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.dao.AbstractHibernateDao;
import com.ufukuzun.kodility.domain.challenge.Solution;
import org.springframework.stereotype.Repository;

@Repository
public class SolutionDao extends AbstractHibernateDao<Solution> {

    protected SolutionDao() {
        super(Solution.class);
    }

}
