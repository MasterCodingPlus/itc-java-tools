package itc.common.tools.cmd;

import lombok.Getter;

import java.io.Serializable;

public enum CMDTools implements Serializable {
    CREATE("CREATE", "创建"),
    DELETE("DELETE", "删除"),
    UPDATE("UPDATE", "更新"),
    READ("READ", "查询");
    @Getter
    private final String cmd;
    @Getter
    private final String desc;

    CMDTools(String cmd, String desc) {
        this.cmd = cmd;
        this.desc = desc;
    }
}
