package com.expercise.service.quote;

import com.expercise.caching.Caching;
import com.expercise.domain.quote.Quote;
import com.expercise.repository.quote.QuoteRepository;
import com.expercise.utils.collection.RandomElement;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteService implements Caching {

    private static List<Quote> QUOTES = new ArrayList<>();

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private QuoteRepository quoteRepository;

    @PostConstruct
    public void init() {
        populateQuoteList();
    }

    public Quote randomQuote() {
        return RandomElement.from(QUOTES);
    }

    private void populateQuoteList() {
        new TransactionTemplate(transactionManager).execute(status -> {
            List<Quote> freshQuotes = quoteRepository.findAll();
            freshQuotes.forEach(q -> Hibernate.initialize(q.getQuoteInMultiLingo()));
            QUOTES = freshQuotes;
            return null;
        });
    }

    @Override
    public void flush() {
        populateQuoteList();
    }

}
