package org.github.soldierkam.retry;

import java.lang.reflect.Method;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 *
 * @author soldier
 */
class RetryApply implements Apply {

    private final Retry anno;
    private final Method method;
    private final ExpressionEvaluator eval;

    private int c = 0;

    RetryApply(Retry anno, Method method, final ExpressionEvaluator outer) {
        Assert.notNull(anno);
        Assert.notNull(method);
        Assert.notNull(outer);
        this.eval = outer;
        this.anno = anno;
        this.method = method;
    }

    @Override
    public boolean matching(Object[] args, Object result, Exception exception) {
        if (!anno.on().isInstance(exception)) {
            return false;
        }
        if (StringUtils.isEmpty(anno.condition())) {
            return true;
        } else {
            return eval.condition(anno.condition(), method, args, result, exception);
        }
    }

    @Override
    public boolean canTryAgain() {
        c++;
        return c < anno.attempts();
    }

}
