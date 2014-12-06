package com.kodility.dao.quote;

import com.kodility.dao.AbstractHibernateDao;
import com.kodility.domain.quote.Quote;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao extends AbstractHibernateDao<Quote> {

    protected QuoteDao() {
        super(Quote.class);
    }

}
