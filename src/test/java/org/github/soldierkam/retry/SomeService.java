package org.github.soldierkam.retry;

import java.io.IOException;

/**
 *
 * @author soldier
 */
public interface SomeService {

    @Retry(on = IllegalStateException.class)
    void alwaysRuntimeExc();

    @Retry(on = IllegalStateException.class)
    void runtimeExcOnFirstCall();

    @Retry(on = IOException.class)
    void alwaysCheckedException() throws IOException;

    @Retry(on = IllegalStateException.class)
    void alwaysCheckedException2() throws IOException;

    @Retry(on = Exception.class)
    String alwaysReturnString();

    @Retry(on = Exception.class)
    String alwaysJoinParams(String p1, String p2);

    @Retry(on = IllegalStateException.class, condition = "#exc.message.contains('wrong')")
    void alwaysRuntimeExcWithMessageSmthWrong();

    @Retry(on = IllegalStateException.class, condition = "#exc.message.contains('failed')")
    void alwaysRuntimeExcWithMessageSmthWrong2();
    
    @Retry(on = IllegalStateException.class, attempts = 5)
    void repeateFiveTimes();

    @RetryWhen("#result == null")
    String alwaysReturnNull(String p1, String p2);

    @RetryWhen("#result == null")
    String alwaysReturnNonEmptyString(String p1, String p2);

    int count();
}
