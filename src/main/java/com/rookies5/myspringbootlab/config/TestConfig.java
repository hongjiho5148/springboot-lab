package com.rookies5.myspringbootlab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test") // 'test' 프로파일이 활성화될 때만 이 클래스가 읽힙니다.
public class TestConfig {

    @Bean
    public MyEnvironment myEnvironment() {
        return MyEnvironment.builder()
                .mode("개발환경")
                .build();
    }
}