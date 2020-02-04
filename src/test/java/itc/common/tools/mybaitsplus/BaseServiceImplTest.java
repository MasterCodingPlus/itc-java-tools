//package itc.common.tools.mybaitsplus;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.enums.SqlMethod;
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import itc.common.tools.mybaitsplus.entity.User;
//import itc.common.tools.mybaitsplus.service.UserService;
//import itc.common.tools.page.QueryDto;
//import org.apache.ibatis.session.SqlSession;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//public class BaseServiceImplTest {
//
//    final UserService objectBaseService = new UserService();
//
//    @Test
//    public void save() {
//        objectBaseService.save(new User());
//    }
//
//    @Test
//    public void list() {
//        System.out.println(objectBaseService.list());
//    }
//
//    @Test
//    public void listTest(){
//        QueryDto queryDto = new QueryDto();
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "str");
//        queryDto.setLike(map);
//        final IPage<User> list = objectBaseService.list(queryDto);
//        System.out.println(list);
//    }
//
//    @Test
//    public void addBatch() {
//        List<User> userList = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            userList.add(new User());
//        }
//        objectBaseService.saveBatch(userList);
//    }
//
//    @Test
//    public void retBool() {
//        System.out.println(objectBaseService.retBool(52));
//    }
//
//    @Test
//    public void currentModelClass() {
//        System.out.println(objectBaseService.currentModelClass());
//    }
//
//    @Test
//    public void sqlSessionBatch() {
//        final SqlSession sqlSession = objectBaseService.sqlSessionBatch();
//        System.out.println(sqlSession);
//    }
//
//    @Test
//    public void closeSqlSession() {
//        final SqlSession sqlSession = objectBaseService.sqlSessionBatch();
//        objectBaseService.closeSqlSession(sqlSession);
//    }
//
//    @Test
//    public void sqlStatement() {
//        final String sqlStatement = objectBaseService.sqlStatement(SqlMethod.SELECT_MAPS);
//        System.out.println(sqlStatement);
//    }
//
//    @Test
//    public void save1() {
//        System.out.println(objectBaseService.save(new User()));
//    }
//
//    @Test
//    public void saveBatch() {
//        List<User> userList = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            final User e = new User();
//            e.setName("str" + i);
//            userList.add(e);
//        }
//        objectBaseService.saveBatch(userList);
//    }
//
//    @Test
//    public void saveOrUpdate() {
//        final User user = new User();
//        user.setId(1122737819852288001L);
//        user.setName("str1");
//        user.setEmail("123");
//        objectBaseService.saveOrUpdate(user);
//    }
//
//    @Test
//    public void saveOrUpdateBatch() {
//        List<User> userList = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            final User e = new User();
//            e.setName("str" + i);
//            userList.add(e);
//        }
//        objectBaseService.saveOrUpdateBatch(userList);
//    }
//
//    @Test
//    public void removeById() {
//        objectBaseService.removeById(1122737819852288001L);
//    }
//
//    @Test
//    public void removeByMap() {
//        final HashMap<String, Object> columnMap = new HashMap<>();
//        columnMap.put("name", "str1");
//        objectBaseService.removeByMap(columnMap);
//    }
//
//    @Test
//    public void remove() {
//        final QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("name", "str0");
//        objectBaseService.remove(wrapper);
//    }
//
//    @Test
//    public void removeByIds() {
//        objectBaseService.removeById(1122737820103946242L);
//    }
//
//    @Test
//    public void updateById() {
//        final User entity = new User();
//        entity.setId(1122737820103946243L);
//        entity.setAge(1);
//        objectBaseService.updateById(entity);
//    }
//
//    @Test
//    public void update() {
//        final User entity = new User();
//        entity.setAge(3);
//        final QueryWrapper<User> updateWrapper = new QueryWrapper<>();
//        updateWrapper.eq("name", "str4");
//        objectBaseService.update(updateWrapper);
//    }
//
//    @Test
//    public void update1() {
//        final User entity = new User();
//        entity.setAge(1);
//        final QueryWrapper<User> updateWrapper = new QueryWrapper<>();
//        updateWrapper.eq("name", "str4");
//        objectBaseService.update(entity, updateWrapper);
//    }
//
//    @Test
//    public void updateBatchById() {
//        final User entity = new User();
//        entity.setAge(123);
//        entity.setId(1122737820103946243L);
//        objectBaseService.updateById(entity);
//    }
//
//    @Test
//    public void getById() {
//        final User entity = new User();
//        entity.setId(1122737820103946243L);
//        entity.setAge(1000);
//        System.out.println(objectBaseService.updateById(entity));
//    }
//
//    @Test
//    public void listByIds() {
//        ArrayList<Long> ids = new ArrayList<>();
//        ids.add(1122737820103946243L);
//        ids.add(1122737820103946244L);
//        System.out.println(objectBaseService.listByIds(ids));
//    }
//
//    @Test
//    public void listByMap() {
//        Map<String, Object> columnMap = new HashMap<>();
//        columnMap.put("id", 1122737820103946243L);
//        System.out.println(objectBaseService.listByMap(columnMap));
//    }
//
//    @Test
//    public void getOne() {
//        final QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("id", 1122737820103946244L);
//        System.out.println(objectBaseService.getOne(queryWrapper));
//    }
//
//    @Test
//    public void getMap() {
//        final QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name", "str4");
//        System.out.println(objectBaseService.getMap(queryWrapper));
//    }
//
//    @Test
//    public void count() {
//        System.out.println(objectBaseService.count());
//    }
//
//    @Test
//    public void list1() {
//        final QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name", "str4");
//        System.out.println(objectBaseService.list(queryWrapper));
//    }
//
//    @Test
//    public void list2() {
//        objectBaseService.list();
//    }
//
//    @Test
//    public void page() {
//        Page<User> page = new Page<>();
//        page.setSize(20L);
//        page.setCurrent(2);
//        page.setPages(10);
//        final IPage<User> page1 = objectBaseService.page(page);
//        final List<User> records = page1.getRecords();
//        System.out.println(records);
//    }
//
//    @Test
//    public void page2() {
//        final QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name", "str4");
//        Page<User> page = new Page<>();
//        page.setSize(20L);
//        final IPage<User> page1 = objectBaseService.page(page, queryWrapper);
//        final List<User> records = page1.getRecords();
//        System.out.println(records);
//    }
//
//    @Test
//    public void listMaps() {
//        final List<Map<String, Object>> maps = objectBaseService.listMaps();
//        System.out.println(maps);
//    }
//
//    @Test
//    public void listObjs() {
//        final List<Object> objects = objectBaseService.listObjs();
//        System.out.println(objects);
//    }
//
//    @Test
//    public void pageMaps() {
//        final QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name", "str4");
//        Page<User> page = new Page<>();
//        page.setSize(20L);
//        final IPage<Map<String, Object>> mapIPage = objectBaseService.pageMaps(page, queryWrapper);
//        final List<Map<String, Object>> records = mapIPage.getRecords();
//        System.out.println(records);
//    }
//
//    @Test
//    public void getBaseMapper() {
//        final BaseMapper<User> baseMapper = objectBaseService.getBaseMapper();
//        System.out.println(baseMapper);
//    }
//
//    @Test
//    public void getResultSetBySQL() throws Exception {
//    }
//
//    @Test
//    public void getObjBySQL() throws Exception {
//    }
//
//    @Test
//    public void getFirst() {
//        final QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name", "893str");
//        final User first = objectBaseService.getFirst(queryWrapper);
//        System.out.println(first);
//    }
//
//    @Test
//    public void exist() {
//        final QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name", "893str1");
//        System.out.println(objectBaseService.exist(queryWrapper));
//    }
//
//    @Test
//    public void test() throws InterruptedException {
//        new Thread(() -> {
//            while (!Thread.currentThread().isInterrupted()){
//                System.out.println("I'm Thread1 by first created.");
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "Thread1").start();
//        new Thread(() -> {
//            while (!Thread.currentThread().isInterrupted()){
//                System.out.println("I'm Thread1 by second created.");
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "Thread2").start();
//
//        Thread.currentThread().join();
//    }
//}