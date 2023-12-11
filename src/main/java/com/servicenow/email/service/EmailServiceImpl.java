package com.servicenow.email.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.communication.email.models.EmailSendStatus;
import com.azure.core.util.polling.LongRunningOperationStatus;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;

@Service
public class EmailServiceImpl implements EmailServiceInt {

	public static final Duration POLLER_WAIT_TIME = Duration.ofSeconds(10);

	@Value("${communication.service.connectionString}")
	String connectionString;

	@Value("${communication.service.senderAddress}")
	String senderAddress;

	@Value("${communication.service.recipientAddress}")
	String recipientAddress;

	@Override
	public void sendMail(String subject, String body) {

		EmailClient client = new EmailClientBuilder().connectionString(connectionString).buildClient();

		EmailMessage message = new EmailMessage().setSenderAddress(senderAddress).setToRecipients(recipientAddress)
				.setSubject(subject).setBodyHtml(body);

		try {
			SyncPoller<EmailSendResult, EmailSendResult> poller;
			try {

			} finally {
				poller = client.beginSend(message);
			}

			PollResponse<EmailSendResult> pollResponse = null;

			Duration timeElapsed = Duration.ofSeconds(0);

			while (pollResponse == null || pollResponse.getStatus() == LongRunningOperationStatus.NOT_STARTED
					|| pollResponse.getStatus() == LongRunningOperationStatus.IN_PROGRESS) {
				pollResponse = poller.poll();
				System.out.println("Email send poller status: " + pollResponse.getStatus());

				Thread.sleep(POLLER_WAIT_TIME.toMillis());
				timeElapsed = timeElapsed.plus(POLLER_WAIT_TIME);

				if (timeElapsed.compareTo(POLLER_WAIT_TIME.multipliedBy(18)) >= 0) {
					throw new RuntimeException("Polling timed out.");
				}
			}

			if (poller.getFinalResult().getStatus() == EmailSendStatus.SUCCEEDED) {
				System.out.printf("Successfully sent the email (operation id: %s)", poller.getFinalResult().getId());
			} else {
				throw new RuntimeException(poller.getFinalResult().getError().getMessage());
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

}
