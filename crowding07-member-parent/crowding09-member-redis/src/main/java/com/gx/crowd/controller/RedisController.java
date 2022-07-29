package com.gx.crowd.controller;

import com.gx.crowd.entity.vo.ReturnJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 10:47
 */
@RestController
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/set/redis/key/value/remote")
    public ReturnJson<String> setRedisKeyValueRemote(@RequestParam("key") String key,
                                                     @RequestParam("value") String value) {
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            return ReturnJson.returnSuccess();
        } catch (Exception e) {
            return ReturnJson.returnFail(e.getMessage());
        }
    }

    @RequestMapping("/set/redis/key/value/remote/timeout")
    public ReturnJson<String> setRedisKeyValueRemoteTimeOut(@RequestParam("key") String key,
                                                            @RequestParam("value") String value,
                                                            @RequestParam("time") Long time,
                                                            @RequestParam("timeUnit") TimeUnit timeUnit) {
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value, time, timeUnit);
            return ReturnJson.returnSuccess();
        } catch (Exception e) {
            return ReturnJson.returnFail(e.getMessage());
        }

    }

    @RequestMapping("/get/redis/key/value/remote")
    public ReturnJson<String> getRedisKeyValueRemote(@RequestParam("key") String key) {
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String value = operations.get(key);
            return ReturnJson.returnSuccessWithData(value);
        } catch (Exception e) {
            return ReturnJson.returnFail(e.getMessage());
        }
    }

    @RequestMapping("/remove/redis/key/value/remote")
    public ReturnJson<String> removeRedisKeyValueRemote(@RequestParam("key") String key) {
        try {
            stringRedisTemplate.delete(key);
            return ReturnJson.returnSuccess();
        } catch (Exception e) {
            return ReturnJson.returnFail(e.getMessage());
        }
    }
}
