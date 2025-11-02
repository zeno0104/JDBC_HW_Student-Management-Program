package com.student.jdbc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Professor {
	private String profId;
	private String profName;
	private String majorCode;
	private String position;
	private String email;
	private String phone;
}
