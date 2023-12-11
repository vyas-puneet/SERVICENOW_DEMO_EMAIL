package com.servicenow.email.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.servicenow.email.dto.ErrorDTO;
import com.servicenow.email.service.EmailServiceInt;
import com.servicenow.email.service.ErrorServiceInt;

@Component
public class ErrorExecutionJob {

	@Autowired
	ErrorServiceInt errorServiceInt;

	@Autowired
	EmailServiceInt emailServiceInt;

	@Scheduled(fixedDelay = 120000)
	public void mailAfterLatestRecordInError() {

		List<ErrorDTO> listErr = errorServiceInt.getTopRecordInError();

		if (listErr.size() > 0) {

			for (ErrorDTO errorDTO : listErr) {

				String body = "<h3>Hi Team</h3> "
						+ "<h4>Please Check the Error of Data Migration in SQL Server to Cosmos DB.</h4>" + "<h4>Error :"
						+ "<h5 style='color:red'>" + errorDTO.getMessage() + "</h5></h4>" + "";

				emailServiceInt.sendMail("SQL to COSMOS DB Migration is Failed", body);
				errorServiceInt.updateFlag(errorDTO.getId());
			}
		}
	}
}
