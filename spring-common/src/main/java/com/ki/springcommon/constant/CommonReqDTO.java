package com.ki.springcommon.constant;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author fanxuebo
 * @description 公共请求对象
 * @company ki
 * @createDate 2019-11-28 08:56:49 星期四
 */
@Getter
@Setter
public class CommonReqDTO implements Serializable {
    private static final long serialVersionUID = 5098229702138714205L;

    private String loginUserCode;
    private String currentPage;
    private String itemsPerPage;
}
