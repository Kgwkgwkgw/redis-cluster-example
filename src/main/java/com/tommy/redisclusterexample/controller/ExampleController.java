package com.tommy.redisclusterexample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ExampleController {
  @Resource(name = "redisTemplate")
  ValueOperations<String, Object> valueOperations;

  @GetMapping("/set")
  public String setCall (@RequestParam String key, @RequestParam String val) {
    valueOperations.set(key, val);
    return "key : " + key + ", val :" + val;
  }
  @GetMapping("get")
  public String getCall (@RequestParam String key) {
    String val = (String) valueOperations.get(key);
    return "key : " + key + ", val :" + val;
  }
}
