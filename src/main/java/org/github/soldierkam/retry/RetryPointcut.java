package org.github.soldierkam.retry;

import java.lang.reflect.Method;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;

/**
 *
 * @author soldier
 */
public class RetryPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        final Retry repeat = method.getAnnotation(Retry.class);
        final RetryWhen repeatWhen = method.getAnnotation(RetryWhen.class);
        final RetryGroup repeats = method.getAnnotation(RetryGroup.class);
        boolean matches = repeat != null || repeatWhen != null || repeats != null;
        if (matches) {
            if (repeat != null) {
                if (repeatWhen != null || repeats != null) {
                    throwTooManyAnnotaions(method);
                }
                validate(repeat, method);
            }
            if (repeatWhen != null) {
                if (repeat != null || repeats != null) {
                    throwTooManyAnnotaions(method);
                }
                validate(repeatWhen, method);
            }
            if (repeats != null) {
                if (repeatWhen != null || repeat != null) {
                    throwTooManyAnnotaions(method);
                }
                validate(repeats, method);
            }

        }
        return matches;
    }

    private void throwTooManyAnnotaions(Method m) {
        throw new BeanDefinitionValidationException("Too many annotation on " + m);
    }

    private void validate(Retry repeat, Method m) {
        if (repeat.attempts() <= 1) {
            throw new BeanDefinitionValidationException("Cannot create pointcut for " + m + ": attempts must be > 1 ");
        }
    }

    private void validate(RetryWhen repeat, Method m) {
        if (repeat.attempts() <= 1) {
            throw new BeanDefinitionValidationException("Cannot create pointcut for " + m + ": attempts must be > 1 ");
        }
        if (repeat.value().isEmpty()) {
            throw new BeanDefinitionValidationException("Cannot create pointcut for " + m + ": condition cannot be empty");
        }
    }

    private void validate(RetryGroup retryGroup, Method m) {
        int sum = 0;
        int lowest = Integer.MAX_VALUE;
        for (RetryWhen retryWhen : retryGroup.when()) {
            sum += retryWhen.attempts();
            lowest = Math.min(lowest, retryWhen.attempts());
        }
        for (Retry retry : retryGroup.value()) {
            sum += retry.attempts();
            lowest = Math.min(lowest, retry.attempts());
        }
        if (sum < retryGroup.attempts()) {
            throw new BeanDefinitionValidationException("Cannot create pointcut for " + m + ": RetryGroup.attemps() must be lower or equal to accumulated attempts(" + sum + ") for childs");
        }
        
        if (lowest > retryGroup.attempts()) {
            throw new BeanDefinitionValidationException("Cannot create pointcut for " + m + ": RetryGroup.attemps() must be greater or equal to lowest attempts(" + lowest + ")");
        }
    }
}
