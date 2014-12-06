package com.kodility.controller;

import com.kodility.domain.quote.Quote;
import com.kodility.service.quote.QuoteService;
import com.kodility.testutils.builder.QuoteBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    @InjectMocks
    private HomeController controller;

    @Mock
    private QuoteService quoteService;

    @Test
    public void shouldRenderIndexView() {
        ModelAndView modelAndView = controller.homePage();

        assertThat(modelAndView.getViewName(), equalTo("index"));
    }

    @Test
    public void shouldShowQuoteOnHomePage() {
        Quote quote = new QuoteBuilder().buildWithRandomId();

        when(quoteService.randomQuote()).thenReturn(quote);

        ModelAndView modelAndView = controller.homePage();

        assertThat(modelAndView.getModel().get("quote"), equalTo(quote));
    }

}