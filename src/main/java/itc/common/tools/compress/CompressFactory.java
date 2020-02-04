package itc.common.tools.compress;

import java.io.File;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.compress
 * @ClassName: CompressFactory
 * @Description: 工厂
 * @Author: Mastercoding
 * @CreateDate: 2019-09-02 17:56
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019-09-02 17:56
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class CompressFactory {
    /**
     * 压缩文件
     *
     * @param filePath
     * @return
     */
    public static File compress(String filePath, CompressType compressType) {
        switch (compressType) {
//            case ZIP:
//                return ZipUtil.c(filePath);
            case GZIP:
                return GZipUtil.compress(filePath);
            case LZO:
                return LzoUtil.compress(filePath);
            default:
                return null;
        }
    }

    /**
     * 解压
     *
     * @param filePath
     * @return
     */
    public static void uncompress(String filePath, CompressType compressType) {
        switch (compressType) {
//            case ZIP:
//                ZipUtil.uncompress(filePath);
            case GZIP:
                GZipUtil.uncompress(filePath);
            case LZO:
                LzoUtil.uncompress(filePath);
            default:
        }
    }

    /**
     * 压缩
     *
     * @param datas 数据
     * @return
     */
    public static byte[] compress(byte[] datas, CompressType compressType) {
        switch (compressType) {
//            case ZIP:
//                return ZipUtil.compress(datas);
            case GZIP:
                return GZipUtil.compress(datas);
            case LZO:
                return LzoUtil.compress(datas);
            case Lz4:
                return Lz4Util.lz4Compress(datas);
            default:
                return null;
        }
    }

    /**
     * 解压
     *
     * @param datas 数据
     * @return
     */
    public static byte[] uncompress(byte[] datas, CompressType compressType) {
        switch (compressType) {
//            case ZIP:
//                return ZipUtil.uncompress(datas);
            case GZIP:
                return GZipUtil.uncompress(datas);
            case LZO:
                return LzoUtil.uncompress(datas);
            case Lz4:
                return Lz4Util.lz4Decompress(datas);
            default:
                return null;
        }
    }
}
