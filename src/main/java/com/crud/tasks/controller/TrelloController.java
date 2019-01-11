package com.crud.tasks.controller;

import com.crud.tasks.domain.trello.TrelloBoardDto;
import com.crud.tasks.domain.trello.TrelloCardDto;
import com.crud.tasks.domain.trello.TrelloCardDtoRequest;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {
    private TrelloFacade trelloFacade;

    @Autowired
    public TrelloController(TrelloFacade trelloFacade) {
        this.trelloFacade = trelloFacade;
    }

    @RequestMapping(method = RequestMethod.GET, value = "boards")
    public List<TrelloBoardDto> getBoards() {
        return trelloFacade.fetchTrelloBoards();
    }

    @RequestMapping(method = RequestMethod.POST, value = "cards/new")
    public TrelloCardDto createTrelloCard(@RequestBody TrelloCardDtoRequest trelloCardDtoRequest) {
        return trelloFacade.createCard(trelloCardDtoRequest);
    }
}
