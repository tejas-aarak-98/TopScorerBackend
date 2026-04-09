package com.demo.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Questions;

@Repository
public interface QuestionsRepo extends JpaRepository<Questions, Integer> {

	
	List<Questions> findByExamId(int examId);


}
