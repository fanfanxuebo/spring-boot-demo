package com.ki.springbootdubboproviderimpl.sevice.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ki.springbootdubboproviderapi.service.SpringBootDubboProviderService;
import com.ki.springbootdubboproviderimpl.sevice.TestTransactionService;
import com.ki.springbootdubboproviderimpl.util.DistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author fanxuebo
 * @description dubbo服务统一对外调用接口实现类
 * @company ki
 * @createDate 2019-11-26 10:30:48 星期二
 */
@Service
public class SpringBootDubboProviderImpl implements SpringBootDubboProviderService {

    public static final Logger LOGGER = LoggerFactory.getLogger(SpringBootDubboProviderImpl.class);
    public static int num = 100;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TestTransactionService testTransactionService;

    @Override
    public String testNet(String name, String ip) {
        LOGGER.info("testNet方法入参：name:[{}], ip:[{}]", name, ip);
        String respStr = "Hi, " + name + ", from " + ip + ". I'm provider.";
        LOGGER.info("testNet方法返参：[{}]", respStr);
        return respStr;
    }

    @Override
    public List testTransaction(String applyYm, String storeCode, String copBankNo, String branchCode) {
        List list = null;
        DistributedLock distributedLock = new DistributedLock(stringRedisTemplate);
        String lockName = "testTransaction";
        String identifier = null;
        try {
            identifier = distributedLock.lockWithTimeout(lockName, 3000);
            list = testTransactionService.updateById(applyYm, storeCode, copBankNo, branchCode);
            distributedLock.releaseLock(lockName, identifier);
        } catch (Exception e) {
            distributedLock.releaseLock(lockName, identifier);
            LOGGER.error("线程名称：{} error:{}", Thread.currentThread().getName(), e.toString());
        }
        return list;
    }
}
