package itc.common.tools.response;

import itc.common.tools.json.JsonUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void getRespNum() {
        final Response response = new Response();
        response.setRespMsg("45");
        response.setData("4456");
        response.setRespNum(200);
//        response.getMap().put("456", "4545");
//        response.getMap().put("45612", "4545");
//        response.getMap().put("4561", "4545");
        System.out.println(JsonUtil.toString(response));
    }
}