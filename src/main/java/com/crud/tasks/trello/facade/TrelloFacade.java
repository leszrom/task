package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);
    private TrelloService trelloService;
    private TrelloMapper trelloMapper;

    @Autowired
    public TrelloFacade(TrelloService trelloService, TrelloMapper trelloMapper) {
        this.trelloService = trelloService;
        this.trelloMapper = trelloMapper;
    }

    public List<TrelloBoardDto> fetchTrelloBoards() {
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloService.fetchTrelloBoards());
        LOGGER.info("Starting filtering boards...");
        List<TrelloBoard> filteredBoards = trelloBoards.stream()
                .filter(board -> !board.getName().equalsIgnoreCase("test"))
                .collect(Collectors.toList());
        LOGGER.info("Board have been filtered. Current list size: " + filteredBoards.size());
        return trelloMapper.mapToBoardsDto(filteredBoards);
    }
}
