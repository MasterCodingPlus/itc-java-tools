package itc.common.tools.messagepack;

import com.fasterxml.jackson.databind.JsonNode;
import itc.common.tools.json.JsonUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class JacksonMsgPackUtilTest {

    @Test
    public void toBytes() {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            hashMap.put("" + i, "val:" + i);
        }
        System.out.println(new String(JacksonMsgPackUtil.toBytes(hashMap)));
    }

    @Test
    public void from() {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            hashMap.put("" + i, "val:" + i);
        }
        byte[] bytes = JacksonMsgPackUtil.toBytes(hashMap);
        System.out.println(new String(bytes));
        try {
            HashMap hashMap1 = JacksonMsgPackUtil.from(bytes, HashMap.class);
            System.out.println(hashMap1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void obj2Bean() {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            hashMap.put("" + i, "val:" + i);
        }
        Object o = hashMap;
        HashMap hashMap1 = JacksonMsgPackUtil.obj2Bean(o, HashMap.class);
        System.out.println(hashMap1);
    }

    @Test
    public void byte2JsonNode() {
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            hashMap.put("" + i, "val:" + i);
        }
        byte[] bytes = JacksonMsgPackUtil.toBytes(hashMap);
        byte[] bytes1 = JsonUtil.toBytes(hashMap);
        try {
            JsonNode jsonNode = JacksonMsgPackUtil.byte2JsonNode(bytes);
            System.out.println(jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}