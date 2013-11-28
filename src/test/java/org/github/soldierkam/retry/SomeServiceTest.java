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
@ContextConfiguration(classes = TestConfig.class)
public class SomeServiceTest {

    @Inject
    private SomeService someService;

    @Test
    public void testRuntimeException() {
        try {
            someService.alwaysRuntimeExc();
            Assert.fail();
        } catch (IllegalStateException exc) {
            Assert.assertEquals(3, someService.count());
        }
    }

    @Test
    public void testRuntimeExceptionOnFirstCall() {
        someService.runtimeExcOnFirstCall();
        Assert.assertEquals(2, someService.count());
    }

    @Test
    public void testCheckedException() {
        try {
            someService.alwaysCheckedException();
            Assert.fail();
        } catch (IOException exc) {
            Assert.assertEquals(3, someService.count());
        }
    }

    @Test
    public void testNoRepeatWhenExceptionIsNotMatch() {
        try {
            someService.alwaysCheckedException2();
            Assert.fail();
        } catch (IOException exc) {
            Assert.assertEquals(1, someService.count());
        }
    }

    @Test
    public void testRepeatWhenExceptionMessageContainsWordWrong() {
        try {
            someService.alwaysRuntimeExcWithMessageSmthWrong();
            Assert.fail();
        } catch (Exception exc) {
            Assert.assertEquals(3, someService.count());
        }
    }

    @Test
    public void testNoRepeatWhenExceptionMessageDoNotContainsWordFailed() {
        try {
            someService.alwaysRuntimeExcWithMessageSmthWrong2();
            Assert.fail();
        } catch (Exception exc) {
            Assert.assertEquals(1, someService.count());
        }
    }

    @Test
    public void testRepeatWhenReturnNull() {
        String result = someService.alwaysReturnNull("arg1", "arg2");
        Assert.assertEquals(3, someService.count());
        Assert.assertNull(result);
    }

    @Test
    public void testNoRepeateWhenReturnNotNull() {
        String result = someService.alwaysReturnNonEmptyString("a1", "a2");
        Assert.assertEquals(1, someService.count());
        Assert.assertEquals("happy ending", result);
    }

    @Test
    public void testRepeateFiveTimes() {
        try {
            someService.repeateFiveTimes();
        } catch (RuntimeException exc) {
            Assert.assertEquals(5, someService.count());
        }
    }
}
