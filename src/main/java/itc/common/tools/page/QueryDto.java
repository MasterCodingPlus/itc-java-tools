/**
 * (C) Copyright Company 2018. All rights reserved.
 * 文件名：PageQueryDto
 * 版 本：1.0
 * 内容简述：描述该文件实现的主要功能
 * 创建日期：2019/3/20 15:50
 * 创建人：cyl
 * 修改记录：
 * 日期  版本  修改人  修改内容
 */
package itc.common.tools.page;

import lombok.Data;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class QueryDto {
    /**
     * 分页
     */
    private PageDto page;

    /**
     * 查询字段
     */
    private String[] selectNames;


    // 2019-5-5 12:36:38 ChenQiuXu

    /**
     * 基于 map 内容等于=
     */
    private Map<String, Object> allEq;

    /**
     * 不等于<>
     */
    private Map<String, Object> ne;

    /**
     * 大于>
     */
    private Map<String, Object> gt;

    /**
     * 大于等于>=
     */
    private Map<String, Object> ge;

    /**
     * 小于<
     */
    private Map<String, Object> lt;

    /**
     * 小于等于<=
     */
    private Map<String, Object> le;

    /**
     * 模糊查询 LIKE
     */
    private Map<String, Object> like;

    /**
     * 模糊查询 NOT LIKE
     */
    private Map<String, Object> notLike;

    /**
     * IN 查询
     */
    private Map<String, Array> in;

    /**
     * NOT IN 查询
     */
    private Map<String, Array> notIn;

    /**
     * NULL 值查询
     */
    private List isNull;

    /**
     * IS NOT NULL
     */
    private List isNotNull;

    /**
     * 分组 GROUP BY
     */
    private List groupBy;

    /**
     * HAVING 关键词
     */
    private Map<String, Object> having;

    /**
     * 排序 ORDER BY
     */
    private String orderBy;

    /**
     * ASC 排序 ORDER BY
     */
    private String orderAsc;

    /**
     * DESC 排序 ORDER BY
     */
    private String orderDesc;

    /**
     * EXISTS 条件语句
     */
    private Map<String, Object> exists;

    /**
     * NOT EXISTS 条件语句
     */
    private Map<String, Object> notExists;

    /**
     * BETWEEN 条件语句
     */
    private List<QueryBetween> between;

    /**
     * NOT BETWEEN 条件语句
     */
    private Map<String, Object> notBetween;

    /**
     * 自由拼接 SQL
     */
    private Map<String, Object> addFilter;

    /**
     * 拼接在最后，例如：last("LIMIT 1")
     */
    private Map<String, Object> last;
}
