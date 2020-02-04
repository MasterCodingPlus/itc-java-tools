package itc.common.tools.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Slf4j
public class FileUtilsTest {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Logger first1 = LoggerFactory.getLogger("03GZDXREST0001");
            Logger first2 = LoggerFactory.getLogger("03GZDXREST0002");
            first1.info("12343");
            first1.error("12343");
            first1.warn("12343");
            first2.info("12343");
            first2.error("12343");
            first2.warn("12343");
            Thread.sleep(1000);
        }
    }

    @Test
    public void getFileToBytes() {
        FileUtils.bytesToFile("G:\\DownLoad\\123", "a.txt", "156456".getBytes());
    }

    @Test
    public void bytesToFile() throws InterruptedException {
//        for (int i = 0; i < 5; i++) {
//            new Thread(() ->
//            {
//                while (true) {
//                    byte[] bytes = new byte[1024 * 1024 * 5];
//                    FileUtils.writeFile("D:\\TEMP\\1.exe", bytes, 0);
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
        //        Thread.currentThread().join();

        while (true) {
            byte[] bytes = new byte[1024 * 1024 * 5];
            FileUtils.writeFile("D:\\TEMP\\1.exe", bytes, 0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void writeFile() throws InterruptedException {
        Logger first = LoggerFactory.getLogger("second");
        first.info("12343");


        while (true) {
            FileUtils.writeFile("./a.txt", new byte[100], 0);
            Thread.sleep(100000);
        }
    }

    @Test
    public void isCanWrite() {
        File file = new File("./a.txt");
        System.out.println(file.canWrite());
    }
}