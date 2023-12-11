package com.servicenow.email.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.servicenow.email.dto.ErrorDTO;

@Service
public class ErrorServiceImpl implements ErrorServiceInt {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<ErrorDTO> getTopRecordInError() {

		return jdbcTemplate.query("SELECT TOP 1 * FROM error WHERE FLAG = 'False' ORDER BY ID DESC",
				new BeanPropertyRowMapper(ErrorDTO.class));
	}

	@Override
	public void updateFlag(long id) {
		String sql = "UPDATE error SET flag = 'True' WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

}
