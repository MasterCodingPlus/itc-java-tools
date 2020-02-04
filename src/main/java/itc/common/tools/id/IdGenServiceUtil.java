//
//package itc.common.tools.id;
//
//import com.robert.vesta.service.bean.Id;
//import com.robert.vesta.service.factory.IdServiceFactoryBean;
//import com.robert.vesta.service.intf.IdService;
//import lombok.Getter;
//
//
///**
// * @ProjectName: java-tools
// * @Package: itc.common.tools.id
// * @ClassName: IdGenServiceUtil
// * @Description: id生成器
// * @Author: zoudaiqiang
// * @CreateDate: 2018/11/1 15:15
// * ***********************************************************
// * @UpdateUser: Chen QiuXu
// * @UpdateDate: 2019-4-4 10:01:24
// * @UpdateRemark: 整理
// * @Version: 1.0
// * ***********************************************************
// * Copyright: Copyright (c) 2018
// **/
//
//public class IdGenServiceUtil {
//    @Getter
//    public static IdService idService;
//
//    static {
//        IdServiceFactoryBean idServiceFactoryBean = new IdServiceFactoryBean();
//        idServiceFactoryBean.setMachineId(10);
//        idServiceFactoryBean.setProviderType(IdServiceFactoryBean.Type.PROPERTY);
//        idServiceFactoryBean.init();
//        try {
//            idService = idServiceFactoryBean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 生成一个id
//     *
//     * @return
//     */
//
//    public static String getNewId() {
//        if (idService != null) {
//            return String.valueOf(idService.genId()).replace("-", "");
//        }
//        return null;
//    }
//
//    /**
//     * 生成id
//     *
//     * @param seq 编号
//     * @return
//     */
//    public static String getNewId(Long seq) {
//        if (idService != null) {
//            return String.valueOf(idService.makeId(System.currentTimeMillis(), seq));
//        }
//        return null;
//    }
//
//    /**
//     * 得到具有编号的id
//     *
//     * @param id
//     * @return
//     */
//    public static Long getSeq(String id) {
//        //id = "-" + id;
//        final Id id1 = getId(Long.valueOf(id));
//        if (id1 != null) {
//            return id1.getSeq();
//        }
//        return null;
//    }
//
//    public static Id getId(Long id) {
//        if (idService != null) {
//            return idService.expId(id);
//        }
//        return null;
//    }
//}
//
