package com.expercise.interceptor;

import com.expercise.service.configuration.ConfigurationService;
import com.expercise.service.user.AuthenticationService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
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

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private Device device;

    private HttpServletRequest request = new MockHttpServletRequest();

    private HttpServletResponse response = new MockHttpServletResponse();

    @Before
    public void before() {
        request.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE, device);
    }

    @Test
    public void shouldAddGoogleAnalyticsScript() {
        String googleAnalyticsScript = RandomStringUtils.randomAlphabetic(11);

        when(configurationService.getGoogleAnalyticsScript()).thenReturn(googleAnalyticsScript);

        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("googleAnalyticsScript", (Object) googleAnalyticsScript));
    }

    @Test
    public void shouldAddBuildId() {
        String buildId = String.valueOf(System.currentTimeMillis());

        ReflectionTestUtils.setField(interceptor, "buildId", buildId);

        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("buildId", (Object) buildId));
    }

    @Test
    public void shouldAddIfDevelopmentEnvironment() {
        boolean developmentEnvironment = BooleanUtils.toBoolean(RandomUtils.nextInt(0, 2));

        when(configurationService.isDevelopment()).thenReturn(developmentEnvironment);

        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("developmentEnvironment", (Object) developmentEnvironment));
    }

    @Test
    public void shouldAddIfCurrentUsersEmail() {
        when(authenticationService.getCurrentUsersEmail()).thenReturn("user@expercise.com");

        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("currentUsersEmail", (Object) "user@expercise.com"));
    }

    @Test
    public void shouldSetTrueAsMobileClientIfDeviceIsMobile() {
        when(device.isMobile()).thenReturn(true);

        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("mobileClient", (Object) true));
    }

    @Test
    public void shouldSetTrueAsMobileClientIfDeviceIsTablet() {
        when(device.isTablet()).thenReturn(true);

        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("mobileClient", (Object) true));
    }

    @Test
    public void shouldSetFalseAsMobileClientIfDeviceIsNormal() {
        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("mobileClient", (Object) false));
    }

}