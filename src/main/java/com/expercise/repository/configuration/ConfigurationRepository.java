package com.expercise.repository.configuration;

import com.expercise.repository.BaseRepository;
import com.expercise.domain.configuration.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurationRepository extends BaseRepository<Configuration> {

    protected ConfigurationRepository() {
        super(Configuration.class);
    }

}
