package org.iushu.weboot.bean;

public enum Role {

    STAFF(1),

    Actor(2),

    Customer(4),

    ;

    short id;
    Role(int id) {
        this.id = (short) id;
    }

    public short id() {
        return id;
    }

    public static boolean hasRole(short id) {
        for (Role value : values()) {
            if ((value.id & id) != 0)
                return true;
        }
        return false;
    }

}
