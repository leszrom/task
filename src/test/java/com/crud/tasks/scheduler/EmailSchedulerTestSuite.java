package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailSchedulerTestSuite {
    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void should_contain_plural_form_TASKS_in_text_message() {
        //Given
        Mockito.when(taskRepository.count()).thenReturn(2L);
        Mockito.when(adminConfig.getAdminMail()).thenReturn("test@test.pl");

        ArgumentCaptor<Mail> emailCaptor = ArgumentCaptor.forClass(Mail.class);

        //When
        emailScheduler.sendInformationEmail();
        Mockito.verify(emailService).send(emailCaptor.capture());

        //Then
        Assert.assertTrue(emailCaptor.getValue().getMessage().endsWith("2 tasks"));
    }

    @Test
    public void should_contain_singular_form_TASK_in_text_message() {
        //Given
        Mockito.when(taskRepository.count()).thenReturn(1L);
        Mockito.when(adminConfig.getAdminMail()).thenReturn("test@test.pl");

        ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);

        //When
        emailScheduler.sendInformationEmail();
        Mockito.verify(emailService).send(argument.capture());

        //Then
        Assert.assertTrue(argument.getValue().getMessage().endsWith("1 task"));
    }
}
