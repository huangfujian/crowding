package com.gx.crowd.api;

import com.gx.crowd.entity.vo.ReturnJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 11:08
 */
@FeignClient("gx-crowd-redis")
public interface RedisServerRemote {
    @RequestMapping("/set/redis/key/value/remote")
    ReturnJson<String> setRedisKeyValueRemote(@RequestParam("key") String key,
                                              @RequestParam("value") String value);
    @RequestMapping("/set/redis/key/value/remote/timeout")
    ReturnJson<String> setRedisKeyValueRemoteTimeOut(@RequestParam("key") String key,
                                                     @RequestParam("value") String value,
                                                     @RequestParam("time") Long time,
                                                     @RequestParam("timeUnit") TimeUnit timeUnit);
    @RequestMapping("/get/redis/key/value/remote")
    ReturnJson<String> getRedisKeyValueRemote(@RequestParam("key") String key);
    @RequestMapping("/remove/redis/key/value/remote")
    ReturnJson<String> removeRedisKeyValueRemote(@RequestParam("key") String key);
}
