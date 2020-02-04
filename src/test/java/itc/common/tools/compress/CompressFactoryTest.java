package itc.common.tools.compress;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CompressFactoryTest {

    @Test
    public void compress() {
        byte[] datas = new byte[10000];
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            datas[i] = (byte) random.nextInt();
        }
        byte[] compressGZIP = CompressFactory.compress(datas, CompressType.GZIP);
        System.out.println(compressGZIP.length);
        byte[] compressLZO = CompressFactory.compress(datas, CompressType.LZO);
        System.out.println(compressLZO.length);
        byte[] compressZIP = CompressFactory.compress(datas, CompressType.ZIP);
        System.out.println(compressZIP.length);
        byte[] compressLz4 = CompressFactory.compress(datas, CompressType.Lz4);
        System.out.println(compressLz4.length);

        byte[] unCompressGZIP = CompressFactory.uncompress(compressGZIP, CompressType.GZIP);
        System.out.println(unCompressGZIP.length);
        byte[] unCompressLZO = CompressFactory.uncompress(compressLZO, CompressType.LZO);
        System.out.println(unCompressLZO.length);
        byte[] unCompressZIP = CompressFactory.uncompress(compressZIP, CompressType.ZIP);
        System.out.println(unCompressZIP.length);
        byte[] unCompressLz4 = CompressFactory.uncompress(compressLz4, CompressType.Lz4);
        System.out.println(unCompressLz4.length);
    }

    @Test
    public void uncompress() {

    }

    @Test
    public void testCompress() {

    }

    @Test
    public void testUncompress() {

    }
}