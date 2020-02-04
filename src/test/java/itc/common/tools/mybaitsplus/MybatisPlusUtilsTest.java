//package itc.common.tools.mybaitsplus;
//
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
//import itc.common.tools.mybaitsplus.entity.User;
//import itc.common.tools.mybaitsplus.service.UserService;
//import org.junit.Test;
//
//import java.util.Map;
//
//
//public class MybatisPlusUtilsTest {
//
//    @Test
//    public void reLoad() {
//
//    }
//
//    @Test
//    public void insert() {
////        final User user = new User();
////        try {
////            final int insert = MybatisPlusUtils.getInstance().insert(user);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        UserService userService = new UserService();
//        userService.save(new User());
//    }
//
//    @Test
//    public void deleteById() {
//        UserService userService = new UserService();
//        User user = new User();
//        QueryWrapper<User> queryWrapper = new QueryWrapper(user);
//        final User entity = queryWrapper.getEntity();
//        queryWrapper.eq("id", 5);
//        userService.remove(queryWrapper);
//    }
//
//    @Test
//    public void findByName() {
//        try {
//
//            User user = new User();
//            UserService userService = new UserService();
//            QueryWrapper<User> queryWrapper = new QueryWrapper(user);
//            final User entity = queryWrapper.getEntity();
//            queryWrapper.eq("name", "user10");
//            Map rs = null;
//            rs = userService.getMap(queryWrapper);
//            System.out.println(rs.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void deleteByMap() {
//    }
//
//    @Test
//    public void delete() {
//    }
//
//    @Test
//    public void deleteBatchIds() {
//    }
//
//    @Test
//    public void updateById() {
//    }
//
//    @Test
//    public void update() {
//    }
//
//    @Test
//    public void selectById() {
//    }
//
//    @Test
//    public void selectBatchIds() {
//    }
//
//    @Test
//    public void selectByMap() {
//    }
//
//    @Test
//    public void selectOne() {
//    }
//
//    @Test
//    public void selectCount() {
//    }
//
//    @Test
//    public void selectList() {
//        Wrapper<User> queryWrapper = new Wrapper<User>() {
//            @Override
//            public String getSqlSegment() {
//                return null;
//            }
//
//            @Override
//            public User getEntity() {
//                return new User();
//            }
//
//            @Override
//            public MergeSegments getExpression() {
//                return null;
//            }
//
//            @Override
//            public String getCustomSqlSegment() {
//                return null;
//            }
//        };
////        final List<User> users = MybatisPlusUtils.getInstance().selectList(User, queryWrapper);
////        System.out.println(users);
//    }
//
//    @Test
//    public void selectMaps() {
//    }
//
//    @Test
//    public void selectObjs() {
//    }
//
//    @Test
//    public void selectPage() {
//    }
//
//    @Test
//    public void selectMapsPage() {
//    }
//
//    @Test
//    public void login() throws InterruptedException {
//
//    }
//}