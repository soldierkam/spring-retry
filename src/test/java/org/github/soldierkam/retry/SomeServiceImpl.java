package org.github.soldierkam.retry;

import java.io.IOException;
import org.springframework.stereotype.Service;

/**
 *
 * @author soldier
 */
@Service
public class SomeServiceImpl implements SomeService {

    private int c = 0;

    @Override
    public int count() {
        final int t = c;
        c = 0;
        return t;
    }

    @Override
    public void alwaysRuntimeExc() {
        c++;
        throw new IllegalStateException();
    }

    @Override
    public void alwaysCheckedException() throws IOException {
        c++;
        throw new IOException();
    }

    @Override
    public void alwaysCheckedException2() throws IOException {
        c++;
        throw new IOException();
    }

    @Override
    public String alwaysReturnString() {
        c++;
        return "success";
    }

    @Override
    public String alwaysJoinParams(String p1, String p2) {
        c++;
        return p1 + " " + p2;
    }

    @Override
    public void runtimeExcOnFirstCall() {
        c++;
        if (c == 1) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void alwaysRuntimeExcWithMessageSmthWrong() {
        c++;
        throw new IllegalStateException("Smth wrong");
    }

    @Override
    public void alwaysRuntimeExcWithMessageSmthWrong2() {
        c++;
        throw new IllegalStateException("Smth wrong");
    }

    @Override
    public String alwaysReturnNull(String p1, String p2) {
        c++;
        return null;
    }

    @Override
    public String alwaysReturnNonEmptyString(String p1, String p2) {
        c++;
        return "happy ending";
    }

    @Override
    public void repeateFiveTimes() {
        c++;
        throw new IllegalStateException();
    }

}
