package com.tommy.redisclusterexample.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePool;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static io.lettuce.core.ReadFrom.REPLICA_PREFERRED;

@Configuration
public class RedisConfig {
  @Bean
  public RedisConnectionFactory connectionFactory() {
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//    클러스터 노드 하나만 명시해주면 알아서 Lettuce가 다른 노드를 알아서 찾는다.
    redisClusterConfiguration.clusterNode("127.0.0.1", 5001);
//    redisClusterConfiguration.clusterNode("127.0.0.1", 5002);
//    redisClusterConfiguration.clusterNode("127.0.0.1", 5003);
//    redisClusterConfiguration.clusterNode("127.0.0.1", 5004);
//    redisClusterConfiguration.clusterNode("127.0.0.1", 5005);
//    redisClusterConfiguration.clusterNode("127.0.0.1", 5006);

    SocketOptions socketOptions = SocketOptions.builder()
      .connectTimeout(Duration.ofSeconds(3))
      .build();

    ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
      .enablePeriodicRefresh(Duration.ofSeconds(60))
      .enableAllAdaptiveRefreshTriggers()
//      .enableAllAdaptiveRefreshTriggers() (이벤트기반 업데이트)
      .build();

    ClientOptions clientOptions = ClusterClientOptions.builder().socketOptions(socketOptions).topologyRefreshOptions(clusterTopologyRefreshOptions).build();
    LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder().clientOptions(clientOptions).readFrom(REPLICA_PREFERRED).commandTimeout(Duration.ofSeconds(3L)).build();
    return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);

  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    return template;
  }
}
