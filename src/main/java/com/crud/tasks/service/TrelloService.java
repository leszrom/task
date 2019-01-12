package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.domain.trello.TrelloCardDtoRequest;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrelloService {
    private static final String SUBJECT = "Tasks: New Trello card";
    private TrelloClient trelloClient;
    private SimpleEmailService emailService;
    private AdminConfig adminConfig;

    @Autowired
    public TrelloService(TrelloClient trelloClient, SimpleEmailService emailService, AdminConfig adminConfig) {
        this.trelloClient = trelloClient;
        this.emailService = emailService;
        this.adminConfig = adminConfig;
    }

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public TrelloCardDto createTrelloCard(final TrelloCardDtoRequest trelloCardDtoRequest) {
        TrelloCardDto newCard = trelloClient.createNewCard(trelloCardDtoRequest);
        Optional.ofNullable(newCard)
                .ifPresent(card -> emailService.send(new Mail(adminConfig.getAdminMail(), "", SUBJECT,
                        "New card: " + trelloCardDtoRequest.getName() + " has been created on your Trello account")));
        return newCard;
    }
}
