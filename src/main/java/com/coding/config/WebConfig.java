package com.coding.config;

import com.coding.common.LocalDateFormatter;
import com.coding.common.LocalDateTimeFormatter;
import com.coding.mybatis.IntegerCodeToEnumConverterFactory;
import com.coding.mybatis.StringCodeToEnumConverterFactory;
import com.coding.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * @author felix
 */
@Configuration
public class WebConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtil.getObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule());
    }

    @Bean
    public Jackson2JsonEncoder jackson2JsonEncoder(ObjectMapper mapper) {
        return new Jackson2JsonEncoder(mapper);
    }

    @Bean
    public Jackson2JsonDecoder jackson2JsonDecoder(ObjectMapper mapper) {
        return new Jackson2JsonDecoder(mapper);
    }

    @Bean
    public WebFluxConfigurer webFluxConfigurer(Jackson2JsonEncoder encoder, Jackson2JsonDecoder decoder) {
        return new WebFluxConfigurer() {
            @Override
            public void configureHttpMessageCodecs(@NotNull ServerCodecConfigurer configurer) {
                configurer.defaultCodecs().jackson2JsonEncoder(encoder);
                configurer.defaultCodecs().jackson2JsonDecoder(decoder);
            }

            @Override
            public void addFormatters(@NotNull FormatterRegistry registry) {
                
                registry.addConverterFactory(new IntegerCodeToEnumConverterFactory());
                registry.addConverterFactory(new StringCodeToEnumConverterFactory());

                registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
                registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
            }
        };

    }

}
