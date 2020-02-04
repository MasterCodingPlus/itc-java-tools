package itc.common.tools.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Status;
import net.sf.ehcache.event.CacheManagerEventListener;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.ehcache
 * @ClassName: PoolCacheManagerEventListener
 * @Description: 缓存管理事件监听
 * @Author: zoudaiqiang
 * @CreateDate: 2018/9/21 9:51
 * ***********************************************************
 * @UpdateUser: Chen QiuXu
 * @UpdateDate: 2019-4-4 10:01:24
 * @UpdateRemark: 整理
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2018
 **/
public class PoolCacheManagerEventListener implements CacheManagerEventListener {
    @Override
    public void init() throws CacheException {

    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public void dispose() throws CacheException {

    }

    @Override
    public void notifyCacheAdded(String s) {

    }

    @Override
    public void notifyCacheRemoved(String s) {

    }
}
