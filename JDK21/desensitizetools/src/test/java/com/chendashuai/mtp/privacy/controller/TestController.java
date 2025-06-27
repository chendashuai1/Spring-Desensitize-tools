package com.chendashuai.mtp.privacy.controller;

import com.chendashuai.desensitizetools.annotation.EnableSensitive;
import com.chendashuai.mtp.privacy.entity.TestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EnableSensitive(enable = true)
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    /**
     * 测试RESTful接口脱敏
     * @return
     */
    @GetMapping("/all")
    public List<TestVO> getAllUser() {
        List<TestVO> list = new ArrayList<>();
        list.add(new TestVO(1L, "zhou", "abcde@126.com", "19999999999", "abcd110"));
        list.add(new TestVO(2L, "wu", "abcde@126.com", "19999999999", "caojcvnaunv198"));
        list.add(new TestVO(3L, "zheng", "abcde@126.com", "19999999999", "vjvjsj"));
        list.add(new TestVO(4L, "wang", "abcde126.com", "19999999999", "123110"));
        return list;
    }

    /**
     * 测试日志脱敏
     */
    @GetMapping("/info")
    public void info(){
        log.info("phone:{}, password:{}" ,"15310763497", ":saqwc?!");
        log.info("your phone:{}, your password:{}" , "15310763497", ":saqwc?!");
        log.info("your phone:{}, your password={}" , "15310763497", ":saqwc?!");

        HashMap<String, String> map = new HashMap<>();
        map.put("phone", "15310763497");
        map.put("password", ":saqwc?!");
        log.info("one map={}",map);

        // 对象类型
        log.info("student:{}", new TestVO(1L, "zhou", "abcde@126.com", "19999999999", "abcd110"));
    }

}
