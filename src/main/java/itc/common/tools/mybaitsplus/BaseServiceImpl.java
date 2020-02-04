//package itc.common.tools.mybaitsplus;
//
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.enums.SqlMethod;
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.core.metadata.TableInfo;
//import com.baomidou.mybatisplus.core.toolkit.*;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
//import itc.common.tools.page.QueryBetween;
//import itc.common.tools.page.QueryDto;
//import org.apache.ibatis.binding.MapperMethod;
//import org.apache.ibatis.session.SqlSession;
//import org.mybatis.spring.SqlSessionUtils;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.Serializable;
//import java.lang.reflect.Array;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
///**
// * @ProjectName: prent
// * @Package: itc.project.data.share.das.service.impl
// * @ClassName: BaseServiceImpl
// * @Description: 服务中心
// * @Author: Mastercoding
// * @CreateDate: 2019/4/24 12:22
// * ***********************************************************
// * @UpdateUser: Mastercoding
// * @UpdateDate: 2019/4/24 12:22
// * @UpdateRemark: The modified content
// * @Version: 1.0
// * ***********************************************************
// * Copyright: Copyright (c) 2019
// **/
//public class BaseServiceImpl<T> implements IBaseService<T> {
//    /**
//     * 判断数据库操作是否成功
//     *
//     * @param result 数据库操作返回影响条数
//     * @return boolean
//     */
//    protected boolean retBool(Integer result) {
//        return SqlHelper.retBool(result);
//    }
//
//    protected Class<T> currentModelClass() {
//        final Class superClassGenericType = ReflectionKit.getSuperClassGenericType(this.getClass(), 0);
//        return superClassGenericType;
//    }
//
//    /**
//     * 批量操作 SqlSession
//     */
//    protected SqlSession sqlSessionBatch() {
//        MybatisPlusUtils.getInstance();
//        return SqlHelper.sqlSessionBatch(currentModelClass());
//    }
//
//    /**
//     * 释放sqlSession
//     *
//     * @param sqlSession session
//     */
//    protected void closeSqlSession(SqlSession sqlSession) {
//        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
//    }
//
//    /**
//     * 获取 SqlStatement
//     *
//     * @param sqlMethod ignore
//     * @return ignore
//     */
//    protected String sqlStatement(SqlMethod sqlMethod) {
//        MybatisPlusUtils.getInstance();
//        final Class<T> clazz = currentModelClass();
//        return SqlHelper.table(clazz).getSqlStatement(sqlMethod.getMethod());
//    }
//
//    @Override
//    public boolean save(T entity) {
//        try {
//            return retBool(MybatisPlusUtils.getInstance().insert(entity));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 批量插入
//     *
//     * @param entityList ignore
//     * @param batchSize  ignore
//     * @return ignore
//     */
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public boolean saveBatch(Collection<T> entityList, int batchSize) {
//        MybatisPlusUtils.getInstance();
//        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
//        try (SqlSession batchSqlSession = sqlSessionBatch()) {
//            int i = 0;
//            for (T anEntityList : entityList) {
//                batchSqlSession.insert(sqlStatement, anEntityList);
//                if (i >= 1 && i % batchSize == 0) {
//                    batchSqlSession.flushStatements();
//                }
//                i++;
//            }
//            batchSqlSession.flushStatements();
//        }
//        return true;
//    }
//
//    /**
//     * TableId 注解存在更新记录，否插入一条记录
//     *
//     * @param entity 实体对象
//     * @return boolean
//     */
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public boolean saveOrUpdate(T entity) {
//        if (null != entity) {
//            MybatisPlusUtils.getInstance();
//            Class<?> cls = entity.getClass();
//            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
//            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
//            String keyProperty = tableInfo.getKeyProperty();
//            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
//            Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
//            return StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal)) ? save(entity) : updateById(entity);
//        }
//        return false;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
//        MybatisPlusUtils.getInstance();
//        Assert.notEmpty(entityList, "error: entityList must not be empty");
//        Class<?> cls = currentModelClass();
//        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
//        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
//        String keyProperty = tableInfo.getKeyProperty();
//        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
//        try (SqlSession batchSqlSession = sqlSessionBatch()) {
//            int i = 0;
//            for (T entity : entityList) {
//                Object idVal = ReflectionKit.getMethodValue(cls, entity, keyProperty);
//                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
//                    batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), entity);
//                } else {
//                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
//                    param.put(Constants.ENTITY, entity);
//                    batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
//                }
//                // 不知道以后会不会有人说更新失败了还要执行插入 😂😂😂
//                if (i >= 1 && i % batchSize == 0) {
//                    batchSqlSession.flushStatements();
//                }
//                i++;
//            }
//            batchSqlSession.flushStatements();
//        }
//        return true;
//    }
//
//    @Override
//    public boolean removeById(Serializable id) {
//        final Class<T> tClass = currentModelClass();
//        try {
//            return SqlHelper.delBool(MybatisPlusUtils.getInstance().deleteById(tClass.newInstance(), id));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean removeByMap(Map<String, Object> columnMap) {
//        Assert.notEmpty(columnMap, "error: columnMap must not be empty");
//        final Class<T> tClass = currentModelClass();
//        try {
//            return SqlHelper.delBool(MybatisPlusUtils.getInstance().deleteByMap(tClass.newInstance(), columnMap));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean remove(Wrapper<T> wrapper) {
//        try {
//            final Class<T> tClass = currentModelClass();
//            return SqlHelper.delBool(MybatisPlusUtils.getInstance().delete(tClass.newInstance(), wrapper));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean removeByIds(Collection<? extends Serializable> idList) {
//        final Class<T> tClass = currentModelClass();
//        try {
//            return SqlHelper.delBool(MybatisPlusUtils.getInstance().deleteBatchIds(tClass.newInstance(), idList));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean updateById(T entity) {
//        try {
//            return retBool(MybatisPlusUtils.getInstance().updateById(entity));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean update(Wrapper<T> updateWrapper) {
//        try {
//            final Class<T> tClass = currentModelClass();
//            return retBool(MybatisPlusUtils.getInstance().update(tClass.newInstance(), updateWrapper));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    @Override
//    public boolean update(T entity, Wrapper<T> updateWrapper) {
//        try {
//            return retBool(MybatisPlusUtils.getInstance().update(entity, updateWrapper));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
//        Assert.notEmpty(entityList, "error: entityList must not be empty");
//        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
//        try (SqlSession batchSqlSession = sqlSessionBatch()) {
//            int i = 0;
//            for (T anEntityList : entityList) {
//                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
//                param.put(Constants.ENTITY, anEntityList);
//                batchSqlSession.update(sqlStatement, param);
//                if (i >= 1 && i % batchSize == 0) {
//                    batchSqlSession.flushStatements();
//                }
//                i++;
//            }
//            batchSqlSession.flushStatements();
//        }
//        return true;
//    }
//
//    @Override
//    public T getById(Serializable id) {
//        final Class<T> tClass = currentModelClass();
//        try {
//            return MybatisPlusUtils.getInstance().selectById(tClass.newInstance(), id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
//        final Class<T> tClass = currentModelClass();
//        try {
//            return MybatisPlusUtils.getInstance().selectBatchIds(tClass.newInstance(), idList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public Collection<T> listByMap(Map<String, Object> columnMap) {
//        final Class<T> tClass = currentModelClass();
//        try {
//            return MybatisPlusUtils.getInstance().selectByMap(tClass.newInstance(), columnMap);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
//        if (throwEx) {
//            try {
//                final Class<T> tClass = currentModelClass();
//                return MybatisPlusUtils.getInstance().selectOne(tClass.newInstance(), queryWrapper);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//        try {
//            final Class<T> tClass = currentModelClass();
//            return SqlHelper.getObject(MybatisPlusUtils.getInstance().selectList(tClass.newInstance(), queryWrapper));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
//        try {
//            final Class<T> tClass = currentModelClass();
//            return SqlHelper.getObject(MybatisPlusUtils.getInstance().selectMaps(tClass.newInstance(), queryWrapper));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public int count(Wrapper<T> queryWrapper) {
//        try {
//            final Class<T> tClass = currentModelClass();
//            return SqlHelper.retCount(MybatisPlusUtils.getInstance().selectCount(tClass.newInstance(), queryWrapper));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//    @Override
//    public List<T> list(Wrapper<T> queryWrapper) {
//        try {
//            MybatisPlusUtils.getInstance();
//            final Class<T> tClass = currentModelClass();
//            return MybatisPlusUtils.getInstance().selectList(tClass.newInstance(), queryWrapper);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
//        try {
//            final Class<T> tClass = currentModelClass();
//            return MybatisPlusUtils.getInstance().selectPage(tClass.newInstance(), page, queryWrapper);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
//        try {
//            final Class<T> tClass = currentModelClass();
//            return MybatisPlusUtils.getInstance().selectMaps(tClass.newInstance(), queryWrapper);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
//        try {
//            final Class<T> tClass = currentModelClass();
//            return MybatisPlusUtils.getInstance().selectObjs(tClass.newInstance(), queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
//        try {
//            final Class<T> tClass = currentModelClass();
//            return MybatisPlusUtils.getInstance().selectMapsPage(tClass.newInstance(), page, queryWrapper);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public BaseMapper<T> getBaseMapper() {
//        final Class<T> tClass = currentModelClass();
//        try {
//            return MybatisPlusUtils.getInstance().getBaseMapper(tClass.newInstance());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public IPage<T> list(QueryDto queryDto) {
//        Page<T> page = null;
//        QueryWrapper condition = new QueryWrapper<T>();
//
//        //设置分页参数
//        if (queryDto.getPage() != null) {
//            page = new Page<T>(queryDto.getPage().getPageNum(), queryDto.getPage().getPageSize());
//        }
//
//        //添加所有不等于的条件
//        final Map<String, Object> ne = queryDto.getNe();
//        if (ne != null) {
//            for (Map.Entry<String, Object> neObj : ne.entrySet()) {
//                condition.ne(neObj.getKey(), neObj.getValue());
//            }
//        }
//
//        //添加所有等于的条件
//        final Map<String, Object> allEq = queryDto.getAllEq();
//        if (allEq != null) {
//            condition.allEq(queryDto.getAllEq());
//        }
//
//        //添加所有大于的条件
//        final Map<String, Object> gt = queryDto.getGt();
//        if (gt != null) {
//            final Set<Map.Entry<String, Object>> entries = gt.entrySet();
//            for (Map.Entry<String, Object> gtEntry : entries) {
//                condition.gt(gtEntry.getKey(), gtEntry.getValue());
//            }
//        }
//
//        //添加所有大于等于条件
//        final Map<String, Object> ge = queryDto.getGe();
//        if (ge != null) {
//            final Set<Map.Entry<String, Object>> entries = ge.entrySet();
//            for (Map.Entry<String, Object> geEntry : entries) {
//                condition.ge(geEntry.getKey(), geEntry.getValue());
//            }
//        }
//
//        //添加所有小于条件
//        final Map<String, Object> lt = queryDto.getLt();
//        if (lt != null) {
//            final Set<Map.Entry<String, Object>> entries = lt.entrySet();
//            for (Map.Entry<String, Object> ltEntry : entries) {
//                condition.lt(ltEntry.getKey(), ltEntry.getValue());
//            }
//        }
//
//        //添加所有小于等于条件
//        final Map<String, Object> le = queryDto.getLe();
//        if (le != null) {
//            final Set<Map.Entry<String, Object>> entries = le.entrySet();
//            for (Map.Entry<String, Object> leEntry : entries) {
//                condition.le(leEntry.getKey(), leEntry.getValue());
//            }
//        }
//
//        //添加所有模糊查询条件 LIKE
//        final Map<String, Object> allLike = queryDto.getLike();
//        if (allLike != null) {
//            final Set<Map.Entry<String, Object>> entries = allLike.entrySet();
//            for (Map.Entry<String, Object> likeEntry : entries) {
//                condition.like(likeEntry.getKey(), likeEntry.getValue());
//            }
//        }
//
//        //添加模糊查询 NOT LIKE
//        final Map<String, Object> notLike = queryDto.getNotLike();
//        if (notLike != null) {
//            final Set<Map.Entry<String, Object>> entries = notLike.entrySet();
//            for (Map.Entry<String, Object> notLikeEntry : entries) {
//                condition.notLike(notLikeEntry.getKey(), notLikeEntry.getValue());
//            }
//        }
//
//        //添加 IN
//        final Map<String, Array> in = queryDto.getIn();
//        if (in != null) {
//            final Set<Map.Entry<String, Array>> entries = in.entrySet();
//            for (Map.Entry<String, Array> inEntry : entries) {
//                condition.in(inEntry.getKey(), inEntry.getValue());
//            }
//        }
//
//        //添加 NOT IN
//        final Map<String, Array> notIn = queryDto.getNotIn();
//        if (notIn != null) {
//            final Set<Map.Entry<String, Array>> entries = notIn.entrySet();
//            for (Map.Entry<String, Array> notInEntry : entries) {
//                condition.notIn(notInEntry.getKey(), notInEntry.getValue());
//            }
//        }
//
//        //添加NULL值查询
//        final List isNull = queryDto.getIsNull();
//        if (isNull != null){
//            for (Object o : isNull) {
//                condition.isNull(o);
//            }
//        }
//
//        //添加非NULL值查询
//        final List isNotNull = queryDto.getIsNotNull();
//        if (isNotNull != null){
//            for (Object o : isNotNull) {
//                condition.isNotNull(o);
//            }
//        }
//
//        //添加分组 GROUP BY
//        List groupBy = queryDto.getGroupBy();
//        if (groupBy != null) {
//            for (Object o : groupBy) {
//                condition.groupBy(o);
//            }
//        }
//
//        //添加 HAVING
//        Map<String, Object> having = queryDto.getHaving();
//        if (having != null){
//            Set<Map.Entry<String, Object>> entries = having.entrySet();
//            for (Map.Entry<String, Object> entry : entries) {
//                condition.having(entry.getKey(), entry.getValue());
//            }
//        }
//
//        //添加 排序 ORDER BY
//        String orderBy = queryDto.getOrderBy();
//        if (!StringUtils.isEmpty(orderBy)) {
//            String[] orderByKeys = orderBy.split(" ");
//            condition.orderBy(true, orderByKeys[1].toLowerCase().equals("asc"), orderByKeys[0]);
//        }
//
//        //添加 ASC 排序 ORDER BY
//        String orderAsc = queryDto.getOrderAsc();
//        if (!StringUtils.isEmpty(orderAsc)) {
//            String[] orderByAsc = orderAsc.split(" ");
//            for (String s : orderByAsc) {
//                condition.orderByAsc(s);
//            }
//        }
//
//        //添加 Desc 排序 ORDER BY
//        String orderDesc = queryDto.getOrderDesc();
//        if (!StringUtils.isEmpty(orderDesc)) {
//            String[] orderByDesc = orderDesc.split(" ");
//            for (String s : orderByDesc) {
//                condition.orderByDesc(s);
//            }
//        }
//
//        //添加查询字段
//        if (queryDto.getSelectNames() != null) {
//            condition.select(queryDto.getSelectNames());
//        }
//
//
//        if (queryDto.getBetween() != null) {
//            for (QueryBetween between : queryDto.getBetween()) {
//                condition.between(between.getColumn(), between.getVal1(), between.getVal2());
//            }
//        }
//
//        if (page == null) {
//            page = new Page();
//        }
//        return page(page, condition);
//    }
//
//    /**
//     * 得到第一个
//     *
//     * @param queryWrapper
//     * @return
//     */
//    public T getFirst(QueryWrapper<T> queryWrapper) {
//        Page<T> page = new Page<>();
//        page.setSize(1);
//        final List<T> records = page(page, queryWrapper).getRecords();
//        if (records == null || records.isEmpty()) {
//            return null;
//        }
//        return records.get(0);
//    }
//
//    /**
//     * 得到第一个
//     *
//     * @param queryWrapper
//     * @return
//     */
//    public Boolean exist(QueryWrapper<T> queryWrapper) {
//        return getFirst(queryWrapper) != null;
//    }
//}
//
