package com.ki.springcommon.constant;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fanxuebo
 * @description 公共请求对象
 * @company ki
 * @createDate 2019-11-28 08:52:15 星期四
 */
@Getter
@Setter
public class CommonResDTO<T> implements Serializable {
    private static final long serialVersionUID = 5098229702138714205L;
    /**
     * 响应编码
     */
    private String resultCode;
    /**
     * 响应详细信息
     */
    private T resultMsg;
    /**
     * 提取时间（数据库查询时间）
     */
    private Date executeSqlTime;
    /**
     * 页面按钮展示
     */
    private T displayStatus;

    public CommonResDTO() {
    }

    public CommonResDTO(T successResultMsg, Date executeSqlTime, T displayStatus) {
        this.resultCode = ConstantRes.SUCCESS_CODE;
        this.resultMsg = successResultMsg;
        this.executeSqlTime = executeSqlTime;
        this.displayStatus = displayStatus;
    }

    public CommonResDTO(T errorResultMsg) {
        this.resultCode = ConstantRes.ERROR_CODE;
        this.resultMsg = errorResultMsg;
    }
}
