package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Questions;
import com.demo.service.QuestionsService;


@RestController
@RequestMapping("topscorer/questions")
public class QuestionsController {

    @Autowired
    private QuestionsService questionsService ;

    @PostMapping("/add/{examId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String addQuestion(@PathVariable int examId,
                              @RequestBody Questions question) {

        return questionsService.addQuestion(examId, question);
    }
	
}
