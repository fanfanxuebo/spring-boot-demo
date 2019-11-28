package com.ki.springcommon.constant;

import java.io.Serializable;

/**
 * @author fanxuebo
 * @description 公共常量对象
 * @company ki
 * @createDate 2019-11-28 08:45:13 星期四
 */
public class ConstantRes implements Serializable {
    private static final long serialVersionUID = 5098229702138714205L;

    public static final String SUCCESS_CODE = "000000";
    public static final String SUCCESS_MSG = "操作成功";
    public static final String ERROR_CODE = "000001";
    public static final String ERROR_MSG = "当前服务器请求繁忙，请稍后重试！";
}
