package org.github.soldierkam.retry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author soldier
 */
@Configuration
@Import(RetryConfig.class)
public class TestConfig {

    @Bean
    public SomeService someService(){
        return new SomeServiceImpl();
    }
}
