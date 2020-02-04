package itc.common.tools.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import itc.common.tools.id.SnowflakeIdWorker;

import java.sql.Date;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.sql
 * @ClassName: SQLExtend
 * @Description: 服务中心
 * @Author: Mastercoding
 * @CreateDate: 2019/4/24 22:55
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/4/24 22:55
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class SQLExtend {
    public static List<HashMap<String, Object>> getDataToMapBySql(String sql) {
        return getDataToMapBySql("Database.ini", sql);
    }

    public static List<HashMap<String, Object>> getDataToMapBySql(String dataBaseINI, String sql) {
        return getDataToMapBySql(getHikariDataSource(dataBaseINI), sql, Boolean.TRUE);
    }

    public static List<HashMap<String, Object>> getDataToMap(HikariDataSource hds, String tableName, Long minId, String orderByName, String orderBy, Integer topN, Boolean isAutoClose) {
        String sql = String.format("SELECT * FROM %s WHERE id>%d ORDER BY %s %s LIMIT %d", tableName, minId, orderByName, orderBy, topN);
        return getDataToMapBySql(hds, sql, isAutoClose);
    }

    public static List<HashMap<String, Object>> getDataToMapByIds(HikariDataSource hds, String tableName, String orderByName, String orderBy, Integer topN, Boolean isAutoClose, String... ids) {
        final String idsStr = String.join(",", ids);
        String sql = String.format("SELECT * FROM %s WHERE id in(%s) ORDER BY %s %s LIMIT %d", tableName, idsStr, orderByName, orderBy, topN);
        return getDataToMapBySql(hds, sql, isAutoClose);
    }

    public static List<HashMap<String, Object>> getDataToMapByIds(HikariDataSource hds, String tableName, Boolean isAutoClose, String... ids) {
        final String idsStr = String.join(",", ids);
        String sql = String.format("SELECT * FROM %s WHERE id in(%s)", tableName, idsStr);
        return getDataToMapBySql(hds, sql, isAutoClose);
    }

    public static List<HashMap<String, Object>> getDataToMapByIds(HikariDataSource hds, String tableName, Boolean isAutoClose, String tableKey, String... ids) {
        String idsStr = String.join(",", ids);
        String sql = String.format("SELECT * FROM %s WHERE %s in(%s)", tableName, tableKey, idsStr);
        return getDataToMapBySql(hds, sql, isAutoClose);
    }

    /**
     * 通过执行sql语句，转换成map
     *
     * @param sql
     * @return
     */
    public static List<HashMap<String, Object>> getDataToMapBySql(HikariDataSource hds, String sql, Boolean isAutoClose) {
        try {
            final Connection connection = hds.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData data = resultSet.getMetaData();
            ArrayList<HashMap<String, Object>> mapArrayList = new ArrayList<>();
            while (resultSet.next()) {
                HashMap<String, Object> map = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {// 数据库里从 1 开始
                    String c = data.getColumnName(i);
                    final Object v = resultSet.getObject(i);
                    map.put(c, v);
                }
                mapArrayList.add(map);
            }
            resultSet.close();
            statement.close();
            connection.close();
            if (isAutoClose) {
                hds.close();
            }
            return mapArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<HashMap<String, Object>> getDataToMapByTableName(String tableName) {
        return getDataToMapByTableName("Database.ini", tableName);
    }

    public static List<HashMap<String, Object>> getDataToMapByTableName(String dataBaseINI, String tableName) {
        String sql = String.format("SELECT * FROM %s", tableName);
        return getDataToMapBySql(dataBaseINI, sql);
    }

    public static List<HashMap<String, Object>> getDataToMap(String tableName, String orderByName, String orderBy) {
        return getDataToMap("Database.ini", tableName, orderByName, orderBy);
    }

    public static List<HashMap<String, Object>> getDataToMap(String dataBaseINI, String tableName, String orderByName, String orderBy) {
        String sql = String.format("SELECT * FROM %s ORDER BY %s %s", tableName, orderByName, orderBy);
        return getDataToMapBySql(dataBaseINI, sql);
    }

    public static List<HashMap<String, Object>> getDataToMap(String tableName, String orderByName, String orderBy, Integer topN) {
        return getDataToMap("Database.ini", tableName, orderByName, orderBy, topN);
    }

    public static List<HashMap<String, Object>> getDataToMap(String dataBaseINI, String tableName, String orderByName, String orderBy, Integer topN) {
        String sql = String.format("SELECT * FROM %s ORDER BY %s %s LIMIT %d", tableName, orderByName, orderBy, topN);
        return getDataToMapBySql(dataBaseINI, sql);
    }

    public static List<HashMap<String, Object>> getDataToMap(String tableName, Long minId, String orderByName, String orderBy, Integer topN) {
        return getDataToMap("Database.ini", tableName, minId, orderByName, orderBy, topN);
    }

    public static List<HashMap<String, Object>> getDataToMap(String dataBaseINI, String tableName, Long minId, String orderByName, String orderBy, Integer topN) {
        String sql = String.format("SELECT * FROM %s WHERE id>%d ORDER BY %s %s LIMIT %d", tableName, minId, orderByName, orderBy, topN);
        return getDataToMapBySql(dataBaseINI, sql);
    }

    public static void insertToDB(String dataBaseINI, String tableName, List<HashMap<String, Object>> hashMapList) {
        insertToDB(getHikariDataSource(dataBaseINI), tableName, hashMapList);
    }

    public static void insertToDB(HikariDataSource hds, String tableName, List<HashMap<String, Object>> hashMapList) {
        insertToDB(hds, tableName, hashMapList, Boolean.TRUE);
    }

    public static void insertToDB(HikariDataSource hds, String tableName, List<HashMap<String, Object>> hashMapList, Boolean isAutoClose) {
        try {
            final Connection connection = hds.getConnection();
            final Statement statement = connection.createStatement();
            StringBuilder sql;
            for (HashMap map : hashMapList) {
                sql = new StringBuilder("insert into " + tableName + "(");
                sql.append(String.join(",", map.keySet()));
                sql.append(") values(");
                Collection collection = map.values();
                StringBuilder finalSql = sql;
                collection.stream().forEach(t -> {
                    if (t instanceof String) {
                        finalSql.append("'" + t + "'");
                    } else if (t instanceof Date) {
                        finalSql.append("'" + t.toString() + "'");
                    } else {
                        finalSql.append(t);
                    }
                    finalSql.append(",");
                });
                finalSql.deleteCharAt(finalSql.length() - 1);
                sql.append(")");
//                statement.executeUpdate("delete FROM " + tableName + " where id=" + map.get("id"));
                System.out.println(sql);
                statement.executeUpdate(sql.toString());
            }
            statement.close();
            connection.close();
            if (isAutoClose) {
                hds.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入数据库
     *
     * @param tableName
     * @param hashMapList
     */
    public static void insertToDB(String tableName, List<HashMap<String, Object>> hashMapList) {
        insertToDB("Database.ini", tableName, hashMapList);
    }

    public static HikariDataSource getHikariDataSource() {
        return getHikariDataSource("Database.ini");
    }

    public static HikariDataSource getHikariDataSource(Properties properties) {
        //数据源
        HikariConfig hikariConfig = new HikariConfig(properties);
        return getHikariDataSource(hikariConfig);
    }

    public static HikariDataSource getHikariDataSource(String dataBaseINI) {
        //数据源
        HikariConfig hikariConfig = new HikariConfig(dataBaseINI);
        return getHikariDataSource(hikariConfig);
    }

    public static HikariDataSource getHikariDataSource(HikariConfig hikariConfig) {
        HikariDataSource hds = new HikariDataSource(hikariConfig);
        return hds;
    }

    public static Map<String, HashMap<String, Object>> getAllTables(String dataBaseINI, String... tableNames) {
        return getAllTables(getHikariDataSource(dataBaseINI), tableNames);
    }

    public static Map<String, HashMap<String, Object>> getAllTables(HikariDataSource hds, String... tableNames) {
        return getAllTables(hds, Boolean.TRUE, tableNames);
    }

    public static Map<String, HashMap<String, Object>> getAllTables(HikariDataSource hds, Boolean isAutoClose, String... tableNames) {
        try {
            final Connection connection = hds.getConnection();
            final Statement statement = connection.createStatement();
            //获取所有数据表
            DatabaseMetaData dbMetaData = connection.getMetaData();
            List<String> tables = null;
            if (tableNames == null || tableNames.length == 0) {
                tables = new ArrayList<>();
                final ResultSet resultSet = dbMetaData.getTables(null, "", null, new String[]{"TABLE"});
                while (resultSet.next()) {
                    tables.add(resultSet.getString("TABLE_NAME"));
                }
            } else {
                tables = Arrays.asList(tableNames);
            }
            Map tableMap = null;
            Map tableMaps = new HashMap<String, HashMap<String, Object>>();

            //获取数据表的字段名和字段数据类型
            for (String tableName : tables) {
                tableMap = new HashMap<String, Object>();
                String sql = "select * from " + tableName;
                ResultSet rs = statement.executeQuery(sql);
                ResultSetMetaData rsMetaData = rs.getMetaData();
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    tableMap.put(rsMetaData.getColumnName(i), rsMetaData.getColumnTypeName(i));
                }
                tableMaps.put(tableName, tableMap);
            }

            statement.close();
            connection.close();
            if (isAutoClose) {
                hds.close();
            }
            return tableMaps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, HashMap<String, Object>> getAllTables(String... tableNames) {
        return getAllTables("Database.ini", tableNames);
    }

    public static void insertIntoAllTablesRandom(HikariDataSource hikariDataSource, Boolean isAutoClose, Integer count, String... tableNames) {
        Map<String, HashMap<String, Object>> tableMaps = getAllTables(tableNames);
        Random random = new Random();
        for (String tableName : tableMaps.keySet()) {
            if (tableMaps.containsKey(tableName)) {
                insertIntoTableRandom(hikariDataSource, tableMaps.get(tableName), tableName, random, isAutoClose, count);
            }
        }
    }

    public static void insertIntoAllTablesRandom(Integer count, String... tableNames) {
        Map<String, HashMap<String, Object>> tableMaps = getAllTables(tableNames);
        Random random = new Random();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (String tableName : tableMaps.keySet()) {
                    if (tableMaps.containsKey(tableName)) {
                        insertIntoTableRandom(tableMaps.get(tableName), tableName, random, count);
                    }
                }
            }
        };
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public static void insertIntoTableRandom(HikariDataSource dataSource, HashMap<String, Object> tableMap, String tableName, Random random, Boolean isAutoClose, Integer count) {
        List entryList = new ArrayList();
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();
        for (int i = 0; i < count; i++) {
            Map<String, Object> entryMap = new HashMap<>();
            for (Map.Entry entry : tableMap.entrySet()) {
                if (entry.getKey().equals("STOREADDR")) {
                    entryMap.put(entry.getKey().toString(), "D:\\TEMP");
                    continue;
                }
                int value = random.nextInt(500);
                if (entry.getKey().equals("CALLIFILENAME") || entry.getKey().equals("LETID") || entry.getKey().equals("CALLEFILENAME")) {
                    if (entry.getKey().equals("CALLIFILENAME") || entry.getKey().equals("CALLEFILENAME")) {
                        if (value > 400 && value < 500) {
                            if (value > 400 && value < 433) {
                                entryMap.put(entry.getKey().toString(), "a.txt");
                            }
                            if (value > 433 && value < 466) {
                                entryMap.put(entry.getKey().toString(), "b.exe");
                            }
                            if (value > 466 && value < 500) {
                                entryMap.put(entry.getKey().toString(), "c.rar");
                            }
                        }
                    } else {
                        entryMap.put(entry.getKey().toString(), snowflakeIdWorker.nextId());
                    }
                    continue;
                }
                if ("BIGINT".equals(entry.getValue()) || "INT".equals(entry.getValue()) || "NUMBER".equals(entry.getValue())) {
                    entryMap.put(entry.getKey().toString(), Integer.valueOf(value));
                } else if ("VARCHAR".equals(entry.getValue()) || "VARCHAR2".equals(entry.getValue())) {
                    entryMap.put(entry.getKey().toString(), value + "str");
                } else if ("DATE".equals(entry.getValue())) {
                    Date date = new Date(new java.util.Date().getTime());
                    //entryMap.put(entry.getKey().toString(), String.format("TO_DATE(\"%S\",\"YYYY-MM-DD\"')", date));
                }
            }
            entryList.add(entryMap);
        }
        insertToDB(dataSource, tableName, entryList, isAutoClose);
    }

    public static void insertIntoTableRandom(HashMap<String, Object> tableMap, String tableName, Random random, Integer count) {
        final HikariDataSource hikariDataSource = getHikariDataSource();
        insertIntoTableRandom(hikariDataSource, tableMap, tableName, random, Boolean.TRUE, count);
    }

    /**
     * 执行sql得到值
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public static <T> T getOneObjBySQL(HikariDataSource hds, String sql, Boolean isAutoColse) throws Exception {
        final List<HashMap<String, Object>> resultSetBySQL = getResultSetBySQL(hds, sql, isAutoColse);
        if (resultSetBySQL == null || resultSetBySQL.isEmpty()) {
            return null;
        }
        return (T) resultSetBySQL.get(0)
                .values()
                .stream()
                .findFirst()
                .get();
    }

    private static List<HashMap<String, Object>> getResultSetBySQL(final HikariDataSource hds, final String sql, final Boolean isAutoColse) {
        return getDataToMapBySql(hds, sql, isAutoColse);
    }

    /**
     * 执行sql得到值
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public static <T> T getOneObjBySQL(String sql) throws Exception {
        return getOneObjBySQL(getHikariDataSource("Database.ini"), sql, Boolean.TRUE);
    }
}
