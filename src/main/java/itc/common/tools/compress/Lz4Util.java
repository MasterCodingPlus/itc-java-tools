package itc.common.tools.compress;

import net.jpountz.lz4.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ProjectName: Lz4Util
 * @Package: itc.common.tools.compress.Lz4Util
 * @ClassName: Lz4Util
 * @Description:
 * @Author: Mastercoding
 * @CreateDate: 2019-08-26 15:58
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019-08-26 15:58
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/

public class Lz4Util {

    /**
     * @param srcByte 原始数据
     * @return 压缩后的数据
     */
    public static byte[] compressedByte(byte[] srcByte) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        LZ4Compressor compressor = factory.fastCompressor();
        return compressor.compress(srcByte);
    }

    /**
     * @param compressorByte 压缩后的数据
     * @param srcLength      压缩前的数据长度
     * @return
     */
    public static byte[] decompressorByte(byte[] compressorByte, int srcLength) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        LZ4FastDecompressor decompressor = factory.fastDecompressor();
        return decompressor.decompress(compressorByte, srcLength);
    }

    /**
     * @param srcByte
     * @return
     * @throws IOException
     */
    public static byte[] lz4Compress(byte[] srcByte) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        LZ4Compressor compressor = factory.fastCompressor();
        LZ4BlockOutputStream compressedOutput = new LZ4BlockOutputStream(byteOutput, 1024 * 1024 * 10, compressor);
        try {
            compressedOutput.write(srcByte);
            compressedOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteOutput.toByteArray();
    }

    /**
     * @param compressorByte
     * @return
     * @throws IOException
     */
    public static byte[] lz4Decompress(byte[] compressorByte) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 1024 * 10);
        LZ4FastDecompressor decompresser = factory.fastDecompressor();
        LZ4BlockInputStream lzis = new LZ4BlockInputStream(new ByteArrayInputStream(compressorByte), decompresser);
        int count;
        byte[] buffer = new byte[1024 * 1024 * 10];
        try {
            while ((count = lzis.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }
            lzis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}