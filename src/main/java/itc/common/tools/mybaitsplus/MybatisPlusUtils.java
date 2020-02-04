package itc.common.tools.mybaitsplus;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.type.JdbcType;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.mybaitsplus
 * @ClassName: MybatisPlusUtils
 * @Description: 服务中心
 * @Author: Mastercoding
 * @CreateDate: 2019/4/24 8:58
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/4/24 8:58
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class MybatisPlusUtils implements AutoCloseable {
    private static MybatisPlusUtils INSTANCE = null;

    private MybatisPlusUtils(final String databaseINI) {
        ReLoad(databaseINI);
    }

    public static MybatisPlusUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (MybatisPlusUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MybatisPlusUtils(MybatisPlusConfig.DATA_BASE_PROPERTY_NAME);
                }
            }
            return INSTANCE;
        }
        return INSTANCE;
    }

    /**
     * 慎用
     *
     * @param DatabaseINI
     * @return
     */
    public MybatisPlusUtils getNewInstance(String DatabaseINI) {
        return new MybatisPlusUtils(DatabaseINI);
    }

    private SqlSessionFactory sqlSessionFactory;
    private Map<Class, Class<? extends BaseMapper>> classHashMap = new ConcurrentHashMap<>();
    private HikariDataSource hds;

    @Override
    public void close() {
        if (hds != null) {
            hds.close();
        }
    }

    /**
     * 重新加载配置
     *
     * @param databaseINI
     */
    public void ReLoad(final String databaseINI) {
        classHashMap.clear();
        //数据源
        HikariConfig hikariConfig = new HikariConfig(databaseINI);
        hds = new HikariDataSource(hikariConfig);
        final MybatisSqlSessionFactoryBean mssfb = new MybatisSqlSessionFactoryBean();
        //设置分页插件
        mssfb.setPlugins(new Interceptor[]{new PaginationInterceptor()});
        //设置数据库
        mssfb.setDataSource(hds);
        final String aliasesPackageName = MybatisPlusConfig.ALIASES_PACKAGE_NAME;
        //实体类包名
        mssfb.setTypeAliasesPackage(aliasesPackageName);
        //配置
        final MybatisConfiguration mc = new MybatisConfiguration();
        mc.setJdbcTypeForNull(JdbcType.NULL);
        mc.setMapUnderscoreToCamelCase(false);
        //添加映射器
        final String mapperPackageName = MybatisPlusConfig.MAPPER_PACKAGE_NAME;
        final Set<Class<? extends BaseMapper>> mapperClasses = MapperUtils.getMapperClass(mapperPackageName);
        for (Class<? extends BaseMapper> mapperClass : mapperClasses) {
            if (mapperClass == null) {
                continue;
            }
            final Type[] genericInterfaces = mapperClass.getGenericInterfaces();
            //ParameterizedType参数化类型，即泛型
            ParameterizedType p = (ParameterizedType) genericInterfaces[0];
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            Class c = (Class) p.getActualTypeArguments()[0];
            classHashMap.put(c, mapperClass);
            mc.addMapper(mapperClass);
        }
        //更新配置
        mssfb.setConfiguration(mc);
        try {
            //获取会话工厂
            sqlSessionFactory = mssfb.getObject();
        } catch (Exception e) {
            //异常处理
            final String message = "获取SqlSessionFactory异常";
            throw new IllegalStateException(message, e);
        }
    }

    /**
     * 得到数据源
     *
     * @return
     */
    public DataSource getDataSource() {
        return hds;
    }

    public SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }

    public SqlSession openSession(boolean autoCommit) {
        return sqlSessionFactory.openSession(autoCommit);
    }

    public SqlSession openSession(Connection connection) {
        return sqlSessionFactory.openSession(connection);

    }

    public SqlSession openSession(TransactionIsolationLevel level) {
        return sqlSessionFactory.openSession(level);

    }

    public SqlSession openSession(ExecutorType execType) {
        return sqlSessionFactory.openSession(execType);
    }

    public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
        return sqlSessionFactory.openSession(execType, autoCommit);
    }

    public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
        return sqlSessionFactory.openSession(execType, level);
    }

    public SqlSession openSession(ExecutorType execType, Connection connection) {
        return sqlSessionFactory.openSession(execType, connection);
    }

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    public <T> int insert(T entity) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final int insert = baseMapper.insert(entity);
        mapperSession.getSqlSession().close();
        return insert;
    }

    public <T> MapperSession getBaseMapperSession(T entity) throws Exception {
        if (!classHashMap.containsKey(entity.getClass())) {
            throw new Exception("未注册" + entity.toString());
        }
        SqlSession sqlSession = openSession();
        final Class<? extends BaseMapper> mapper = classHashMap.get(entity.getClass());
        final BaseMapper baseMapper = sqlSession.getMapper(mapper);
        MapperSession mapperSession = new MapperSession(baseMapper, sqlSession);
        return mapperSession;
    }

    public <T> BaseMapper getBaseMapper(T entity) throws Exception {
        return getBaseMapperSession(entity).getBaseMapper();
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    public <T> int deleteById(T entity, Serializable id) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final int deleteById = baseMapper.deleteById(id);
        mapperSession.getSqlSession().close();
        return deleteById;
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    public <T> int deleteByMap(T entity, @Param(Constants.COLUMN_MAP) Map<String, Object> columnMap) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final int deleteByMap = baseMapper.deleteByMap(columnMap);
        mapperSession.getSqlSession().close();
        return deleteByMap;
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param wrapper 实体对象封装操作类（可以为 null）
     */
    public <T> int delete(T entity, @Param(Constants.WRAPPER) Wrapper<T> wrapper) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final int delete = baseMapper.delete(wrapper);
        mapperSession.getSqlSession().close();
        return delete;
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    public <T> int deleteBatchIds(T entity, @Param(Constants.COLLECTION) Collection<? extends Serializable> idList) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final int deleteBatchIds = baseMapper.deleteBatchIds(idList);
        mapperSession.getSqlSession().close();
        return deleteBatchIds;

    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     */
    public <T> int updateById(@Param(Constants.ENTITY) T entity) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final int updateById = baseMapper.updateById(entity);
        mapperSession.getSqlSession().close();
        return updateById;
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象 (set 条件值,可以为 null)
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     */
    public <T> int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final int update = baseMapper.update(entity, updateWrapper);
        mapperSession.getSqlSession().close();
        return update;
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    public <T> T selectById(T entity, Serializable id) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final T selectById = (T) baseMapper.selectById(id);
        mapperSession.getSqlSession().close();
        return selectById;
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    public <T> List<T> selectBatchIds(T entity, @Param(Constants.COLLECTION) Collection<? extends Serializable> idList) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final List<T> selectBatchIds = baseMapper.selectBatchIds(idList);
        mapperSession.getSqlSession().close();
        return selectBatchIds;
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    public <T> List<T> selectByMap(T entity, @Param(Constants.COLUMN_MAP) Map<String, Object> columnMap) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final List<T> deleteBatchIds = baseMapper.selectByMap(columnMap);
        mapperSession.getSqlSession().close();
        return deleteBatchIds;
    }

    /**
     * 根据 entity 条件，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public <T> T selectOne(T entity, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final T selectOne = (T) baseMapper.selectOne(queryWrapper);
        mapperSession.getSqlSession().close();
        return selectOne;
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public <T> Integer selectCount(T entity, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final Integer selectCount = baseMapper.selectCount(queryWrapper);
        mapperSession.getSqlSession().close();
        return selectCount;
    }

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public <T> List<T> selectList(T entity, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final List<T> selectList = baseMapper.selectList(queryWrapper);
        mapperSession.getSqlSession().close();
        return selectList;
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public <T> List<Map<String, Object>> selectMaps(T entity, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final List<Map<String, Object>> selectMaps = baseMapper.selectMaps(queryWrapper);
        mapperSession.getSqlSession().close();
        return selectMaps;
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     * <p>注意： 只返回第一个字段的值</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public <T> List<Object> selectObjs(T entity, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final List<Object> selectObjs = baseMapper.selectObjs(queryWrapper);
        mapperSession.getSqlSession().close();
        return selectObjs;

    }

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    public <T> IPage<T> selectPage(T entity, IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final IPage<T> selectPage = baseMapper.selectPage(page, queryWrapper);
        mapperSession.getSqlSession().close();
        return selectPage;
    }

    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类
     */
    public <T> IPage<Map<String, Object>> selectMapsPage(T entity, IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) throws Exception {
        final MapperSession mapperSession = getBaseMapperSession(entity);
        final BaseMapper baseMapper = mapperSession.getBaseMapper();
        final IPage<Map<String, Object>> selectMapsPage = baseMapper.selectMapsPage(page, queryWrapper);
        mapperSession.getSqlSession().close();
        return selectMapsPage;
    }

    @Data
    @AllArgsConstructor
    class MapperSession {
        private BaseMapper baseMapper;
        private SqlSession sqlSession;
    }
}
