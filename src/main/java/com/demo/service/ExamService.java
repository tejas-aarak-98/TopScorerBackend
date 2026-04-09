package com.demo.service;

import java.util.List;

import com.demo.dto.LeaderboardDTO;
import com.demo.entity.Exam;
import com.demo.entity.Result;




public interface ExamService {

	String createExam(Exam exam, String name);

	List<LeaderboardDTO> getLeaderboard(int examId);

}
