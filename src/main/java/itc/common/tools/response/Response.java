package itc.common.tools.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 响应
 *
 * @author MasterCoding
 * 2018-08-28 00:16:47
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Accessors(chain = true)
public class Response {
    /**
     * 状态码
     */
    private Integer respNum;
    /**
     * 返回说明
     */
    private String respMsg;
    /**
     * 返回数据
     */
    private Object data;
}