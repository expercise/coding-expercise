package com.expercise.service.configuration;

import com.expercise.repository.configuration.ConfigurationRepository;
import com.expercise.domain.configuration.Configuration;
import com.expercise.testutils.builder.ConfigurationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocaleContextHolder.class})
public class ConfigurationServiceTest {

    @InjectMocks
    private ConfigurationService service;

    @Mock
    private ConfigurationRepository configurationRepository;

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

    private void initConfigurationsWith(Configuration... configurations) {
        when(configurationRepository.findAll()).thenReturn(Arrays.asList(configurations));
        service.init();
    }

}