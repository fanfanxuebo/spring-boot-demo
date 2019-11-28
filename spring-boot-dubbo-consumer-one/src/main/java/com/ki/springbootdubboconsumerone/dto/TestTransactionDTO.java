package com.ki.springbootdubboconsumerone.dto;

import com.ki.springcommon.constant.CommonReqDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author fanxuebo
 * @description 测试事务方法统一入参
 * @company ki
 * @createDate 2019-11-28 09:18:55 星期四
 */
@Getter
@Setter
@ToString
public class TestTransactionDTO extends CommonReqDTO {

    private String applyYm;
    private String storeCode;
    private String copBankNo;
    private String branchCode;
}
