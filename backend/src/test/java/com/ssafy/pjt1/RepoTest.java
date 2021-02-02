package com.ssafy.pjt1;

import com.ssafy.pjt1.model.mapper.MainMapper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepoTest {

    static final Logger log = LoggerFactory.getLogger(RepoTest.class);

    @Autowired
    MainMapper mainRepo;

    @Test
    public void selectTest() {
        int res = mainRepo.getSubsriptionNumber("10");
        
        log.info("결과값:{}", res);
    }
}
