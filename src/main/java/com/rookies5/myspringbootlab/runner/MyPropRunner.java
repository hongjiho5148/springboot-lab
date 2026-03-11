package com.rookies5.myspringbootlab.runner;

import com.rookies5.myspringbootlab.config.MyEnvironment;
import com.rookies5.myspringbootlab.property.MyPropProperties;
import com.rookies5.myspringbootlab.property.MyPropProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j // Logger 사용을 위한 어노테이션
public class MyPropRunner implements ApplicationRunner {

    private final MyPropProperties myPropProperties;
    private final MyEnvironment myEnvironment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 1. 중요한 정보는 INFO 레벨로 출력
        log.info("********** 환경 설정 정보 (INFO) **********");
        log.info("현재 모드: {}", myEnvironment.getMode());
        log.info("사용자 이름: {}", myPropProperties.getUsername());

        // 2. 상세한 디버깅 정보는 DEBUG 레벨로 출력
        log.debug("---------- 상세 디버깅 정보 (DEBUG) ----------");
        log.debug("포트 번호: {}", myPropProperties.getPort());
        log.debug("*******************************************");
    }
}