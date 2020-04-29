package com.lx.login.demo.service.impl;

import com.lx.login.demo.entity.TestBo;
import com.lx.login.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/29 17:32
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Override
    public void test(@Valid TestBo bo) {
        log.info("测试 @Valid 不放在controller上行不行");
        log.info(bo.toString());
    }
}
