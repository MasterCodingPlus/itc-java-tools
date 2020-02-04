package itc.common.tools.response;

import org.springframework.http.HttpStatus;

/**
 * @program: prent
 * @description: 返回体帮助类
 * @author: Chen QiuXu - CoderZero
 * @create: 2019-04-25 11:11
 * ************************************************************
 * @uodateUser Kenary 李书羽
 * @uodateDate 2019-08-05 13:04
 * @UpdateRemark 完善统一使用HttpStatus状态码，完善注释，新增400错误码
 **/
public class ReponseUtills {

    /**
     * 获取没有数据返回体
     *
     * @param respNum 状态码
     * @param respMsg 信息
     * @return 返回体
     */
    public static Response getResNoData(Integer respNum, String respMsg) {
        return new Response().setRespNum(respNum).setRespMsg(respMsg);
    }

    /**
     * 获取有数据的返回体
     *
     * @param respNum 状态码
     * @param respMsg 信息
     * @return 返回体
     */
    public static Response getResponse(Integer respNum, String respMsg, Object data) {
        return new Response().setRespNum(respNum).setRespMsg(respMsg).setData(data);
    }

    /**
     * 200成功 无返回值
     *
     * @param msg 需要返回的msg
     * @return Response封装
     */
    public static Response success(String msg) {
        return getResNoData(HttpStatus.OK.value(), msg);
    }

    /**
     * 200成功 默认msg
     *
     * @param data 需要返回的data
     * @return Response封装
     */
    public static Response success(Object data) {
        return getResponse(HttpStatus.OK.value(), "请求成功!", data);
    }

    /**
     * 200成功 有返回值
     *
     * @param msg  需要返回的msg
     * @param data 需要返回的data
     * @return Response封装
     */
    public static Response success(String msg, Object data) {
        return getResponse(HttpStatus.OK.value(), msg, data);
    }

    /**
     * 500服务器报错 默认msg 无返回值
     *
     * @return Response封装
     */
    public static Response failBack() {
        return getResNoData(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器繁忙!");
    }

    /**
     * 500服务器报错 无返回值
     *
     * @param msg 需要返回的msg
     * @return Response封装
     */
    public static Response failBack(String msg) {
        return getResNoData(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    /**
     * 500服务器报错 有返回值
     *
     * @param msg 需要返回的msg
     * @return Response封装
     */
    public static Response failBack(String msg, Object data) {
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    /**
     * 400客户端错误 无返回值
     *
     * @return Response封装
     */
    public static Response badRequest() {
        return getResNoData(HttpStatus.BAD_REQUEST.value(), "请求错误!");
    }

    /**
     * 400客户端错误 无返回值
     *
     * @param msg 需要返回的msg
     * @return Response封装
     */
    public static Response badRequest(String msg) {
        return getResNoData(HttpStatus.BAD_REQUEST.value(), msg);
    }

    /**
     * 404资源不存在 无返回值
     *
     * @return Response封装
     */
    public static Response failFront() {
        return getResNoData(HttpStatus.NOT_FOUND.value(), "资源不存在!");
    }

    /**
     * 404资源不存在 无返回值
     *
     * @param msg 需要返回的msg
     * @return Response封装
     */
    public static Response failFront(String msg) {
        return getResNoData(HttpStatus.NOT_FOUND.value(), msg);
    }

    /**
     * 404资源不存在 有返回值
     *
     * @param msg  需要返回的msg
     * @param data 需要返回的data
     * @return Response封装
     */
    public static Response failFront(String msg, Object data) {
        return getResponse(HttpStatus.NOT_FOUND.value(), msg, data);
    }

    /**
     * 401权限不足 无返回值
     *
     * @return Response封装
     */
    public static Response permissionDenied() {
        return getResNoData(HttpStatus.UNAUTHORIZED.value(), "权限不足!");
    }

    /**
     * 401权限不足 无返回值
     *
     * @param msg 需要返回的msg
     * @return Response封装
     */
    public static Response permissionDenied(String msg) {
        return getResNoData(HttpStatus.UNAUTHORIZED.value(), msg);
    }

    /**
     * 401权限不足 有返回值
     *
     * @param msg  需要返回的msg
     * @param data 需要返回的data
     * @return Response封装
     */
    public static Response permissionDenied(String msg, Object data) {
        return getResponse(HttpStatus.UNAUTHORIZED.value(), msg, data);
    }

}
