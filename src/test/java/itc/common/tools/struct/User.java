package itc.common.tools.struct;

import lombok.Data;
import org.msgpack.annotation.Message;
import struct.StructClass;
import struct.StructField;

/**
 * @program: java-tools
 * @description:
 * @author: Chen QiuXu - CoderZero
 * @create: 2019-08-22 14:09
 **/
@StructClass
@Data
@Message
public class User {

    @StructField(order = 0)
    public long name;

    @StructField(order = 1)
    public int age;

}
