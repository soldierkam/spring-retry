package org.github.soldierkam.retry;

import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 *
 * @author soldier
 */
public class RetryEvaluationContext extends StandardEvaluationContext {

    public RetryEvaluationContext(Object[] args, Exception exc, Object returnValue) {
        super();
        setVariable("exc", exc);
        setVariable("result", returnValue);
        for (int i = 0; i < args.length; i++) {
            setVariable("p" + i, args[i]);
        }
    }

}
