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
public class Student {
	private int stdNo;
	private String stdName;
	private int stdAge;
	private String major;
	private String entDate;
	
	@Override
	public String toString() {
		return String.format("%-8d   %-7s   %4d   %-10s   %s",
	            stdNo, stdName, stdAge, major, entDate);
	}
}
