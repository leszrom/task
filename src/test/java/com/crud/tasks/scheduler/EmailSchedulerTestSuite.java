package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailSchedulerTestSuite {
    private static final String SUBJECT = "Tasks: Once a day email";
    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void should_return_Name() {
        //Given
        Mail mail = new Mail("test@test.com", "", SUBJECT, "Currently in database you got: 1 task");

        //When
        Mockito.when(taskRepository.count()).thenReturn((long) 1);
        Mockito.when(adminConfig.getAdminMail()).thenReturn("test@test.pl");

        emailScheduler.sendInformationEmail();

        //Then
        Mockito.verify(emailService).send(mail);
    }
}