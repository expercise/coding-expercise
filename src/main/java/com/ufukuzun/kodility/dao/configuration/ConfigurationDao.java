package com.ufukuzun.kodility.dao.configuration;

import com.ufukuzun.kodility.domain.configuration.Configuration;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConfigurationDao extends PagingAndSortingRepository<Configuration, String> {
}
