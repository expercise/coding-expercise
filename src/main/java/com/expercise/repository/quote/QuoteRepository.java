package com.expercise.repository.quote;

import com.expercise.repository.BaseRepository;
import com.expercise.domain.quote.Quote;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteRepository extends BaseRepository<Quote> {

    protected QuoteRepository() {
        super(Quote.class);
    }

}
