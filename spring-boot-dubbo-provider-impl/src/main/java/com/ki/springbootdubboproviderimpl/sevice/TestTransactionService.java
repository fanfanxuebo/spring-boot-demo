package com.ki.springbootdubboproviderimpl.sevice;

import java.util.List;

/**
 * @author fanxuebo
 * @description 模拟业务接口类
 * @company sinosig
 * @createDate 2019-11-29 15:38:42 星期五
 */
public interface TestTransactionService {
    List updateById(String applyYm, String storeCode, String copBankNo, String branchCode);
}
