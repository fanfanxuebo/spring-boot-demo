package com.ki.springbootdubboproviderimpl.sevice.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ki.springbootdubboproviderapi.service.SpringBootDubboProviderService;
import com.ki.springbootdubboproviderimpl.util.DistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String testNet(String name, String ip) {
        LOGGER.info("testNet方法入参：name:[{}], ip:[{}]", name, ip);
        String respStr = "Hi, " + name + ", from " + ip + ". I'm provider.";
        LOGGER.info("testNet方法返参：[{}]", respStr);
        return respStr;
    }

    @Transactional
    @Override
    public List testTransaction(String applyYm, String storeCode, String copBankNo, String branchCode) {
        List list = null;
        DistributedLock distributedLock = new DistributedLock(stringRedisTemplate);
        String lockName = "testTransaction";
        String identifier = null;
        try {
            identifier = distributedLock.lockWithTimeout(lockName, 3000);
            list = updateById(applyYm, storeCode, copBankNo, branchCode);
            distributedLock.releaseLock(lockName, identifier);
        } catch (Exception e) {
            distributedLock.releaseLock(lockName, identifier);
            LOGGER.error("线程名称：{} error:{}", Thread.currentThread().getName(), e.toString());
        }
        return list;
    }

    private List updateById(String applyYm, String storeCode, String copBankNo, String branchCode) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT amount_used FROM cgp_cbm_regional_funds WHERE apply_ym = :applyYm AND store_code = :storeCode AND cop_bank_no = :copBankNo AND branch_code = :branchCode LIMIT 1;");
        Query nativeQuery = entityManager.createNativeQuery(sqlBuilder.toString());
        nativeQuery.setParameter("applyYm", applyYm);
        nativeQuery.setParameter("storeCode", storeCode);
        nativeQuery.setParameter("copBankNo", copBankNo);
        nativeQuery.setParameter("branchCode", branchCode);
        BigDecimal amountUsed = (BigDecimal) nativeQuery.getSingleResult();
        LOGGER.info("查询剩余金额:[{}]", amountUsed);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (0 == new BigDecimal(SpringBootDubboProviderImpl.num).compareTo(amountUsed)) {
            StringBuilder sqlBuilder2 = new StringBuilder("UPDATE cgp_cbm_regional_funds SET amount_used = amount_used - 50 WHERE apply_ym = :applyYm AND store_code = :storeCode AND cop_bank_no = :copBankNo AND branch_code = :branchCode ;");
            Query nativeQuery2 = entityManager.createNativeQuery(sqlBuilder2.toString());
            nativeQuery2.setParameter("applyYm", applyYm);
            nativeQuery2.setParameter("storeCode", storeCode);
            nativeQuery2.setParameter("copBankNo", copBankNo);
            nativeQuery2.setParameter("branchCode", branchCode);
            nativeQuery2.executeUpdate();
        }
        return new ArrayList();
    }
}
