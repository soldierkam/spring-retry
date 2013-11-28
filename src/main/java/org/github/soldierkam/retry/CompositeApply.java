package org.github.soldierkam.retry;

/**
 *
 * @author soldier
 */
public class CompositeApply implements Apply {

    private final Apply[] parts;
    private final int maxAttempts;

    private int c = 0;
    private boolean doBreak = false;
            
    CompositeApply(Apply[] parts, int maxAttempts) {
        this.parts = parts;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public boolean matching(Object[] args, Object result, Exception exception) {
        boolean matching = false;
        for (Apply apply : parts) {
            if (apply.matching(args, result, exception)) {
                doBreak |= !apply.canTryAgain();
                matching = true;
            }
        }
        return matching;
    }

    @Override
    public boolean canTryAgain() {
        c++;
        return c < maxAttempts && !doBreak;
    }

}
