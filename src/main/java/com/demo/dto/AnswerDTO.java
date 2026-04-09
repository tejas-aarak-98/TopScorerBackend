package com.demo.dto;

import lombok.Data;

@Data
public class AnswerDTO {

    private int questionId;   // Long hatao

    private String selectedOption;
	
}
