package itc.common.tools.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.encryption
 * @ClassName: Md5
 * @Description: md5加密(用一句话描述该文件做什么)
 * @Author: Mastercoding
 * @CreateDate: 2019/9/8 20:40
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/9/8 20:40
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class Md5Util {

    public static String getMd5Str(File file) {
        byte[] md5 = getMd5(file);
        BigInteger var5 = new BigInteger(1, md5);
        return String.format("%1$032x", var5);
    }

    public static byte[] getMd5(File file) {
        MessageDigest digest = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024];
        try {
            if (!file.isFile()) {
                return null;
            }
            digest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            while (true) {
                int len;
                if ((len = fis.read(buffer, 0, 1024)) == -1) {
                    fis.close();
                    break;
                }
                digest.update(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return digest.digest();
    }

    /**
     * 得到字节数组md5
     *
     * @param datas
     * @return
     */
    public static byte[] getMd5(byte[] datas) {
        //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
        MessageDigest mdInst = null;
        try {
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
        mdInst.update(datas);

        // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
        byte[] md = mdInst.digest();
        return md;
    }
}
