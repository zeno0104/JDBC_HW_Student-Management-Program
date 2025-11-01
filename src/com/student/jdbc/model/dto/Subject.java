package com.student.jdbc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Subject {
	private String subjectCode;
	private String subjectName;
	private String major;
	private String professor;
	private int credit; // 학점
	private int openYear; // 개설년도
	private int semester; // 학기
	
	@Override
	public String toString() {
	    return String.format("%-10s  %-15s  %-6s  %-3d  %-8d  %-2d",
	            subjectCode, subjectName, professor, credit, openYear, semester);
	}

}
