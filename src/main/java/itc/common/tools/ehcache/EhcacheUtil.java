package itc.common.tools.ehcache;

import itc.common.tools.object.ObjectUtil;
import lombok.Getter;
import lombok.NonNull;
import net.sf.ehcache.*;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: Ehcache缓存工具类
 * @vesion: 1.0.0
 * @author: Chen QiuXu
 * @date: 2019/4/4 9:48
 */
public class EhcacheUtil {
    private static EhcacheUtil ourInstance = new EhcacheUtil();

    public static EhcacheUtil getInstance() {
        return ourInstance;
    }

    @Getter
    private final static CacheManager manager;

    static {
        manager = CacheManager.create();
        manager.setCacheManagerEventListener(new PoolCacheManagerEventListener());
    }

    private EhcacheUtil() {

    }

    /**
     * 创建缓存
     *
     * @param name                      缓存名称
     * @param maxElementsInMemory       缓存最大节点数
     * @param overflowToDisk            是否持续化到硬盘
     * @param eternal                   缓存是否永久有效
     * @param timeToLiveSeconds         缓存自创建之时起至失效时的间隔时间
     * @param timeToIdleSeconds         缓存创建以后，最后一次访问缓存之时至失效之时时间
     * @return
     */
    public Ehcache createCache(@NonNull String name, int maxElementsInMemory, boolean overflowToDisk, boolean eternal, long timeToLiveSeconds, long timeToIdleSeconds) {
        if (manager.cacheExists(name)) {
            return getCache(name);
        }

        final CacheConfiguration cacheConfiguration = new CacheConfiguration(name, maxElementsInMemory)
                .overflowToDisk(overflowToDisk)
                .eternal(eternal)
                .timeToLiveSeconds(timeToLiveSeconds)
                .timeToIdleSeconds(timeToIdleSeconds)
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.CLOCK);
        Cache cache = new Cache(cacheConfiguration);
        BlockingCache blockingCache = new BlockingCache(cache);
        manager.addCache(blockingCache);
        return blockingCache;
    }

    /**
     * 创建缓存
     *
     * @param name                      缓存名称
     * @param maxElementsInMemory       缓存最大节点数
     * @param overflowToDisk            是否持续化到硬盘
     * @param eternal                   缓存是否永久有效
     * @param timeToLiveSeconds         缓存自创建之时起至失效时的间隔时间
     * @param timeToIdleSeconds         缓存创建以后，最后一次访问缓存之时至失效之时时间
     * @param copyOnRead                缓存创建以后，是否在读取时复制
     * @param copyOnWrite               缓存创建以后，是否在写入时复制
     * @return
     */
    public Ehcache createCache(@NonNull String name, int maxElementsInMemory, boolean overflowToDisk, boolean eternal, long timeToLiveSeconds, long timeToIdleSeconds,
                               boolean copyOnRead, boolean copyOnWrite) {
        if (manager.cacheExists(name)) {
            return getCache(name);
        }

        final CacheConfiguration cacheConfiguration = new CacheConfiguration(name, maxElementsInMemory)
                .overflowToDisk(overflowToDisk)
                .eternal(eternal)
                .timeToLiveSeconds(timeToLiveSeconds)
                .timeToIdleSeconds(timeToIdleSeconds)
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.CLOCK)
                .copyOnRead(copyOnRead)
                .copyOnWrite(copyOnWrite);
        Cache cache = new Cache(cacheConfiguration);
        BlockingCache blockingCache = new BlockingCache(cache);
        manager.addCache(blockingCache);
        return blockingCache;
    }

    /**
     * 得到缓存
     *
     * @param cacheName     缓存名称
     * @return              缓存对象
     */
    public Ehcache getCache(@NonNull String cacheName) throws IllegalStateException {
        return manager.getEhcache(cacheName);
    }

    /**
     * 添加元素
     *
     * @param cacheName 缓存名称
     * @param element   缓存节点
     */
    public void addElement(@NonNull String cacheName, @NonNull Element element) throws IllegalStateException, CacheException {
        final Ehcache cache = getCache(cacheName);
        //判断当前缓存名时候存在
        if (!cache.isKeyInCache(element.getObjectKey())) {
            cache.put(element);
        } else {
            cache.get(element.getObjectKey());
        }
    }

    /**
     * 得到元素
     *
     * @param cacheName 缓存名称
     * @param key       键
     * @return
     */
    public Element getElement(@NonNull String cacheName, String key) {
        Element element = null;
        if (getCache(cacheName).getKeys().contains(key)) {
            try {
                element = getCache(cacheName).get(key);
            } catch (IllegalStateException | CacheException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return element;
    }

    /**
     * 得到值
     *
     * @param cacheName     缓存名称
     * @param key           键
     * @return              缓存对象
     */
    public <T> T getValue(@NonNull String cacheName, @NonNull Object key) {
        Object objectValue = null;
        if (getCache(cacheName).getKeys().contains(key)) {
            try {
                objectValue = getCache(cacheName).get(key).getObjectValue();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (CacheException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return (T) objectValue;
    }

    /**
     * 得到值
     *
     * @param cacheName     缓存名称
     * @param key           键
     * @return              缓存对象
     */
    public <T> T getValueClone(@NonNull String cacheName, @NonNull Object key) {
        return (T) ObjectUtil.deepCopy(getValue(cacheName, key));
    }

    /**
     * 得到所有的值
     *
     * @param cacheName     缓存名称
     * @return              缓存对象列表
     */
    public <T> List<T> getAll(@NonNull String cacheName) {
        final List allValue = new ArrayList();
        if (!getCache(cacheName).getKeys().isEmpty()) {
            try {
                getCache(cacheName).getKeys().forEach(k -> {
                    allValue.add(getValue(cacheName, k));
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return allValue;
    }

    /**
     * 得到所有的值
     *
     * @param cacheName     缓存名称
     * @return              缓存对象列表
     */
    public <T> List<T> getAllClone(@NonNull String cacheName) {
        final List<T> all = (List<T>) ObjectUtil.deepCopy(getAll(cacheName));
        return all;
    }

    /**
     * 替换缓存中的值
     *
     * @param cacheName 缓存名称
     * @param key       需要替换的对象名称
     * @param objectNew 需要替换的新对象
     * @return          替换结果
     */
    public boolean replace(@NonNull String cacheName, @NonNull String key, @NonNull Object objectNew) {
        Element elementOld = getElement(cacheName, key);
        Element elementNew = new Element(key, objectNew, elementOld.getTimeToIdle(), elementOld.getTimeToLive());
        boolean result = false;
        try {
            result = getCache(cacheName).replace(elementOld, elementNew);
        } catch (NullPointerException | IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
        }
        return result;
    }
}
