package org.github.soldierkam.retry;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author soldier
 */
public class RetryInterceptor implements MethodInterceptor {

    private static final Log LOG = LogFactory.getLog(RetryInterceptor.class);
    private final ExpressionEvaluator eval = new ExpressionEvaluator();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Apply apply = gethandler(invocation.getMethod());
        Object result;
        Exception exc;
        do {
            result = null;
            exc = null;
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Proceed " + invocation);
                }
                result = invocation.proceed();
            } catch (Exception e) {
                exc = e;
            }
        } while (apply.matching(invocation.getArguments(), result, exc) && checkCan(apply));
        if (exc != null) {
            throw exc;
        }
        return result;
    }
    
    private boolean checkCan(Apply apply){
        return apply.canTryAgain();
    }

    private Apply gethandler(Method m) {
        final RetryWhen repeatWhen = m.getAnnotation(RetryWhen.class);
        if (repeatWhen != null) {
            return new RetryWhenApply(repeatWhen, m, eval);
        }
        final Retry repeat = m.getAnnotation(Retry.class);
        if (repeat != null) {
            return new RetryApply(repeat, m, eval);
        }
        final RetryGroup repeats = m.getAnnotation(RetryGroup.class);
        if(repeats != null){
            return getComposite(repeats, m);
        }
        throw new RuntimeException("No annotation");
    }

    private Apply getComposite(RetryGroup retryGroup, Method m) {
        final List<Apply> items = new ArrayList<>(retryGroup.value().length + retryGroup.when().length);
        for (Retry r : retryGroup.value()) {
            items.add(new RetryApply(r, m, eval));
        }
        for (RetryWhen r : retryGroup.when()) {
            items.add(new RetryWhenApply(r, m, eval));
        }
        return new CompositeApply(items.toArray(new Apply[items.size()]), retryGroup.attempts());
    }

}
