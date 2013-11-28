package org.github.soldierkam.retry;

import java.io.IOException;

/**
 *
 * @author soldier
 */
public class SomeService2Impl implements SomeService2 {

    private int c = 0;

    @Override
    public void throwIoExceptionAndRuntimeException() throws IOException {
        c++;
        if (c % 1 == 1) {
            throw new IOException();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void throwIoException() throws IOException {
        c++;
        throw new IOException();
    }

    @Override
    public int getCount() {
        final int tmp = c;
        c = 0;
        return tmp;
    }

}
