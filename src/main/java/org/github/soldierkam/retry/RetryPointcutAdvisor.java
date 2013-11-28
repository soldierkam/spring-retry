package org.github.soldierkam.retry;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 *
 * @author soldier
 */
public class RetryPointcutAdvisor extends AbstractPointcutAdvisor {

    private final RetryPointcut pointcut = new RetryPointcut();
    private final RetryInterceptor interceptor = new RetryInterceptor();

    @Override
    public Advice getAdvice() {
        return interceptor;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

}
