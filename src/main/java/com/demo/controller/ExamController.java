package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.LeaderboardDTO;
import com.demo.entity.Exam;
import com.demo.entity.Result;
import com.demo.service.ExamService;

@RestController
@RequestMapping("topscorer/exam")
public class ExamController {

    @Autowired
    private ExamService examService;


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createExam(@RequestBody Exam exam,
                             Authentication authentication) {

        return examService.createExam(exam, authentication.getName());
    }
    
    @GetMapping("/{examId}/leaderboard")
    public List<LeaderboardDTO> getLeaderboard(@PathVariable int examId) {
        return examService.getLeaderboard(examId);
    }
}
