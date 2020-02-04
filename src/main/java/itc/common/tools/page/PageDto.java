/**
 * (C) Copyright Company 2018. All rights reserved.
 * 文件名：PageDto
 * 版 本：1.0
 * 内容简述：描述该文件实现的主要功能
 * 创建日期：2019/3/20 16:02
 * 创建人：cyl
 * 修改记录：
 * 日期  版本  修改人  修改内容
 */
package itc.common.tools.page;

import lombok.Data;

@Data
public class PageDto {
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 页面大小
     */
    private Integer pageSize;
}
