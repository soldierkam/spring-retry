package org.github.soldierkam.retry;

import java.io.IOException;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author soldier
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig2.class)
public class SomeService2Test {

    @Inject
    private SomeService2 instance;

    @Test
    public void test() {
        try {
            instance.throwIoExceptionAndRuntimeException();
        } catch (IOException | RuntimeException exc) {
            Assert.assertEquals(3, instance.getCount());
        }
    }

    @Test
    public void test2() {
        try {
            instance.throwIoException();
        } catch (IOException exc) {
            Assert.assertEquals(2, instance.getCount());
        }
    }
}
