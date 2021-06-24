package org.iushu.weboot.exchange;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * @see org.springframework.http.HttpStatus
 *
 * @author iuShu
 * @since 6/24/21
 */
public class Response<T> {

    private int code;
    private String msg;
    private T payload;

    public static <T> Response<T> create(int code, String msg, T payload) {
        Response<T> response = new Response<>();
        response.code = code;
        response.msg = msg;
        response.payload = payload;
        return response;
    }

    public static Response of(HttpStatus status) {
        return create(status.value(), status.getReasonPhrase(), null);
    }

    public static Response success() {
        return success("");
    }

    public static Response success(String msg) {
        return success(msg, null);
    }

    public static <T> Response<T> success(T payload) {
        return success("", payload);
    }

    public static <T> Response<T> success(String msg, T payload) {
        return create(OK.value(), msg, payload);
    }

    public static Response failure() {
        return failure("");
    }

    public static Response failure(String msg) {
        return create(INTERNAL_SERVER_ERROR.value(), msg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", payload=" + payload +
                '}';
    }
}
