package com.xiaoyun.active.controller.test;

import org.springframework.stereotype.Component;

/**
 * @author al
 * @date 2019/6/21 13:19
 * @description
 */
@Component
public class TestService {

    public String testGet(String name){
        return name+"! Now, you have got it!";
    }

}
