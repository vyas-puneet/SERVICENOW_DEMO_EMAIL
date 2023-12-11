package com.servicenow.email.dto;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ERROR")
public class ErrorDTO {

	@Transient
	private Long id;

	@Transient
	private String message;
}
