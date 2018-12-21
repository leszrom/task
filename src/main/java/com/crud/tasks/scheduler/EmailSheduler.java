package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailSheduler {
    private static final String SUBJECT = "Tasks: Once a day email";
    private SimpleEmailService emailService;
    private TaskRepository taskRepository;
    private AdminConfig adminConfig;

    @Autowired
    public EmailSheduler(SimpleEmailService emailService, TaskRepository taskRepository, AdminConfig adminConfig) {
        this.emailService = emailService;
        this.taskRepository = taskRepository;
        this.adminConfig = adminConfig;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        emailService.send(new Mail(
                adminConfig.getAdminMail(),
                "",
                SUBJECT,
                "Currently in database you got: " + size + (size == 1 ? " task" : " tasks")));
    }
}
