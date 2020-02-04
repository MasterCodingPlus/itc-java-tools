package itc.common.tools.sql;

import com.zaxxer.hikari.HikariDataSource;
import itc.common.tools.json.JsonUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SQLExtendTest {


    @Test
    public void getDataToMapBySql() {
        System.out.println(SQLExtend.getDataToMapBySql("SELECT * FROM STUDENT"));
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("ID", 112);
        map.put("SCHOOLID", 12);
        map.put("NAME", "张三");
//        map.put("CREATETIME",new Date(new java.util.Date().getTime()));
        hashMapList.add(map);
        SQLExtend.insertToDB("STUDENT", hashMapList);
    }

    @Test
    public void getDataToMapByTableName() {
        System.out.println(SQLExtend.getDataToMapByTableName("STUDENT"));
    }

    @Test
    public void getDataToMap() {
        System.out.println(SQLExtend.getDataToMap("user", "id", "desc"));
    }

    @Test
    public void getDataToMap1() {
        System.out.println(SQLExtend.getDataToMap("user", "id", "desc", 5));
    }

    @Test
    public void insertToDB() throws InterruptedException {

    }

    public void sub(byte[] bytes) {
        try {
            List from = JsonUtil.from(bytes, List.class);
            SQLExtend.insertToDB("user", from);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertIntoAllTables() throws InterruptedException {
        final HikariDataSource hikariDataSource = SQLExtend.getHikariDataSource();
        final Random random = new Random();
        while (true) {
            SQLExtend.insertIntoAllTablesRandom(hikariDataSource, Boolean.FALSE, 10, "T_VOICEMANAGAFTREDU");
            Thread.sleep(random.nextInt(10000));
        }
    }
}