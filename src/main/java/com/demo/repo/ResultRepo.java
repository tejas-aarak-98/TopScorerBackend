package com.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Exam;
import com.demo.entity.Result;
import com.demo.entity.User;

@Repository
public interface ResultRepo extends JpaRepository<Result, Integer> {

	  List<Result> findByExamOrderByScoreDesc(Exam exam);

	    List<Result> findByUser(User user);

	    Optional<Result> findByUserAndExam(User user, Exam exam);

		List<Result> findByExamIdOrderByScoreDesc(int examId);
	
}
