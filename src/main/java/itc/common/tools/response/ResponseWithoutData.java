package itc.common.tools.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 无数据的响应
 *
 * @author MasterCoding
 * 2018-08-28 00:20:09
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ResponseWithoutData {
    /**
     * 响应数
     */
    private Integer respNum;

    /**
     * 响应信息
     */
    private String respMsg;
}
