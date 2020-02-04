package itc.common.tools.ehcache;

import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalNotification;
import org.junit.Test;

import java.util.function.Consumer;

public class CacheManageTest {

    @Test
    public void createCache() throws InterruptedException {
        LoadingCache<String, Object> abc = CacheManage.createCache(5, "abc", new Consumer<RemovalNotification>() {
            @Override
            public void accept(RemovalNotification removalNotification) {
                System.out.println(removalNotification.getCause());
                System.out.println(removalNotification.wasEvicted());
                System.out.println(removalNotification);
            }
        });
        abc.put("123", 4123);
        Thread.sleep(3000);
        abc.put("123", 4123);
        Thread.sleep(1000);
        abc.invalidate("123");
        abc.put("1233", 4123);
        abc.put("1234", 4123);
        abc.put("1235", 4123);
        abc.put("1236", 4123);
        abc.put("1238", 4123);
        Thread.sleep(3000);
        abc.put("123", 4123);
        Thread.sleep(1000000);
    }

    @Test
    public void getCache() {
    }

    @Test
    public void addCache() {
    }

    @Test
    public void getCahe() {
    }
}