package org.github.soldierkam.retry;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 *
 * @author soldier
 */
@Configuration
public class RetryConfig {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public RetryBeanPostProcessor retryBeanPostProcessor() {
        return new RetryBeanPostProcessor();
    }

}
