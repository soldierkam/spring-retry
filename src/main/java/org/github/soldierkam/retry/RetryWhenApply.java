package org.github.soldierkam.retry;

import java.lang.reflect.Method;
import org.springframework.util.Assert;

/**
 *
 * @author soldier
 */
class RetryWhenApply implements Apply {

    private final RetryWhen anno;
    private final Method method;
    private final ExpressionEvaluator eval;

    private int c = 0;

    RetryWhenApply(RetryWhen instance, Method method, final ExpressionEvaluator outer) {
        Assert.notNull(instance);
        Assert.notNull(method);
        Assert.notNull(outer);
        this.eval = outer;
        this.anno = instance;
        this.method = method;
    }

    @Override
    public boolean matching(Object[] args, Object result, Exception exception) {
        return eval.condition(anno.value(), method, args, result, exception);
    }

    @Override
    public boolean canTryAgain() {
        c++;
        return c < anno.attempts();
    }

}
