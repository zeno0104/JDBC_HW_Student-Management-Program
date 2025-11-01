package com.student.jdbc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	private String stdNo;
	private String stdName;
	private int stdAge;
	private String major;
	private String entDate;
	private String status;
	private String subjectName;
	private String subjectCode;
	private int score;
	private String grade;
	private double gpa;
	
	@Override
	public String toString() {
	    return String.format("%-10s %-9s %-4d %-15s %-20s %-4s",
	            stdNo, stdName, stdAge, major, entDate, status);
	}
}
