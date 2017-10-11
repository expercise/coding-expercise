package com.expercise.testutils.builder;

import com.expercise.domain.quote.Quote;

public class QuoteBuilder extends BaseEntityBuilder<Quote, QuoteBuilder> {

    @Override
    protected Quote doBuild() {
        return new Quote();
    }

}
