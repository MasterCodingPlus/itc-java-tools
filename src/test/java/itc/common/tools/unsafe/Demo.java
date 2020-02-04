package itc.common.tools.unsafe;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

//http://hg.openjdk.java.net/jdk9/client/jdk/file/adc00ab4ac58/src/jdk.unsupported/share/classes/sun/misc/Unsafe.java
//http://hg.openjdk.java.net/jdk9/client/jdk/file/65464a307408/src/java.base/share/classes/jdk/internal/misc/Unsafe.java
public class Demo {
    private static String fieldB = "Unknown";
    private int fieldA = 2;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, UnsupportedEncodingException {
        demo0();
        demo1();
        demo2();
    }

    //创建对象, 而不经过构造方法
    private static void demo0() {
        Demo t2 = UnsafeWrapper.allocateInstance(Demo.class);
        System.out.println("demo0: Test2 = " + t2);
        System.out.println("demo0: fieldA = " + t2.fieldA);
        System.out.println("demo0: fieldB = " + Demo.fieldB);
    }

    //操作一个对象的属性 (private修饰的属性也可以操作)
    static void demo1() throws NoSuchFieldException {
        /**
         try {
         //不能直接调用Unsafe.getUnsafe()方法: Exception in thread "main" java.lang.SecurityException: Unsafe
         //Unsafe unsafe = Unsafe.getUnsafe();

         //使用反射获取Unsafe的实例, 绕过安全检查
         Unsafe unsafe = getUnsafe();
         Test2 t = new Test2();
         Field aField = Test2.class.getDeclaredField("fieldA");
         int aValue = unsafe.getInt(t, unsafe.objectFieldOffset(aField));
         System.out.println("fieldA = " + aValue);


         unsafe.putInt(t, unsafe.objectFieldOffset(aField), 102);
         aValue = unsafe.getInt(t, unsafe.objectFieldOffset(aField));
         System.out.println("fieldA = " + aValue);
         } catch(Exception e) {
         e.printStackTrace();
         }*/


        ///////////操作实例属性/////////////////
        Demo t = new Demo();
        System.out.println("primitive type: ");
        int fieldA = UnsafeWrapper.getInt(t, UnsafeWrapper.objectFieldOffset(Demo.class.getDeclaredField("fieldA")));
        System.out.println("fieldA value (before change) = " + fieldA);

        UnsafeWrapper.putInt(t, UnsafeWrapper.objectFieldOffset(Demo.class.getDeclaredField("fieldA")), 5023);
        fieldA = UnsafeWrapper.getInt(t, UnsafeWrapper.objectFieldOffset(Demo.class.getDeclaredField("fieldA")));
        System.out.println("fieldA value (after change) = " + fieldA);

        ///////////操作静态属性/////////////////
        System.out.println("reference type: ");
        String fieldB = (String) UnsafeWrapper.getObject(Demo.class, UnsafeWrapper.staticFieldOffset(Demo.class.getDeclaredField("fieldB")));
        System.out.println("fieldB value: " + fieldB);


        UnsafeWrapper.putObject(Demo.class, UnsafeWrapper.staticFieldOffset(Demo.class.getDeclaredField("fieldB")), "呵呵呵呵");
        fieldB = (String) UnsafeWrapper.getObject(Demo.class, UnsafeWrapper.staticFieldOffset(Demo.class.getDeclaredField("fieldB")));
        System.out.println("fieldB value: " + fieldB);
    }

    //管理和操作内存
    static void demo2() throws UnsupportedEncodingException {
        //开辟一块内存
        long initMemorySize = 20;
        long address = UnsafeWrapper.allocateMemory(initMemorySize);
        System.out.println("original memory address: " + address);
        String greeting = "Hello world !";
        byte[] bytes = greeting.getBytes(StandardCharsets.UTF_8);

        //将byte[]放入手动开辟的内存块中
        UnsafeWrapper.putByteArray(address, bytes);

        //从内指定的内存块中取出数据
        byte[] readedBytes = UnsafeWrapper.readByteArray(address, bytes.length);
        System.out.println("content in original memory: " + new String(readedBytes, StandardCharsets.UTF_8));


        //扩展内存大小
        long extMemorySize = 1024; //扩展的内存大小必须比之前分配的内存大
        address = UnsafeWrapper.reallocateMemory(address, extMemorySize);
        System.out.println("reallocate memory address: " + address);
        readedBytes = UnsafeWrapper.readByteArray(address, bytes.length);
        System.out.println("content in reallocate memory: " + new String(readedBytes, StandardCharsets.UTF_8));

        //释放内存
        UnsafeWrapper.freeMemory(address);
    }
}