spring-retry
============

Spring retry pattern

    @Retry(on = IOException.class, attempts = 5)
    void connectToUnreliableServer() throws IOException;

    @Retry(on = IOException.class, attempts = 5, condition = "#exc.message.contains('timeout')")
    void connectToUnreliableServer() throws IOException;

    @RetryGroup({
        @Retry(on = OptimisticLockException.class),
        @Retry(on = IOException.class)})
    void connectToUnreliableServer() throws IOException;

