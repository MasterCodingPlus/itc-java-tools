package itc.common.tools.compress;

import java.io.*;
import java.util.zip.*;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.compress
 * @ClassName: GZipUtil
 * @Description:
 * @Author: Mastercoding
 * @CreateDate: 2019-09-02 17:43
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019-09-02 17:43
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class GZipUtil {
    public static File compress(String filePath) {
        File target = null;
        File source = new File(filePath);
        if (source.exists()) {
            // 压缩文件名=源文件名.zip
            String zipName = source.getName() + ".gzip";
            target = new File(source.getParent(), zipName);
            if (target.exists()) {
                target.delete(); // 删除旧的文件
            }
            FileOutputStream fos = null;
            GZIPOutputStream zos = null;
            try {
                fos = new FileOutputStream(target);
                zos = new GZIPOutputStream(new BufferedOutputStream(fos));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtil.closeQuietly(zos, fos);
            }
        }
        return target;
    }

    public static void uncompress(String filePath) {
        File source = new File(filePath);
        if (source.exists()) {
            GZIPInputStream zis = null;
            BufferedOutputStream bos = null;
            try {
                zis = new GZIPInputStream(new FileInputStream(source));
                ZipEntry entry = null;
                File target = new File(source.getParent(), entry.getName());
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
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(datas);
            gzip.close();
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
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024 * 1024 * 10];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
