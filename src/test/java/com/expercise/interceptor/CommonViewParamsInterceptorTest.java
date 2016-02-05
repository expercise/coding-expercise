package com.expercise.interceptor;

import com.expercise.domain.user.User;
import com.expercise.service.configuration.ConfigurationService;
import com.expercise.service.user.AuthenticationService;
import com.expercise.service.util.UrlService;
import com.expercise.testutils.builder.UserBuilder;
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
    private UrlService urlService;

    @Mock
    private Device device;

    private MockHttpServletRequest request = new MockHttpServletRequest();

    private MockHttpServletResponse response = new MockHttpServletResponse();

    private ModelAndView modelAndView = new ModelAndView("viewName");

    @Before
    public void before() {
        request.setAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE, device);
    }

    @Test
    public void shouldAddGoogleAnalyticsApplicationKey() {
        String googleAnalyticsApplicationKey = RandomStringUtils.randomAlphabetic(11);

        when(configurationService.getGoogleAnalyticsApplicationKey()).thenReturn(googleAnalyticsApplicationKey);

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("googleAnalyticsApplicationKey", (Object) googleAnalyticsApplicationKey));
    }

    @Test
    public void shouldAddUserReportApplicationKey() {
        String userReportApplicationKey = RandomStringUtils.randomAlphabetic(11);

        when(configurationService.getUserReportApplicationKey()).thenReturn(userReportApplicationKey);

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("userReportApplicationKey", (Object) userReportApplicationKey));
    }

    @Test
    public void shouldAddBuildId() {
        String buildId = String.valueOf(System.currentTimeMillis());

        ReflectionTestUtils.setField(interceptor, "buildId", buildId);

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("buildId", (Object) buildId));
    }

    @Test
    public void shouldAddIfDevelopmentEnvironment() {
        boolean developmentEnvironment = BooleanUtils.toBoolean(RandomUtils.nextInt(0, 2));

        when(configurationService.isDevelopment()).thenReturn(developmentEnvironment);

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("developmentEnvironment", (Object) developmentEnvironment));
    }

    @Test
    public void shouldAddIfCurrentUsersEmail() {
        User user = new UserBuilder().buildWithRandomId();

        when(authenticationService.getCurrentUser()).thenReturn(user);

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("currentUser", (Object) user));
    }

    @Test
    public void shouldSetTrueAsMobileClientIfDeviceIsMobile() {
        when(device.isMobile()).thenReturn(true);

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("mobileClient", (Object) true));
    }

    @Test
    public void shouldSetTrueAsMobileClientIfDeviceIsTablet() {
        when(device.isTablet()).thenReturn(true);

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("mobileClient", (Object) true));
    }

    @Test
    public void shouldSetFalseAsMobileClientIfDeviceIsNormal() {
        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("mobileClient", (Object) false));
    }

    @Test
    public void shouldAddCanonicalUrlWithoutWWW() {
        when(urlService.getCanonical(request)).thenReturn("http://expercise.com/");

        interceptor.postHandle(request, response, null, modelAndView);

        assertThat(modelAndView.getModel(), hasEntry("canonical", (Object) "http://expercise.com/"));
    }

}