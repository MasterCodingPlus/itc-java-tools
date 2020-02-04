package itc.common.tools.file;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * 文件工具
 *
 * @author MasterCoding
 * 2018-08-17 15:20:11
 */
@Slf4j
public final class FileUtils {
    private FileUtils() {
    }

    //删除文件夹
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath;
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//删除指定文件夹下的所有文件

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                boolean delete = temp.delete();
                for (int j = 0; j < 4; j++) {
                    System.gc();
                    if (temp.delete()) {
                        break;
                    }

                }
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取指定目录下Jar文件的路径列表
     * <p>
     * 注：遍历深度为2
     *
     * @param start 指定目录的路径
     * @return Jar文件的路径列表
     * @throws IOException 发生I/O异常
     */
    public static List<Path> getJarPaths(@NonNull Path start) throws IOException {
        //URI列表
        return Files.walk(start, 2)
                //名称以.jar结束
                .filter(p -> p.toString().endsWith(".jar"))
                //是常规文件且可读
                .filter(p -> {
                    //尝试创建ZipFile
                    try (JarFile file = new JarFile(p.toFile(), true)) {
                        file.close();
                        return true;
                    } catch (IOException e) {
                        log.error("文件[{}]不是ZIP格式，放弃该文件", e);
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * 去除拓展名
     *
     * @param file 文件
     * @return 去除拓展名后的文件名
     */
    public static String stripExtension(File file) {
        final String name = file.getName();
        // Get position of last '.'.
        final int pos = name.lastIndexOf(".");
        // If there wasn't any '.' just return the string as is.
        if (pos == -1) {
            return name;
        }
        // Otherwise return the string, up to the dot.
        return name.substring(0, pos);
    }

    /**
     * 读取文件成byte数组
     *
     * @param path
     * @return
     */
    public static byte[] getFileToBytes(String path) {
        File file = new File(path);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("发送文件太大");
            return null;
        }
        try {
            FileInputStream fi = new FileInputStream(file);
            byte[] buffer = new byte[(int) fileSize];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            if (offset != buffer.length) {
                System.out.println("未读取完");
            }
            fi.close();
            return buffer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * byte[] 数组存文件
     *
     * @param filePath
     * @param fileName
     * @param bfile
     */
    public static void bytesToFile(String filePath, String fileName, byte[] bfile) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static URL[] getJarUrls(@NonNull Path start) throws IOException {
        //URI列表
        return Files.walk(start, 2)
                //名称以.jar结束
                .filter(p -> p.toString().endsWith(".jar"))
                //是常规文件且可读
                .filter(p -> {
                    //尝试创建ZipFile
                    try (JarFile file = new JarFile(p.toFile(), true)) {
                        file.close();
                        return true;
                    } catch (IOException e) {
                        log.error("文件[{}]不是ZIP格式，放弃该文件", e);
                        return false;
                    }
                }).map(jar -> {
                    try {
                        return jar.toUri().toURL();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(URL[]::new);
    }

    /**
     * 写文件 （不存在则创建）
     *
     * @param fileName  文件名
     * @param datas     数据
     * @param srcOffset
     * @param srcLength
     * @param dstOffset
     */
    public static void writeFile(String fileName, byte[] datas, int srcOffset, int srcLength, long dstOffset) {
        if (datas == null || datas.length == 0 || fileName == null) {
            return;
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileName, "rw");
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
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写文件
     *
     * @param fileName
     * @param datas
     * @param dstOffset
     */
    public static void writeFile(String fileName, byte[] datas, long dstOffset) {
        writeFile(fileName, datas, 0, datas.length, dstOffset);
    }

    /**
     * 创建文件
     *
     * @param fileName 文件名称
     * @return
     * @throws IOException
     */
    public static boolean createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            return true;
        }
        return true;
    }

    /**
     * 创建文件
     *
     * @param fileName   文件名称
     * @param fileLength 文件长度
     * @return
     * @throws IOException
     */
    public static boolean createFile(String fileName, long fileLength) throws IOException {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileName, "rw");
            raf.setLength(fileLength);
        } finally {
            raf.close();
        }
        return true;
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * File  to byte[]
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] returnFileByte(String filePath) {
        FileInputStream fileInputStream = null;
        ByteBuffer byteBuffer = null;
        try {
            fileInputStream = new FileInputStream(new File(filePath));
            FileChannel channel = fileInputStream.getChannel();
            byteBuffer = ByteBuffer.allocate((int) channel.size());
            channel.read(byteBuffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteBuffer.array();
    }

    /**
     * createFile
     *
     * @param fileByte
     * @param filePath
     */
    public static void createFile(byte[] fileByte, String filePath) {
        BufferedOutputStream bufferedOutputStream;
        FileOutputStream fileOutputStream;
        File file = new File(filePath);
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(fileByte);
            fileOutputStream.close();
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

