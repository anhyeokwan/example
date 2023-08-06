package com.example.example.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SampleJSONController {

    @GetMapping(value = "/helloArr")
    public String[] helloArr() {

        log.info("helloArr..................");
        return new String[]{"AAA", "BBB", "CCC"};

    }
}

