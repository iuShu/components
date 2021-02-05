package org.iushu.kafka.alcohol.data;

import java.util.Arrays;

/**
 * A simple payload of the message entity.
 *
 * Created by iuShu on 19-2-11
 */
public class Payload {

    private long createTime;
    private String value;

    public Payload(String value) {
        this.value = value;
        this.createTime = System.currentTimeMillis();
    }

    public Payload(long createTime, String value) {
        this.value = value;
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "createTime=" + createTime +
                ", value='" + value + '\'' +
                '}';
    }

    public static byte[] head(byte[] data) {
        byte[] head = new byte[8];
        for (int i=0; i<8; i++)
            head[i] = data[i];
        return head;
    }

    public static byte[] tail(byte[] data) {
        byte[] tail = new byte[data.length - 8];
        for (int i=0; i<tail.length; i++)
            tail[i] = data[8+i];
        return tail;
    }

}
