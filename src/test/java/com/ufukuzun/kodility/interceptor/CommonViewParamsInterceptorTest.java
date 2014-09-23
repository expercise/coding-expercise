package com.ufukuzun.kodility.interceptor;

import com.ufukuzun.kodility.service.configuration.ConfigurationService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommonViewParamsInterceptorTest {

    @InjectMocks
    private CommonViewParamsInterceptor interceptor;

    @Mock
    private ConfigurationService configurationService;

    private HttpServletRequest request = new MockHttpServletRequest();

    private HttpServletResponse response = new MockHttpServletResponse();

    @Test
    public void shouldAddGoogleAnalyticsScript() throws Exception {
        String googleAnalyticsScript = RandomStringUtils.randomAlphabetic(11);

        when(configurationService.getGoogleAnalyticsScript()).thenReturn(googleAnalyticsScript);

        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("googleAnalyticsScript", (Object) googleAnalyticsScript));
    }

    @Test
    public void shouldAddBuildId() throws Exception {
        String buildId = String.valueOf(System.currentTimeMillis());

        ReflectionTestUtils.setField(interceptor, "buildId", buildId);

        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("buildId", (Object) buildId));
    }

    @Test
    public void shouldAddIfDevelopmentEnvironment() throws Exception {
        boolean developmentEnvironment = BooleanUtils.toBoolean(RandomUtils.nextInt(0, 2));

        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("developmentEnvironment", (Object) developmentEnvironment));
    }

}