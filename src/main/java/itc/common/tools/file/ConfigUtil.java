package itc.common.tools.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.file
 * @ClassName: ConfigUtil
 * @Description: 配置文件帮助类
 * @Author: zoudaiqiang
 * @CreateDate: 2018/11/1 10:04
 * ***********************************************************
 * @UpdateUser: Chen QiuXu
 * @UpdateDate: 2019-4-4 10:01:24
 * @UpdateRemark: 整理修改
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2018
 **/
@Slf4j
public class ConfigUtil {
    public static final INIConfiguration config = new INIConfiguration();
    public final SubnodeConfiguration section;


    public ConfigUtil(String configFileName, String configName){
        try (final FileInputStream fis = new FileInputStream(configFileName);
             final InputStreamReader fr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            //读取配置文件
            config.read(fr);
            section = config.getSection(configName);
        } catch (Exception e) {
            final String message = "配置文件异常";
            log.error(message, e);
            throw new IllegalStateException(message, e);
        }
    }


    /**
     * 得到值
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        return section.getString(key);
    }

    /**
     * 得到值
     *
     * @param key
     * @return
     */
    public String getValue(String key, String defaultValue) {
        return section.getString(key, defaultValue);
    }

}
