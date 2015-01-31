package com.expercise.dao.quote;

import com.expercise.dao.AbstractHibernateDao;
import com.expercise.domain.quote.Quote;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao extends AbstractHibernateDao<Quote> {

    protected QuoteDao() {
        super(Quote.class);
    }

}
