package itc.common.tools.slidingwindow;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class SlidingWindowCounterTest {

    @Test
    public void testAddCount() throws InterruptedException {
        SlidingWindowCounter.init("lidingWindow1", 5, TimeUnit.SECONDS, new BiConsumer<String, Long>() {
            @Override
            public void accept(String slidingName, Long sum) {
                System.out.println(new Date());
                System.out.println(slidingName + ": " + sum);
            }
        });
        while (!Thread.currentThread().isInterrupted()) {
            for (int i = 0; i < 10; i++) {
                SlidingWindowCounter.addCount("lidingWindow1", Long.valueOf(i));
            }
        }
        Thread.currentThread().join();
    }
}