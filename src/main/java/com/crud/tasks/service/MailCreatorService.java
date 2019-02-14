package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    private final TemplateEngine templateEngine;
    private final AdminConfig adminConfig;
    private final TaskRepository taskRepository;

    @Autowired
    public MailCreatorService(@Qualifier("templateEngine") TemplateEngine templateEngine, AdminConfig adminConfig, TaskRepository taskRepository) {
        this.templateEngine = templateEngine;
        this.adminConfig = adminConfig;
        this.taskRepository = taskRepository;
    }

    public String buildTrelloCardEmail(String message) {

        String templateName;
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        List<Task> tasks = taskRepository.findAll();

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit website");
        context.setVariable("goodbye_message", "This message was sent automatically. Do not reply.");
        context.setVariable("show_button", true);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        context.setVariable("tasks_list", tasks);

        if (message.contains("New card")) {
            templateName = "mail/created-trello-card-mail";
        } else {
            templateName = "mail/current-number-of-tasks";
        }
        return templateEngine.process(templateName, context);
    }
}
