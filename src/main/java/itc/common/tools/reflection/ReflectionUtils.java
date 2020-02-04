
package itc.common.tools.reflection;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Set;


/**
 * 反射工具类
 *
 * @author MasterCoding
 * 2018-08-17 15:52:42
 */

@Slf4j
public final class ReflectionUtils {
    private ReflectionUtils() {
    }


    /**
     * 从指定的URL数组中寻找指定的类的子类集合
     *
     * @param jarPath Jar文件路径
     * @param type    指定的类
     * @param <T>     类型参数
     * @return 子类集合
     */

    public static <T> Set<Class<? extends T>> getSubTypes(
            final Path jarPath,
            final Class<T> type) {
        final URLClassLoader urlClassLoader = AccessController
                .doPrivileged(((PrivilegedAction<URLClassLoader>) () -> {
                    try {
                        return new URLClassLoader(
                                new URL[]{jarPath.toUri().toURL()},
                                ReflectionUtils.class.getClassLoader());
                    } catch (MalformedURLException e) {
                        log.error("错误的URL", e);
                        throw new IllegalStateException(e);
                    }
                }));
        //配置实例
        final ConfigurationBuilder conf = new ConfigurationBuilder()
                //子类扫描器
                .setScanners(new SubTypesScanner(true))
                //URL数组
                .setUrls(urlClassLoader.getURLs())
                //只扫描class文件
                .filterInputsBy(s -> s != null && s.endsWith(".class"));
        //设置类加载器
        conf.setClassLoaders(new ClassLoader[]{urlClassLoader});
        return new Reflections(conf).getSubTypesOf(type);
    }

    public static <T> Set<Class<? extends T>> getSubTypes(
            final URL[] jarUrls,
            final Class<T> type) {
        final URLClassLoader urlClassLoader = AccessController
                .doPrivileged(((PrivilegedAction<URLClassLoader>) () -> new URLClassLoader(
                        jarUrls,
                        ReflectionUtils.class.getClassLoader())));
        //配置实例
        final ConfigurationBuilder conf = new ConfigurationBuilder()
                //子类扫描器
                .setScanners(new SubTypesScanner(true))
                //URL数组
                .setUrls(urlClassLoader.getURLs())
                //只扫描class文件
                .filterInputsBy(s -> s != null && s.endsWith(".class"));
        //设置类加载器
        conf.setClassLoaders(new ClassLoader[]{urlClassLoader});
        return new Reflections(conf).getSubTypesOf(type);
    }
}

