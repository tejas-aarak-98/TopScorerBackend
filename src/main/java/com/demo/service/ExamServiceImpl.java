package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.LeaderboardDTO;
import com.demo.entity.Exam;
import com.demo.entity.Result;
import com.demo.repo.ExamRepo;
import com.demo.repo.ResultRepo;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ResultRepo resultRepo;
	
	@Autowired
	private ExamRepo examRepo;

	@Override
	  public String createExam(Exam exam, String name) {

        exam.setCreatedBy(name);   // ✔ correct variable
        examRepo.save(exam);       // ✔ correct repository name

        return "Exam Created Successfully";
    }
	
	@Override
	  public List<LeaderboardDTO> getLeaderboard(int examId) {

	        // 1️⃣ Fetch results sorted by score (highest first)
	        List<Result> results = resultRepo
	                .findByExamIdOrderByScoreDesc(examId);

	        // 2️⃣ Convert to DTO
	        List<LeaderboardDTO> leaderboard = new ArrayList<>();

	        int rank = 1;
	        int previousScore = -1;
	        int actualRank = 1;

	        for (Result result : results) {

	            if (previousScore != result.getScore()) {
	                actualRank = rank;
	            }

	            LeaderboardDTO dto = new LeaderboardDTO();
	            dto.setRank(actualRank);
	            dto.setUsername(result.getUser().getName());
	            dto.setScore(result.getScore());

	            leaderboard.add(dto);

	            previousScore = result.getScore();
	            rank++;
	        }

	        return leaderboard;
	    }




}
