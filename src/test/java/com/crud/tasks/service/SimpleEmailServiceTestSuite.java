package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTestSuite {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MailCreatorService mailCreatorService;

    @Test
    public void should_send_mime_email() {
        //Given
        Mail mail = new Mail("test@test.com", "testCC@test.com", "Subject", "Test Message");

        Mockito.when(mailCreatorService.buildTrelloCardEmail("Test Message")).thenReturn("Build email");

        MimeMessagePreparator simpleMimeMessage = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo("test@test.com");
            messageHelper.setCc("testCC@test.com");
            messageHelper.setSubject("Subject");
            messageHelper.setText("Build email", true);
        };

        //When
        simpleEmailService.send(mail);

        //Then
        Mockito.verify(javaMailSender, Mockito.times(1)).send((MimeMessagePreparator) Matchers.any());
    }
}
