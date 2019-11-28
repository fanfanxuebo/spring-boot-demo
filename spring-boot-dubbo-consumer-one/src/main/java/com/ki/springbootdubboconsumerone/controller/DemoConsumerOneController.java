package com.ki.springbootdubboconsumerone.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.ki.springbootdubboconsumerone.dto.TestTransactionDTO;
import com.ki.springbootdubboproviderapi.service.SpringBootDubboProviderService;
import com.ki.springcommon.constant.CommonResDTO;
import com.ki.springcommon.constant.ConstantRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author fanxuebo
 * @description dubbo客户端消费者controller
 * @company ki
 * @createDate 2019-11-26 10:42:23 星期二
 */
@RestController
@RequestMapping("demoConsumerOneController")
public class DemoConsumerOneController {

    public static final Logger LOGGER = LoggerFactory.getLogger(DemoConsumerOneController.class);

    @Reference
    private SpringBootDubboProviderService springBootDubboProviderService;

    @GetMapping(value = "testNet")
    public CommonResDTO testNet(@RequestParam(value = "name", defaultValue = "default") String name, @RequestParam(value = "ip", defaultValue = "0.0.0.0") String ip) {
        LOGGER.info("测试网络方法入参：name:[{}], ip:[{}]", name, ip);
        String respStr = springBootDubboProviderService.testNet(name, ip);
        LOGGER.info("测试网络方法返参：[{}]", respStr);
        return new CommonResDTO(respStr, null, null);
    }

    @PostMapping(value = "testTransaction")
    public CommonResDTO testTransaction(@RequestBody String reqJson) {
        LOGGER.info("测试事务方法入参：[{}]", reqJson);
        TestTransactionDTO testTransactionDTO = JSONObject.parseObject(reqJson, TestTransactionDTO.class);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    springBootDubboProviderService.testTransaction(
                            testTransactionDTO.getApplyYm(), testTransactionDTO.getStoreCode(),
                            testTransactionDTO.getCopBankNo(), testTransactionDTO.getBranchCode());
                }
            }).start();
        }
        return new CommonResDTO(ConstantRes.SUCCESS_MSG, null, null);
    }
}
