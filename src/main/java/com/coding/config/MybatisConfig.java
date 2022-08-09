package com.coding.config;

import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.autoconfigure.ConfigurationCustomizer;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan("com.coding.mapper")
public class MybatisConfig implements ConfigurationCustomizer {
    @Override
    public void customize(org.apache.ibatis.session.Configuration configuration) {
        TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
    }
}
