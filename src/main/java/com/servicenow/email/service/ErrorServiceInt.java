package com.servicenow.email.service;

import java.util.List;

import com.servicenow.email.dto.ErrorDTO;

public interface ErrorServiceInt {

	public List<ErrorDTO> getTopRecordInError();

	public void updateFlag(long id);

}
