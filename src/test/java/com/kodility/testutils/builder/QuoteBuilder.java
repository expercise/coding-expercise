package com.kodility.testutils.builder;

import com.kodility.domain.quote.Quote;

public class QuoteBuilder extends AbstractEntityBuilder<Quote, QuoteBuilder> {

    @Override
    protected Quote doBuild() {
        return new Quote();
    }

}
