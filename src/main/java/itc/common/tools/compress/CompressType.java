package itc.common.tools.compress;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.compress
 * @ClassName: CompressType
 * @Description: 压缩类型
 * @Author: Mastercoding
 * @CreateDate: 2019-09-02 15:24
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019-09-02 15:24
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public enum CompressType {
    ZIP("zip", 1), GZIP("GZIP", 2), LZO("Lzo", 3), Lz4("Lz4", 4);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private CompressType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (CompressType c : CompressType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
