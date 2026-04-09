package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entity.Exam;
import com.demo.entity.Questions;
import com.demo.repo.ExamRepo;
import com.demo.repo.QuestionsRepo;

@Service
public class QuestionsServiceImpl implements QuestionsService{

    @Autowired
    private QuestionsRepo questionsRepo;

    @Autowired
    private ExamRepo examRepo;
	
	@Override
	public String addQuestion(int examId, Questions question) {
		
	     Exam exam = examRepo.findById(examId)
	                .orElseThrow(() -> new RuntimeException("Exam Not Found"));

	        question.setExam(exam);

	        questionsRepo.save(question);

	        return "Question Added Successfully";
	        
	}

}
