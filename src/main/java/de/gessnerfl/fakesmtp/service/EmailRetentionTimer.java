package de.gessnerfl.fakesmtp.service;

import de.gessnerfl.fakesmtp.config.FakeSmtpConfigurationProperties;
import de.gessnerfl.fakesmtp.repository.EmailRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailRetentionTimer {

    private final FakeSmtpConfigurationProperties fakeSmtpConfigurationProperties;
    private final EmailRepository emailRepository;
    private final Logger logger;

    @Autowired
    public EmailRetentionTimer(FakeSmtpConfigurationProperties fakeSmtpConfigurationProperties, EmailRepository emailRepository, Logger logger) {
        this.fakeSmtpConfigurationProperties = fakeSmtpConfigurationProperties;
        this.emailRepository = emailRepository;
        this.logger = logger;
    }

	@Scheduled(fixedDelayString = "${fakesmtp.persistence.emailDataRetentionTimer.fixedDelay:300000}", initialDelayString = "${fakesmtp.persistence.emailDataRetentionTimer.initialDelay:60000}")
	public void deleteOutdatedMails() {
		final var persistence = fakeSmtpConfigurationProperties.getPersistence();
		var maxNumber = persistence.getMaxNumberEmails();
		if (maxNumber > 0) {
			var count = emailRepository.deleteEmailsExceedingDateRetentionLimit(maxNumber);
			logger.info("Deleted {} emails which exceeded the maximum number {} of emails to be stored", count, maxNumber);
		}
	}
}
