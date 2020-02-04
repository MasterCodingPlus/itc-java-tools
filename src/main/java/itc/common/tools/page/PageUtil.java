package itc.common.tools.page;

import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.page
 * @ClassName: PageUtil
 * @Description: 分页工具类
 * @Author: MasterCoding
 * @CreateDate: 9/6/18 3:35 PM
 * Copyright: Copyright (c) 2018
 **/
public final class PageUtil {

    /**
     * 查询参数，页码
     */
    public static final String PARAM_PAGE = "page";

    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE = 1;

    /**
     * 查询参数，每页记录条数上限
     */
    public static final String PARAM_LIMIT = "limit";

    /**
     * 默认每页记录条数上限
     */
    public static final int DEFAULT_LIMIT = 10;


    /**
     * 查询分页对象
     */
    @Getter
    public final static class Page {

        /**
         * 页码
         */
        private final int page;

        /**
         * 每页记录条数上限
         */
        private final int limit;

        /**
         * 构造方法
         *
         * @param queryParamMap 查询参数映射实例
         */
        public Page(final Map<String, List<String>> queryParamMap) {
            if (queryParamMap.containsKey(PARAM_PAGE)) {
                //若映射含有页码，取出并赋值
                page = Integer.parseInt(queryParamMap.get(PARAM_PAGE).get(0));
            } else {
                //否则赋值默认值
                page = DEFAULT_PAGE;
            }
            if (queryParamMap.containsKey(PARAM_LIMIT)) {
                //若映射含有上限值，取出并赋值
                limit = Integer.parseInt(queryParamMap.get(PARAM_LIMIT).get(0));
            } else {
                //否则赋值默认值
                limit = DEFAULT_LIMIT;
            }
        }
    }
}
