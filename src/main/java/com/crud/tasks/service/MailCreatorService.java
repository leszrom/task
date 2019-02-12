package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {
    private final TemplateEngine templateEngine;
    private final AdminConfig adminConfig;

    @Autowired
    public MailCreatorService(@Qualifier("templateEngine") TemplateEngine templateEngine, AdminConfig adminConfig) {
        this.templateEngine = templateEngine;
        this.adminConfig = adminConfig;
    }

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message", "This message was sent automatically. Do not reply.");
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("street", adminConfig.getStreet());
        context.setVariable("number", adminConfig.getNumber());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
