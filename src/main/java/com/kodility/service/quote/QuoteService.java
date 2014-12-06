package com.kodility.service.quote;

import com.kodility.dao.quote.QuoteDao;
import com.kodility.domain.quote.Quote;
import com.kodility.utils.collection.RandomElement;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

// TODO ufuk: create a generic cache mechanism
@Service
public class QuoteService {

    private static final List<Quote> QUOTES = new ArrayList<>();

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private QuoteDao quoteDao;

    @PostConstruct
    public void init() {
        populateQuoteList();
    }

    public Quote randomQuote() {
        return RandomElement.from(QUOTES);
    }

    private void populateQuoteList() {
        new TransactionTemplate(transactionManager).execute(status -> {
            List<Quote> quotes = quoteDao.findAll();
            quotes.forEach(q -> Hibernate.initialize(q.getQuoteInMultiLingo()));
            QUOTES.addAll(quotes);
            return null;
        });
    }

}
