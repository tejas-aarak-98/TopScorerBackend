package com.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Exam;

@Repository
public interface ExamRepo extends JpaRepository<Exam, Integer> {

}
