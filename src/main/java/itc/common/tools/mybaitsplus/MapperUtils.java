package itc.common.tools.mybaitsplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import itc.common.tools.file.FileUtils;
import itc.common.tools.reflection.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.mybaitsplus
 * @ClassName: MapperUtils
 * @Description: 服务中心
 * @Author: Mastercoding
 * @CreateDate: 2019/4/24 8:57
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/4/24 8:57
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
@Slf4j
public class MapperUtils {

    public static <T> ConcurrentHashMap<String, T> getMapperClasses(String pack) {
        ConcurrentHashMap<String, T> baseMapperHashMap = new ConcurrentHashMap<>();
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                //设置扫描路径
                .setUrls(ClasspathHelper.forPackage(pack))
                //设置扫描方式（子类扫描）
                .setScanners(new SubTypesScanner())//TODO:是否需要
                //过滤条件(允许什么通过)？？
                .filterInputsBy(s -> s != null && s.endsWith(".class")));

        Set<Class<? extends BaseMapper>> mappers = reflections.getSubTypesOf(BaseMapper.class);
        if (mappers == null) {
            log.info(pack + "该包下没有BaseMapper的子类");
            return null;
        }
        mappers.parallelStream().forEach(mapper ->
        {
            MapperEntity annotation = mapper.getAnnotation(MapperEntity.class);
            if (annotation != null) {
                baseMapperHashMap.put(annotation.value(), (T) mapper);
            }
        });
        return baseMapperHashMap;
    }

    /**
     * 根据实体获取Mappers，用于添加到Datasource
     *
     * @param pack :包路径
     * @return 实体对应的Mapper
     */
    public static Set<Class<? extends BaseMapper>> getMapperClass(String pack) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                //设置扫描路径
                .setUrls(ClasspathHelper.forPackage(pack))
//                .setUrls(ClasspathHelper.forJavaClassPath())
                //设置扫描方式（子类扫描）
                .setScanners(new SubTypesScanner())
                //过滤条件(允许什么通过)？？
                .filterInputsBy(s -> s != null && s.endsWith(".class")));
        Set<Class<? extends BaseMapper>> mappers1 = reflections.getSubTypesOf(BaseMapper.class);
        if (mappers1 == null || mappers1.isEmpty()) {
            log.warn(pack + "该包下没有BaseMapper的子类");
        }
        URL[] plugins = null;
        try {
            File dir = new File("plugins");
            if (dir.exists()) {
                plugins = FileUtils.getJarUrls(Paths.get("plugins"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (plugins != null) {
            final Set<Class<? extends BaseMapper>> mappers2 = ReflectionUtils.getSubTypes(plugins, BaseMapper.class);
            if (mappers2 == null || mappers2.isEmpty()) {
                log.warn(pack + "Plugins没有BaseMapper的子类");
            }
            mappers1.addAll(mappers2);
        }
        return mappers1;
    }

}

