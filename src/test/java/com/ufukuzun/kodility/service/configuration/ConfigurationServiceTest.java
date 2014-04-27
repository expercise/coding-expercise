package com.ufukuzun.kodility.service.configuration;

import com.ufukuzun.kodility.dao.configuration.ConfigurationDao;
import com.ufukuzun.kodility.domain.configuration.Configuration;
import com.ufukuzun.kodility.testutils.builder.ConfigurationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationServiceTest {

    @InjectMocks
    private ConfigurationService service;

    @Mock
    private ConfigurationDao configurationDao;

    @Test
    public void shouldInitializeConfiguration() {
        Configuration configuration1 = new ConfigurationBuilder().id("1").key("key1").value("value1").build();
        Configuration configuration2 = new ConfigurationBuilder().id("2").key("key2").value("value2").build();

        when(configurationDao.findAll()).thenReturn(Arrays.asList(configuration1, configuration2));

        service.init();

        assertThat(service.getValue("key1"), equalTo("value1"));
        assertThat(service.getValue("key2"), equalTo("value2"));
    }

}