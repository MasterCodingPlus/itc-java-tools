/**
 * (C) Copyright Company 2018. All rights reserved.
 * 文件名：QueryBetween
 * 版 本：1.0
 * 内容简述：between模型
 * 创建日期：2018/9/27 15:19
 * 创建人：cyl
 * 修改记录：
 * 日期  版本  修改人  修改内容
 */
package itc.common.tools.page;

import lombok.Data;

@Data
public class QueryBetween {
    /**
     * 字段名
     */
    private String column;
    /**
     * 值1
     */
    private String val1;
    /**
     * 值2
     */
    private String val2;
}
