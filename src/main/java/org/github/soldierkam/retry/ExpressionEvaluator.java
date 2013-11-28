package org.github.soldierkam.retry;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 *
 * @author soldier
 */
public class ExpressionEvaluator {

    private final SpelExpressionParser parser = new SpelExpressionParser();

    private final Map<String, Expression> conditionCache = new ConcurrentHashMap<>();

    private EvaluationContext createEvaluationContext(Object[] args, Object returnValue, Exception exc) {
        return new RetryEvaluationContext(args,exc, returnValue);
    }

    public boolean condition(String conditionExpression, Method method, Object[] args, Object returnValue, Exception exc) {
        EvaluationContext evalContext = createEvaluationContext(args, returnValue, exc);
        String key = toString(method, conditionExpression);
        Expression condExp = this.conditionCache.get(key);
        if (condExp == null) {
            condExp = this.parser.parseExpression(conditionExpression);
            this.conditionCache.put(key, condExp);
        }
        return condExp.getValue(evalContext, boolean.class);
    }

    private String toString(Method method, String expression) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getDeclaringClass().getName());
        sb.append("#");
        sb.append(method.toString());
        sb.append("#");
        sb.append(expression);
        return sb.toString();
    }
}
