package com.yasinyuan.testspring.core;

import com.yasinyuan.testspring.tools.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果生成器
 */
public class ResultGenerator {
    
    private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    private static final String DEFAULT_FAIL_MESSAGE = "操作失败";
    private static final String DEFAULT_UNAUTHORIZED_MESSAGE = "暂未登录或登录已过期";
    private static final String DEFAULT_FORBIDDEN_MESSAGE = "没有相关权限";

    public static R genSuccessResult() {
        R result = new R();
        result.put("code", ResultCode.SUCCESS.code());
        result.put("msg", DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    public static R genSuccessResult(Object data) {
        R result = new R();
        result.put("code", ResultCode.SUCCESS.code());
        result.put("msg", DEFAULT_SUCCESS_MESSAGE);
        result.put("data", data);
        return result;
    }

    public static R genSuccessResult(String msg, Object data) {
        R result = new R();
        result.put("code", ResultCode.SUCCESS.code());
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }

    public static R genFailResult() {
        R result = new R();
        result.put("code", ResultCode.FAIL.code());
        result.put("msg", DEFAULT_FAIL_MESSAGE);
        return result;
    }

    public static R genFailResult(String msg) {
        R result = new R();
        result.put("code", ResultCode.FAIL.code());
        result.put("msg", msg);
        return result;
    }

    public static R genFailResult(int code, String msg) {
        R result = new R();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    public static R genUnauthorizedResult() {
        R result = new R();
        result.put("code", ResultCode.UNAUTHORIZED.code());
        result.put("msg", DEFAULT_UNAUTHORIZED_MESSAGE);
        return result;
    }

    public static R genForbiddenResult() {
        R result = new R();
        result.put("code", ResultCode.INTERNAL_SERVER_ERROR.code());
        result.put("msg", DEFAULT_FORBIDDEN_MESSAGE);
        return result;
    }
}