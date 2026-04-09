package com.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Result {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private int score;

	    @ManyToOne
	    private User user;

	    @ManyToOne
	    private Exam exam;
	
}
