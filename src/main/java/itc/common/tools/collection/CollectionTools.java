package itc.common.tools.collection;

import java.util.Collection;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.collection
 * @ClassName: CollectionTools
 * @Description: 服务中心
 * @Author: Mastercoding
 * @CreateDate: 2019/5/11 15:06
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/5/11 15:06
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class CollectionTools {
    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return
     */
    public static Boolean isNullOrEmpty(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * 数组集合是否为空
     *
     * @param objects
     * @return
     */
    public static Boolean isNullOrEmptyArry(Object... objects) {
        if (objects == null || objects.length == 0) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
