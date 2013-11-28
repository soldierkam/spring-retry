package org.github.soldierkam.retry;

import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;

/**
 *
 * @author soldier
 */
public class RetryBeanPostProcessor extends AbstractAdvisingBeanPostProcessor {

    public RetryBeanPostProcessor() {
        setBeforeExistingAdvisors(true);
        this.advisor = new RetryPointcutAdvisor();
    }

}
