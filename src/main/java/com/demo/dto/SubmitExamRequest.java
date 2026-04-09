package com.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class SubmitExamRequest {

    private int examId;
    private List<AnswerDTO> answers;

	
}
