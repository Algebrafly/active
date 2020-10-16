package com.xiaoyun.active.config;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author al
 * @date 2020/10/16 14:51
 * @description
 */
@Component
public class UUIDGenerator implements IdGenerator {
    @Override
    public String getNextId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
