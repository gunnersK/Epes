package com.gunners.epes.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.gunners.epes.mapper")
public class MybatisPlusConfig {
}
