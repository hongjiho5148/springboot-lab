package com.rookies5.myspringbootlab.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor  // 기본 생성자 (Builder 사용 시 관례적으로 추가)
@AllArgsConstructor // 모든 필드 생성자 (Builder 사용 시 필수)
public class MyEnvironment {
    private String mode; 
}