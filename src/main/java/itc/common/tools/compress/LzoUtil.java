package itc.common.tools.compress;

import org.anarres.lzo.*;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.compress
 * @ClassName: LzoUtil
 * @Description: Lzo解压缩
 * @Author: Mastercoding
 * @CreateDate: 2019-09-02 17:47
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019-09-02 17:47
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class LzoUtil {
    public static File compress(String filePath) {
        File target = null;
        File source = new File(filePath);
        if (source.exists()) {
            // 压缩文件名=源文件名.zip
            String zipName = source.getName() + ".lzo";
            target = new File(source.getParent(), zipName);
            if (target.exists()) {
                target.delete(); // 删除旧的文件
            }
            FileOutputStream fos = null;
            LzoOutputStream lzos = null;
            try {
                fos = new FileOutputStream(target);
                LzoAlgorithm algorithm = LzoAlgorithm.LZO1X;
                LzoCompressor compressor = LzoLibrary.getInstance().newCompressor(algorithm, null);
                lzos = new LzoOutputStream(fos, compressor, 1024 * 1024 * 10);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtil.closeQuietly(lzos, fos);
            }
        }
        return target;
    }

    public static void uncompress(String filePath) {
        File source = new File(filePath);
        if (source.exists()) {
            LzoInputStream zis = null;
            BufferedOutputStream bos = null;
            try {
                LzoAlgorithm algorithm = LzoAlgorithm.LZO1X;
                LzoDecompressor decompressor = LzoLibrary.getInstance().newDecompressor(algorithm, null);
                zis = new LzoInputStream(new FileInputStream(source), decompressor);
                File target = new File(source.getName());
                if (!target.getParentFile().exists()) {
                    // 创建文件父目录
                    target.getParentFile().mkdirs();
                }
                // 写入文件
                bos = new BufferedOutputStream(new FileOutputStream(target));
                int read = 0;
                byte[] buffer = new byte[1024 * 1024 * 10];
                while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, read);
                }
                bos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtil.closeQuietly(zis, bos);
            }
        }
    }

    public static byte[] compress(byte[] datas) {
        if (datas == null || datas.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            LzoAlgorithm algorithm = LzoAlgorithm.LZO1X;
            LzoCompressor compressor = LzoLibrary.getInstance().newCompressor(algorithm, null);
            LzoOutputStream stream = new LzoOutputStream(out, compressor, 1024 * 1024 * 10);
            stream.write(datas);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            LzoAlgorithm algorithm = LzoAlgorithm.LZO1X;
            LzoDecompressor decompressor = LzoLibrary.getInstance().newDecompressor(algorithm, null);
            LzoInputStream streamInput = new LzoInputStream(in, decompressor);
            byte[] buffer = new byte[1024 * 1024 * 10];
            int n;
            while ((n = streamInput.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
