package com.lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.lib.service","com.lib.mapper","com.lib.security","com.lib.repository",
        "com.lib.dto","com.lib.controller","com.lib.domain","com.lib.exception"})
public class LibraryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryAppApplication.class, args);
    }

}
