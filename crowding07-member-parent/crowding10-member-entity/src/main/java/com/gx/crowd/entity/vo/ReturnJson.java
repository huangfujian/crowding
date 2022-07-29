package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 10:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnJson<T> {
    private int code;
    private String msg;
    private Boolean status;
    private T data;

    public static ReturnJson returnSuccess() {
        return new ReturnJson(200, null, true, null);
    }

    public static ReturnJson returnSuccessMsg(String msg) {
        return new ReturnJson(200, msg, true, null);
    }

    public static <E> ReturnJson<E> returnSuccessWithData(E data) {
        return new ReturnJson<E>(200, null, true, data);
    }

    public static ReturnJson returnFail(String msg) {
        return new ReturnJson(444, msg, false, null);
    }

    public static ReturnJson returnFail() {
        return new ReturnJson(444, null, false, null);
    }
}
