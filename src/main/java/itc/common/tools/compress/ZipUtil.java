package itc.common.tools.compress;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 压缩工具类
 *
 * @author helloworld
 */
@Slf4j
public class ZipUtil {

    private static void writeFile(File file, ZipArchiveOutputStream zipArchiveOutputStream, String basePath) {
        ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getPath().replace(basePath, ""));
        try {
            zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                writeFile(listFile, zipArchiveOutputStream, basePath);
            }
        } else {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                byte[] buffer = new byte[1024 * 10 * 1024];
                int len = -1;
                while ((len = inputStream.read(buffer)) != -1) {
                    //把缓冲区的字节写入到ZipArchiveEntry
                    zipArchiveOutputStream.write(buffer, 0, len);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将文件打包成zip压缩包文件
     *
     * @param files              要压缩的文件
     * @param zipFile            压缩文件
     * @param deleteFileAfterZip 压缩文件后删除原来的文件，临时文件时记得删除
     * @return 是否压缩成功
     */
    public static boolean compressFiles2Zip(List<File> files, File zipFile, boolean deleteFileAfterZip) {
        if (!zipFile.getParentFile().exists()) {
            zipFile.getParentFile().mkdirs();
        }

        InputStream inputStream = null;
        ZipArchiveOutputStream zipArchiveOutputStream = null;
        try {
            zipArchiveOutputStream = new ZipArchiveOutputStream(zipFile);
            //Use Zip64 extensions for all entries where they are required
            zipArchiveOutputStream.setUseZip64(Zip64Mode.AsNeeded);
            for (File file : files) {
                writeFile(file, zipArchiveOutputStream, file.getParent());
            }
            zipArchiveOutputStream.closeArchiveEntry();
            zipArchiveOutputStream.finish();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                //关闭输入流
                if (null != inputStream) {
                    inputStream.close();
                }
                //关闭输出流
                if (null != zipArchiveOutputStream) {
                    zipArchiveOutputStream.close();
                }
                if (deleteFileAfterZip) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            itc.common.tools.file.FileUtils.delFolder(file.getPath());
                        } else {
                            for (int i = 0; i < 4; i++) {
                                if (file.delete()) {
                                    break;
                                }
                                System.gc();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 将zip压缩包解压成文件到指定文件夹
     *
     * @param zipFilePath   要解压的文件
     * @param targetDirPath 解压的目的路径
     * @return 是否成功
     */
    public static ArrayList<File> decompressZip2Files(String zipFilePath, String targetDirPath) {
        File file = new File(targetDirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //zip文件输入流
        ZipArchiveInputStream zipArchiveInputStream = null;
        ArchiveEntry archiveEntry = null;
        ArrayList<File> subFileList = new ArrayList<>();
        try {
            File zipFile = new File(zipFilePath);
            inputStream = new FileInputStream(zipFile);
            zipArchiveInputStream = new ZipArchiveInputStream(inputStream, "UTF-8");
            while (null != (archiveEntry = zipArchiveInputStream.getNextEntry())) {
                //获取文件名
                String archiveEntryFileName = archiveEntry.getName();
                //构造解压后文件的存放路径
                String archiveEntryPath = targetDirPath + "\\" + archiveEntryFileName;
                //把解压出来的文件写到指定路径
                File entryFile = new File(archiveEntryPath);
                if (archiveEntry.isDirectory()) {
                    if (!entryFile.exists()) {
                        boolean mkdirs = entryFile.mkdirs();
                    }
                    continue;
                }
                if (!entryFile.exists()) {
                    boolean mkdirs = entryFile.createNewFile();
                }
                subFileList.add(entryFile);
                byte[] buffer = new byte[1024 * 10 * 1024];
                outputStream = new FileOutputStream(entryFile);
                int len = -1;
                while ((len = zipArchiveInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != zipArchiveInputStream) {
                    zipArchiveInputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return subFileList;
    }

    public static List<File> fileFilter(List<File> srcFiles, String parentPath) {
        List<File> destFiles = srcFiles.parallelStream()
                .filter(t -> !t.isDirectory() && t.getParent().equals(parentPath))
                .collect(Collectors.toList());
        return destFiles;
    }

    public static ArrayList<File> decompressZip2Files(String zipFilePath) {
        File file = new File(zipFilePath);
        if (file.getParentFile().exists()) {
            file.getParentFile().delete();
            file.getParentFile().mkdirs();
        } else {
            file.getParentFile().mkdirs();
        }
        return decompressZip2Files(zipFilePath, file.getParent());
    }

    /**
     * 将文件打包成zip压缩包文件
     *
     * @param docs       要压缩的文件
     * @param targetFile 压缩文件
     * @return 是否压缩成功
     */
    public static boolean compressDoc2Zip(List<Pair<String, XWPFDocument>> docs, File targetFile) throws IOException {
        List<File> fileList = new ArrayList<>();
        for (Pair<String, XWPFDocument> doc : docs) {
            String name = doc.getFirst();
            XWPFDocument document = doc.getSecond();
            File file = convertDocument2File(document, name);
            fileList.add(file);
        }
        return compressFiles2Zip(fileList, targetFile, true);
    }

    /**
     * 将XWPFDocument转为File
     *
     * @param document 源文件
     * @param fileName file名称
     * @return 转换后的file
     * @throws IOException 转换那一层
     */
    public static File convertDocument2File(XWPFDocument document, String fileName) throws IOException {
        if (fileName.length() <= 3) {
            fileName += "-" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        }
        if (!fileName.endsWith("docx")) {
            fileName += ".docx";
        }

        File file = createTempFile(fileName);
        try (OutputStream os = new FileOutputStream(file)) {
            document.write(os);
        }

        return file;
    }

    /**
     * 创建临时文件
     */
    public static File createTempFile(String fullFileName) throws IOException {
        String tempDirectoryPath = FileUtils.getTempDirectoryPath();
        File file = new File(tempDirectoryPath + File.separator + fullFileName);
        file.deleteOnExit();
        boolean newFile = file.createNewFile();
        log.debug("newFile {} => {}", fullFileName, newFile);
        return file;
    }
//
//    public static void main(String[] args) {
//        File f1 = new File("/Users/my/Desktop/WechatIMG78.jpeg");
//        File f2 = new File("/Users/my/Desktop/WechatIMG79.jpeg");
//        File f3 = new File("/Users/my/Desktop/aaaaa.docx");
//
//        // 压缩文件
//        boolean b = compressFiles2Zip(new File[]{f1, f2, f3}, "/Users/my/Desktop/haha.zip");
//        log.info("compressFiles2Zip={}", b);
//
//        // 解压缩文件
//        boolean b1 = decompressZip2Files("/Users/my/Desktop/haha.zip", "/Users/zhangbaozhen/my/hahaha/");
//        log.info("decompressZip2Files={}", b1);
//    }

}