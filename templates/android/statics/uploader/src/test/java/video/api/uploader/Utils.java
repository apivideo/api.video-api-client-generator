package video.api.uploader;

import org.junit.Assert;
import org.junit.function.ThrowingRunnable;

public class Utils {
    public static <T extends Throwable> void assertThrows(Class<T> expectedThrowable,  String expectedMessage,
                                    ThrowingRunnable runnable) {
        Exception exception = (Exception) Assert.assertThrows(expectedThrowable, runnable);

        String actualMessage = exception.getMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
}
