package com.gunners.epes.config;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    @Value("${spring.profiles.active}")
    private String active;

    @Bean
    public RedissonClient redisson() throws IOException {
        String file = StrUtil.format("redisson-{}.yaml", active);
        Config config = Config.fromYAML(new ClassPathResource(file).getInputStream());
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
