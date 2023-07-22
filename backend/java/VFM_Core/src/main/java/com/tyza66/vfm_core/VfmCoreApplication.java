package com.tyza66.vfm_core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tyza66.vfm_core.mapper")
public class  VfmCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(VfmCoreApplication.class, args);
    }

}
