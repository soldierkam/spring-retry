package org.github.soldierkam.retry;

import java.io.IOException;

/**
 *
 * @author soldier
 */
public interface SomeService2 {

    @RetryGroup({
        @Retry(on = RuntimeException.class),
        @Retry(on = IOException.class)})
    void throwIoExceptionAndRuntimeException() throws IOException;

    @RetryGroup({
        @Retry(on = RuntimeException.class),
        @Retry(on = IOException.class, attempts = 2)})
    void throwIoException() throws IOException;

    int getCount();
}
