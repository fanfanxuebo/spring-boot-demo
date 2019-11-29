package com.ki.springbootdubboproviderimpl.sevice.impl;

import com.ki.springbootdubboproviderimpl.sevice.TestTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxuebo
 * @description 模拟业务实现类
 * @company sinosig
 * @createDate 2019-11-29 15:39:20 星期五
 */
@Service
public class TestTransactionServiceImpl implements TestTransactionService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TestTransactionServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public List updateById(String applyYm, String storeCode, String copBankNo, String branchCode) {
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
