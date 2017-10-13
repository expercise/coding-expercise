package com.expercise.service.quote;

import com.expercise.domain.quote.Quote;
import com.expercise.repository.quote.QuoteRepository;
import com.expercise.testutils.builder.QuoteBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RandomUtils.class)
public class QuoteServiceTest {

    @InjectMocks
    private QuoteService service;

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Test
    public void shouldReturnRandomQuote() {
        Quote quote1 = new QuoteBuilder().id(1L).build();
        Quote quote2 = new QuoteBuilder().id(2L).build();

        when(quoteRepository.findAll()).thenReturn(Arrays.asList(quote1, quote2));

        service.init();

        PowerMockito.mockStatic(RandomUtils.class);
        when(RandomUtils.nextInt(0, 2)).thenReturn(1, 0, 0, 1);

        Quote randomQuote1 = service.randomQuote();
        Quote randomQuote2 = service.randomQuote();
        Quote randomQuote3 = service.randomQuote();
        Quote randomQuote4 = service.randomQuote();

        assertThat(randomQuote1, equalTo(quote2));
        assertThat(randomQuote2, equalTo(quote1));
        assertThat(randomQuote3, equalTo(quote1));
        assertThat(randomQuote4, equalTo(quote2));
    }

    @Test
    public void shouldReturnNullIfQuoteListIsEmpty() {
        when(quoteRepository.findAll()).thenReturn(new ArrayList<>());

        service.init();

        assertThat(service.randomQuote(), equalTo(null));
    }

}