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
	private String profName;
	private int credit; // 학점
	private int openYear; // 개설년도
	private int semester; // 학기

	
	public Subject(String subjectCode, String subjectName, String major, String professor, int credit, int openYear,
			int semester) {
		super();
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.major = major;
		this.professor = professor;
		this.credit = credit;
		this.openYear = openYear;
		this.semester = semester;
	}
	@Override
	public String toString() {
		return String.format("%-10s   %-15s   %-8s   %-4d   %-8d   %-3d", subjectCode, subjectName, professor, credit,
				openYear, semester);
	}

}
