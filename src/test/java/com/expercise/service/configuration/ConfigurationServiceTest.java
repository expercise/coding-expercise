package com.expercise.service.configuration;

import com.expercise.dao.configuration.ConfigurationDao;
import com.expercise.domain.configuration.Configuration;
import com.expercise.testutils.builder.ConfigurationBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocaleContextHolder.class})
public class ConfigurationServiceTest {

    @InjectMocks
    private ConfigurationService service;

    @Mock
    private ConfigurationDao configurationDao;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Test
    public void shouldInitializeConfiguration() {
        Configuration configuration1 = new ConfigurationBuilder().key("key1").value("value1").buildWithRandomId();
        Configuration configuration2 = new ConfigurationBuilder().key("key2").value("value2").buildWithRandomId();

        initConfigurationsWith(configuration1, configuration2);

        assertThat(service.getValue("key1"), equalTo("value1"));
        assertThat(service.getValue("key2"), equalTo("value2"));
    }

    @Test
    public void shouldGetUserReportApplicationKeyByCurrentLingo() {
        mockStatic(LocaleContextHolder.class);
        when(LocaleContextHolder.getLocale()).thenReturn(Locale.forLanguageTag("tr")).thenReturn(Locale.ENGLISH);

        String applicationKeyForEnglish = RandomStringUtils.random(10);
        String applicationKeyForTurkish = RandomStringUtils.random(10);
        Configuration configuration1 = new ConfigurationBuilder().key("userReport.applicationKey.English").value(applicationKeyForEnglish).buildWithRandomId();
        Configuration configuration2 = new ConfigurationBuilder().key("userReport.applicationKey.Turkish").value(applicationKeyForTurkish).buildWithRandomId();

        initConfigurationsWith(configuration1, configuration2);

        assertThat(service.getUserReportApplicationKey(), equalTo(applicationKeyForTurkish));
        assertThat(service.getUserReportApplicationKey(), equalTo(applicationKeyForEnglish));
    }

    private void initConfigurationsWith(Configuration... configurations) {
        when(configurationDao.findAll()).thenReturn(Arrays.asList(configurations));
        service.init();
    }

}