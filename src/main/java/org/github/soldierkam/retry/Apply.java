package org.github.soldierkam.retry;

/**
 *
 * @author soldier
 */
interface Apply {

    boolean matching(Object[] args, Object result, Exception exception);

    boolean canTryAgain();

}
