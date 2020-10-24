package com.tommy.redisclusterexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisClusterExampleApplication {
  public static void main(String[] args) {
    SpringApplication.run(RedisClusterExampleApplication.class, args);
  }
}
