package com.ssafy.pjt1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/study")
public class StudyController {

    public static final Logger logger = LoggerFactory.getLogger(StudyController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    
}