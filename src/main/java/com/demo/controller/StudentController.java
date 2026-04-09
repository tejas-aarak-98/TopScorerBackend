package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.AnswerDTO;
import com.demo.dto.QuestionStudentDTO;
import com.demo.dto.SubmitExamRequest;
import com.demo.entity.Exam;
import com.demo.entity.Questions;
import com.demo.entity.Result;
import com.demo.entity.User;
import com.demo.repo.ExamRepo;
import com.demo.repo.QuestionsRepo;
import com.demo.repo.ResultRepo;
import com.demo.repo.UserRepo;

@RestController
@RequestMapping("/topscorer/student")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

	@Autowired
	private UserRepo userRepo;

	
    @Autowired
    private ExamRepo examRepo;
    
    @Autowired
    private ResultRepo resultRepo;

    @Autowired
    private QuestionsRepo questionsRepo;

    // ✅ 1. Get All Exams
    @GetMapping("/exams")
    public List<Exam> getAllExams() {
        return examRepo.findAll();
    }

    // ✅ 2. Get Questions of Specific Exam (Without Correct Answer)
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/exam/{examId}")
    public List<QuestionStudentDTO> getExamQuestions(@PathVariable int examId) {

        List<Questions> questions = questionsRepo.findByExamId(examId);

        return questions.stream().map(q -> {
            QuestionStudentDTO dto = new QuestionStudentDTO();
            dto.setId(q.getId());
            dto.setQuestionText(q.getQuestionText());
            dto.setOptionA(q.getOptionA());
            dto.setOptionB(q.getOptionB());
            dto.setOptionC(q.getOptionC());
            dto.setOptionD(q.getOptionD());
            return dto;
        }).toList();
    }
    
    @PostMapping("/submit")
    public ResponseEntity<?> submitExam(@RequestBody SubmitExamRequest request) {

    	System.out.println(SecurityContextHolder
    	        .getContext()
    	        .getAuthentication());

    	
        int totalScore = 0;

        for (AnswerDTO ans : request.getAnswers()) {

            Questions question = questionsRepo
                    .findById(ans.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            if (question.getCorrectAnswer()
                    .equals(ans.getSelectedOption())) {

                totalScore += question.getMarks();
            }
        }
     

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepo.findByEmail(username);
    


        Exam exam = examRepo.findById(request.getExamId())
                .orElseThrow();
        
        if (resultRepo.findByUserAndExam(user, exam).isPresent()) {
        	Map<String,String> res = new HashMap<>();
        	res.put("message", "You have already attempted this exam");

        	return ResponseEntity.badRequest().body(res);
        }
        
        
        Result result = new Result();
        result.setScore(totalScore);
        result.setUser(user);
        result.setExam(exam);

        resultRepo.save(result);

        Map<String, Object> response = new HashMap<>();
        response.put("score", totalScore);
        response.put("message", "Exam submitted successfully");

        return ResponseEntity.ok(response);
    }

}








