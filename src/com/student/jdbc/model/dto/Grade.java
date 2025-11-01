package com.student.jdbc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
	private String gradeId;
	private String stdNo;
	private String subjectCode;
	private int score;
	private String grade;
	private int gpa;
	private String regDate;
	
}
