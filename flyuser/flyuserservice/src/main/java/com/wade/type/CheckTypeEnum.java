package com.wade.type;

public enum CheckTypeEnum {

    USER_TYPE(1),
    PHONE_TYPE(2);

    private int type;

    CheckTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static CheckTypeEnum getCheckEnum(int type) {
        for (CheckTypeEnum typeEnum : CheckTypeEnum.values()) {
            if (typeEnum.getType() == type) {
                return typeEnum;
            }
        }
        return null;
    }
}
