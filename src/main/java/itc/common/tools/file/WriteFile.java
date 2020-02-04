package itc.common.tools.file;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.*;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.file
 * @ClassName: WriteFile
 * @Description: 写文件类(用一句话描述该文件做什么)
 * @Author: Mastercoding
 * @CreateDate: 2019/9/9 12:27
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/9/9 12:27
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
@Accessors(chain = true)
public class WriteFile implements Closeable {

    @Getter
    private String filePath;
    private RandomAccessFile raf = null;

    public void openFile(String filePath) {
        try {
            raf = new RandomAccessFile(filePath, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件 （不存在则创建）
     *
     * @param datas     数据
     * @param srcOffset
     * @param srcLength
     * @param dstOffset
     */
    public void writeFile(byte[] datas, int srcOffset, int srcLength, long dstOffset) {
        if (datas == null || datas.length == 0) {
            return;
        }
        try {
            //设置位置
            raf.seek(dstOffset);
            if (datas.length != srcLength) {
                raf.write(datas, srcOffset, srcLength);
            } else {
                raf.write(datas);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        } catch (EOFException e) {
            System.out.println("reachs end before read enough bytes");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 写文件
     *
     * @param datas
     * @param dstOffset
     */
    public void writeFile(byte[] datas, long dstOffset) {
        writeFile(datas, 0, datas.length, dstOffset);
    }

    @Override
    public void close() throws IOException {
        if (raf != null) {
            raf.close();
        }
    }
}
