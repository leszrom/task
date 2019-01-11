package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.domain.trello.TrelloCardDtoRequest;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrelloFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);
    private TrelloService trelloService;
    private TrelloMapper trelloMapper;
    private TrelloValidator trelloValidator;

    @Autowired
    public TrelloFacade(TrelloService trelloService, TrelloMapper trelloMapper, TrelloValidator trelloValidator) {
        this.trelloService = trelloService;
        this.trelloMapper = trelloMapper;
        this.trelloValidator = trelloValidator;
    }

    public List<TrelloBoardDto> fetchTrelloBoards() {
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloService.fetchTrelloBoards());
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);
        return trelloMapper.mapToBoardsDto(filteredBoards);
    }

    public TrelloCardDto createCard(final TrelloCardDtoRequest trelloCardDtoRequest) {
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDtoRequest);
        trelloValidator.validCard(trelloCard);
        return trelloService.createTrelloCard(trelloMapper.mapToCardDtoRequest(trelloCard));
    }
}
