package itc.common.tools.compress;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ZipUtilTest {

    @Test
    public void compress() {
//        File compress = ZipUtil.compress("E:\\apache-jmeter-5.1.1");
//        System.out.println(compress);
//        ZipUtil.compress("e:\\1", "E:\\1.txt", "E:\\2.zip", "E:\\3.txt");
    }


    @Test
    public void uncompress() {
        ZipUtil.decompressZip2Files("E:\\test.zip", "e:\\test1");
        System.out.println();
    }

    @Test
    public void compressFiles2ZipTest() {
        List<File> files = new ArrayList<>();
        File file = new File("E:\\TCP&UDP测试工具");
        File file1 = new File("E:\\apache-maven-3.6.2-bin.zip");
        File file2 = new File("E:\\123.docx");
        File file3 = new File("E:\\小工具1");
        files.add(file);
        files.add(file1);
        files.add(file2);
        files.add(file3);
        ZipUtil.compressFiles2Zip(files, new File("e:\\test.zip"), true);
    }

    @Test
    public void testCompress() {
    }

    @Test
    public void testUncompress() {
    }
}