package itc.common.tools.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.msgpack.annotation.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chen QiuXu
 */
@Data
@Accessors(chain = true)
@Message
public class HttpServletRequest {

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求协议
     */
    private String protocolVersion;

    /**
     * 请求源IP
     */
    private String ip;

    /**
     * 请求的后台接口地址（Get请求不带请求内容）
     */
    private String requestUri;

    /**
     * 暂未用
     */
    private String requestUrl;

    /**
     * 请求的地址（Get请求带请求内容）
     */
    private String relativeUri;

    /**
     * 请求头
     */
    private Map<String, String> header;

    /**
     * Get请求参数列表
     */
    private Map<String, List<String>> queryList;

    /**
     * 文件上传内容（包含分块上传信息及附带信息等）
     */
    private HashMap<String, byte[]> fileData;

    /**
     * 请求内容大小（暂未用）
     */
    private Integer contextLength = 0;

    /**
     * 请求体内容（不包含Get请求地址中附带内容及文件请求内容）
     */
    private byte[] bodyBytes;
}
