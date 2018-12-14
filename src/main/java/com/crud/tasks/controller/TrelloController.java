package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloCardDtoRequest;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {
    private TrelloClient trelloClient;

    @Autowired
    public TrelloController(TrelloClient trelloClient) {
        this.trelloClient = trelloClient;
    }

    @RequestMapping(method = RequestMethod.GET, value = "boards")
    public void getBoards() {
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.stream()
                .filter(boardDto -> boardDto.getId() != null && boardDto.getName().contains("Kodilla"))
                .forEach(boardDto -> {
                    System.out.println(boardDto.getId() + " " + boardDto.getName());
                    System.out.println("This board contains lists: ");
                    boardDto.getLists()
                            .forEach(listDto -> System.out.println(
                                    listDto.getId()
                                            + " - "
                                            + listDto.getName()
                                            + " - "
                                            + listDto.isClosed()
                            ));
                });
    }

    @RequestMapping(method = RequestMethod.POST, value = "cards/new")
    public TrelloCardDto createTrelloCard (@RequestBody TrelloCardDtoRequest trelloCardDtoRequest) {
        return trelloClient.createNewCard(trelloCardDtoRequest);
    }
}
