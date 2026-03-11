package com.rookies5.myspringbootlab.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component // 스프링 빈으로 등록
@ConfigurationProperties(prefix = "myprop") // "myprop"으로 시작하는 설정값들을 매핑
@Getter
@Setter
public class MyPropProperties {
    // properties의 키값과 변수명이 일치해야 합니다. (myprop.username -> username)
    private String username;
    private int port;
}