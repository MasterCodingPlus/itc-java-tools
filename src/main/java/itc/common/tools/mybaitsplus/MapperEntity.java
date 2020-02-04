package itc.common.tools.mybaitsplus;

/**
 * @ProjectName: lei
 * @Package: itc.real.time.common.anno
 * @ClassName: MapperEntity
 * @Description: 自定义注解，用于标注Mapper的泛型
 * @Author: 雷家俊
 * @CreateDate: 2018/11/15 15:03
 * ***********************************************************
 * @UpdateUser: 雷家俊
 * @UpdateDate: 2018/11/15 15:03
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2018
 **/

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

/**
 * 自定义注解，用于标注Mapper的泛型
 */
public @interface MapperEntity {
    /**
     * 泛型对应的实体类名
     *
     * @return
     */
    String value();
}
