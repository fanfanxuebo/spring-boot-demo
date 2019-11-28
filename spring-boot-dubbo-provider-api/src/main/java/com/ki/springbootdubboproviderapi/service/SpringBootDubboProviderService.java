package com.ki.springbootdubboproviderapi.service;

import java.util.List;

/**
 * @author fanxuebo
 * @description dubbo服务对外统一调用接口
 * @company ki
 * @createDate 2019-11-26 10:28:33 星期二
 */
public interface SpringBootDubboProviderService {

    /**
     * @Author fanxuebo
     * @Date 2019/11/28 9:23
     * @Description 测试服务
     * @Parameters
     * @Returns
     **/
    String testNet(String name, String ip);

    List testTransaction(String applyYm, String storeCode, String copBankNo, String branchCode);
}
