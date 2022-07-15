package io.github.lvyahui8.sdk.logging;

import org.junit.Test;

public class TestCase extends BaseTest {
    @Test
    public void testRootLogger() {
        TestLogger._root.info("hello");
    }
}
