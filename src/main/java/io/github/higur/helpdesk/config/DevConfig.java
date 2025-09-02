package io.github.higur.helpdesk.config;

import io.github.higur.helpdesk.service.DBService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {
    @Autowired
    private DBService dbService;

    @PostConstruct
    public void instanceDB() {
        dbService.dbStart();
    }
}
